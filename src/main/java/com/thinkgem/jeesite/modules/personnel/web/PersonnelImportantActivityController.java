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
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelImportantActivity;
import com.thinkgem.jeesite.modules.personnel.service.PersonnelImportantActivityService;
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
 * 参与重要活动信息集Controller
 * @author cecil.li
 * @version 2019-11-13
 */
@Controller
@RequestMapping(value = "${adminPath}/personnel/personnelImportantActivity")
public class PersonnelImportantActivityController extends BaseController {

	@Autowired
	private PersonnelImportantActivityService personnelImportantActivityService;
	
	@ModelAttribute
	public PersonnelImportantActivity get(@RequestParam(required=false) String id) {
		PersonnelImportantActivity entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = personnelImportantActivityService.get(id);
		}
		if (entity == null){
			entity = new PersonnelImportantActivity();
		}
		return entity;
	}
	
	@RequiresPermissions("personnel:personnelImportantActivity:view")
	@RequestMapping(value = {"list", ""})
	public String list(PersonnelImportantActivity personnelImportantActivity, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber"))){
			personnelImportantActivity.setIdNumber(request.getParameter("idNumber").toString());
		}
		Page<PersonnelImportantActivity> page = personnelImportantActivityService.findPage(new Page<PersonnelImportantActivity>(request, response), personnelImportantActivity);
		if(request.getParameter("mType")!=null && !"".equals(request.getParameter("mType"))){
			request.setAttribute("mType",request.getParameter("mType").toString());
		}
		model.addAttribute("page", page);
		return "modules/personnel/personnelImportantActivityList";
	}

	@RequiresPermissions("personnel:personnelImportantActivity:view")
	@RequestMapping(value = "form")
	public String form(PersonnelImportantActivity personnelImportantActivity, Model model, HttpServletRequest request) {
		model.addAttribute("personnelImportantActivity", personnelImportantActivity);
		if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber"))){
			personnelImportantActivity.setIdNumber(request.getParameter("idNumber").toString());
		}
		return "modules/personnel/personnelImportantActivityForm";
	}

	@RequiresPermissions("personnel:personnelImportantActivity:edit")
	@RequestMapping(value = "save")
	public String save(PersonnelImportantActivity personnelImportantActivity, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, personnelImportantActivity)){
			return form(personnelImportantActivity, model, request);
		}
		personnelImportantActivityService.save(personnelImportantActivity);
		addMessage(redirectAttributes, "保存参与重要活动信息成功");
		request.setAttribute("saveResult","success");
		return "modules/personnel/personnelImportantActivityForm";
	}
	
	@RequiresPermissions("personnel:personnelImportantActivity:edit")
	@RequestMapping(value = "delete")
	public String delete(PersonnelImportantActivity personnelImportantActivity, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		String url ="redirect:"+Global.getAdminPath()+"/personnel/personnelImportantActivity/?repage&idNumber="+personnelImportantActivity.getIdNumber();

		if(request.getParameter("mType")!=null && !"".equals(request.getParameter("mType"))){
			request.setAttribute("mType",request.getParameter("mType").toString());
			url = "redirect:"+Global.getAdminPath()+"/personnel/personnelImportantActivity/?repage&mType="+request.getParameter("mType").toString();

		}
		personnelImportantActivityService.delete(personnelImportantActivity);
		addMessage(redirectAttributes, "删除参与重要活动信息成功");
        return url;
	}

	@ResponseBody
	@RequiresPermissions("personnel:personnelImportantActivity:view")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids){
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			personnelImportantActivityService.deleteByIds(ids);
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
	public String exportExcelByTemplate(PersonnelImportantActivity personnelImportantActivity, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes,boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<PersonnelImportantActivity> page = null;
			if(flag == true){
				page = personnelImportantActivityService.findPage(new Page<PersonnelImportantActivity>(request, response), personnelImportantActivity);
			}else{
				page = personnelImportantActivityService.findPage(new Page<PersonnelImportantActivity>(request, response,-1), personnelImportantActivity);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, PersonnelImportantActivity.class);
			exportExcelNew.setWb(wb);
			List<PersonnelImportantActivity> list =page.getList();
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
		return "redirect:" + adminPath + "/personnel/personnelImportantActivity?repage";
	}
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(HttpServletRequest request,MultipartFile file, RedirectAttributes redirectAttributes) {
		String isCover = request.getParameter("isCover");

		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<PersonnelImportantActivity> list = ei.getDataList(PersonnelImportantActivity.class);
			//选择覆盖模式，须先将改身份证下相关履历信息删除
			if(StringUtils.isNotBlank(isCover) && "1".equals(isCover)){
				List<String> idNumbers = new ArrayList<>();
				list.stream().forEach(personnelResume -> {
					if(personnelResume.getIdNumber()!=null){
						idNumbers.add(personnelResume.getIdNumber());
					}
				});
				if(idNumbers.size()>0&&idNumbers!=null)
					personnelImportantActivityService.deleteByIdNumbers(idNumbers);
			}
			for (PersonnelImportantActivity personnelImportantActivity : list){
				try{
					BeanValidators.validateWithException(validator, personnelImportantActivity);
					personnelImportantActivityService.save(personnelImportantActivity);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("(身份证号码:"+personnelImportantActivity.getIdNumber()+")"+" 导入失败："+ex.getMessage());
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

/*
		return "redirect:" + adminPath + "/file/template/personnelImportantActivity/view?id=personnel_personnelImportantActivity&isCover="+isCover;
*/
	}
}