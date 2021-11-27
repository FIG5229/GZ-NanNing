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
import com.thinkgem.jeesite.modules.affair.entity.*;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelEndowmentInsurance;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
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
import com.thinkgem.jeesite.modules.affair.service.AffairTrainingStatisticsService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * 培训班学习统计Controller
 * @author alan.wu
 * @version 2020-07-17
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairTrainingStatistics")
public class AffairTrainingStatisticsController extends BaseController {

	@Autowired
	private AffairTrainingStatisticsService affairTrainingStatisticsService;

	
	@ModelAttribute
	public AffairTrainingStatistics get(@RequestParam(required=false) String id) {
		AffairTrainingStatistics entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairTrainingStatisticsService.get(id);
		}
		if (entity == null){
			entity = new AffairTrainingStatistics();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairTrainingStatistics:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairTrainingStatistics affairTrainingStatistics, HttpServletRequest request, HttpServletResponse response, Model model) {

		Page<AffairTrainingStatistics> page = affairTrainingStatisticsService.findPage(new Page<AffairTrainingStatistics>(request, response), affairTrainingStatistics);
		model.addAttribute("page", page);
		return "modules/affair/affairTrainingStatisticsList";
	}

	@RequiresPermissions("affair:affairTrainingStatistics:view")
	@RequestMapping(value = "form")
	public String form(AffairTrainingStatistics affairTrainingStatistics, Model model) {
		model.addAttribute("affairTrainingStatistics", affairTrainingStatistics);
		return "modules/affair/affairTrainingStatisticsForm";
	}

	@RequiresPermissions("affair:affairTrainingStatistics:edit")
	@RequestMapping(value = "save")
	public String save(AffairTrainingStatistics affairTrainingStatistics, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, affairTrainingStatistics)){
			return form(affairTrainingStatistics, model);
		}
		affairTrainingStatisticsService.save(affairTrainingStatistics);
		addMessage(redirectAttributes, "保存培训班学习统计成功");
		model.addAttribute("saveResult","sucess");
		return "modules/affair/affairTrainingStatisticsForm";
	}
	
	@RequiresPermissions("affair:affairTrainingStatistics:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairTrainingStatistics affairTrainingStatistics, RedirectAttributes redirectAttributes) {
		affairTrainingStatisticsService.delete(affairTrainingStatistics);
		addMessage(redirectAttributes, "删除培训班学习统计成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairTrainingStatistics/?repage";
	}

	/**
	 * 详情
	 *
	 * @param affairTrainingStatistics
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairTrainingStatistics:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairTrainingStatistics affairTrainingStatistics, Model model) {
		model.addAttribute("affairTrainingStatistics", affairTrainingStatistics);
		return "modules/affair/affairTrainingStatisticsFormDetail";
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairTrainingStatistics:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairTrainingStatisticsService.deleteByIds(ids);
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
	public String exportExcelByTemplate(AffairTrainingStatistics affairTrainingStatistics, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			affairTrainingStatistics.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<AffairTrainingStatistics> page = null;
			if(flag == true){
				page = affairTrainingStatisticsService.findPage(new Page<AffairTrainingStatistics>(request, response), affairTrainingStatistics);
			}else {
				page = affairTrainingStatisticsService.findPage(new Page<AffairTrainingStatistics>(request, response,-1), affairTrainingStatistics);
			}
/*
			Page<AffairFocusStudy> page = affairFocusStudyService.findPage(new Page<AffairFocusStudy>(request, response,-1), affairFocusStudy);
*/
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairTrainingStatistics.class);
			exportExcelNew.setWb(wb);
			List<AffairTrainingStatistics> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairTrainingStatistics?repage";
	}


	@RequestMapping(value = "import", method = RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairTrainingStatistics> list = ei.getDataList(AffairTrainingStatistics.class);
			for (AffairTrainingStatistics affairTrainingStatistics : list) {
				try {

					BeanValidators.validateWithException(validator, affairTrainingStatistics);
					affairTrainingStatisticsService.save(affairTrainingStatistics);
					successNum++;
				} catch (ConstraintViolationException ex) {
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList) {
						failureMsg.append(message + "; ");
						failureNum++;
					}
				} catch (Exception ex) {
					failureMsg.append(" 导入失败：" + ex.getMessage());
				}
			}
			if (failureNum > 0) {
				failureMsg.insert(0, "，失败 " + failureNum + " 条，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 " + successNum + " 条" + failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入失败！失败信息：" + e.getMessage());
		}
		redirectAttributes.addFlashAttribute("result", "success");
		return "redirect:" + adminPath + "/file/template/download/view";
	}



}