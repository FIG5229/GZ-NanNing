/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.web;

import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcelNew;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelRecuperate;
import com.thinkgem.jeesite.modules.personnel.service.PersonnelRecuperateService;
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
 * 疗（休）养信息集Controller
 * @author cecil.li
 * @version 2019-11-09
 */
@Controller
@RequestMapping(value = "${adminPath}/personnel/personnelRecuperate")
public class PersonnelRecuperateController extends BaseController {

	@Autowired
	private PersonnelRecuperateService personnelRecuperateService;
	
	@ModelAttribute
	public PersonnelRecuperate get(@RequestParam(required=false) String id) {
		PersonnelRecuperate entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = personnelRecuperateService.get(id);
		}
		if (entity == null){
			entity = new PersonnelRecuperate();
		}
		return entity;
	}
	
	@RequiresPermissions("personnel:personnelRecuperate:view")
	@RequestMapping(value = {"list", ""})
	public String list(PersonnelRecuperate personnelRecuperate, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber"))){
			personnelRecuperate.setIdNumber(request.getParameter("idNumber").toString());
		}
		Page<PersonnelRecuperate> page = personnelRecuperateService.findPage(new Page<PersonnelRecuperate>(request, response), personnelRecuperate);
		if(request.getParameter("mType")!=null && !"".equals(request.getParameter("mType"))){
			request.setAttribute("mType",request.getParameter("mType").toString());
		}
		model.addAttribute("page", page);
		return "modules/personnel/personnelRecuperateList";
	}

	@RequiresPermissions("personnel:personnelRecuperate:view")
	@RequestMapping(value = "form")
	public String form(PersonnelRecuperate personnelRecuperate, Model model,HttpServletRequest request) {
		if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber"))){
			personnelRecuperate.setIdNumber(request.getParameter("idNumber").toString());
		}
		model.addAttribute("personnelRecuperate", personnelRecuperate);
		return "modules/personnel/personnelRecuperateForm";
	}

	@RequiresPermissions("personnel:personnelRecuperate:edit")
	@RequestMapping(value = "save")
	public String save(PersonnelRecuperate personnelRecuperate, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, personnelRecuperate)){
			return form(personnelRecuperate, model,request);
		}
		personnelRecuperateService.save(personnelRecuperate);
		addMessage(redirectAttributes, "保存疗（休）养信息集成功");
		request.setAttribute("saveResult","success");
		return "modules/personnel/personnelRecuperateForm";
	}
	
	@RequiresPermissions("personnel:personnelRecuperate:edit")
	@RequestMapping(value = "delete")
	public String delete(PersonnelRecuperate personnelRecuperate, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		String url =  "redirect:"+Global.getAdminPath()+"/personnel/personnelRecuperate/?repage&idNumber="+personnelRecuperate.getIdNumber();
		if(request.getParameter("mType")!=null && !"".equals(request.getParameter("mType"))){
			request.setAttribute("mType",request.getParameter("mType").toString());
			url = "redirect:"+Global.getAdminPath()+"/personnel/personnelRecuperate/?repage&mType="+request.getParameter("mType").toString();

		}
		personnelRecuperateService.delete(personnelRecuperate);
		addMessage(redirectAttributes, "删除疗（休）养信息集成功");
		return url;
	}
	@RequiresPermissions("personnel:personnelRecuperate:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(PersonnelRecuperate personnelRecuperate, Model model) {
		model.addAttribute("personnelRecuperate", personnelRecuperate);
		return "modules/personnel/personnelRecuperateFormDetail";
	}
	@ResponseBody
	@RequiresPermissions("personnel:personnelRecuperate:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			personnelRecuperateService.deleteByIds(ids);
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
	public String exportExcelByTemplate(PersonnelRecuperate personnelRecuperate, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes,boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<PersonnelRecuperate> page = null;
			if(flag == true){
				page = personnelRecuperateService.findPage(new Page<PersonnelRecuperate>(request, response), personnelRecuperate);
			}else{
				page = personnelRecuperateService.findPage(new Page<PersonnelRecuperate>(request, response,-1), personnelRecuperate);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, PersonnelRecuperate.class);
			exportExcelNew.setWb(wb);
			List<PersonnelRecuperate> list =page.getList();
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
		return "redirect:" + adminPath + "/personnel/personnelRecuperate/?repage";
	}
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<PersonnelRecuperate> list = ei.getDataList(PersonnelRecuperate.class);
			for (PersonnelRecuperate personnelRecuperate : list){
				try{
					BeanValidators.validateWithException(validator, personnelRecuperate);
					personnelRecuperateService.save(personnelRecuperate);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append(personnelRecuperate.getName()+"(身份证号码:"+personnelRecuperate.getIdNumber()+")"+" 导入失败："+ex.getMessage());
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