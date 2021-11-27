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
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelAchievement;
import com.thinkgem.jeesite.modules.personnel.service.PersonnelAchievementService;
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
 * 专业技术工作及成果信息集Controller
 * @author cecil.li
 * @version 2019-11-11
 */
@Controller
@RequestMapping(value = "${adminPath}/personnel/personnelAchievement")
public class PersonnelAchievementController extends BaseController {

	@Autowired
	private PersonnelAchievementService personnelAchievementService;
	
	@ModelAttribute
	public PersonnelAchievement get(@RequestParam(required=false) String id) {
		PersonnelAchievement entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = personnelAchievementService.get(id);
		}
		if (entity == null){
			entity = new PersonnelAchievement();
		}
		return entity;
	}
	
	@RequiresPermissions("personnel:personnelAchievement:view")
	@RequestMapping(value = {"list", ""})
	public String list(PersonnelAchievement personnelAchievement, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber"))){
			personnelAchievement.setIdNumber(request.getParameter("idNumber").toString());
		}
		Page<PersonnelAchievement> page = personnelAchievementService.findPage(new Page<PersonnelAchievement>(request, response), personnelAchievement);
		if(request.getParameter("mType")!=null && !"".equals(request.getParameter("mType"))){
			request.setAttribute("mType",request.getParameter("mType").toString());
		}
		model.addAttribute("page", page);
		return "modules/personnel/personnelAchievementList";
	}

	@RequiresPermissions("personnel:personnelAchievement:view")
	@RequestMapping(value = "form")
	public String form(PersonnelAchievement personnelAchievement, Model model,HttpServletRequest request) {
		if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber"))){
			personnelAchievement.setIdNumber(request.getParameter("idNumber").toString());
		}
		model.addAttribute("personnelAchievement", personnelAchievement);
		return "modules/personnel/personnelAchievementForm";
	}

	@RequiresPermissions("personnel:personnelAchievement:edit")
	@RequestMapping(value = "save")
	public String save(PersonnelAchievement personnelAchievement, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, personnelAchievement)){
			return form(personnelAchievement, model,request);
		}
		personnelAchievementService.save(personnelAchievement);
		addMessage(redirectAttributes, "保存专业技术工作及成果信息成功");
		request.setAttribute("saveResult","success");
		return "modules/personnel/personnelAchievementForm";
	}
	
	@RequiresPermissions("personnel:personnelAchievement:edit")
	@RequestMapping(value = "delete")
	public String delete(PersonnelAchievement personnelAchievement, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		String url = "redirect:"+Global.getAdminPath()+"/personnel/personnelAchievement/?repage&idNumber="+personnelAchievement.getIdNumber();
		if(request.getParameter("mType")!=null && !"".equals(request.getParameter("mType"))){
			request.setAttribute("mType",request.getParameter("mType").toString());
			url = "redirect:"+Global.getAdminPath()+"/personnel/personnelAchievement/?repage&mType="+request.getParameter("mType").toString();
		}
		personnelAchievementService.delete(personnelAchievement);
		addMessage(redirectAttributes, "删除专业技术工作及成果信息成功");
		return url;
	}

	@RequiresPermissions("personnel:personnelAchievement:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(PersonnelAchievement personnelAchievement, Model model) {
		model.addAttribute("personnelAchievement", personnelAchievement);
		return "modules/personnel/personnelAchievementFormDetail";
	}
	@ResponseBody
	@RequiresPermissions("personnel:personnelAchievement:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			personnelAchievementService.deleteByIds(ids);
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
	public String exportExcelByTemplate(PersonnelAchievement personnelAchievement, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes,boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<PersonnelAchievement> page = null;
			if(flag == true){
				page = personnelAchievementService.findPage(new Page<PersonnelAchievement>(request, response), personnelAchievement);
			}else{
				page = personnelAchievementService.findPage(new Page<PersonnelAchievement>(request, response,-1), personnelAchievement);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, PersonnelAchievement.class);
			exportExcelNew.setWb(wb);
			List<PersonnelAchievement> list =page.getList();
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
		return "redirect:" + adminPath + "/personnel/personnelAchievement/?repage";
	}
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		String isCover = request.getParameter("isCover");
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			//修改
			List<PersonnelAchievement> list = ei.getDataList(PersonnelAchievement.class);
			if(StringUtils.isNotBlank(isCover) && "1".equals(isCover)){
				List<String> idNumbers = new ArrayList<>();
				list.stream().forEach(item -> {
					if(item.getIdNumber()!=null){
						idNumbers.add(item.getIdNumber());
					}
				});
				if(idNumbers.size()>0&&idNumbers!=null)
					personnelAchievementService.deleteByIdNumbers(idNumbers);
			}
			for (PersonnelAchievement personnelAchievement : list){
				try{
					BeanValidators.validateWithException(validator, personnelAchievement);
					personnelAchievementService.save(personnelAchievement);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("(身份证号码:"+personnelAchievement.getIdNumber()+")"+" 导入失败："+ex.getMessage());
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
		return "redirect:" + adminPath + "/file/template/personnelBasesdownload/view?id=personnel_personnelAchievement&isCover="+isCover;
	}
}