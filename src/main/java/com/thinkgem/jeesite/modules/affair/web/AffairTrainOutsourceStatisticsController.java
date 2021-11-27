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
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.modules.affair.dao.AffairTrainOutsourceDao;
import com.thinkgem.jeesite.modules.affair.entity.*;
import com.thinkgem.jeesite.modules.affair.service.AffairTrainOutsourceService;
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
import com.thinkgem.jeesite.modules.affair.service.AffairTrainOutsourceStatisticsService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 委外培训统计Controller
 * @author alan.wu
 * @version 2020-07-30
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairTrainOutsourceStatistics")
public class AffairTrainOutsourceStatisticsController extends BaseController {

	@Autowired
	private AffairTrainOutsourceStatisticsService affairTrainOutsourceStatisticsService;

	@Autowired
	private AffairTrainOutsourceService affairTrainOutsourceService;

	@Autowired
	private AffairTrainOutsourceDao affairTrainOutsourceDao;

	@Autowired
	private OfficeService officeService;
	
	@ModelAttribute
	public AffairTrainOutsourceStatistics get(@RequestParam(required=false) String id) {
		AffairTrainOutsourceStatistics entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairTrainOutsourceStatisticsService.get(id);
		}
		if (entity == null){
			entity = new AffairTrainOutsourceStatistics();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairTrainOutsourceStatistics:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairTrainOutsourceStatistics affairTrainOutsourceStatistics, HttpServletRequest request, HttpServletResponse response, Model model) {

		/*//所有的单位id
		List<String> idList = new ArrayList<>();
		idList = affairTrainOutsourceDao.selectAllId();

		String unitid = affairTrainOutsourceStatistics.getUnitId();

		List<AffairTrainOutsourceStatistics> list = new ArrayList<>();

		if (StringUtils.isNotBlank(unitid)){

			AffairTrainOutsourceStatistics affairTrainOutsourceStatistics2 = new AffairTrainOutsourceStatistics();

			//单位
			String unitName = affairTrainOutsourceDao.selectUnit(unitid);

			//总人数
			String sumPeople = affairTrainOutsourceDao.selectPeople(unitid);

			//全脱产人数
			String off = "1";
			String offJob = affairTrainOutsourceDao.selectOffJob(unitid,off);

			//半脱产人数
			String half = "2";
			String halfJob = affairTrainOutsourceDao.selectOffJob(unitid,half);

			//不脱产人数
			String not = "3";
			String notJob = affairTrainOutsourceDao.selectOffJob(unitid,not);

			//公安部人数
			String h = "1";
			String hq = affairTrainOutsourceDao.selecthq(unitid,h);

			//省部人数
			String pr = "2";
			String provincial = affairTrainOutsourceDao.selecthq(unitid,pr);

			//市部人数
			String ci = "3";
			String city = affairTrainOutsourceDao.selecthq(unitid,ci);

			//县部人数
			String co = "4";
			String county = affairTrainOutsourceDao.selecthq(unitid,co);

			affairTrainOutsourceStatistics2.setUnit(unitName);
			affairTrainOutsourceStatistics2.setUnitId(unitid);
			affairTrainOutsourceStatistics2.setPeopleSum(sumPeople);
			affairTrainOutsourceStatistics2.setOffJob(offJob);
			affairTrainOutsourceStatistics2.setHalfJob(halfJob);
			affairTrainOutsourceStatistics2.setNotJob(notJob);
			affairTrainOutsourceStatistics2.setHq(hq);
			affairTrainOutsourceStatistics2.setProvincial(provincial);
			affairTrainOutsourceStatistics2.setCity(city);
			affairTrainOutsourceStatistics2.setCounty(county);

			if (StringUtils.isNotBlank(unitName)){
				list.add(affairTrainOutsourceStatistics2);
			}

		}else {

		for (int i = 0; i < idList.size(); i++){

			AffairTrainOutsourceStatistics affairTrainOutsourceStatistics1 = new AffairTrainOutsourceStatistics();
			//单位id
			String id = idList.get(i);

			//单位
			String unitName = affairTrainOutsourceDao.selectUnit(id);

			//总人数
			String sumPeople = affairTrainOutsourceDao.selectPeople(id);

			//全脱产人数
			String off = "1";
			String offJob = affairTrainOutsourceDao.selectOffJob(id,off);

			//半脱产人数
			String half = "2";
			String halfJob = affairTrainOutsourceDao.selectOffJob(id,half);

			//不脱产人数
			String not = "3";
			String notJob = affairTrainOutsourceDao.selectOffJob(id,not);

			//公安部人数
			String h = "1";
			String hq = affairTrainOutsourceDao.selecthq(id,h);

			//省部人数
			String pr = "2";
			String provincial = affairTrainOutsourceDao.selecthq(id,pr);

			//市部人数
			String ci = "3";
			String city = affairTrainOutsourceDao.selecthq(id,ci);

			//县部人数
			String co = "4";
			String county = affairTrainOutsourceDao.selecthq(id,co);

			affairTrainOutsourceStatistics1.setUnit(unitName);
			affairTrainOutsourceStatistics1.setUnitId(id);
			affairTrainOutsourceStatistics1.setPeopleSum(sumPeople);
			affairTrainOutsourceStatistics1.setOffJob(offJob);
			affairTrainOutsourceStatistics1.setHalfJob(halfJob);
			affairTrainOutsourceStatistics1.setNotJob(notJob);
			affairTrainOutsourceStatistics1.setHq(hq);
			affairTrainOutsourceStatistics1.setProvincial(provincial);
			affairTrainOutsourceStatistics1.setCity(city);
			affairTrainOutsourceStatistics1.setCounty(county);

			list.add(affairTrainOutsourceStatistics1);
			}
		}
			model.addAttribute("list", list);
*/

		Page<AffairTrainOutsourceStatistics> page = affairTrainOutsourceStatisticsService.findPage(new Page<AffairTrainOutsourceStatistics>(request, response), affairTrainOutsourceStatistics);
		model.addAttribute("page",page);
		return "modules/affair/affairTrainOutsourceStatisticsList";
	}

