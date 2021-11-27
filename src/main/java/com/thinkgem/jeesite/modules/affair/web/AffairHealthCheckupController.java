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
import com.thinkgem.jeesite.modules.affair.entity.AffairHealthCheckup;
import com.thinkgem.jeesite.modules.affair.service.AffairHealthCheckupService;
import com.thinkgem.jeesite.modules.personnel.service.PersonnelBaseService;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
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
 * 健康体检Controller
 * @author mason.xv
 * @version 2020-03-23
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairHealthCheckup")
public class AffairHealthCheckupController extends BaseController {

	@Autowired
	private OfficeService officeService;
	@Autowired
	private AffairHealthCheckupService affairHealthCheckupService;

	@Autowired
	private PersonnelBaseService personnelBaseService;

	@ModelAttribute
	public AffairHealthCheckup get(@RequestParam(required=false) String id) {
		AffairHealthCheckup entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairHealthCheckupService.get(id);
		}
		if (entity == null){
			entity = new AffairHealthCheckup();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairHealthCheckup:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairHealthCheckup affairHealthCheckup, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairHealthCheckup> page = affairHealthCheckupService.findPage(new Page<AffairHealthCheckup>(request, response), affairHealthCheckup); 
		model.addAttribute("page", page);
		return "modules/affair/affairHealthCheckupList";
	}

	@RequiresPermissions("affair:affairHealthCheckup:view")
	@RequestMapping(value = "form")
	public String form(AffairHealthCheckup affairHealthCheckup, Model model) {
		model.addAttribute("affairHealthCheckup", affairHealthCheckup);
		if (affairHealthCheckup.getType() != null && affairHealthCheckup.getType().length() >0) {
			String[] typeArr = affairHealthCheckup.getType().split(",");
			affairHealthCheckup.setTypeArr(typeArr);
		}
		return "modules/affair/affairHealthCheckupForm";
	}

	@RequiresPermissions("affair:affairHealthCheckup:edit")
	@RequestMapping(value = "save")
	public String save(AffairHealthCheckup affairHealthCheckup, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairHealthCheckup)){
			return form(affairHealthCheckup, model);
		}
		if(null != affairHealthCheckup.getTypeArr()){
			affairHealthCheckup.setType(StringUtils.join(affairHealthCheckup.getTypeArr(),","));
		}
		affairHealthCheckupService.save(affairHealthCheckup);
		addMessage(redirectAttributes, "保存健康体检成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairHealthCheckupForm";
	}
	
	@RequiresPermissions("affair:affairHealthCheckup:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairHealthCheckup affairHealthCheckup, RedirectAttributes redirectAttributes) {
		affairHealthCheckupService.delete(affairHealthCheckup);
		addMessage(redirectAttributes, "删除健康体检成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairHealthCheckup/?repage";
	}


	/**
	 * 详情
	 * @param affairHealthCheckup
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairHealthCheckup:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairHealthCheckup affairHealthCheckup, Model model) {
		model.addAttribute("affairHealthCheckup", affairHealthCheckup);
		return "modules/affair/affairHealthCheckupFormDetail";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairHealthCheckup:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairHealthCheckupService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}
	/**
	 * 导出excel格式数据
	 * @param
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "export", method= RequestMethod.POST)
	public String exportExcelByTemplate(AffairHealthCheckup affairHealthCheckup, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<AffairHealthCheckup> page = null;
			if(flag == true){
				page = affairHealthCheckupService.findPage(new Page<AffairHealthCheckup>(request, response), affairHealthCheckup);
			}else {
				page = affairHealthCheckupService.findPage(new Page<AffairHealthCheckup>(request, response,-1), affairHealthCheckup);
			}
/*
			Page<affairHealthCheckup> page = affairHealthCheckupService.findPage(new Page<affairHealthCheckup>(request, response,-1), affairHealthCheckup);
*/
			String fileSeperator = File.separator;
			String filePath= Global.getUserfilesBaseDir()+fileSeperator+"userfiles"+fileSeperator+"template"+fileSeperator;
			InputStream inputStream = new FileInputStream(filePath+fileName);
			if (null != inputStream)
			{
				try
				{
					wb = new XSSFWorkbook(inputStream);
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			//修改
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairHealthCheckup.class);
			exportExcelNew.setWb(wb);
			List<AffairHealthCheckup> list =page.getList();
			exportExcelNew.setDataList(list);
			HSSFFormulaEvaluator.evaluateAllFormulaCells(wb);
			response.reset();
			response.setContentType("application/octet-stream; charset=utf-8");
			response.setHeader("Content-Disposition", "attachment; filename="+ Encodes.urlEncode(fileName));
			ServletOutputStream fout = response.getOutputStream();
			wb.write(fout);
			fout.close();
			return null;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			addMessage(redirectAttributes, "导出用户失败！失败信息："+ex);
		}
		//修改
		return "redirect:" + adminPath + "/affair/affairHealthCheckup/?repage";
	}
	@RequestMapping(value = "import", method= RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			//修改
			List<AffairHealthCheckup> list = ei.getDataList(AffairHealthCheckup.class);
			for (AffairHealthCheckup affairHealthCheckup : list){
				try{
					if (affairHealthCheckup.getUnit()!= null&&!"".equals(affairHealthCheckup.getUnit())){
						affairHealthCheckup.setUnitId(officeService.findByName(affairHealthCheckup.getUnit()));
					}
					BeanValidators.validateWithException(validator, affairHealthCheckup);
					affairHealthCheckupService.save(affairHealthCheckup);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("(单位名称:"+affairHealthCheckup.getUnit()+")"+" 导入失败："+ex.getMessage());
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

	@RequestMapping("healthCheckupDetail")
	public String healthCheckupDetail(AffairHealthCheckup affairHealthCheckup,HttpServletRequest request,HttpServletResponse response,Model model){
		Page<AffairHealthCheckup> page = affairHealthCheckupService.findHealthCheckupPage(new Page<AffairHealthCheckup>(request,response),affairHealthCheckup);
		model.addAttribute("page", page);
		return "modules/charts/chartHealthCheckupList";
	}

	@RequestMapping("healthReferenceDetail")
	public String healthReferenceDetail(AffairHealthCheckup affairHealthCheckup,HttpServletRequest request,HttpServletResponse response,Model model){
		Page<AffairHealthCheckup> page = affairHealthCheckupService.findHealthReferencePage(new Page<AffairHealthCheckup>(request,response),affairHealthCheckup);
		model.addAttribute("page", page);
		return "modules/charts/chartHealthCheckupList";
	}
}