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
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelPension;
import com.thinkgem.jeesite.modules.personnel.service.PersonnelPensionService;
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
 * 抚恤信息集Controller
 * @author cecil.li
 * @version 2019-11-13
 */
@Controller
@RequestMapping(value = "${adminPath}/personnel/personnelPension")
public class PersonnelPensionController extends BaseController {

	@Autowired
	private PersonnelPensionService personnelPensionService;
	
	@ModelAttribute
	public PersonnelPension get(@RequestParam(required=false) String id) {
		PersonnelPension entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = personnelPensionService.get(id);
		}
		if (entity == null){
			entity = new PersonnelPension();
		}
		return entity;
	}
	
	@RequiresPermissions("personnel:personnelPension:view")
	@RequestMapping(value = {"list", ""})
	public String list(PersonnelPension personnelPension, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber"))){
			personnelPension.setIdNumber(request.getParameter("idNumber").toString());
		}
		Page<PersonnelPension> page = personnelPensionService.findPage(new Page<PersonnelPension>(request, response), personnelPension);
		if(request.getParameter("mType")!=null && !"".equals(request.getParameter("mType"))){
			request.setAttribute("mType",request.getParameter("mType").toString());
		}
		model.addAttribute("page", page);
		return "modules/personnel/personnelPensionList";
	}

	@RequiresPermissions("personnel:personnelPension:view")
	@RequestMapping(value = "form")
	public String form(PersonnelPension personnelPension, Model model, HttpServletRequest request) {
		if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber"))){
			personnelPension.setIdNumber(request.getParameter("idNumber").toString());
		}
		model.addAttribute("personnelPension", personnelPension);
		return "modules/personnel/personnelPensionForm";
	}

	@RequiresPermissions("personnel:personnelPension:edit")
	@RequestMapping(value = "save")
	public String save(PersonnelPension personnelPension, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, personnelPension)){
			return form(personnelPension, model, request);
		}
		personnelPensionService.save(personnelPension);
		addMessage(redirectAttributes, "保存抚恤信息成功");
		request.setAttribute("saveResult","success");
		return "modules/personnel/personnelPensionForm";
	}
	
	@RequiresPermissions("personnel:personnelPension:edit")
	@RequestMapping(value = "delete")
	public String delete(PersonnelPension personnelPension, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		personnelPensionService.delete(personnelPension);
		String url =  "redirect:"+Global.getAdminPath()+"/personnel/personnelPension/?repage&idNumber="+personnelPension.getIdNumber();

		if(request.getParameter("mType")!=null && !"".equals(request.getParameter("mType"))){
			request.setAttribute("mType",request.getParameter("mType").toString());
			url = "redirect:"+Global.getAdminPath()+"/personnel/personnelPension/?repage&mType="+request.getParameter("mType").toString();

		}
		addMessage(redirectAttributes, "删除抚恤信息成功");
		return url;
	}

	/**
	 * 详情
	 * @param personnelPension
	 * @param model
	 * @return
	 */
	@RequiresPermissions("personnel:personnelPension:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(PersonnelPension personnelPension, Model model) {
		model.addAttribute("personnelPension", personnelPension);
		return "modules/personnel/personnelPensionFormDetail";
	}

	@ResponseBody
	@RequiresPermissions("personnel:personnelPension:view")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids){
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			personnelPensionService.deleteByIds(ids);
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
	public String exportExcelByTemplate(PersonnelPension personnelPension, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes,boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<PersonnelPension> page = null;
			if(flag == true){
				page = personnelPensionService.findPage(new Page<PersonnelPension>(request, response), personnelPension);
			}else{
				page = personnelPensionService.findPage(new Page<PersonnelPension>(request, response,-1), personnelPension);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, PersonnelPension.class);
			exportExcelNew.setWb(wb);
			List<PersonnelPension> list =page.getList();
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
		return "redirect:" + adminPath + "/personnel/personnelPension?repage";
	}

	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		String isCover = request.getParameter("isCover");
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<PersonnelPension> list = ei.getDataList(PersonnelPension.class);
			//选择覆盖模式，须先将改身份证下相关履历信息删除
			if(StringUtils.isNotBlank(isCover) && "1".equals(isCover)){
				List<String> idNumbers = new ArrayList<>();
				list.stream().forEach(personnelPension -> {
					if(personnelPension.getIdNumber()!=null){
						idNumbers.add(personnelPension.getIdNumber());
					}
				});
				if(idNumbers.size()>0&&idNumbers!=null)
					personnelPensionService.deleteByIdNumbers(idNumbers);
			}
			for (PersonnelPension personnelPension : list){
				try{
					BeanValidators.validateWithException(validator, personnelPension);
					personnelPensionService.save(personnelPension);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("(身份证号码:"+personnelPension.getIdNumber()+")"+" 导入失败："+ex.getMessage());
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
		return "redirect:" + adminPath + "/file/template/personnelBasesdownload/view?id=personnel_personnelPension&isCover="+isCover;

	}
}