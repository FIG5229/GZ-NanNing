/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcelNew;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.web.UploadController;
import com.thinkgem.jeesite.modules.affair.entity.AffairTalkManagement;
import com.thinkgem.jeesite.modules.affair.service.AffairTalkManagementService;
import com.thinkgem.jeesite.modules.exam.entity.ExamAutoEvaluation;
import com.thinkgem.jeesite.modules.exam.service.ExamAutoEvaluationService;
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

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 谈话函询管理Controller
 * @author cecil.li
 * @version 2019-11-08
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairTalkManagement")
public class AffairTalkManagementController extends BaseController {

	@Autowired
	private OfficeService officeService;

	@Autowired
	private AffairTalkManagementService affairTalkManagementService;

	@Autowired
	private UploadController uploadController;

	@Autowired
	private ExamAutoEvaluationService examAutoEvaluationService;
	
	@ModelAttribute
	public AffairTalkManagement get(@RequestParam(required=false) String id) {
		AffairTalkManagement entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairTalkManagementService.get(id);
		}
		if (entity == null){
			entity = new AffairTalkManagement();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairTalkManagement:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairTalkManagement affairTalkManagement, HttpServletRequest request, HttpServletResponse response, Model model) {
		if ("6".equals(UserUtils.getUser().getOffice().getId())){
			affairTalkManagement.setQxUnitId(UserUtils.getUser().getCompany().getId());
		}else {
			affairTalkManagement.setQxUnitId(UserUtils.getUser().getOffice().getId());
		}
		affairTalkManagement.setCreateBy(UserUtils.getUser());
		String reason = request.getParameter("reason");//2 全部 3 其他年份
		model.addAttribute("reason",reason);
		if("3".equals(reason)){
			affairTalkManagement.setStartDate(null);
			affairTalkManagement.setEndDate(null);
		}else if("2".equals(reason)){
			//全部
			if(affairTalkManagement.getStartDate()==null && affairTalkManagement.getEndDate() == null){
				String year = DateUtils.getYear();//获取当前年
				affairTalkManagement.setOtherYear(year);
			}else{
				affairTalkManagement.setOtherYear(null);
			}
		}else{
			//默认显示当前年度数据
			String year =DateUtils.getYear();//获取当前年
			affairTalkManagement.setOtherYear(year);
			affairTalkManagement.setStartDate(null);
			affairTalkManagement.setEndDate(null);
		}
		Page<AffairTalkManagement> page = affairTalkManagementService.findPage(new Page<AffairTalkManagement>(request, response), affairTalkManagement);
		model.addAttribute("page", page);
		/*if(affairTalkManagement.getStartDate() != null || affairTalkManagement.getEndDate() != null){
			Page<AffairTalkManagement> page = affairTalkManagementService.findPage(new Page<AffairTalkManagement>(request, response), affairTalkManagement);
			model.addAttribute("page", page);
		}else if (affairTalkManagement.getStartDate() == null && affairTalkManagement.getEndDate() == null){
			if ("".equals(affairTalkManagement.getOtherYear()) || affairTalkManagement.getOtherYear() == null){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
				Date date = new Date();
				String nowDate = sdf.format(date);
				Date yearDate = null;
				try {
					yearDate = sdf.parse(nowDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				affairTalkManagement.setStartYear(yearDate);
				model.addAttribute("startYear", yearDate);
				Page<AffairTalkManagement> page = affairTalkManagementService.findPage(new Page<AffairTalkManagement>(request, response), affairTalkManagement);
				model.addAttribute("page", page);
			}

		}else if (affairTalkManagement.getOtherYear() != null && !"".equals(affairTalkManagement.getOtherYear())){
			Page<AffairTalkManagement> page = affairTalkManagementService.findPage(new Page<AffairTalkManagement>(request, response), affairTalkManagement);
			model.addAttribute("page", page);
		}*/
		return "modules/affair/affairTalkManagementList";
	}

	@RequiresPermissions("affair:affairTalkManagement:add")
	@RequestMapping(value = "form")
	public String form(AffairTalkManagement affairTalkManagement, Model model) {
		model.addAttribute("affairTalkManagement", affairTalkManagement);
		return "modules/affair/affairTalkManagementForm";
	}