	@RequiresPermissions("affair:affairTrainOutsourceStatistics:view")
	@RequestMapping(value = "form")
	public String form(AffairTrainOutsourceStatistics affairTrainOutsourceStatistics, Model model) {
		model.addAttribute("affairTrainOutsourceStatistics", affairTrainOutsourceStatistics);
		return "modules/affair/affairTrainOutsourceStatisticsForm";
	}

	@RequiresPermissions("affair:affairTrainOutsourceStatistics:edit")
	@RequestMapping(value = "save")
	public String save(AffairTrainOutsourceStatistics affairTrainOutsourceStatistics, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, affairTrainOutsourceStatistics)){
			return form(affairTrainOutsourceStatistics, model);
		}
		affairTrainOutsourceStatisticsService.save(affairTrainOutsourceStatistics);
		addMessage(redirectAttributes, "保存委外培训统计成功");
		model.addAttribute("saveResult","success");
		return "modules/affair/affairTrainOutsourceStatisticsForm";
	}
	
	@RequiresPermissions("affair:affairTrainOutsourceStatistics:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairTrainOutsourceStatistics affairTrainOutsourceStatistics, RedirectAttributes redirectAttributes) {
		affairTrainOutsourceStatisticsService.delete(affairTrainOutsourceStatistics);
		addMessage(redirectAttributes, "删除委外培训统计成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairTrainOutsourceStatistics/?repage";
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
	public String exportExcelByTemplate(AffairTrainOutsourceStatistics affairTrainOutsourceStatistics, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {

		XSSFWorkbook wb = null;
		try {

			if ("5".equals(UserUtils.getUser().getOffice().getId())) {
				affairTrainOutsourceStatistics.setUnitId(UserUtils.getUser().getCompany().getId());
			} else {
				affairTrainOutsourceStatistics.setUnitId(UserUtils.getUser().getOffice().getId());
			}
			affairTrainOutsourceStatistics.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if (request.getParameter("fileName") != null && !"".equals(request.getParameter("fileName"))) {
				fileName = request.getParameter("fileName").toString();
			}
			Page<AffairTrainOutsourceStatistics> page = null;
			if (flag == true) {
				page = affairTrainOutsourceStatisticsService.findPage(new Page<AffairTrainOutsourceStatistics>(request, response), affairTrainOutsourceStatistics);
			} else {
				page = affairTrainOutsourceStatisticsService.findPage(new Page<AffairTrainOutsourceStatistics>(request, response, -1), affairTrainOutsourceStatistics);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairTrainOutsourceStatistics.class);
			exportExcelNew.setWb(wb);
			List<AffairTrainOutsourceStatistics> list = page.getList();
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
		return "redirect:" + adminPath + "/affair/affairTrainOutsourceStatistics?repage";
	}

	@RequestMapping(value = "import", method = RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<AffairTrainOutsourceStatistics> list = ei.getDataList(AffairTrainOutsourceStatistics.class);
			for (AffairTrainOutsourceStatistics affairTrainOutsourceStatistics : list) {
				try {
					affairTrainOutsourceStatistics.setUnitId(officeService.findByName(affairTrainOutsourceStatistics.getUnit()));
					BeanValidators.validateWithException(validator, affairTrainOutsourceStatistics);
					affairTrainOutsourceStatisticsService.save(affairTrainOutsourceStatistics);
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
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairTrainOutsourceStatistics:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if (ids != null && ids.size() > 0) {
			affairTrainOutsourceStatisticsService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		} else {
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	/**
	 * 详情
	 *
	 * @param affairTrainOutsourceStatistics
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairTrainOutsourceStatistics:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairTrainOutsourceStatistics affairTrainOutsourceStatistics, Model model) {
		model.addAttribute("affairTrainOutsourceStatistics", affairTrainOutsourceStatistics);

		return "modules/affair/affairTrainOutsourceStatisticsFormDetail";
	}
}