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
import com.thinkgem.jeesite.modules.affair.entity.AffairClassManage;
import com.thinkgem.jeesite.modules.affair.entity.AffairExamEntering;
import com.thinkgem.jeesite.modules.affair.entity.AffairTrainingStatistics;
import com.thinkgem.jeesite.modules.affair.service.AffairClassManageService;
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
import com.thinkgem.jeesite.modules.affair.entity.AffairPoliceStatistics;
import com.thinkgem.jeesite.modules.affair.service.AffairPoliceStatisticsService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 民警课程学习统计Controller
 * @author alan.wu
 * @version 2020-07-17
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairPoliceStatistics")
public class AffairPoliceStatisticsController extends BaseController {

	@Autowired
	private AffairPoliceStatisticsService affairPoliceStatisticsService;

	@Autowired
	private AffairClassManageService affairClassManageService;

	@ModelAttribute
	public AffairPoliceStatistics get(@RequestParam(required=false) String id) {
		AffairPoliceStatistics entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairPoliceStatisticsService.get(id);
		}
		if (entity == null){
			entity = new AffairPoliceStatistics();
		}
		return entity;
	}
 

	@RequiresPermissions("affair:affairPoliceStatistics:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairPoliceStatistics affairPoliceStatistics, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairPoliceStatistics> page = affairPoliceStatisticsService.findPage(new Page<AffairPoliceStatistics>(request, response), affairPoliceStatistics);
		model.addAttribute("page", page);
	return "modules/affair/affairPoliceStatisticsList";
	}

	@RequiresPermissions("affair:affairPoliceStatistics:view")
	@RequestMapping(value = "form")
	public String form(AffairPoliceStatistics affairPoliceStatistics, Model model) {
		model.addAttribute("affairPoliceStatistics", affairPoliceStatistics);
		return "modules/affair/affairPoliceStatisticsForm";
	}

	@RequiresPermissions("affair:affairPoliceStatistics:edit")
	@RequestMapping(value = "save")
	public String save(AffairPoliceStatistics affairPoliceStatistics, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, affairPoliceStatistics)){
			return form(affairPoliceStatistics, model);
		}
		affairPoliceStatisticsService.save(affairPoliceStatistics);
		addMessage(redirectAttributes, "保存民警课程学习统计成功");
		model.addAttribute("saveResult","sucess");
		return "modules/affair/affairPoliceStatisticsForm";
	}
	
	@RequiresPermissions("affair:affairPoliceStatistics:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairPoliceStatistics affairPoliceStatistics, RedirectAttributes redirectAttributes) {
		affairPoliceStatisticsService.delete(affairPoliceStatistics);
		addMessage(redirectAttributes, "删除民警课程学习统计成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairPoliceStatistics/?repage";
	}


	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairPoliceStatistics:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairPoliceStatisticsService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}


	/**
	 * 详情
	 *
	 * @param affairPoliceStatistics
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairPoliceStatistics:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairPoliceStatistics affairPoliceStatistics, Model model) {
		model.addAttribute("affairPoliceStatistics", affairPoliceStatistics);
		return "modules/affair/affairPoliceStatisticsFormDetail";
	}



	/*
	* 导入
	*
	* */

	@RequestMapping(value = "import", method = RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairPoliceStatistics> list = ei.getDataList(AffairPoliceStatistics.class);
			for (AffairPoliceStatistics affairPoliceStatistics : list) {
				try {

					BeanValidators.validateWithException(validator, affairPoliceStatistics);
					affairPoliceStatisticsService.save(affairPoliceStatistics);
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




	/**
	 * 导出excel格式数据
	 * @param
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "export", method= RequestMethod.POST)
	public String exportExcelByTemplate(AffairPoliceStatistics affairPoliceStatistics, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			affairPoliceStatistics.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<AffairPoliceStatistics> page = null;
			if(flag == true){
				page = affairPoliceStatisticsService.findPage(new Page<AffairPoliceStatistics>(request, response), affairPoliceStatistics);
			}else {
				page = affairPoliceStatisticsService.findPage(new Page<AffairPoliceStatistics>(request, response,-1), affairPoliceStatistics);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairPoliceStatistics.class);
			exportExcelNew.setWb(wb);
			List<AffairPoliceStatistics> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairPoliceStatistics?repage";
	}



}