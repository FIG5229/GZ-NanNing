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
import com.thinkgem.jeesite.modules.affair.entity.AffairBasicWage;
import com.thinkgem.jeesite.modules.affair.service.AffairBasicWageService;
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
 * 职务工资标准Controller
 * @author cecil.li
 * @version 2020-06-08
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairBasicWage")
public class AffairBasicWageController extends BaseController {

	@Autowired
	private AffairBasicWageService affairBasicWageService;
	
	@ModelAttribute
	public AffairBasicWage get(@RequestParam(required=false) String id) {
		AffairBasicWage entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairBasicWageService.get(id);
		}
		if (entity == null){
			entity = new AffairBasicWage();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairBasicWage:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairBasicWage affairBasicWage, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairBasicWage> page = affairBasicWageService.findPage(new Page<AffairBasicWage>(request, response), affairBasicWage);
//		List<AffairBasicWage> page = affairBasicWageService.findList(affairBasicWage);
		model.addAttribute("page", page);
		return "modules/affair/affairBasicWageList";
	}

	@RequiresPermissions("affair:affairBasicWage:view")
	@RequestMapping(value = "form")
	public String form(AffairBasicWage affairBasicWage, Model model) {
		model.addAttribute("affairBasicWage", affairBasicWage);
		return "modules/affair/affairBasicWageForm";
	}

	@RequiresPermissions("affair:affairBasicWage:edit")
	@RequestMapping(value = "save")
	public String save(AffairBasicWage affairBasicWage, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairBasicWage)){
			return form(affairBasicWage, model);
		}
		affairBasicWageService.save(affairBasicWage);
		addMessage(redirectAttributes, "保存职务工资标准成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairBasicWageForm";
	}
	
	@RequiresPermissions("affair:affairBasicWage:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairBasicWage affairBasicWage, RedirectAttributes redirectAttributes) {
		affairBasicWageService.delete(affairBasicWage);
		addMessage(redirectAttributes, "删除职务工资标准成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairBasicWage/?repage";
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairBasicWage:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairBasicWageService.deleteByIds(ids);
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
	public String exportExcelByTemplate(AffairBasicWage affairBasicWage, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}

			Page<AffairBasicWage> page = null;
			if(flag == true){
				page = affairBasicWageService.findPage(new Page<AffairBasicWage>(request, response,-1), affairBasicWage);
			}else{
				page = affairBasicWageService.findPage(new Page<AffairBasicWage>(request, response,-1), affairBasicWage);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairBasicWage.class);
			exportExcelNew.setWb(wb);
			List<AffairBasicWage> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairBasicWage?repage";
	}

	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairBasicWage> list = ei.getDataList(AffairBasicWage.class);
			for (AffairBasicWage affairBasicWage : list){
				try{
					BeanValidators.validateWithException(validator, affairBasicWage);
					affairBasicWageService.save(affairBasicWage);
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