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
import com.thinkgem.jeesite.modules.affair.entity.AffairActivist;
import com.thinkgem.jeesite.modules.affair.entity.AffairApplicant;
import com.thinkgem.jeesite.modules.affair.service.AffairActivistService;
import com.thinkgem.jeesite.modules.affair.service.AffairApplicantService;
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

/**
 * 入党申请人表Controller
 *
 * @author eav.liu
 * @version 2020-03-12
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairApplicant")
public class AffairApplicantController extends BaseController {

    @Autowired
    private AffairApplicantService affairApplicantService;

    @Autowired
    private AffairGeneralSituationService affairGeneralSituationService;

    @Autowired
    private AffairActivistService affairActivistService;

    @ModelAttribute
    public AffairApplicant get(@RequestParam(required = false) String id) {
        AffairApplicant entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = affairApplicantService.get(id);
        }
        if (entity == null) {
            entity = new AffairApplicant();
        }
        return entity;
    }

    @RequiresPermissions("affair:affairApplicant:view")
    @RequestMapping(value = {""})
    public String index() {
        return "modules/affair/affairApplicantIndex";
    }

    @RequiresPermissions("affair:affairApplicant:view")
    @RequestMapping(value = {"list"})
    public String list(AffairApplicant affairApplicant, HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("treeId", affairApplicant.getTreeId());
        Page<AffairApplicant> page = affairApplicantService.findPage(new Page<AffairApplicant>(request, response), affairApplicant);
        model.addAttribute("page", page);
        return "modules/affair/affairApplicantList";
    }

    @RequiresPermissions("affair:affairApplicant:view")
    @RequestMapping(value = "form")
    public String form(AffairApplicant affairApplicant, Model model) {
        model.addAttribute("affairApplicant", affairApplicant);
        return "modules/affair/affairApplicantForm";
    }

    @RequiresPermissions("affair:affairApplicant:edit")
    @RequestMapping(value = "save")
    public String save(AffairApplicant affairApplicant, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if (!beanValidator(model, affairApplicant)) {
            return form(affairApplicant, model);
        }
        affairApplicantService.save(affairApplicant);
        addMessage(redirectAttributes, "保存入党申请人成功");
        request.setAttribute("saveResult", "sucess");
        return "modules/affair/affairApplicantForm";
    }

    @RequiresPermissions("affair:affairApplicant:edit")
    @RequestMapping(value = "delete")
    public String delete(AffairApplicant affairApplicant, RedirectAttributes redirectAttributes) {
        affairApplicantService.delete(affairApplicant);
        addMessage(redirectAttributes, "删除入党申请人成功");
        return "redirect:" + Global.getAdminPath() + "/affair/affairApplicant/list?repage&treeId="+affairApplicant.getTreeId();
    }

    @RequestMapping(value = "push")
    public String push(AffairApplicant affairApplicant, RedirectAttributes redirectAttributes) {
        AffairActivist affairActivist = new AffairActivist();
        affairActivist.setName(affairApplicant.getName());
        affairActivist.setPoliceNo(affairApplicant.getPoliceNum());
        affairActivist.setSex(affairApplicant.getSex());
        affairActivist.setIdNumber(affairApplicant.getIdNumber());
        affairActivist.setPartyBranch(affairApplicant.getPartyBranch());
        affairActivist.setPartyBranchId(affairApplicant.getPartyBranchId());
        affairActivist.setApprovalDate(affairApplicant.getEnterDate());
        affairActivist.setStatus("0");
        affairActivistService.save(affairActivist);
        affairApplicant.setStatus("1");
        affairApplicantService.save(affairApplicant);
        addMessage(redirectAttributes, "推送至入党积极分子成功");
        return "redirect:" + Global.getAdminPath() + "/affair/affairApplicant/list?repage&treeId="+affairApplicant.getTreeId();
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @ResponseBody
    @RequiresPermissions("affair:affairApplicant:edit")
    @RequestMapping(value = {"deleteByIds"})
    public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
        Result result = new Result();
        if (ids != null && ids.size() > 0) {
            affairApplicantService.deleteByIds(ids);
            result.setSuccess(true);
            result.setMessage("删除成功");
        } else {
            result.setSuccess(false);
            result.setMessage("请先选择要删除的内容");
        }
        return result;
    }

    /**
     * form表单详情
     *
     * @param affairApplicant
     * @param model
     * @return
     */
    @RequiresPermissions("affair:affairApplicant:view")
    @RequestMapping(value = {"formDetail"})
    public String formDetail(AffairApplicant affairApplicant, Model model) {
        model.addAttribute("affairApplicant", affairApplicant);
        return "modules/affair/affairApplicantFormDetail";
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
    public String exportExcelByTemplate(AffairApplicant affairApplicant, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
        XSSFWorkbook wb = null;
        try {
            String fileName = "";
            if (request.getParameter("fileName") != null && !"".equals(request.getParameter("fileName"))) {
                fileName = request.getParameter("fileName").toString();
            }

            Page<AffairApplicant> page = null;
            if (flag == true) {
                page = affairApplicantService.findPage(new Page<AffairApplicant>(request, response), affairApplicant);
            } else {
                page = affairApplicantService.findPage(new Page<AffairApplicant>(request, response, -1), affairApplicant);
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
            ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairApplicant.class);
            exportExcelNew.setWb(wb);
            List<AffairApplicant> list = page.getList();
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
        return "redirect:" + adminPath + "/affair/affairApplicant/list?repage&treeId="+affairApplicant.getTreeId();
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
            List<AffairApplicant> list = ei.getDataList(AffairApplicant.class);
            for (AffairApplicant affairApplicant : list) {
                try {
                    if (StringUtils.isNotBlank(affairApplicant.getPartyBranch())) {
                        //党组织要绑定党组织id
                        String partyBranchId = affairGeneralSituationService.findByName(affairApplicant.getPartyBranch());
                        affairApplicant.setPartyBranchId(partyBranchId);
                        BeanValidators.validateWithException(validator, affairApplicant);
                        affairApplicantService.save(affairApplicant);
                        successNum++;
                    }

                } catch (ConstraintViolationException ex) {
                    List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
                    for (String message : messageList) {
                        failureMsg.append(message + "; ");
                        failureNum++;
                    }
                } catch (Exception ex) {
                    failureMsg.append(affairApplicant.getName() + "(身份证号码:" + affairApplicant.getIdNumber() + ")" + " 导入失败：" + ex.getMessage());
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
}