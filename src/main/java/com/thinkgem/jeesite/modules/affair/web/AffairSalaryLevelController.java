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
import com.thinkgem.jeesite.modules.affair.entity.AffairSalaryLevel;
import com.thinkgem.jeesite.modules.affair.service.AffairSalaryLevelService;
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
import java.util.Calendar;
import java.util.List;

/**
 * 级别工资标准Controller
 * @author cecil.li
 * @version 2020-06-08
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairSalaryLevel")
public class AffairSalaryLevelController extends BaseController {

	@Autowired
	private AffairSalaryLevelService affairSalaryLevelService;
	
	@ModelAttribute
	public AffairSalaryLevel get(@RequestParam(required=false) String id) {
		AffairSalaryLevel entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairSalaryLevelService.get(id);
		}
		if (entity == null){
			entity = new AffairSalaryLevel();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairSalaryLevel:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairSalaryLevel affairSalaryLevel, HttpServletRequest request, HttpServletResponse response, Model model) {

		Calendar calendar = Calendar.getInstance();
		Integer ye  = calendar.get(Calendar.YEAR);
		String year = String.valueOf(ye);

		String yea = affairSalaryLevel.getYear();
		if (StringUtils.isBlank(yea)){
			affairSalaryLevel.setYear(year);
		}


		Page<AffairSalaryLevel> page = affairSalaryLevelService.findPage(new Page<AffairSalaryLevel>(request, response), affairSalaryLevel); 
		model.addAttribute("page", page);
		return "modules/affair/affairSalaryLevelList";
	}

	@RequiresPermissions("affair:affairSalaryLevel:view")
	@RequestMapping(value = "form")
	public String form(AffairSalaryLevel affairSalaryLevel, Model model) {
		model.addAttribute("affairSalaryLevel", affairSalaryLevel);
		return "modules/affair/affairSalaryLevelForm";
	}

	@RequiresPermissions("affair:affairSalaryLevel:edit")
	@RequestMapping(value = "save")
	public String save(AffairSalaryLevel affairSalaryLevel, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairSalaryLevel)){
			return form(affairSalaryLevel, model);
		}
		affairSalaryLevelService.save(affairSalaryLevel);
		addMessage(redirectAttributes, "保存级别工资标准成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairSalaryLevelForm";
	}
	
	@RequiresPermissions("affair:affairSalaryLevel:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairSalaryLevel affairSalaryLevel, RedirectAttributes redirectAttributes) {
		affairSalaryLevelService.delete(affairSalaryLevel);
		addMessage(redirectAttributes, "删除级别工资标准成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairSalaryLevel/?repage";
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairSalaryLevel:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairSalaryLevelService.deleteByIds(ids);
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
	public String exportExcelByTemplate(AffairSalaryLevel affairSalaryLevel, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName");
			}

			Page<AffairSalaryLevel> page = null;
			if(flag == true){
				page = affairSalaryLevelService.findPage(new Page<AffairSalaryLevel>(request, response,-1), affairSalaryLevel);
			}else{
				page = affairSalaryLevelService.findPage(new Page<AffairSalaryLevel>(request, response,-1), affairSalaryLevel);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairSalaryLevel.class);
			exportExcelNew.setWb(wb);
			List<AffairSalaryLevel> list =page.getList();
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
			List<AffairSalaryLevel> list = ei.getDataList(AffairSalaryLevel.class);
			for (AffairSalaryLevel affairSalaryLevel : list){
				try{
					BeanValidators.validateWithException(validator, affairSalaryLevel);
					affairSalaryLevelService.save(affairSalaryLevel);
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