	/**
	 * 统计汇总页面
	 * @param affairTalkManagement
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairTalkManagement:view")
	@RequestMapping(value = "affairTalkManagementListSum")
	public String Sum(AffairTalkManagement affairTalkManagement ,Model model) {
		List<Map<String, Object>> list = affairTalkManagementService.findStatistic(affairTalkManagement);
		model.addAttribute("list", list);
		Integer sum = 0;
		for (Map<String, Object> m:list) {
			sum += ((Long) m.get("num")).intValue();
		}
		model.addAttribute("sum", sum);
		return "modules/affair/affairTalkManagementListSum";
	}

	@RequiresPermissions("affair:affairTalkManagement:edit")
	@RequestMapping(value = "save")
	public String save(AffairTalkManagement affairTalkManagement, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairTalkManagement)){
			return form(affairTalkManagement, model);
		}
		/*//局考局机关月度绩效自动考评
		if (affairTalkManagement.getUnit().contains("公安局")){
			if ("6".equals(affairTalkManagement.getLetterCategory())){
				String unit = affairTalkManagement.getUnit();
				String code = affairTalkManagementService.findCodeByUnit(unit);
				String userId = affairTalkManagementService.findUserIdByCode(code);
				Date time = affairTalkManagement.getTime();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(time);
				String year = String.valueOf(calendar.get(Calendar.YEAR));
				String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
				ExamAutoEvaluation examAutoEvaluationJjg = new ExamAutoEvaluation();
				examAutoEvaluationJjg.setEvaluationId(userId);
				examAutoEvaluationJjg.setType("1");
				examAutoEvaluationJjg.setEvalType("2");
				examAutoEvaluationJjg.setPeriod("1");
				examAutoEvaluationJjg.setYear(year);
				examAutoEvaluationJjg.setMonth(month);
				examAutoEvaluationJjg.setDetails("民警被诫勉谈话");
				examAutoEvaluationJjg.setUnit(affairTalkManagement.getUnit());
				examAutoEvaluationJjg.setUnitId(affairTalkManagement.getUnitId());
				examAutoEvaluationJjg.setAssess("机关考评小组");
				examAutoEvaluationJjg.setRemark("每人次");
				examAutoEvaluationJjg.setModel("2020年公安局对局机关各处室及直属支队月度绩效考核公共扣分标准");
				examAutoEvaluationJjg.setModelId("7112140f6a7945a9ba1e32e7f06e7480");
				examAutoEvaluationJjg.setOption("受到诫勉谈话");
				examAutoEvaluationJjg.setOptionId("acb6fee097644889b65447dce1fa561d");
				examAutoEvaluationJjg.setScore("-4");
				examAutoEvaluationJjg.setTime(time);
				examAutoEvaluationJjg.setHappenTime(time);
				examAutoEvaluationJjg.setCheckTime(time);
				examAutoEvaluationService.save(examAutoEvaluationJjg);
			}
		}
		// 处考处机关月度
		//处考处机关民警被提醒谈话的
		else if ((affairTalkManagement.getUnit().contains("南宁处") || affairTalkManagement.getUnit().contains("柳州处") || affairTalkManagement.getUnit().contains("北海处")) && !affairTalkManagement.getUnit().contains("支队") && !affairTalkManagement.getUnit().contains("派出所")){
			if ("1".equals(affairTalkManagement.getLetterCategory())){
				String unit = affairTalkManagement.getUnit();
				String code = affairTalkManagementService.findCodeByUnit(unit);
				String userId = affairTalkManagementService.findUserIdByCode(code);
				Date time = affairTalkManagement.getTime();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(time);
				String year = String.valueOf(calendar.get(Calendar.YEAR));
				String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
				ExamAutoEvaluation examAutoEvaluationJjg = new ExamAutoEvaluation();
				examAutoEvaluationJjg.setEvaluationId(userId);
				examAutoEvaluationJjg.setType("1");
				examAutoEvaluationJjg.setEvalType("3");
				examAutoEvaluationJjg.setPeriod("1");
				examAutoEvaluationJjg.setYear(year);
				examAutoEvaluationJjg.setMonth(month);
				examAutoEvaluationJjg.setDetails("");
				examAutoEvaluationJjg.setUnit("");
				examAutoEvaluationJjg.setUnitId("");
				examAutoEvaluationJjg.setAssess("");
				examAutoEvaluationJjg.setRemark("");
				examAutoEvaluationJjg.setModel("");
				examAutoEvaluationJjg.setModelId("");
				examAutoEvaluationJjg.setOption("");
				examAutoEvaluationJjg.setOptionId("");
				examAutoEvaluationJjg.setScore("");
				examAutoEvaluationJjg.setTime(time);
				examAutoEvaluationJjg.setHappenTime(time);
				examAutoEvaluationJjg.setCheckTime(time);
				examAutoEvaluationService.save(examAutoEvaluationJjg);
			}
			// 处考处机关月度
			//处考处机关民警被诫勉谈话的
			else if ("6".equals(affairTalkManagement.getLetterCategory())){
				String unit = affairTalkManagement.getUnit();
				String code = affairTalkManagementService.findCodeByUnit(unit);
				String userId = affairTalkManagementService.findUserIdByCode(code);
				Date time = affairTalkManagement.getTime();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(time);
				String year = String.valueOf(calendar.get(Calendar.YEAR));
				String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
				ExamAutoEvaluation examAutoEvaluationJjg = new ExamAutoEvaluation();
				examAutoEvaluationJjg.setEvaluationId(userId);
				examAutoEvaluationJjg.setType("1");
				examAutoEvaluationJjg.setEvalType("3");
				examAutoEvaluationJjg.setPeriod("1");
				examAutoEvaluationJjg.setYear(year);
				examAutoEvaluationJjg.setMonth(month);
				examAutoEvaluationJjg.setDetails("");
				examAutoEvaluationJjg.setUnit("");
				examAutoEvaluationJjg.setUnitId("");
				examAutoEvaluationJjg.setAssess("");
				examAutoEvaluationJjg.setRemark("");
				examAutoEvaluationJjg.setModel("");
				examAutoEvaluationJjg.setModelId("");
				examAutoEvaluationJjg.setOption("");
				examAutoEvaluationJjg.setOptionId("");
				examAutoEvaluationJjg.setScore("");
				examAutoEvaluationJjg.setTime(time);
				examAutoEvaluationJjg.setHappenTime(time);
				examAutoEvaluationJjg.setCheckTime(time);
				examAutoEvaluationService.save(examAutoEvaluationJjg);
			}
		}
		// 处考队所
		else if (affairTalkManagement.getUnit().contains("支队") || affairTalkManagement.getUnit().contains("派出所")){
			//处考队所民警被提醒谈话的
			if ("1".equals(affairTalkManagement.getLetterCategory())){
				String unit = affairTalkManagement.getUnit();
				String code = affairTalkManagementService.findCodeByUnit(unit);
				String userId = affairTalkManagementService.findUserIdByCode(code);
				Date time = affairTalkManagement.getTime();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(time);
				String year = String.valueOf(calendar.get(Calendar.YEAR));
				String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
				ExamAutoEvaluation examAutoEvaluationJjg = new ExamAutoEvaluation();
				examAutoEvaluationJjg.setEvaluationId(userId);
				examAutoEvaluationJjg.setType("1");
				examAutoEvaluationJjg.setEvalType("4");
				examAutoEvaluationJjg.setPeriod("1");
				examAutoEvaluationJjg.setYear(year);
				examAutoEvaluationJjg.setMonth(month);
				examAutoEvaluationJjg.setDetails("");
				examAutoEvaluationJjg.setUnit("");
				examAutoEvaluationJjg.setUnitId("");
				examAutoEvaluationJjg.setAssess("");
				examAutoEvaluationJjg.setRemark("");
				examAutoEvaluationJjg.setModel("");
				examAutoEvaluationJjg.setModelId("");
				examAutoEvaluationJjg.setOption("");
				examAutoEvaluationJjg.setOptionId("");
				examAutoEvaluationJjg.setScore("");
				examAutoEvaluationJjg.setTime(time);
				examAutoEvaluationJjg.setHappenTime(time);
				examAutoEvaluationJjg.setCheckTime(time);
				examAutoEvaluationService.save(examAutoEvaluationJjg);
			}
			// 处考队所民警被诫勉谈话的
			else if ("6".equals(affairTalkManagement.getLetterCategory())){
				String unit = affairTalkManagement.getUnit();
				String code = affairTalkManagementService.findCodeByUnit(unit);
				String userId = affairTalkManagementService.findUserIdByCode(code);
				Date time = affairTalkManagement.getTime();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(time);
				String year = String.valueOf(calendar.get(Calendar.YEAR));
				String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
				ExamAutoEvaluation examAutoEvaluationJjg = new ExamAutoEvaluation();
				examAutoEvaluationJjg.setEvaluationId(userId);
				examAutoEvaluationJjg.setType("1");
				examAutoEvaluationJjg.setEvalType("4");
				examAutoEvaluationJjg.setPeriod("1");
				examAutoEvaluationJjg.setYear(year);
				examAutoEvaluationJjg.setMonth(month);
				examAutoEvaluationJjg.setDetails("");
				examAutoEvaluationJjg.setUnit("");
				examAutoEvaluationJjg.setUnitId("");
				examAutoEvaluationJjg.setAssess("");
				examAutoEvaluationJjg.setRemark("");
				examAutoEvaluationJjg.setModel("");
				examAutoEvaluationJjg.setModelId("");
				examAutoEvaluationJjg.setOption("");
				examAutoEvaluationJjg.setOptionId("");
				examAutoEvaluationJjg.setScore("");
				examAutoEvaluationJjg.setTime(time);
				examAutoEvaluationJjg.setHappenTime(time);
				examAutoEvaluationJjg.setCheckTime(time);
				examAutoEvaluationService.save(examAutoEvaluationJjg);
			}
		}*/
		affairTalkManagementService.save(affairTalkManagement);
		addMessage(redirectAttributes, "保存谈话函询成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairTalkManagementForm";
	}
	
