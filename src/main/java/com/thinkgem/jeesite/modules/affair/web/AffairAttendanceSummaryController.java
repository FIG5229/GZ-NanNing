/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcelNew;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.affair.dao.AffairAttendanceFlagDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairAttendanceSummary;
import com.thinkgem.jeesite.modules.affair.service.AffairAttendanceSummaryService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

/**
 * 考勤关联计算Controller
 * @author cecil.li
 * @version 2020-06-02
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairAttendanceSummary")
public class AffairAttendanceSummaryController extends BaseController {

	@Autowired
	private AffairAttendanceSummaryService affairAttendanceSummaryService;

	@Autowired
	private AffairAttendanceFlagDao affairAttendanceFlagDao;

	@ModelAttribute
	public AffairAttendanceSummary get(@RequestParam(required=false) String id) {
		AffairAttendanceSummary entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairAttendanceSummaryService.get(id);
		}
		if (entity == null){
			entity = new AffairAttendanceSummary();
		}
		return entity;
	}

	@RequiresPermissions("affair:affairAttendanceSummary:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairAttendanceSummary affairAttendanceSummary, HttpServletRequest request, HttpServletResponse response, Model model) {
		int sumDay = 0;
		Integer ye = affairAttendanceSummary.getYear();
		if ("".equals(ye) || null == ye){

			Calendar calendar = Calendar.getInstance();
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH)+1;
			affairAttendanceSummary.setYear(year);
			affairAttendanceSummary.setMonth(month);

		}
		Calendar calendar = Calendar.getInstance();
		int year = affairAttendanceSummary.getYear();
		int month = affairAttendanceSummary.getMonth();
		String realMonth = "";
		if (String.valueOf(month).length() < 2){
			realMonth = "0"+ String.valueOf(month);
		}
		String startDate = String.valueOf(year) + "-" + realMonth + "-01";
		String endDate = String.valueOf(year) + "-" + realMonth + "-31";
		//查询本月应上班天数
		sumDay = affairAttendanceFlagDao.countXiuDays(startDate, endDate);
		//根据年月查询相应单位下人员考勤数据
		Page<AffairAttendanceSummary> page = affairAttendanceSummaryService.findPage(new Page<AffairAttendanceSummary>(request, response,-1), affairAttendanceSummary);

		String zqType =null;
		String xlType = null;
		String zqDays = null;
		String jbDays = null;
		String jfDays = null;
		String qqDays = null;
		Double realXlSummary = null;
		if (!page.getList().isEmpty()){
			for (AffairAttendanceSummary s: page.getList()) {
				zqType = s.getZqType();//值勤类别
				xlType = s.getXlType();//线路岗位（线路类别）
				zqDays = s.getZqDays();//值勤天数
				jbDays = s.getJbDays();//加班天数
				jfDays = s.getJfDays();//加发天数
				qqDays = s.getQqDays();//缺勤天数
				String xlType1 = s.getXlType();
				if (!StringUtils.isNotBlank(s.getXlType())){
					//线路岗位为空
					if ("0".equals(zqType)){
						//值勤类别  一类
						Double zqSummary = 50 * Double.parseDouble(zqDays);//执勤津贴  50元/日（一类）×值勤天数
						Double jbSummary = 177.5 * (Double.parseDouble(jbDays) + Double.parseDouble(jfDays));//加班工资  177.5元/日×（加班天数＋加发天数）
						Double xlSummary = 0.0;//线路津贴为0
						Double summary = zqSummary + jbSummary + xlSummary;//合计金额
						s.setZqMoney(String.valueOf(zqSummary));
						s.setJbMoney(String.valueOf(jbSummary));
						s.setXlMoney(String.valueOf(xlSummary));
						s.setSummary(String.valueOf(summary));
					}else {
						//值勤类别  二类
						Double zqSummary = 40 * Double.parseDouble(zqDays);//执勤津贴  40元/日（二类）×值勤天数
						Double jbSummary = 177.5 * (Double.parseDouble(jbDays) + Double.parseDouble(jfDays));//加班工资  177.5元/日×（加班天数＋加发天数）
						Double xlSummary = 0.0;//线路津贴为0
						Double summary = zqSummary + jbSummary + xlSummary;//合计
						s.setZqMoney(String.valueOf(zqSummary));
						s.setJbMoney(String.valueOf(jbSummary));
						s.setXlMoney(String.valueOf(xlSummary));
						s.setSummary(String.valueOf(summary));
					}
				}
				else {
					if ("0".equals(zqType)){
						//值(执)勤类别  一类
						if ("0".equals(xlType)){
							//线路岗位 一类
							Double xlSummary = 0.0;
							Double zqSummary = 50 * Double.parseDouble(zqDays);//50元/日（一类）×值勤天数
							Double jbSummary = 177.5 * (Double.parseDouble(jbDays) + Double.parseDouble(jfDays));//加班工资 177.5元/日×（加班天数＋加发天数）
							if (Double.valueOf(sumDay) <= Double.parseDouble(qqDays)){
								//缺勤天数 大于等于  本月应上班天数  线路津贴为 0
								xlSummary = 0.0;
								if (xlSummary <= 0){
									xlSummary = 0.0;
									realXlSummary = 0.0;
								}else {
//								xlSummary = xlSummary;
									BigDecimal bg = new BigDecimal(xlSummary);
									realXlSummary = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
								}
							}else {
								//缺勤天数 小于 本月应上班天数  线路津贴为 0
								xlSummary = 833.2 - (40 * Double.parseDouble(qqDays));//线路津贴一类 = 833.2-40元/日×缺勤天数
								if (xlSummary <= 0){
									xlSummary = 0.0;
									realXlSummary = 0.0;
								}else {
//								xlSummary = xlSummary;
									BigDecimal bg = new BigDecimal(xlSummary);
									realXlSummary = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
								}
							}
//							Double xlSummary = 833.2 - (40 * Double.parseDouble(qqDays));

							Double summary = zqSummary + jbSummary + realXlSummary;
							if (summary <= 0){
								summary = 0.0;
							}else {
								summary = summary;
							}
							s.setZqMoney(String.valueOf(zqSummary));
							s.setJbMoney(String.valueOf(jbSummary));
							s.setXlMoney(String.valueOf(realXlSummary));
							s.setSummary(String.valueOf(summary));
						}
						else {
							//线路岗位 二类
							Double xlSummary = 0.0;
							Double zqSummary = 50 * Double.parseDouble(zqDays);
							Double jbSummary = 177.5 * (Double.parseDouble(jbDays) + Double.parseDouble(jfDays));
							if (Double.valueOf(sumDay) <= Double.parseDouble(qqDays)){
								xlSummary = 0.0;
								if (xlSummary <= 0){
									xlSummary = 0.0;
									realXlSummary = 0.0;
								}else {
//								xlSummary = xlSummary;
									BigDecimal bg = new BigDecimal(xlSummary);
									realXlSummary = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
								}
							}else {
								xlSummary = 520.75 - (25 * Double.parseDouble(qqDays));
								if (xlSummary <= 0){
									xlSummary = 0.0;
									realXlSummary = 0.0;
								}else {
//								xlSummary = xlSummary;
									BigDecimal bg = new BigDecimal(xlSummary);
									realXlSummary = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
								}
							}
//							Double xlSummary = 520.75 - (25 * Double.parseDouble(qqDays));

							Double summary = zqSummary + jbSummary + realXlSummary;
							if (summary <= 0){
								summary = 0.0;
							}else {
								summary = summary;
							}
							s.setZqMoney(String.valueOf(zqSummary));
							s.setJbMoney(String.valueOf(jbSummary));
							s.setXlMoney(String.valueOf(realXlSummary));
							s.setSummary(String.valueOf(summary));
						}
					}
					else {
						if ("0".equals(xlType)){
							Double xlSummary = 0.0;
							Double zqSummary = 40 * Double.parseDouble(zqDays);
							Double jbSummary = 177.5 * (Double.parseDouble(jbDays) + Double.parseDouble(jfDays));
							if (Double.valueOf(sumDay) <= Double.parseDouble(qqDays)){
								xlSummary = 0.0;
								if (xlSummary <= 0){
									xlSummary = 0.0;
									realXlSummary = 0.0;
								}else {
//								xlSummary = xlSummary;
									BigDecimal bg = new BigDecimal(xlSummary);
									realXlSummary = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
								}
							}else {
								xlSummary = 833.2 - (40 * Double.parseDouble(qqDays));
								if (xlSummary <= 0){
									xlSummary = 0.0;
									realXlSummary = 0.0;
								}else {
//								xlSummary = xlSummary;
									BigDecimal bg = new BigDecimal(xlSummary);
									realXlSummary = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
								}
							}
//							Double xlSummary = 833.2 - (40 * Double.parseDouble(qqDays));

							Double summary = zqSummary + jbSummary + realXlSummary;
							if (summary <= 0){
								summary = 0.0;
							}else {
								summary = summary;
							}
							s.setZqMoney(String.valueOf(zqSummary));
							s.setJbMoney(String.valueOf(jbSummary));
							s.setXlMoney(String.valueOf(realXlSummary));
							s.setSummary(String.valueOf(summary));
						}else
						{
							Double xlSummary = 0.0;
							Double zqSummary = 40 * Double.parseDouble(zqDays);
							Double jbSummary = 177.5 * (Double.parseDouble(jbDays) + Double.parseDouble(jfDays));
							if (Double.valueOf(sumDay) <= Double.parseDouble(qqDays)){
								xlSummary = 0.0;
								if (xlSummary <= 0){
									xlSummary = 0.0;
									realXlSummary = 0.0;
								}else {
//								xlSummary = xlSummary;
									BigDecimal bg = new BigDecimal(xlSummary);
									realXlSummary = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
								}
							}else {
								xlSummary = 520.75 - (25 * Double.parseDouble(qqDays));
								if (xlSummary <= 0){
									xlSummary = 0.0;
									realXlSummary = 0.0;
								}else {
//								xlSummary = xlSummary;
									BigDecimal bg = new BigDecimal(xlSummary);
									realXlSummary = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
								}
							}
//							Double xlSummary = 520.75 - (25 * Double.parseDouble(qqDays));

							Double summary = zqSummary + jbSummary + realXlSummary;
							if (summary <= 0){
								summary = 0.0;
							}else {
								summary = summary;
							}
							s.setZqMoney(String.valueOf(zqSummary));
							s.setJbMoney(String.valueOf(jbSummary));
							s.setXlMoney(String.valueOf(realXlSummary));
							s.setSummary(String.valueOf(summary));
						}
					}
				}

			}

		}
		model.addAttribute("page", page);
		return "modules/affair/affairAttendanceSummaryList";
	}

	@RequiresPermissions("affair:affairAttendanceSummary:view")
	@RequestMapping(value = "form")
	public String form(AffairAttendanceSummary affairAttendanceSummary, Model model) {
		model.addAttribute("affairAttendanceSummary", affairAttendanceSummary);
		return "modules/affair/affairAttendanceSummaryForm";
	}

	@RequiresPermissions("affair:affairAttendanceSummary:edit")
	@RequestMapping(value = "save")
	public String save(AffairAttendanceSummary affairAttendanceSummary, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairAttendanceSummary)){
			return form(affairAttendanceSummary, model);
		}
		affairAttendanceSummaryService.save(affairAttendanceSummary);
		addMessage(redirectAttributes, "保存考勤关联计算成功");
		request.setAttribute("saveResult", "success");
		return "modules/affair/affairAttendanceSummaryForm";
	}

	@RequiresPermissions("affair:affairAttendanceSummary:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairAttendanceSummary affairAttendanceSummary, RedirectAttributes redirectAttributes) {
		affairAttendanceSummaryService.delete(affairAttendanceSummary);
		addMessage(redirectAttributes, "删除考勤关联计算成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairAttendanceSummary/?repage";
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
	public String exportExcelByTemplate(AffairAttendanceSummary affairAttendanceSummary, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			affairAttendanceSummary.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName");
			}

			Page<AffairAttendanceSummary> page = null;
			if(flag == true){
				page = affairAttendanceSummaryService.findPage(new Page<AffairAttendanceSummary>(request, response), affairAttendanceSummary);
			}else{
				page = affairAttendanceSummaryService.findPage(new Page<AffairAttendanceSummary>(request, response,-1), affairAttendanceSummary);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairAttendanceSummary.class);
			exportExcelNew.setWb(wb);
			List<AffairAttendanceSummary> list =page.getList();
			int sumDay = 0;
			list.forEach(item->{
				String zqType =null;
				String xlType = null;
				String zqDays = null;
				String jbDays = null;
				String jfDays = null;
				String qqDays = null;
				Double realXlSummary = null;
				zqType = item.getZqType();
				xlType = item.getXlType();
				zqDays = item.getZqDays();
				jbDays = item.getJbDays();
				jfDays = item.getJfDays();
				qqDays = item.getQqDays();
				if (" ".equals(item.getXlType())){
					if ("0".equals(zqType)){
						Double zqSummary = 50 * Double.parseDouble(zqDays);
						Double jbSummary = 177.5 * (Double.parseDouble(jbDays) + Double.parseDouble(jfDays));
						Double xlSummary = 0.0;
						Double summary = zqSummary + jbSummary + xlSummary;
						item.setZqMoney(String.valueOf(zqSummary));
						item.setJbMoney(String.valueOf(jbSummary));
						item.setXlMoney(String.valueOf(xlSummary));
						item.setSummary(String.valueOf(summary));
					}else {
						Double zqSummary = 40 * Double.parseDouble(zqDays);
						Double jbSummary = 177.5 * (Double.parseDouble(jbDays) + Double.parseDouble(jfDays));
						Double xlSummary = 0.0;
						Double summary = zqSummary + jbSummary + xlSummary;
						item.setZqMoney(String.valueOf(zqSummary));
						item.setJbMoney(String.valueOf(jbSummary));
						item.setXlMoney(String.valueOf(xlSummary));
						item.setSummary(String.valueOf(summary));
					}
				}
				else {
					if ("0".equals(zqType)){
						if ("0".equals(xlType)){
							Double xlSummary = 0.0;
							Double zqSummary = 50 * Double.parseDouble(zqDays);
							Double jbSummary = 177.5 * (Double.parseDouble(jbDays) + Double.parseDouble(jfDays));
							if (Double.valueOf(sumDay) <= Double.parseDouble(qqDays)){
								xlSummary = 0.0;
								if (xlSummary <= 0){
									xlSummary = 0.0;
									realXlSummary = 0.0;
								}else {
//								xlSummary = xlSummary;
									BigDecimal bg = new BigDecimal(xlSummary);
									realXlSummary = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
								}
							}else {
								xlSummary = 833.2 - (40 * Double.parseDouble(qqDays));
								if (xlSummary <= 0){
									xlSummary = 0.0;
									realXlSummary = 0.0;
								}else {
//								xlSummary = xlSummary;
									BigDecimal bg = new BigDecimal(xlSummary);
									realXlSummary = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
								}
							}
//							Double xlSummary = 833.2 - (40 * Double.parseDouble(qqDays));

							Double summary = zqSummary + jbSummary + realXlSummary;
							if (summary <= 0){
								summary = 0.0;
							}else {
								summary = summary;
							}
							item.setZqMoney(String.valueOf(zqSummary));
							item.setJbMoney(String.valueOf(jbSummary));
							item.setXlMoney(String.valueOf(realXlSummary));
							item.setSummary(String.valueOf(summary));
						}else
						{
							Double xlSummary = 0.0;
							Double zqSummary = 50 * Double.parseDouble(zqDays);
							Double jbSummary = 177.5 * (Double.parseDouble(jbDays) + Double.parseDouble(jfDays));
							if (Double.valueOf(sumDay) <= Double.parseDouble(qqDays)){
								xlSummary = 0.0;
								if (xlSummary <= 0){
									xlSummary = 0.0;
									realXlSummary = 0.0;
								}else {
//								xlSummary = xlSummary;
									BigDecimal bg = new BigDecimal(xlSummary);
									realXlSummary = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
								}
							}else {
								xlSummary = 520.75 - (25 * Double.parseDouble(qqDays));
								if (xlSummary <= 0){
									xlSummary = 0.0;
									realXlSummary = 0.0;
								}else {
//								xlSummary = xlSummary;
									BigDecimal bg = new BigDecimal(xlSummary);
									realXlSummary = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
								}
							}
//							Double xlSummary = 520.75 - (25 * Double.parseDouble(qqDays));

							Double summary = zqSummary + jbSummary + realXlSummary;
							if (summary <= 0){
								summary = 0.0;
							}else {
								summary = summary;
							}
							item.setZqMoney(String.valueOf(zqSummary));
							item.setJbMoney(String.valueOf(jbSummary));
							item.setXlMoney(String.valueOf(realXlSummary));
							item.setSummary(String.valueOf(summary));
						}
					}
					else {
						if ("0".equals(xlType)){
							Double xlSummary = 0.0;
							Double zqSummary = 40 * Double.parseDouble(zqDays);
							Double jbSummary = 177.5 * (Double.parseDouble(jbDays) + Double.parseDouble(jfDays));
							if (Double.valueOf(sumDay) <= Double.parseDouble(qqDays)){
								xlSummary = 0.0;
								if (xlSummary <= 0){
									xlSummary = 0.0;
									realXlSummary = 0.0;
								}else {
//								xlSummary = xlSummary;
									BigDecimal bg = new BigDecimal(xlSummary);
									realXlSummary = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
								}
							}else {
								xlSummary = 833.2 - (40 * Double.parseDouble(qqDays));
								if (xlSummary <= 0){
									xlSummary = 0.0;
									realXlSummary = 0.0;
								}else {
//								xlSummary = xlSummary;
									BigDecimal bg = new BigDecimal(xlSummary);
									realXlSummary = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
								}
							}
//							Double xlSummary = 833.2 - (40 * Double.parseDouble(qqDays));

							Double summary = zqSummary + jbSummary + realXlSummary;
							if (summary <= 0){
								summary = 0.0;
							}else {
								summary = summary;
							}
							item.setZqMoney(String.valueOf(zqSummary));
							item.setJbMoney(String.valueOf(jbSummary));
							item.setXlMoney(String.valueOf(realXlSummary));
							item.setSummary(String.valueOf(summary));
						}else
						{
							Double xlSummary = 0.0;
							Double zqSummary = 40 * Double.parseDouble(zqDays);
							Double jbSummary = 177.5 * (Double.parseDouble(jbDays) + Double.parseDouble(jfDays));
							if (Double.valueOf(sumDay) <= Double.parseDouble(qqDays)){
								xlSummary = 0.0;
								if (xlSummary <= 0){
									xlSummary = 0.0;
									realXlSummary = 0.0;
								}else {
//								xlSummary = xlSummary;
									BigDecimal bg = new BigDecimal(xlSummary);
									realXlSummary = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
								}
							}else {
								xlSummary = 520.75 - (25 * Double.parseDouble(qqDays));
								if (xlSummary <= 0){
									xlSummary = 0.0;
									realXlSummary = 0.0;
								}else {
//								xlSummary = xlSummary;
									BigDecimal bg = new BigDecimal(xlSummary);
									realXlSummary = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
								}
							}
//							Double xlSummary = 520.75 - (25 * Double.parseDouble(qqDays));

							Double summary = zqSummary + jbSummary + realXlSummary;
							if (summary <= 0){
								summary = 0.0;
							}else {
								summary = summary;
							}
							item.setZqMoney(String.valueOf(zqSummary));
							item.setJbMoney(String.valueOf(jbSummary));
							item.setXlMoney(String.valueOf(realXlSummary));
							item.setSummary(String.valueOf(summary));
						}
					}
				}
			});
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
		return "redirect:" + adminPath + "/affair/affairAttendanceSummary/?repage";
	}

}