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
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelPoliceWork1;
import com.thinkgem.jeesite.modules.personnel.service.PersonnelPoliceWork1Service;
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
 * 警务技术(专业技术)职务信息集Controller
 * @author cecil.li
 * @version 2019-11-12
 */
@Controller
@RequestMapping(value = "${adminPath}/personnel/personnelPoliceWork1")
public class PersonnelPoliceWork1Controller extends BaseController {

	@Autowired
	private PersonnelPoliceWork1Service personnelPoliceWork1Service;
	
	@ModelAttribute
	public PersonnelPoliceWork1 get(@RequestParam(required=false) String id) {
		PersonnelPoliceWork1 entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = personnelPoliceWork1Service.get(id);
		}
		if (entity == null){
			entity = new PersonnelPoliceWork1();
		}
		return entity;
	}
	
	@RequiresPermissions("personnel:personnelPoliceWork1:view")
	@RequestMapping(value = {"list", ""})
	public String list(PersonnelPoliceWork1 personnelPoliceWork1, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber"))){
			personnelPoliceWork1.setIdNumber(request.getParameter("idNumber").toString());
		}
		Page<PersonnelPoliceWork1> page = personnelPoliceWork1Service.findPage(new Page<PersonnelPoliceWork1>(request, response), personnelPoliceWork1);
		if(request.getParameter("mType")!=null && !"".equals(request.getParameter("mType"))){
			request.setAttribute("mType",request.getParameter("mType").toString());
		}
		model.addAttribute("page", page);
		return "modules/personnel/personnelPoliceWork1List";
	}

	@RequiresPermissions("personnel:personnelPoliceWork1:view")
	@RequestMapping(value = "form")
	public String form(PersonnelPoliceWork1 personnelPoliceWork1, Model model, HttpServletRequest request) {
		model.addAttribute("personnelPoliceWork1", personnelPoliceWork1);
		if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber"))){
			personnelPoliceWork1.setIdNumber(request.getParameter("idNumber").toString());
		}
		return "modules/personnel/personnelPoliceWork1Form";
	}

	@RequiresPermissions("personnel:personnelPoliceWork1:edit")
	@RequestMapping(value = "save")
	public String save(PersonnelPoliceWork1 personnelPoliceWork1, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, personnelPoliceWork1)){
			return form(personnelPoliceWork1, model, request);
		}
		personnelPoliceWork1Service.save(personnelPoliceWork1);
		addMessage(redirectAttributes, "保存警务技术(专业技术)职务信息成功");
		request.setAttribute("saveResult","success");
		return "modules/personnel/personnelPoliceWork1Form";
	}
	
	@RequiresPermissions("personnel:personnelPoliceWork1:edit")
	@RequestMapping(value = "delete")
	public String delete(PersonnelPoliceWork1 personnelPoliceWork1, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		personnelPoliceWork1Service.delete(personnelPoliceWork1);
		String url = "redirect:"+Global.getAdminPath()+"/personnel/personnelPoliceWork1/?repage&idNumber="+personnelPoliceWork1.getIdNumber();

		if(request.getParameter("mType")!=null && !"".equals(request.getParameter("mType"))){
			request.setAttribute("mType",request.getParameter("mType").toString());
			url = "redirect:"+Global.getAdminPath()+"/personnel/personnelPoliceWork1/?repage&mType="+request.getParameter("mType").toString();

		}
		addMessage(redirectAttributes, "删除警务技术(专业技术)职务信息成功");
		return url;
	}

	/**
	 * 详情
	 * @param personnelPoliceWork1
	 * @param model
	 * @return
	 */
	@RequiresPermissions("personnel:personnelPoliceWork1:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(PersonnelPoliceWork1 personnelPoliceWork1, Model model) {
		model.addAttribute("personnelPoliceWork1", personnelPoliceWork1);
		return "modules/personnel/personnelPoliceWork1FormDetail";
	}

	@ResponseBody
	@RequiresPermissions("personnel:personnelPoliceWork1:view")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids){
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			personnelPoliceWork1Service.deleteByIds(ids);
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
	public String exportExcelByTemplate(PersonnelPoliceWork1 personnelPoliceWork1, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes,boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<PersonnelPoliceWork1> page = null;
			if(flag == true){
				page = personnelPoliceWork1Service.findPage(new Page<PersonnelPoliceWork1>(request, response), personnelPoliceWork1);
			}else{
				page = personnelPoliceWork1Service.findPage(new Page<PersonnelPoliceWork1>(request, response,-1), personnelPoliceWork1);
			}
			String fileSeperator = File.separator;
			String filePath= Global.getUserfilesBaseDir()+fileSeperator+"userfiles"+fileSeperator+"template"+fileSeperator;
			InputStream inputStream = new FileInputStream(filePath+fileName);
			if (null != inputStream)
			{
				try
				{
					wb = new  XSSFWorkbook(inputStream);
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, PersonnelPoliceWork1.class);
			exportExcelNew.setWb(wb);
			List<PersonnelPoliceWork1> list =page.getList();
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
		return "redirect:" + adminPath + "/personnel/personnelPoliceWork1?repage";
	}
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		String isCover = request.getParameter("isCover");
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<PersonnelPoliceWork1> list = ei.getDataList(PersonnelPoliceWork1.class);
			//选择覆盖模式，须先将改身份证下相关履历信息删除
			if(StringUtils.isNotBlank(isCover) && "1".equals(isCover)){
				List<String> idNumbers = new ArrayList<>();
				list.stream().forEach(personnelPoliceWork1 -> {
					if(personnelPoliceWork1.getIdNumber()!=null){
						idNumbers.add(personnelPoliceWork1.getIdNumber());
					}
				});
				if(idNumbers.size()>0&&idNumbers!=null)
					personnelPoliceWork1Service.deleteByIdNumbers(idNumbers);
			}
			for (PersonnelPoliceWork1 personnelPoliceWork1 : list){
				try{
					BeanValidators.validateWithException(validator, personnelPoliceWork1);
					personnelPoliceWork1Service.save(personnelPoliceWork1);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("(身份证号码:"+personnelPoliceWork1.getIdNumber()+")"+" 导入失败："+ex.getMessage());
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入失败！失败信息："+e.getMessage());
		}
//		redirectAttributes.addFlashAttribute("result","success");
//		return "redirect:" + adminPath + "/file/template/download/view";
		return "redirect:" + adminPath + "/file/template/personnelBasesdownload/view?id=personnel_personnelPoliceWork1&isCover="+isCover;
	}
}