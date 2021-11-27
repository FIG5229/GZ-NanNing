/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcelNew;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.web.UploadController;
import com.thinkgem.jeesite.modules.affair.entity.AffairAssess;
import com.thinkgem.jeesite.modules.affair.service.AffairAssessService;
import com.thinkgem.jeesite.modules.affair.service.AffairGeneralSituationService;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 党委书记述职测评Controller
 *
 * @author eav.liu
 * @version 2019-11-11
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairAssess")
public class AffairAssessController extends BaseController {
    

    @Autowired
    private AffairAssessService affairAssessService;

    @Autowired
    private AffairGeneralSituationService affairGeneralSituationService;

    @Autowired
    private UploadController uploadController;

    @ModelAttribute
    public AffairAssess get(@RequestParam(required = false) String id) {
        AffairAssess entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = affairAssessService.get(id);
        }
        if (entity == null) {
            entity = new AffairAssess();
        }
        return entity;
    }

    @RequiresPermissions("affair:affairAssess:view")
    @RequestMapping(value = {""})
    public String index() {
        return "modules/affair/affairAssessIndex";
    }

    @RequiresPermissions("affair:affairAssess:view")
    @RequestMapping(value = {"list"})
    public String list(AffairAssess affairAssess, HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("treeId", affairAssess.getTreeId());
        model.addAttribute("parentId", request.getParameter("parentId"));
        Page<AffairAssess> page = affairAssessService.findPage(new Page<AffairAssess>(request, response), affairAssess);
        model.addAttribute("page", page);
        return "modules/affair/affairAssessList";
    }

    @RequiresPermissions("affair:affairAssess:view")
    @RequestMapping(value = "form")
    public String form(AffairAssess affairAssess, Model model) {
        model.addAttribute("affairAssess", affairAssess);
        return "modules/affair/affairAssessForm";
    }

    @RequiresPermissions("affair:affairAssess:edit")
    @RequestMapping(value = "save")
    public String save(AffairAssess affairAssess, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if (!beanValidator(model, affairAssess)) {
            return form(affairAssess, model);
        }
        affairAssess.setStatus("1");
        affairAssessService.save(affairAssess);

        addMessage(redirectAttributes, "保存党委书记述职测评成功");
        request.setAttribute("saveResult", "sucess");
        return "modules/affair/affairAssessForm";
    }

    @RequiresPermissions("affair:affairAssess:edit")
    @RequestMapping(value = "delete")
    public String delete(AffairAssess affairAssess, RedirectAttributes redirectAttributes) {
        affairAssessService.delete(affairAssess);
        addMessage(redirectAttributes, "删除党委书记述职测评成功");
        return "redirect:" + Global.getAdminPath() + "/affair/affairAssess/list?repage&treeId="+affairAssess.getTreeId();
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @ResponseBody
    @RequiresPermissions("affair:affairAssess:edit")
    @RequestMapping(value = {"deleteByIds"})
    public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
        Result result = new Result();
        if (ids != null && ids.size() > 0) {
            affairAssessService.deleteByIds(ids);
            result.setSuccess(true);
            result.setMessage("删除成功");
        } else {
            result.setSuccess(false);
            result.setMessage("请先选择要删除的内容");
        }
        return result;
    }

    /**
     * 详情
     *
     * @param affairAssess
     * @param model
     * @return
     */
    @RequiresPermissions("affair:affairAssess:view")
    @RequestMapping(value = "formDetail")
    public String formDetail(AffairAssess affairAssess, Model model) {
        model.addAttribute("affairAssess", affairAssess);
        if (affairAssess.getFilePath() != null && affairAssess.getFilePath().length() > 0){
            List<Map<String, String>> filePathList = uploadController.filePathHandle(affairAssess.getFilePath());
            model.addAttribute("filePathList", filePathList);
        }
        return "modules/affair/affairAssessFormDetail";
    }

    /**
     * 导出excel格式数据
     *
     * @param
     * @param request
     * @param response
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "export", method = RequestMethod.POST)
    public String exportExcelByTemplate(AffairAssess affairAssess, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
        XSSFWorkbook wb = null;
        try {
            String fileName = "";
            if (request.getParameter("fileName") != null && !"".equals(request.getParameter("fileName"))) {
                fileName = request.getParameter("fileName").toString();
            }

            Page<AffairAssess> page = null;
            if (flag == true) {
                page = affairAssessService.findPage(new Page<AffairAssess>(request, response), affairAssess);
            } else {
                page = affairAssessService.findPage(new Page<AffairAssess>(request, response, -1), affairAssess);
            }

//            String filePath = Global.getUserfilesBaseDir() + "\\userfiles\\template\\";
            String fileSeperator = File.separator;
            String filePath= Global.getUserfilesBaseDir()+fileSeperator+"userfiles"+fileSeperator+"template"+fileSeperator;
            InputStream inputStream = new FileInputStream(filePath + fileName);
            if (null != inputStream) {
                try {
                    wb = new XSSFWorkbook(inputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairAssess.class);
            exportExcelNew.setWb(wb);
            List<AffairAssess> list = page.getList();
            exportExcelNew.setDataList(list);
            HSSFFormulaEvaluator.evaluateAllFormulaCells(wb);
            response.reset();
            response.setContentType("application/octet-stream; charset=utf-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + Encodes.urlEncode(fileName));
            ServletOutputStream fout = response.getOutputStream();
            wb.write(fout);
            fout.close();
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            addMessage(redirectAttributes, "导出用户失败！失败信息：" + ex);
        }
        return "redirect:" + adminPath + "/affair/affairAssess/list?repage&treeId="+affairAssess.getTreeId();
    }

    /**
     * 导入excel数据
     *
     * @param file
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "import", method = RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcel ei = new ImportExcel(file, 0, 0);
            List<AffairAssess> list = ei.getDataList(AffairAssess.class);
            for (AffairAssess affairAssess : list) {
                try {
                    //党支部要绑定党支部id
                    String partyOrganizationId = affairGeneralSituationService.findByName(affairAssess.getPartyOrganization());
                    affairAssess.setPartyOrganizationId(partyOrganizationId);
                    BeanValidators.validateWithException(validator, affairAssess);
                    affairAssessService.save(affairAssess);
                    successNum++;
                } catch (ConstraintViolationException ex) {
                    List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
                    for (String message : messageList) {
                        failureMsg.append(message + "; ");
                        failureNum++;
                    }
                } catch (Exception ex) {
                    failureMsg.append(affairAssess.getShuji() + "(年度:" + affairAssess.getYear() + " 身份证号码:" + affairAssess.getShujiIdNo() + ")" + " 导入失败：" + ex.getMessage());
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条，导入信息如下：");
            }
            addMessage(redirectAttributes, "已成功导入 " + successNum + " 条" + failureMsg);
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入失败！失败信息：" + e.getMessage());
        }
        redirectAttributes.addFlashAttribute("result", "success");
        return "redirect:" + adminPath + "/file/template/download/view";
    }

    @RequestMapping("assessLevelDetail")
    public String assessLevelDetail(AffairAssess affairAssess, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<AffairAssess> page = affairAssessService.findAssessLevelPage(new Page<AffairAssess>(request, response), affairAssess);
        model.addAttribute("page", page);
        return "modules/charts/chartAssessList";
    }

    /**
     * 上报
     * @param affairAssess
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("affair:affairAssess:edit")
    @RequestMapping(value = "report")
    public String report(AffairAssess affairAssess, RedirectAttributes redirectAttributes) {
        String treeId = affairAssess.getTreeId();
        affairAssess.setStatus("2");
        affairAssessService.save(affairAssess);
        addMessage(redirectAttributes, "上报成功");
        return "redirect:" + adminPath + "/affair/affairAssess/list?repage&treeId="+affairAssess.getTreeId();
    }

    @RequiresPermissions("affair:affairAssess:edit")
    @RequestMapping(value = {"shenHeDialog"})
    public String shenHeDialog() {
        return "modules/affair/affairAssessCheckDialog";
    }

    @ResponseBody
    @RequiresPermissions("affair:affairAssess:edit")
    @RequestMapping(value = {"shenHeSave"})
    public Result shenHeSave(AffairAssess affairAssess) {
        affairAssessService.shenHeSave(affairAssess);
        Result result = new Result();
        result.setSuccess(true);
        result.setMessage("审核成功");
        return result;
    }
}