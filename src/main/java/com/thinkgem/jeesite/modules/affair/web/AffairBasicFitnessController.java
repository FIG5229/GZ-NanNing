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
import com.thinkgem.jeesite.modules.affair.entity.AffairBasicKnowledge;
import com.thinkgem.jeesite.modules.sys.entity.Office;
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
import com.thinkgem.jeesite.modules.affair.entity.AffairBasicFitness;
import com.thinkgem.jeesite.modules.affair.service.AffairBasicFitnessService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;

/**
 * 基本体能成绩Controller
 * @author cecil.li
 * @version 2020-12-28
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairBasicFitness")
public class AffairBasicFitnessController extends BaseController {

	@Autowired
	private OfficeService officeService;

	@Autowired
	private AffairBasicFitnessService affairBasicFitnessService;
	
	@ModelAttribute
	public AffairBasicFitness get(@RequestParam(required=false) String id) {
		AffairBasicFitness entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairBasicFitnessService.get(id);
		}
		if (entity == null){
			entity = new AffairBasicFitness();
		}
		return entity;
	}

	@RequiresPermissions("affair:affairBasicFitness:view")
	@RequestMapping(value = {""})
	public String index(Office office, Model model) {
		return "modules/affair/affairBasicFitnessIndex";
	}


	@RequiresPermissions("affair:affairBasicFitness:view")
	@RequestMapping(value = {"list"})
	public String list(AffairBasicFitness affairBasicFitness, HttpServletRequest request, HttpServletResponse response, Model model) {
		Calendar cal = Calendar.getInstance();
		String year = String.valueOf(cal.get(Calendar.YEAR));
		String month = String.valueOf(cal.get(Calendar.MONTH )+1);
		String monthCopy = "";
		if (month.length() == 1){
			monthCopy = "0" + month;
		}
		String yearMonth = year + "-" +monthCopy;
		if (affairBasicFitness.getYearMonth() == null || "".equals(affairBasicFitness.getYearMonth())) {
			affairBasicFitness.setYearMonth(yearMonth);
		}
		Page<AffairBasicFitness> page = affairBasicFitnessService.findPage(new Page<AffairBasicFitness>(request, response), affairBasicFitness);
		model.addAttribute("yearMonth", affairBasicFitness.getYearMonth());
		model.addAttribute("page", page);
		model.addAttribute("unit",affairBasicFitness.getUnit());
		model.addAttribute("unitId",affairBasicFitness.getUnitId());
		return "modules/affair/affairBasicFitnessList";
	}

	@RequiresPermissions("affair:affairBasicFitness:view")
	@RequestMapping(value = "form")
	public String form(AffairBasicFitness affairBasicFitness, Model model) {
		model.addAttribute("affairBasicFitness", affairBasicFitness);
		return "modules/affair/affairBasicFitnessForm";
	}

	@RequiresPermissions("affair:affairBasicFitness:edit")
	@RequestMapping(value = "save")
	public String save(AffairBasicFitness affairBasicFitness, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairBasicFitness)){
			return form(affairBasicFitness, model);
		}
		affairBasicFitnessService.save(affairBasicFitness);
		addMessage(redirectAttributes, "保存基本体能成绩成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairBasicFitnessForm";
	}
	
	@RequiresPermissions("affair:affairBasicFitness:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairBasicFitness affairBasicFitness, RedirectAttributes redirectAttributes) {
		affairBasicFitnessService.delete(affairBasicFitness);
		addMessage(redirectAttributes, "删除基本体能成绩成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairBasicFitness/list/?repage";
	}

	@RequiresPermissions("affair:affairBasicFitness:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairBasicFitness affairBasicFitness, Model model) {
		model.addAttribute("affairBasicFitness", affairBasicFitness);
		return "modules/affair/affairBasicFitnessFormDetail";
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairBasicFitness:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairBasicFitnessService.deleteByIds(ids);
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
	public String exportExcelByTemplate(AffairBasicFitness affairBasicFitness, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}

			Page<AffairBasicFitness> page = null;
			if(flag == true){
				page = affairBasicFitnessService.findPage(new Page<AffairBasicFitness>(request, response), affairBasicFitness);
			}else{
				page = affairBasicFitnessService.findPage(new Page<AffairBasicFitness>(request, response,-1), affairBasicFitness);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(2, AffairBasicFitness.class);
			exportExcelNew.setWb(wb);
			List<AffairBasicFitness> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairBasicFitness/list/?repage";
	}

	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 2, 0);
			List<AffairBasicFitness> list = ei.getDataList(AffairBasicFitness.class);
			for (AffairBasicFitness affairBasicFitness : list){
				try{
					BeanValidators.validateWithException(validator, affairBasicFitness);
					affairBasicFitness.setUnitId(officeService.findByName(affairBasicFitness.getUnit()));
					affairBasicFitnessService.save(affairBasicFitness);
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