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
import com.thinkgem.jeesite.modules.affair.entity.AffairSendTeacher;
import com.thinkgem.jeesite.modules.affair.service.AffairSendTeacherService;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
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
 * 送教上门Controller
 * @author jack.xu
 * @version 2020-07-20
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairSendTeacher")
public class AffairSendTeacherController extends BaseController {

	@Autowired
	private AffairSendTeacherService affairSendTeacherService;
	
	@Autowired
    private OfficeService officeService;
	
	@ModelAttribute
	public AffairSendTeacher get(@RequestParam(required=false) String id) {
		AffairSendTeacher entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairSendTeacherService.get(id);
		}
		if (entity == null){
			entity = new AffairSendTeacher();
		}
		return entity;
	}

    @RequiresPermissions("affair:affairSendTeacher:view")
    @RequestMapping(value = {""})
    public String index(Office office, Model model) {
        return "modules/affair/affairSendTeacherIndex";
    }
	
	@RequiresPermissions("affair:affairSendTeacher:view")
	@RequestMapping(value = {"list"})
	public String list(AffairSendTeacher affairSendTeacher, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairSendTeacher> page = affairSendTeacherService.findPage(new Page<AffairSendTeacher>(request, response), affairSendTeacher); 
		model.addAttribute("page", page);
        model.addAttribute("unit",affairSendTeacher.getUnit());
        model.addAttribute("unitId",affairSendTeacher.getUnitId());
        return "modules/affair/affairSendTeacherList";
	}

	@RequiresPermissions("affair:affairSendTeacher:view")
	@RequestMapping(value = "form")
	public String form(AffairSendTeacher affairSendTeacher, Model model) {
		model.addAttribute("affairSendTeacher", affairSendTeacher);
		return "modules/affair/affairSendTeacherForm";
	}

	@RequiresPermissions("affair:affairSendTeacher:edit")
	@RequestMapping(value = "save")
	public String save(AffairSendTeacher affairSendTeacher, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairSendTeacher)){
			return form(affairSendTeacher, model);
		}
		affairSendTeacherService.save(affairSendTeacher);
		addMessage(redirectAttributes, "保存送教上门成功");
		request.setAttribute("saveResult","success");
        return "modules/affair/affairSendTeacherForm";
	}
	
	@RequiresPermissions("affair:affairSendTeacher:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairSendTeacher affairSendTeacher, RedirectAttributes redirectAttributes,String unitId) {
		affairSendTeacherService.delete(affairSendTeacher);
		addMessage(redirectAttributes, "删除送教上门成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairSendTeacher/list/?unitId="+unitId;
	}

    @ResponseBody
    @RequiresPermissions("affair:affairSendTeacher:edit")
    @RequestMapping(value = {"deleteByIds"})
    public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
        Result result = new Result();
        if(ids != null && ids.size() > 0){
            affairSendTeacherService.deleteByIds(ids);
            result.setSuccess(true);
            result.setMessage("删除成功");
        }else{
            result.setSuccess(false);
            result.setMessage("请先选择要删除的内容");
        }
        return result;
    }

    @RequiresPermissions("affair:affairSendTeacher:view")
    @RequestMapping(value = "formDetail")
    public String formDetail(AffairSendTeacher affairSendTeacher, Model model) {
        model.addAttribute("affairSendTeacher", affairSendTeacher);
        return "modules/affair/affairSendTeacherFormDetail";
    }

    /**
     * 导出excel格式数据
     * @param affairSendTeacher
     * @param request
     * @param response
     * @param redirectAttributes
     * @param flag
     * @return
     */
    @RequestMapping(value = "export", method = RequestMethod.POST)
    public String exportExcelByTemplate(AffairSendTeacher affairSendTeacher, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {

        XSSFWorkbook wb = null;
        try {

            affairSendTeacher.setCreateBy(UserUtils.getUser());
            String fileName = "";
            if (request.getParameter("fileName") != null && !"".equals(request.getParameter("fileName"))) {
                fileName = request.getParameter("fileName").toString();
            }
            Page<AffairSendTeacher> page = null;
            if (flag == true) {
                page = affairSendTeacherService.findPage(new Page<AffairSendTeacher>(request, response), affairSendTeacher);
            } else {
                page = affairSendTeacherService.findPage(new Page<AffairSendTeacher>(request, response, -1), affairSendTeacher);
            }

            String fileSeperator = File.separator;
            String filePath = Global.getUserfilesBaseDir() + fileSeperator + "userfiles" + fileSeperator + "template" + fileSeperator;
            InputStream inputStream = new FileInputStream(filePath + fileName);
            if (null != inputStream) {
                try {
                    wb = new XSSFWorkbook(inputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairSendTeacher.class);
            exportExcelNew.setWb(wb);
            List<AffairSendTeacher> list = page.getList();
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
        return "redirect:" + adminPath + "/affair/affairSendTeacher?repage";
    }

    /**
     * 导入excel数据
     * @param file
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "import", method= RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcel ei = new ImportExcel(file, 0, 0);
            List<AffairSendTeacher> list = ei.getDataList(AffairSendTeacher.class);
            for (AffairSendTeacher thoughtAnalysis : list){
                try{
                    //单位绑定单位id
                    String orgId = officeService.findByName(thoughtAnalysis.getUnit());
                    if(orgId != null){
                        thoughtAnalysis.setUnitId(orgId);
                    }
                    BeanValidators.validateWithException(validator, thoughtAnalysis);
                    affairSendTeacherService.save(thoughtAnalysis);
                    successNum++;
                }catch(ConstraintViolationException ex){
                    List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
                    for (String message : messageList){
                        failureMsg.append(message+"; ");
                        failureNum++;
                    }
                }catch (Exception ex) {
                    failureMsg.append(" 导入失败："+ex.getMessage());
                }
            }
            if (failureNum>0){
                failureMsg.insert(0, "，失败 "+failureNum+" 条，导入信息如下：");
            }
            addMessage(redirectAttributes, "已成功导入 "+successNum+" 条"+failureMsg);
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入失败！失败信息："+e.getMessage());
        }
        redirectAttributes.addFlashAttribute("result","success");
        return "redirect:" + adminPath + "/file/template/download/view";
    }

}