	@RequiresPermissions("affair:affairTalkManagement:delete")
	@RequestMapping(value = "delete")
	public String delete(AffairTalkManagement affairTalkManagement, RedirectAttributes redirectAttributes) {
		affairTalkManagementService.delete(affairTalkManagement);
		addMessage(redirectAttributes, "删除谈话函询成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairTalkManagement/?repage";
	}

	/**
	 * 详情
	 * @param affairTalkManagement
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairTalkManagement:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairTalkManagement affairTalkManagement, Model model) {
		model.addAttribute("affairTalkManagement", affairTalkManagement);
		if(affairTalkManagement.getAnnex() != null && affairTalkManagement.getAnnex().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairTalkManagement.getAnnex());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairTalkManagementFormDetail";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairTalkManagement:delete")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairTalkManagementService.deleteByIds(ids);
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
	public String exportExcelByTemplate(AffairTalkManagement affairTalkManagement, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			if ("6".equals(UserUtils.getUser().getOffice().getId())){
				affairTalkManagement.setQxUnitId(UserUtils.getUser().getCompany().getId());
			}else {
				affairTalkManagement.setQxUnitId(UserUtils.getUser().getOffice().getId());
			}
			affairTalkManagement.setCreateBy(UserUtils.getUser());
			String fileName = "";
				if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName");
			}
			Page<AffairTalkManagement> page = null;
			String reason = request.getParameter("reason");//2 全部 3 其他年份
			if("3".equals(reason)){
				affairTalkManagement.setStartDate(null);
				affairTalkManagement.setEndDate(null);
			}else if("2".equals(reason)){
				//全部
				if(affairTalkManagement.getStartDate()==null && affairTalkManagement.getEndDate() == null){
					String year = DateUtils.getYear();//获取当前年
					affairTalkManagement.setOtherYear(year);
				}else{
					affairTalkManagement.setOtherYear(null);
				}
			}else{
				//默认显示当前年度数据
				String year =DateUtils.getYear();//获取当前年
				affairTalkManagement.setOtherYear(year);
				affairTalkManagement.setStartDate(null);
				affairTalkManagement.setEndDate(null);
			}
			if(flag == true){
				page = affairTalkManagementService.findPage(new Page<AffairTalkManagement>(request, response), affairTalkManagement);
			}else{
				page = affairTalkManagementService.findPage(new Page<AffairTalkManagement>(request, response,-1), affairTalkManagement);
			}
			/*if(flag == true){
				if(affairTalkManagement.getStartDate() != null || affairTalkManagement.getEndDate() != null){
					page = affairTalkManagementService.findPage(new Page<AffairTalkManagement>(request, response), affairTalkManagement);
				}else if (affairTalkManagement.getStartDate() == null && affairTalkManagement.getEndDate() == null){
					if ("".equals(affairTalkManagement.getOtherYear()) || affairTalkManagement.getOtherYear() == null){
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
						Date date = new Date();
						String nowDate = sdf.format(date);
						Date yearDate = null;
						try {
							yearDate = sdf.parse(nowDate);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						affairTalkManagement.setStartYear(yearDate);
						page = affairTalkManagementService.findPage(new Page<AffairTalkManagement>(request, response), affairTalkManagement);
					}

				}else if (affairTalkManagement.getOtherYear() != null && !"".equals(affairTalkManagement.getOtherYear())){
					 page = affairTalkManagementService.findPage(new Page<AffairTalkManagement>(request, response), affairTalkManagement);
				}
			}else {
				if(affairTalkManagement.getStartDate() != null || affairTalkManagement.getEndDate() != null){
					page = affairTalkManagementService.findPage(new Page<AffairTalkManagement>(request, response,-1), affairTalkManagement);
				}else if (affairTalkManagement.getStartDate() == null && affairTalkManagement.getEndDate() == null){
					if ("".equals(affairTalkManagement.getOtherYear()) || affairTalkManagement.getOtherYear() == null){
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
						Date date = new Date();
						String nowDate = sdf.format(date);
						Date yearDate = null;
						try {
							yearDate = sdf.parse(nowDate);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						affairTalkManagement.setStartYear(yearDate);
						page = affairTalkManagementService.findPage(new Page<AffairTalkManagement>(request, response,-1), affairTalkManagement);
					}

				}else if (affairTalkManagement.getOtherYear() != null && !"".equals(affairTalkManagement.getOtherYear())){
					page = affairTalkManagementService.findPage(new Page<AffairTalkManagement>(request, response,-1), affairTalkManagement);
				}
			}*/
/*
			Page<AffairTousujubaoguanli> page = affairTousujubaoguanliService.findPage(new Page<AffairTousujubaoguanli>(request, response,-1), affairTousujubaoguanli);
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairTalkManagement.class);
			exportExcelNew.setWb(wb);
			List<AffairTalkManagement> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairTalkManagement?repage";
	}

	@RequestMapping(value = {"findDetailInfoByZbUnit"})
	public String findDetailInfoByZbUnit(AffairTalkManagement affairTalkManagement, HttpServletRequest request, HttpServletResponse response, Model model){
		Page<AffairTalkManagement> page;
		page = affairTalkManagementService.findDeatilInfoByZbUnit(new Page<AffairTalkManagement>(request, response), affairTalkManagement);
		model.addAttribute("page", page);
		model.addAttribute("affairTalkManagement", affairTalkManagement);
		return "modules/charts/monitorThFirstJiLvDetail";
	}

	@RequestMapping(value = {"findDetailInfoByLx"})
	public String findDetailInfoByLx(AffairTalkManagement affairTalkManagement, HttpServletRequest request, HttpServletResponse response, Model model){
		Page<AffairTalkManagement> page;
		page = affairTalkManagementService.findDetailInfoByLx(new Page<AffairTalkManagement>(request, response), affairTalkManagement);
		model.addAttribute("page", page);
		model.addAttribute("affairTalkManagement", affairTalkManagement);
		return "modules/charts/monitorThSecondDetail";
	}

	/**
	 * 导入
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairTalkManagement> list = ei.getDataList(AffairTalkManagement.class);
			for (AffairTalkManagement affairTalkManagement : list){
				try{
					BeanValidators.validateWithException(validator, affairTalkManagement);
					affairTalkManagementService.save(affairTalkManagement);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("导入失败："+ex.getMessage());
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