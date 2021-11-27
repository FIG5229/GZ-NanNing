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
import com.thinkgem.jeesite.modules.affair.entity.*;
import com.thinkgem.jeesite.modules.affair.service.AffairSwapExerciseService;
import com.thinkgem.jeesite.modules.affair.service.AffairTrainOutsourceService;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.web.session.HttpServletSession;
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
import com.thinkgem.jeesite.modules.affair.service.AffairExchangeStatisticsService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 交流锻炼统计表Controller
 * @author alan.wu
 * @version 2020-07-29
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairExchangeStatistics")
public class AffairExchangeStatisticsController extends BaseController {

	@Autowired
	private AffairExchangeStatisticsService affairExchangeStatisticsService;

	@Autowired
	private AffairSwapExerciseService affairSwapExerciseService;

	@Autowired
	private OfficeService officeService;
	
	@ModelAttribute
	public AffairExchangeStatistics get(@RequestParam(required=false) String id) {
		AffairExchangeStatistics entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairExchangeStatisticsService.get(id);
		}
		if (entity == null){
			entity = new AffairExchangeStatistics();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairExchangeStatistics:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairExchangeStatistics affairExchangeStatistics, HttpServletRequest request, HttpServletResponse response, Model model) {

	/*	AffairSwapExercise affairSwapExercise = new AffairSwapExercise();

		//用户名
		String userName = affairExchangeStatistics.getUserName();
		//警号
		String number = affairExchangeStatistics.getNumber();
		//姓名
		String name = affairExchangeStatistics.getName();
		//证件号
		String idNumber = affairExchangeStatistics.getIdNumber();
		//数据来源
		String come = affairExchangeStatistics.getCome();
		//警衔
		String rank = affairExchangeStatistics.getPoliceRank();
		//警种
		String classify = affairExchangeStatistics.getPeopleClassify();
		//人员类别
		String people = affairExchangeStatistics.getPeopleClassify();
		//管理类别
		String manageMent = affairExchangeStatistics.getManagementClass();
		//行政职务
		String post = affairExchangeStatistics.getPost();
		//职务级别
		String level = affairExchangeStatistics.getPostLevel();
		//培训开始时间
		Date begin = affairExchangeStatistics.getBeginTime();
		//培训结束时间
		Date end = affairExchangeStatistics.getEndTime();



		affairSwapExercise.setUserName(userName);
		affairSwapExercise.setNumber(number);
		affairSwapExercise.setName(name);
		affairSwapExercise.setIdNumber(idNumber);
		*//*affairSwapExercise.setCome(come);*//*
		affairSwapExercise.setPoliceRank(rank);
		affairSwapExercise.setPoliceClassification(classify);
		affairSwapExercise.setPersonType(people);
		affairSwapExercise.setManagementType(manageMent);
		affairSwapExercise.setPost(post);
		affairSwapExercise.setPostLevel(level);
		if (begin != null){
			affairSwapExercise.setStartDate(begin);
		}
		if (end != null){
			affairSwapExercise.setEndDate(end);
		}*/

		Page<AffairExchangeStatistics> page = affairExchangeStatisticsService.findPage(new Page<AffairExchangeStatistics>(request, response), affairExchangeStatistics);


/*
		List<AffairSwapExercise> affairSwapExerciseList = affairSwapExerciseService.findBeanList(affairSwapExercise);
*/
		model.addAttribute("page", page);/*
		model.addAttribute("affairSwapExerciseList",affairSwapExerciseList);*/
		return "modules/affair/affairExchangeStatisticsList";
	}

	@RequestMapping(value = "deputy")
	public String deputy(AffairExchangeStatistics affairExchangeStatistics,String idNumber, Model model, HttpServletRequest request, HttpSession session) {


/*		String idNumber = affairExchangeStatistics.getIdNumber();*/
		if (StringUtils.isNotBlank(idNumber)){

			session.setAttribute("idNumber",idNumber);

			List<AffairSwapExercise> list = affairSwapExerciseService.selectTrain(idNumber);
			model.addAttribute("affairExchangeStatisticsList", list);
		}

		return "modules/affair/affairExchangeStatisticsDeputyList";
	}

	@RequiresPermissions("affair:affairExchangeStatistics:view")
	@RequestMapping(value = "form")
	public String form(AffairExchangeStatistics affairExchangeStatistics, Model model) {
		model.addAttribute("affairExchangeStatistics", affairExchangeStatistics);
		return "modules/affair/affairExchangeStatisticsForm";
	}

	/**
	 * 详情
	 *
	 * @param affairExchangeStatistics
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairLearnDaily:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairExchangeStatistics affairExchangeStatistics, Model model) {
		model.addAttribute("affairExchangeStatistics", affairExchangeStatistics);

		return "modules/affair/affairExchangeStatisticsFormDetail";
	}

	@RequiresPermissions("affair:affairExchangeStatistics:edit")
	@RequestMapping(value = "save")
	public String save(AffairExchangeStatistics affairExchangeStatistics, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, affairExchangeStatistics)){
			return form(affairExchangeStatistics, model);
		}
		affairExchangeStatisticsService.save(affairExchangeStatistics);
		addMessage(redirectAttributes, "保存交流锻炼统计表成功");
		model.addAttribute("saveResult","success");
		return "modules/affair/affairExchangeStatisticsForm";
	}
	
	@RequiresPermissions("affair:affairExchangeStatistics:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairExchangeStatistics affairExchangeStatistics, RedirectAttributes redirectAttributes) {
		affairExchangeStatisticsService.delete(affairExchangeStatistics);
		addMessage(redirectAttributes, "删除交流锻炼统计表成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairExchangeStatistics/?repage";
	}


	/**
	 * 导出excel格式数据
	 *
	 * @param
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportExcelByTemplate(AffairExchangeStatistics affairExchangeStatistics, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {

		XSSFWorkbook wb = null;
		try {

			if ("5".equals(UserUtils.getUser().getOffice().getId())) {
				affairExchangeStatistics.setUnitId(UserUtils.getUser().getCompany().getId());
			} else {
				affairExchangeStatistics.setUnitId(UserUtils.getUser().getOffice().getId());
			}
			affairExchangeStatistics.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if (request.getParameter("fileName") != null && !"".equals(request.getParameter("fileName"))) {
				fileName = request.getParameter("fileName").toString();
			}
			Page<AffairExchangeStatistics> page = null;
			if (flag == true) {
				page = affairExchangeStatisticsService.findPage(new Page<AffairExchangeStatistics>(request, response), affairExchangeStatistics);
			} else {
				page = affairExchangeStatisticsService.findPage(new Page<AffairExchangeStatistics>(request, response, -1), affairExchangeStatistics);
			}
/*
			Page<AffairFocusStudy> page = affairFocusStudyService.findPage(new Page<AffairFocusStudy>(request, response,-1), affairFocusStudy);
*/
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairExchangeStatistics.class);
			exportExcelNew.setWb(wb);
			List<AffairExchangeStatistics> list = page.getList();
			exportExcelNew.setDataList(list);
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
		return "redirect:" + adminPath + "/affair/affairExchangeStatistics?repage";
	}

	@RequestMapping(value = "import", method = RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairExchangeStatistics> list = ei.getDataList(AffairExchangeStatistics.class);
			for (AffairExchangeStatistics affairExchangeStatistics : list) {
				try {
					affairExchangeStatistics.setUnitId(officeService.findByName(affairExchangeStatistics.getUnit()));
					BeanValidators.validateWithException(validator, affairExchangeStatistics);
					affairExchangeStatisticsService.save(affairExchangeStatistics);
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