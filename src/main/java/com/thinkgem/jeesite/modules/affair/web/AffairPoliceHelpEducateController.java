/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.excel.ExportExcelNew;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.UploadController;
import com.thinkgem.jeesite.modules.affair.entity.AffairPoliceHelpEducate;
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

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.affair.entity.AffairPoliceHelpEducate;
import com.thinkgem.jeesite.modules.affair.service.AffairPoliceHelpEducateService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 民警帮教Controller
 * @author daniel.liu
 * @version 2020-05-14
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairPoliceHelpEducate")
public class AffairPoliceHelpEducateController extends BaseController {

	@Autowired
	private AffairPoliceHelpEducateService affairPoliceHelpEducateService;

	@Autowired
	private UploadController uploadController;

	@Autowired
	private OfficeService officeService;
	
	@ModelAttribute
	public AffairPoliceHelpEducate get(@RequestParam(required=false) String id) {
		AffairPoliceHelpEducate entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairPoliceHelpEducateService.get(id);
		}
		if (entity == null){
			entity = new AffairPoliceHelpEducate();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairPoliceHelpEducate:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairPoliceHelpEducate affairPoliceHelpEducate, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairPoliceHelpEducate> page = affairPoliceHelpEducateService.findPage(new Page<AffairPoliceHelpEducate>(request, response), affairPoliceHelpEducate); 
		model.addAttribute("page", page);
		return "modules/affair/affairPoliceHelpEducateList";
	}

	@RequiresPermissions("affair:affairPoliceHelpEducate:view")
	@RequestMapping(value = "form")
	public String form(AffairPoliceHelpEducate affairPoliceHelpEducate, Model model) {
		model.addAttribute("affairPoliceHelpEducate", affairPoliceHelpEducate);
		return "modules/affair/affairPoliceHelpEducateForm";
	}
	@RequiresPermissions("affair:affairPoliceHelpEducate:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairPoliceHelpEducate affairPoliceHelpEducate, Model model) {
		model.addAttribute("affairPoliceHelpEducate", affairPoliceHelpEducate);
		if(affairPoliceHelpEducate.getFilepath() != null && affairPoliceHelpEducate.getFilepath().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairPoliceHelpEducate.getFilepath());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairPoliceHelpEducateFormDetail";
	}

	@RequiresPermissions("affair:affairPoliceHelpEducate:edit")
	@RequestMapping(value = "save")
	public String save(AffairPoliceHelpEducate affairPoliceHelpEducate, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairPoliceHelpEducate)){
			return form(affairPoliceHelpEducate, model);
		}
		affairPoliceHelpEducateService.save(affairPoliceHelpEducate);
		addMessage(redirectAttributes, "保存民警帮教成功");
		request.setAttribute("saveResult","success");

		return "modules/affair/affairPoliceHelpEducateList";
	}
	
	@RequiresPermissions("affair:affairPoliceHelpEducate:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairPoliceHelpEducate affairPoliceHelpEducate, RedirectAttributes redirectAttributes) {
		affairPoliceHelpEducateService.delete(affairPoliceHelpEducate);
		addMessage(redirectAttributes, "删除民警帮教成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairPoliceHelpEducate/?repage";
	}
	@ResponseBody
	@RequiresPermissions("affair:AffairPoliceHelpEducate:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairPoliceHelpEducateService.deleteByIds(ids);
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
	public String exportExcelByTemplate(AffairPoliceHelpEducate helpEducate, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<AffairPoliceHelpEducate> page = null;
			if(flag == true){
				page = affairPoliceHelpEducateService.findPage(new Page<AffairPoliceHelpEducate>(request, response), helpEducate);
			}else {
				page = affairPoliceHelpEducateService.findPage(new Page<AffairPoliceHelpEducate>(request, response,-1), helpEducate);
			}
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairPoliceHelpEducate.class);
			exportExcelNew.setWb(wb);
			List<AffairPoliceHelpEducate> list =page.getList();
			for (AffairPoliceHelpEducate AffairPoliceHelpEducate:list){
				AffairPoliceHelpEducate.setContent(StringUtils.replaceHtml(AffairPoliceHelpEducate.getContent()));
			}
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
		return "redirect:" + adminPath + "/affair/AffairPoliceHelpEducate/?repage";
	}
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			//修改
			List<AffairPoliceHelpEducate> list = ei.getDataList(AffairPoliceHelpEducate.class);
			for (AffairPoliceHelpEducate helpEducate : list){
				try{
					//单位绑定单位id
					String orgId = officeService.findByName(helpEducate.getUnit());
					if(orgId != null){
						helpEducate.setUnitId(orgId);
					}
					BeanValidators.validateWithException(validator, helpEducate);
					affairPoliceHelpEducateService.save(helpEducate);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("(更新者:"+helpEducate.getUpdateBy().getLoginName()+")"+" 导入失败："+ex.getMessage());
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