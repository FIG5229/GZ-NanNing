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
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelSalaryPay;
import com.thinkgem.jeesite.modules.personnel.service.PersonnelSalaryPayService;
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
import java.util.ArrayList;
import java.util.List;

/**
 * 工资发放信息集Controller
 * @author cecil.li
 * @version 2019-11-11
 */
@Controller
@RequestMapping(value = "${adminPath}/personnel/personnelSalaryPay")
public class PersonnelSalaryPayController extends BaseController {

	@Autowired
	private PersonnelSalaryPayService personnelSalaryPayService;
	
	@ModelAttribute
	public PersonnelSalaryPay get(@RequestParam(required=false) String id) {
		PersonnelSalaryPay entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = personnelSalaryPayService.get(id);
		}
		if (entity == null){
			entity = new PersonnelSalaryPay();
		}
		return entity;
	}
	
	@RequiresPermissions("personnel:personnelSalaryPay:view")
	@RequestMapping(value = {"list", ""})
	public String list(PersonnelSalaryPay personnelSalaryPay, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber"))){
			personnelSalaryPay.setIdNumber(request.getParameter("idNumber").toString());
		}
		Page<PersonnelSalaryPay> page = personnelSalaryPayService.findPage(new Page<PersonnelSalaryPay>(request, response), personnelSalaryPay);
		if(request.getParameter("mType")!=null && !"".equals(request.getParameter("mType"))){
			request.setAttribute("mType",request.getParameter("mType").toString());
		}
		model.addAttribute("page", page);
		return "modules/personnel/personnelSalaryPayList";
	}

	@RequiresPermissions("personnel:personnelSalaryPay:view")
	@RequestMapping(value = "form")
	public String form(PersonnelSalaryPay personnelSalaryPay, Model model,HttpServletRequest request) {
		if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber"))){
			personnelSalaryPay.setIdNumber(request.getParameter("idNumber").toString());
		}
		model.addAttribute("personnelSalaryPay", personnelSalaryPay);
		return "modules/personnel/personnelSalaryPayForm";
	}

	@RequiresPermissions("personnel:personnelSalaryPay:edit")
	@RequestMapping(value = "save")
	public String save(PersonnelSalaryPay personnelSalaryPay, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, personnelSalaryPay)){
			return form(personnelSalaryPay, model,request);
		}
		personnelSalaryPayService.save(personnelSalaryPay);
		addMessage(redirectAttributes, "保存工资发放信息成功");
		request.setAttribute("saveResult","success");
		return "modules/personnel/personnelSalaryPayForm";
	}
	
	@RequiresPermissions("personnel:personnelSalaryPay:edit")
	@RequestMapping(value = "delete")
	public String delete(PersonnelSalaryPay personnelSalaryPay, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		personnelSalaryPayService.delete(personnelSalaryPay);
		addMessage(redirectAttributes, "删除工资发放信息成功");
		String url ="redirect:"+Global.getAdminPath()+"/personnel/personnelSalaryPay/?repage&idNumber="+personnelSalaryPay.getIdNumber();

		if(request.getParameter("mType")!=null && !"".equals(request.getParameter("mType"))){
			request.setAttribute("mType",request.getParameter("mType").toString());
			url = "redirect:"+Global.getAdminPath()+"/personnel/personnelSalaryPay/?repage&mType="+request.getParameter("mType").toString();

		}
		return  url;
	}

	@RequiresPermissions("personnel:personnelSalaryPay:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(PersonnelSalaryPay personnelSalaryPay, Model model) {
		model.addAttribute("personnelSalaryPay", personnelSalaryPay);
		return "modules/personnel/personnelSalaryPayFormDetail";
	}
	@ResponseBody
	@RequiresPermissions("personnel:personnelSalaryPay:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			personnelSalaryPayService.deleteByIds(ids);
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
	public String exportExcelByTemplate(PersonnelSalaryPay personnelSalaryPay, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<PersonnelSalaryPay> page = null;
			if(flag == true){
				page = personnelSalaryPayService.findPage(new Page<PersonnelSalaryPay>(request, response), personnelSalaryPay);
			}else {
				page = personnelSalaryPayService.findPage(new Page<PersonnelSalaryPay>(request, response,-1), personnelSalaryPay);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, PersonnelSalaryPay.class);
			exportExcelNew.setWb(wb);
			List<PersonnelSalaryPay> list =page.getList();
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
		return "redirect:" + adminPath + "/personnel/personnelSalaryPay/?repage";
	}
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		String isCover = request.getParameter("isCover");
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<PersonnelSalaryPay> list = ei.getDataList(PersonnelSalaryPay.class);
			//选择覆盖模式，须先将改身份证下相关履历信息删除
			if(StringUtils.isNotBlank(isCover) && "1".equals(isCover)){
				List<String> idNumbers = new ArrayList<>();
				list.stream().forEach(personnelResume -> {
					if(personnelResume.getIdNumber()!=null){
						idNumbers.add(personnelResume.getIdNumber());
					}
				});
				if(idNumbers.size()>0&&idNumbers!=null)
					personnelSalaryPayService.deleteByIdNumbers(idNumbers);
			}
			for (PersonnelSalaryPay personnelSalaryPay : list){
				try{
					BeanValidators.validateWithException(validator, personnelSalaryPay);
					personnelSalaryPayService.save(personnelSalaryPay);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append(personnelSalaryPay.getName()+"(身份证号码:"+personnelSalaryPay.getIdNumber()+")"+" 导入失败："+ex.getMessage());
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
		return "redirect:" + adminPath + "/file/template/personnelBasesdownload/view?id=personnel_personnelSalaryPay&isCover="+isCover;
	}
}