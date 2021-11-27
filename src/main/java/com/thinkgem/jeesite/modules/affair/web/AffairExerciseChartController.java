/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolationException;

import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.excel.ExportExcelNew;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.modules.affair.dao.AffairExerciseChartDao;
import com.thinkgem.jeesite.modules.affair.entity.*;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.affair.service.AffairExerciseChartService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 练习报表Controller
 * @author alan.wu
 * @version 2020-08-07
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairExerciseChart")
public class AffairExerciseChartController extends BaseController {

	@Autowired
	private AffairExerciseChartService affairExerciseChartService;

	@Autowired
	private OfficeService officeService;

	@Autowired
	private AffairExerciseChartDao affairExerciseChartDao;

	
	@ModelAttribute
	public AffairExerciseChart get(@RequestParam(required=false) String id) {
		AffairExerciseChart entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairExerciseChartService.get(id);
		}
		if (entity == null){
			entity = new AffairExerciseChart();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairExerciseChart:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairExerciseChart affairExerciseChart, HttpServletRequest request, HttpServletResponse response, Model model) {

		List<AffairExerciseChart> list = affairExerciseChartService.selectAll();

		model.addAttribute("page", list);
		return "modules/affair/affairExerciseChartList";
	}

	@RequiresPermissions("affair:affairExerciseChart:view")
	@RequestMapping(value = "form")
	public String form(AffairExerciseChart affairExerciseChart, Model model) {
		model.addAttribute("affairExerciseChart", affairExerciseChart);
		return "modules/affair/affairExerciseChartForm";
	}

	@RequiresPermissions("affair:affairExerciseChart:edit")
	@RequestMapping(value = "save")
	public String save(AffairExerciseChart affairExerciseChart, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, affairExerciseChart)){
			return form(affairExerciseChart, model);
		}
		affairExerciseChartService.save(affairExerciseChart);
		addMessage(redirectAttributes, "保存练习报表成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairExerciseChart/?repage";
	}
	
	@RequiresPermissions("affair:affairExerciseChart:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairExerciseChart affairExerciseChart, RedirectAttributes redirectAttributes) {
		affairExerciseChartService.delete(affairExerciseChart);
		addMessage(redirectAttributes, "删除练习报表成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairExerciseChart/?repage";
	}


	/**
	 * 导出excel格式数据
	 * @param affairExerciseChart
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @param flag
	 * @return
	 */
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportExcelByTemplate(AffairExerciseChart affairExerciseChart, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {

		XSSFWorkbook wb = null;
		try {

			affairExerciseChart.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if (request.getParameter("fileName") != null && !"".equals(request.getParameter("fileName"))) {
				fileName = request.getParameter("fileName").toString();
			}
			List<AffairExerciseChart> page = null;
			page = affairExerciseChartService.selectAll();

			String fileSeperator = File.separator;
			String filePath = Global.getUserfilesBaseDir() + fileSeperator + "userfiles" + fileSeperator + "template" + fileSeperator;
			InputStream inputStream = new FileInputStream(filePath + fileName);
			if (null != inputStream) {
				try {
					wb = new XSSFWorkbook(inputStream);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairExerciseChart.class);
			exportExcelNew.setWb(wb);
			exportExcelNew.setDataList(page);
			HSSFFormulaEvaluator.evaluateAllFormulaCells(wb);
			response.reset();
			response.setContentType("application/octet-stream; charset=utf-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + Encodes.urlEncode(fileName));
			ServletOutputStream fout = response.getOutputStream();
			wb.write(fout);
			fout.close();
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			addMessage(redirectAttributes, "导出用户失败！失败信息：" + ex);
		}
		return "redirect:" + adminPath + "/affair/affairExerciseChart?repage";
	}

	/**
	 * 导入excel数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "import", method= RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairExerciseChart> list = ei.getDataList(AffairExerciseChart.class);
			for (AffairExerciseChart affairExerciseChart : list){
				try{
					//单位绑定单位id
					String orgId = officeService.findByName(affairExerciseChart.getUnit());
					if(orgId != null){
						affairExerciseChart.setUnitId(orgId);
					}
					BeanValidators.validateWithException(validator, affairExerciseChart);
					affairExerciseChartService.save(affairExerciseChart);
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