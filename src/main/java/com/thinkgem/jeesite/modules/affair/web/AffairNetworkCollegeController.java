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
import com.thinkgem.jeesite.modules.affair.entity.AffairLearnPower;
import com.thinkgem.jeesite.modules.affair.entity.AffairLearnPowerYear;
import com.thinkgem.jeesite.modules.affair.entity.AffairNetworkCollegeYear;
import com.thinkgem.jeesite.modules.affair.service.AffairNetworkCollegeYearService;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
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
import com.thinkgem.jeesite.modules.affair.entity.AffairNetworkCollege;
import com.thinkgem.jeesite.modules.affair.service.AffairNetworkCollegeService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 中国干部网络学院Controller
 * @author alan.wu
 * @version 2020-08-03
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairNetworkCollege")
public class AffairNetworkCollegeController extends BaseController {

	@Autowired
	private AffairNetworkCollegeService affairNetworkCollegeService;

	@Autowired
	private AffairNetworkCollegeYearService affairNetworkCollegeYearService;

	@Autowired
	private OfficeService officeService;
	
	@ModelAttribute
	public AffairNetworkCollege get(@RequestParam(required=false) String id) {
		AffairNetworkCollege entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairNetworkCollegeService.get(id);
		}
		if (entity == null){
			entity = new AffairNetworkCollege();
		}
		return entity;
	}
	

	@RequiresPermissions("affair:affairNetworkCollege:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairNetworkCollege affairNetworkCollege, Model model){

		String time = affairNetworkCollege.getTime();
		if (StringUtils.isBlank(time)){

			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");

			//当前时间
			String dateString1 = formatter.format(currentTime);
			affairNetworkCollege.setTime(dateString1);
			List<AffairNetworkCollege> list = affairNetworkCollegeService.selectAllBean(affairNetworkCollege);


			for (int i = 0; i < list.size(); i++) {

				AffairNetworkCollege affairNetworkCollege1 = list.get(i);

				//身份证号
				String idNumber = affairNetworkCollege1.getIdNumber();

				//本月学习积分
				Double thisMonthIntegral = 0.0;
				if(affairNetworkCollege1.getThisMonthIntegral()!=null){
					thisMonthIntegral = affairNetworkCollege1.getThisMonthIntegral();
				}

				//年份
				String year = dateString1.substring(0, 4);
				//月份
				String strh = dateString1.substring(dateString1.length() - 2, dateString1.length());

				int mon = Integer.parseInt(strh);
				int lastMo = mon - 1;

				//上月月份
				String lastMonth = String.valueOf(lastMo);

				if (lastMo < 10) {
					//上月时间
					String lastTime = year + "-0" + lastMonth;

					//上月分数
					Double lastScore = affairNetworkCollegeService.selectLastScore(idNumber, lastTime);
					lastScore= lastScore==null?0.0:lastScore;
					if (StringUtils.isNotBlank(lastScore.toString())){
						affairNetworkCollege1.setLastMonthIntegral(lastScore);
					}else{
						affairNetworkCollege1.setLastMonthIntegral(0.0);
					}
					Double sum = thisMonthIntegral + lastScore;
					affairNetworkCollege1.setThisMonthStatistics(sum);

					String y = affairNetworkCollegeService.selectSumYearById(year,idNumber);
					Double yearScore = 0.0;
					try {
						yearScore = Double.valueOf(y);
					}catch (Exception e){
						e.printStackTrace();
					}
					affairNetworkCollege1.setThisYearStatistics(yearScore);

				}else {
					//上个月时间
					String lastTime = year + "-" + lastMonth;

					//上月分数
					Double lastScore = affairNetworkCollegeService.selectLastScore(idNumber,lastTime);

					//本月学习分数
					Double score = 0.0;
					if(affairNetworkCollege1.getThisMonthIntegral()!=null){
						score = affairNetworkCollege1.getThisMonthIntegral();
					}
					if (StringUtils.isNotBlank(lastScore.toString())){
						affairNetworkCollege1.setLastMonthIntegral(lastScore);
						//上月及本月分数总和
						Double sum = lastScore + score;
						affairNetworkCollege1.setThisMonthStatistics(sum);
					}else {
						affairNetworkCollege1.setThisMonthStatistics(score);
					}
					//一年分数总和
					String y = affairNetworkCollegeService.selectSumYearById(year,idNumber);
					Double yearScore;
					//11.7  为空处理  kevin.jia
					if(StringUtils.isNotBlank(y)){
						yearScore = Double.valueOf(y);
					}else{
						yearScore = 0.0;
					}
					affairNetworkCollege1.setThisYearStatistics(yearScore);

				}
			}

			model.addAttribute("list",list);
			return "modules/affair/affairNetworkCollegeList";

		}else if (StringUtils.isNotBlank(time)){
			List<AffairNetworkCollege> list = affairNetworkCollegeService.selectAllBean(affairNetworkCollege);


			for (int i = 0; i <list.size(); i++) {

				AffairNetworkCollege affairNetworkCollege1 = list.get(i);

				//身份证号
				String idNumber = affairNetworkCollege1.getIdNumber();

				//本月学习积分
				Double thisMonthIntegral = 0.0;
				if(affairNetworkCollege1.getThisMonthIntegral()!=null){
					thisMonthIntegral = affairNetworkCollege1.getThisMonthIntegral();
				}

				//年份
				String year = time.substring(0, 4);
				//月份
				String strh = time.substring(time.length() - 2, time.length());

				int mon = Integer.parseInt(strh);
				int lastMo = mon - 1;

				//上月月份
				String lastMonth = String.valueOf(lastMo);

				if (lastMo < 10) {
					//上月时间
					String lastTime = year + "-0" + lastMonth;

					//上月分数
					Double lastScore = affairNetworkCollegeService.selectLastScore(idNumber, lastTime);
					lastScore= lastScore==null?0.0:lastScore;
					if (StringUtils.isNotBlank(lastScore.toString())){
						affairNetworkCollege1.setLastMonthIntegral(lastScore);
					}else{
						affairNetworkCollege1.setLastMonthIntegral(0.0);
					}
					Double sum = thisMonthIntegral + lastScore;
					affairNetworkCollege1.setThisMonthStatistics(sum);

					String y = affairNetworkCollegeService.selectSumYearById(year,idNumber);
					Double yearScore = Double.valueOf(y);
					affairNetworkCollege1.setThisYearStatistics(yearScore);

				}else {
					//上个月时间
					String lastTime = year + "-" + lastMonth;

					//上月分数
					Double lastScore = affairNetworkCollegeService.selectLastScore(idNumber,lastTime);

					//本月学习分数
					Double score = 0.0;
					if(affairNetworkCollege1.getThisMonthIntegral()!=null){
						score = affairNetworkCollege1.getThisMonthIntegral();
					}
					if (StringUtils.isNotBlank(lastScore.toString())){
						affairNetworkCollege1.setLastMonthIntegral(lastScore);
						//上月及本月分数总和
						Double sum = lastScore + score;
						affairNetworkCollege1.setThisMonthStatistics(sum);
					}else {
						affairNetworkCollege1.setThisMonthStatistics(score);
					}
					//一年分数总和
					String y = affairNetworkCollegeService.selectSumYearById(year,idNumber);
					//11.7 为空处理  kevin.jia
					Double yearScore ;
					if(StringUtils.isNotBlank(y)){
						yearScore = Double.valueOf(y);
					}else{
						yearScore = 0.0;
					}
					affairNetworkCollege1.setThisYearStatistics(yearScore);

				}
			}
			model.addAttribute("list",list);
		}
		return "modules/affair/affairNetworkCollegeList";
	}


	@RequestMapping(value = {"affairNetworkCollegeProblemDataList"})
	public String affairNetworkCollegeProblemDataList(AffairNetworkCollege affairNetworkCollege, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairNetworkCollege> page = affairNetworkCollegeService.findProblemDataList(new Page<AffairNetworkCollege>(request,response),affairNetworkCollege);
		model.addAttribute("page", page);
		return "modules/affair/affairNetworkCollegeProblemDataList";
	}


	@RequiresPermissions("affair:affairNetworkCollege:view")
	@RequestMapping(value = "form")
	public String form(AffairNetworkCollege affairNetworkCollege, Model model) {
		model.addAttribute("affairNetworkCollege", affairNetworkCollege);
		return "modules/affair/affairNetworkCollegeForm";
	}



	@RequiresPermissions("affair:affairNetworkCollege:edit")
	@RequestMapping(value = "save")
	public String save(AffairNetworkCollege affairNetworkCollege, Model model, RedirectAttributes redirectAttributes) throws ParseException {
		if (!beanValidator(model, affairNetworkCollege)) {
			return form(affairNetworkCollege, model);
		}
		affairNetworkCollegeService.save(affairNetworkCollege);
		addMessage(redirectAttributes, "保存中国干部网络学院成功");
		model.addAttribute("saveResult", "sucess");
		return "modules/affair/affairNetworkCollegeForm";
	}

	@RequiresPermissions("affair:affairNetworkCollege:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairNetworkCollege affairNetworkCollege, RedirectAttributes redirectAttributes) {
		affairNetworkCollegeService.delete(affairNetworkCollege);
		addMessage(redirectAttributes, "删除成功");
		return "redirect:" + Global.getAdminPath() + "/affair/affairNetworkCollege/?repage";
	}

	//问题数据删除
	@RequiresPermissions("affair:affairNetworkCollege:edit")
	@RequestMapping(value = "problemDataDelete")
	public String problemDataDelete(AffairNetworkCollege affairNetworkCollege, RedirectAttributes redirectAttributes) {
		affairNetworkCollegeService.delete(affairNetworkCollege);
		addMessage(redirectAttributes, "删除成功");
		return "redirect:" + Global.getAdminPath() + "/affair/affairNetworkCollege/affairNetworkCollegeProblemDataList?repage";
	}


	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairNetworkCollege:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if (ids != null && ids.size() > 0) {
			affairNetworkCollegeService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		} else {
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
	public String exportExcelByTemplate(AffairNetworkCollege affairNetworkCollege, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{

			if ("5".equals(UserUtils.getUser().getOffice().getId())){
				affairNetworkCollege.setUnitId(UserUtils.getUser().getCompany().getId());
			}else {
				affairNetworkCollege.setUnitId(UserUtils.getUser().getOffice().getId());
			}
			affairNetworkCollege.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName").toString();
			}
			Page<AffairNetworkCollege> page = null;
			if(flag == true){
				page = affairNetworkCollegeService.findPage(new Page<AffairNetworkCollege>(request, response), affairNetworkCollege);
			}else {
				page = affairNetworkCollegeService.findPage(new Page<AffairNetworkCollege>(request, response,-1), affairNetworkCollege);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairNetworkCollege.class);
			exportExcelNew.setWb(wb);
			List<AffairNetworkCollege> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairNetworkCollege?repage";
	}


	@RequestMapping(value = "import", method = RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairNetworkCollege> list = ei.getDataList(AffairNetworkCollege.class);
			for (AffairNetworkCollege affairNetworkCollege : list) {
				try {
					affairNetworkCollege.setUnitId(officeService.findByName(affairNetworkCollege.getUnit()));
					BeanValidators.validateWithException(validator, affairNetworkCollege);

					affairNetworkCollegeService.save(affairNetworkCollege);
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
		//redirectAttributes.addFlashAttribute("result", "success");
		return "redirect:" + adminPath + "/file/template/download/view?id=affair_affairNetworkCollege";
	}

}