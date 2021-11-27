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
import com.thinkgem.jeesite.modules.affair.entity.PersonnelGoAbroad;
import com.thinkgem.jeesite.modules.affair.service.PersonnelGoAbroadService;
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
 * 领导干部出国管理表Controller
 * @author mason.xv
 * @version 2019-11-06
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/personnelGoAbroad")
public class PersonnelGoAbroadController extends BaseController {

	@Autowired
	private PersonnelGoAbroadService personnelGoAbroadService;
	
	@ModelAttribute
	public PersonnelGoAbroad get(@RequestParam(required=false) String id) {
		PersonnelGoAbroad entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = personnelGoAbroadService.get(id);
		}
		if (entity == null){
			entity = new PersonnelGoAbroad();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:personnelGoAbroad:view")
	@RequestMapping(value = {"list", ""})
	public String list(PersonnelGoAbroad personnelGoAbroad, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber"))){
			personnelGoAbroad.setIdNumber(request.getParameter("idNumber").toString());
		}
		Page<PersonnelGoAbroad> page = personnelGoAbroadService.findPage(new Page<PersonnelGoAbroad>(request, response), personnelGoAbroad); 
		model.addAttribute("page", page);
		if(request.getParameter("mType")!=null && !"".equals(request.getParameter("mType"))){
			request.setAttribute("mType",request.getParameter("mType").toString());
		}
		return "modules/affair/personnelGoAbroadList";
	}

	@RequiresPermissions("affair:personnelGoAbroad:view")
	@RequestMapping(value = "form")
	public String form(PersonnelGoAbroad personnelGoAbroad, Model model,HttpServletRequest request) {
		if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber"))){
			personnelGoAbroad.setIdNumber(request.getParameter("idNumber").toString());
		}
		model.addAttribute("personnelGoAbroad", personnelGoAbroad);
		return "modules/affair/personnelGoAbroadForm";
	}

	@RequiresPermissions("affair:personnelGoAbroad:edit")
	@RequestMapping(value = "save")
	public String save(PersonnelGoAbroad personnelGoAbroad, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, personnelGoAbroad)){
			return form(personnelGoAbroad, model,request);
		}
		personnelGoAbroadService.save(personnelGoAbroad);
		addMessage(redirectAttributes, "保存领导干部出国管理表成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/personnelGoAbroadForm";
	}
	
	@RequiresPermissions("affair:personnelGoAbroad:edit")
	@RequestMapping(value = "delete")
	public String delete(PersonnelGoAbroad personnelGoAbroad, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		String url ="redirect:"+Global.getAdminPath()+"/affair/personnelGoAbroad?repage&idNumber="+personnelGoAbroad.getIdNumber();

		if(request.getParameter("mType")!=null && !"".equals(request.getParameter("mType"))){
			request.setAttribute("mType",request.getParameter("mType").toString());
			url = "redirect:"+Global.getAdminPath()+"/affair/personnelGoAbroad/?repage&mType="+request.getParameter("mType").toString();
		}
		personnelGoAbroadService.delete(personnelGoAbroad);
		addMessage(redirectAttributes, "删除领导干部出国管理表成功");
        return  url;
	}

	@ResponseBody
	@RequiresPermissions("affair:personnelGoAbroad:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			personnelGoAbroadService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}
	@RequiresPermissions("affair:personnelGoAbroad:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(PersonnelGoAbroad personnelGoAbroad, Model model) {
		model.addAttribute("personnelGoAbroad", personnelGoAbroad);
		return "modules/affair/personnelGoAbroadFormDetail";
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
	public String exportExcelByTemplate(PersonnelGoAbroad personnelGoAbroad, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes,boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}

			Page<PersonnelGoAbroad> page = null;
			if(flag == true){
				page = personnelGoAbroadService.findPage(new Page<PersonnelGoAbroad>(request, response), personnelGoAbroad);
			}else{
				page = personnelGoAbroadService.findPage(new Page<PersonnelGoAbroad>(request, response,-1), personnelGoAbroad);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, PersonnelGoAbroad.class);
			exportExcelNew.setWb(wb);
			List<PersonnelGoAbroad> list =page.getList();
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
		return "redirect:" + adminPath + "/personnel/personnelGoAbroad?repage";
	}
	@RequestMapping(value = "import", method= RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		String isCover = request.getParameter("isCover");
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			//修改
			List<PersonnelGoAbroad> list = ei.getDataList(PersonnelGoAbroad.class);
			//选择覆盖模式，须先将改身份证下相关履历信息删除
			if(StringUtils.isNotBlank(isCover) && "1".equals(isCover)){
				List<String> idNumbers = new ArrayList<>();
				list.stream().forEach(personnelGoAbroad -> {
					if(personnelGoAbroad.getIdNumber()!=null){
						idNumbers.add(personnelGoAbroad.getIdNumber());
					}
				});
				if(idNumbers.size()>0&&idNumbers!=null)
					personnelGoAbroadService.deleteByIdNumbers(idNumbers);
			}
			for (PersonnelGoAbroad personnelGoAbroad : list){
				try{
					BeanValidators.validateWithException(validator, personnelGoAbroad);
					personnelGoAbroadService.save(personnelGoAbroad);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append(personnelGoAbroad.getName()+"(身份证号码:"+personnelGoAbroad.getIdNumber()+")"+" 导入失败："+ex.getMessage());
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
		return "redirect:" + adminPath + "/file/template/personnelBasesdownload/view?id=personnel_personnelGoAbroad&isCover="+isCover;
	}
}