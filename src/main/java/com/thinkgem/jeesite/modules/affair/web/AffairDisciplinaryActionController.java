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
import com.thinkgem.jeesite.modules.affair.entity.AffairDisciplinaryAction;
import com.thinkgem.jeesite.modules.affair.service.AffairDisciplinaryActionService;
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
 * 纪律处分Controller
 * @author cecil.li
 * @version 2019-11-08
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairDisciplinaryAction")
public class AffairDisciplinaryActionController extends BaseController {

	@Autowired
	private OfficeService officeService;

	@Autowired
	private AffairDisciplinaryActionService affairDisciplinaryActionService;

	@Autowired
	private UploadController uploadController;

	@Autowired
	private AffairTalkManagementService affairTalkManagementService;

	@Autowired
	private ExamAutoEvaluationService examAutoEvaluationService;
	
	@ModelAttribute
	public AffairDisciplinaryAction get(@RequestParam(required=false) String id) {
		AffairDisciplinaryAction entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairDisciplinaryActionService.get(id);
		}
		if (entity == null){
			entity = new AffairDisciplinaryAction();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairDisciplinaryAction:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairDisciplinaryAction affairDisciplinaryAction, HttpServletRequest request, HttpServletResponse response, Model model) {
		if ("6".equals(UserUtils.getUser().getOffice().getId())){
			affairDisciplinaryAction.setQxUnitId(UserUtils.getUser().getCompany().getId());
		}else {
			affairDisciplinaryAction.setQxUnitId(UserUtils.getUser().getOffice().getId());
		}
		affairDisciplinaryAction.setCreateBy(UserUtils.getUser());
		if("1".equals(affairDisciplinaryAction.getDisciplinaryType())){
			affairDisciplinaryAction.setSubOption(affairDisciplinaryAction.getXzSubOption());
		}else{
			affairDisciplinaryAction.setSubOption(affairDisciplinaryAction.getDjSubOption());
		}
		if("1".equals(affairDisciplinaryAction.getSubOption())){
			affairDisciplinaryAction.setZzSubOption(affairDisciplinaryAction.getRySubOption());
		}
		/*else {
			affairDisciplinaryAction.setZzSubOption(affairDisciplinaryAction.getDzzSubOption());
		}*/
		/*if("1".equals(affairDisciplinaryAction.getSubOption())){
			affairDisciplinaryAction.setRySubOption(affairDisciplinaryAction.getZzSubOption());
		}else {
			affairDisciplinaryAction.setDzzSubOption(affairDisciplinaryAction.getZzSubOption());
		}*/
		String reason = request.getParameter("reason");//2 全部 3 其他年份
		model.addAttribute("reason",reason);
		if("3".equals(reason)){
			affairDisciplinaryAction.setStartDate(null);
			affairDisciplinaryAction.setEndDate(null);
		}else if("2".equals(reason)){
			//全部
			if(affairDisciplinaryAction.getStartDate()==null && affairDisciplinaryAction.getEndDate() == null){
				String year = DateUtils.getYear();//获取当前年
				affairDisciplinaryAction.setOtherYear(year);
			}else{
				affairDisciplinaryAction.setOtherYear(null);
			}
		}else{
			//默认显示当前年度数据
			String year =DateUtils.getYear();//获取当前年
			affairDisciplinaryAction.setOtherYear(year);
			affairDisciplinaryAction.setStartDate(null);
			affairDisciplinaryAction.setEndDate(null);
		}
		Page<AffairDisciplinaryAction> page = affairDisciplinaryActionService.findPage(new Page<AffairDisciplinaryAction>(request, response), affairDisciplinaryAction);
		model.addAttribute("page", page);
		/*if (affairDisciplinaryAction.getStartApprovalDate() != null || affairDisciplinaryAction.getEndApprovalDate() != null){
			Page<AffairDisciplinaryAction> page = affairDisciplinaryActionService.findPage(new Page<AffairDisciplinaryAction>(request, response), affairDisciplinaryAction);
			model.addAttribute("page", page);
		}else if (affairDisciplinaryAction.getStartApprovalDate() == null && affairDisciplinaryAction.getEndApprovalDate() == null){
			if ("".equals(affairDisciplinaryAction.getOtherYear()) || affairDisciplinaryAction.getOtherYear() == null){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
				Date date = new Date();
				String nowDate = sdf.format(date);
				Date yearDate = null;
				try {
					yearDate = sdf.parse(nowDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				affairDisciplinaryAction.setStartYear(yearDate);
				model.addAttribute("startYear", yearDate);
				Page<AffairDisciplinaryAction> page = affairDisciplinaryActionService.findPage(new Page<AffairDisciplinaryAction>(request, response), affairDisciplinaryAction);
				model.addAttribute("page", page);
			}

		}else if (affairDisciplinaryAction.getOtherYear() != null && !"".equals(affairDisciplinaryAction.getOtherYear())){
			Page<AffairDisciplinaryAction> page = affairDisciplinaryActionService.findPage(new Page<AffairDisciplinaryAction>(request, response), affairDisciplinaryAction);
			model.addAttribute("page", page);
		}*/
		/*if (affairDisciplinaryAction.getOtherYear() != null){
			Page<AffairDisciplinaryAction> page = affairDisciplinaryActionService.findPage(new Page<AffairDisciplinaryAction>(request, response), affairDisciplinaryAction);
			model.addAttribute("page", page);
		}else
		{
			Page<AffairDisciplinaryAction> page = affairDisciplinaryActionService.findPage(new Page<AffairDisciplinaryAction>(request, response), affairDisciplinaryAction);
			model.addAttribute("page", page);
		}*/

		return "modules/affair/affairDisciplinaryActionList";
	}

	@RequiresPermissions("affair:affairDisciplinaryAction:view")
	@RequestMapping(value = "affairDisciplinaryActionListSum")
	public String Sum() {
		return "modules/affair/affairDisciplinaryActionListSum";
	}

	@RequiresPermissions("affair:affairDisciplinaryAction:add")
	@RequestMapping(value = "form")
	public String form(AffairDisciplinaryAction affairDisciplinaryAction, Model model) {
		model.addAttribute("affairDisciplinaryAction", affairDisciplinaryAction);
		return "modules/affair/affairDisciplinaryActionForm";
	}

	@RequiresPermissions("affair:affairDisciplinaryAction:edit")
	@RequestMapping(value = "save")
	public String save(AffairDisciplinaryAction affairDisciplinaryAction, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairDisciplinaryAction)){
			return form(affairDisciplinaryAction, model);
		}

		if("1".equals(affairDisciplinaryAction.getDisciplinaryType())){
//			affairDisciplinaryAction.setXzSubOption(affairDisciplinaryAction.getSubOption());
			affairDisciplinaryAction.setSubOption(affairDisciplinaryAction.getXzSubOption());
		}else{
//			affairDisciplinaryAction.setDjSubOption(affairDisciplinaryAction.getSubOption());
			affairDisciplinaryAction.setSubOption(affairDisciplinaryAction.getDjSubOption());
		}
		if("1".equals(affairDisciplinaryAction.getSubOption())){
			affairDisciplinaryAction.setZzSubOption(affairDisciplinaryAction.getRySubOption());
		}
		/*else {
			affairDisciplinaryAction.setZzSubOption(affairDisciplinaryAction.getDzzSubOption());
		}*/
		/*//局考局机关月度绩效自动考评
		if (affairDisciplinaryAction.getUnit().contains("公安局")){
			//局考局机关，受到行政警告
			if ("1".equals(affairDisciplinaryAction.getDisciplinaryType()) && "1".equals(affairDisciplinaryAction.getXzSubOption())){
				String unit = affairDisciplinaryAction.getUnit();
				String code = affairTalkManagementService.findCodeByUnit(unit);
				String userId = affairTalkManagementService.findUserIdByCode(code);
				Date lrDate = affairDisciplinaryAction.getLrDate();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(lrDate);
				String year = String.valueOf(calendar.get(Calendar.YEAR));
				String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
				ExamAutoEvaluation examAutoEvaluationJjg = new ExamAutoEvaluation();
				examAutoEvaluationJjg.setEvaluationId(userId);
				examAutoEvaluationJjg.setType("1");
				examAutoEvaluationJjg.setEvalType("2");
				examAutoEvaluationJjg.setPeriod("1");
				examAutoEvaluationJjg.setYear(year);
				examAutoEvaluationJjg.setMonth(month);
				examAutoEvaluationJjg.setDetails("受到行政警告的");
				examAutoEvaluationJjg.setUnit(affairDisciplinaryAction.getUnit());
				examAutoEvaluationJjg.setUnitId(affairDisciplinaryAction.getUnitId());
				examAutoEvaluationJjg.setAssess("机关考评小组");
				examAutoEvaluationJjg.setRemark("每人次");
				examAutoEvaluationJjg.setModel("2020年公安局对局机关各处室及直属支队月度绩效考核公共扣分标准");
				examAutoEvaluationJjg.setModelId("7112140f6a7945a9ba1e32e7f06e7480");
				examAutoEvaluationJjg.setOption("受到行政警告处分");
				examAutoEvaluationJjg.setOptionId("0b95b94b74944defb57b47882625877c");
				examAutoEvaluationJjg.setScore("-6");
				examAutoEvaluationJjg.setTime(lrDate);
				examAutoEvaluationJjg.setHappenTime(lrDate);
				examAutoEvaluationJjg.setCheckTime(lrDate);
				examAutoEvaluationService.save(examAutoEvaluationJjg);
			}
			//局考局机关收到行政记过处分、党内警告处分
			else if (("1".equals(affairDisciplinaryAction.getDisciplinaryType()) && "2".equals(affairDisciplinaryAction.getDisciplinaryType()) || ("2".equals(affairDisciplinaryAction.getDisciplinaryType()) && "1".equals(affairDisciplinaryAction.getDjSubOption()) && "1".equals(affairDisciplinaryAction.getRySubOption())))){
				String unit = affairDisciplinaryAction.getUnit();
				String code = affairTalkManagementService.findCodeByUnit(unit);
				String userId = affairTalkManagementService.findUserIdByCode(code);
				Date lrDate = affairDisciplinaryAction.getLrDate();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(lrDate);
				String year = String.valueOf(calendar.get(Calendar.YEAR));
				String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
				ExamAutoEvaluation examAutoEvaluationJjg = new ExamAutoEvaluation();
				examAutoEvaluationJjg.setEvaluationId(userId);
				examAutoEvaluationJjg.setType("1");
				examAutoEvaluationJjg.setEvalType("2");
				examAutoEvaluationJjg.setPeriod("1");
				examAutoEvaluationJjg.setYear(year);
				examAutoEvaluationJjg.setMonth(month);
				examAutoEvaluationJjg.setDetails("受到行政记过处分、党内警告处分的");
				examAutoEvaluationJjg.setUnit(affairDisciplinaryAction.getUnit());
				examAutoEvaluationJjg.setUnitId(affairDisciplinaryAction.getUnitId());
				examAutoEvaluationJjg.setAssess("机关考评小组");
				examAutoEvaluationJjg.setRemark("每人次");
				examAutoEvaluationJjg.setModel("2020年公安局对局机关各处室及直属支队月度绩效考核公共扣分标准");
				examAutoEvaluationJjg.setModelId("7112140f6a7945a9ba1e32e7f06e7480");
				examAutoEvaluationJjg.setOption("受到行政记过、党内警告处分的");
				examAutoEvaluationJjg.setOptionId("5d7aa175842c448f9a4d793788861dbf");
				examAutoEvaluationJjg.setScore("-8");
				examAutoEvaluationJjg.setTime(lrDate);
				examAutoEvaluationJjg.setHappenTime(lrDate);
				examAutoEvaluationJjg.setCheckTime(lrDate);
				examAutoEvaluationService.save(examAutoEvaluationJjg);
			}
			//受到行政记大过、党内严重警告及以上处分
			else if (("1".equals(affairDisciplinaryAction.getDisciplinaryType()) && "3".equals(affairDisciplinaryAction.getXzSubOption())) || ("2".equals(affairDisciplinaryAction.getDisciplinaryType()) && "1".equals(affairDisciplinaryAction.getDjSubOption()) && "2".equals(affairDisciplinaryAction.getRySubOption()))){
				String unit = affairDisciplinaryAction.getUnit();
				String code = affairTalkManagementService.findCodeByUnit(unit);
				String userId = affairTalkManagementService.findUserIdByCode(code);
				Date lrDate = affairDisciplinaryAction.getLrDate();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(lrDate);
				String year = String.valueOf(calendar.get(Calendar.YEAR));
				String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
				ExamAutoEvaluation examAutoEvaluationJjg = new ExamAutoEvaluation();
				examAutoEvaluationJjg.setEvaluationId(userId);
				examAutoEvaluationJjg.setType("1");
				examAutoEvaluationJjg.setEvalType("2");
				examAutoEvaluationJjg.setPeriod("1");
				examAutoEvaluationJjg.setYear(year);
				examAutoEvaluationJjg.setMonth(month);
				examAutoEvaluationJjg.setDetails("受到行政记大过、党内严重警告处分的");
				examAutoEvaluationJjg.setUnit(affairDisciplinaryAction.getUnit());
				examAutoEvaluationJjg.setUnitId(affairDisciplinaryAction.getUnitId());
				examAutoEvaluationJjg.setAssess("机关考评小组");
				examAutoEvaluationJjg.setRemark("每人次");
				examAutoEvaluationJjg.setModel("2020年公安局对局机关各处室及直属支队月度绩效考核公共扣分标准");
				examAutoEvaluationJjg.setModelId("7112140f6a7945a9ba1e32e7f06e7480");
				examAutoEvaluationJjg.setOption("受到行政记大过、党内严重警告处分的");
				examAutoEvaluationJjg.setOptionId("9ca2e08940c14395b6602e5c6c3478dd");
				examAutoEvaluationJjg.setScore("-10");
				examAutoEvaluationJjg.setTime(lrDate);
				examAutoEvaluationJjg.setHappenTime(lrDate);
				examAutoEvaluationJjg.setCheckTime(lrDate);
				examAutoEvaluationService.save(examAutoEvaluationJjg);
			}
			// 因教育管理责任落实不到位，民警受到降级、撤销党内职务及以上处分的
			else {
				String unit = affairDisciplinaryAction.getUnit();
				String code = affairTalkManagementService.findCodeByUnit(unit);
				String userId = affairTalkManagementService.findUserIdByCode(code);
				Date lrDate = affairDisciplinaryAction.getLrDate();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(lrDate);
				String year = String.valueOf(calendar.get(Calendar.YEAR));
				String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
				ExamAutoEvaluation examAutoEvaluationJjg = new ExamAutoEvaluation();
				examAutoEvaluationJjg.setEvaluationId(userId);
				examAutoEvaluationJjg.setType("1");
				examAutoEvaluationJjg.setEvalType("2");
				examAutoEvaluationJjg.setPeriod("1");
				examAutoEvaluationJjg.setYear(year);
				examAutoEvaluationJjg.setMonth(month);
				examAutoEvaluationJjg.setDetails("因教育管理责任落实不到位，民警受到降级、撤销党内职务及以上处分的");
				examAutoEvaluationJjg.setUnit(affairDisciplinaryAction.getUnit());
				examAutoEvaluationJjg.setUnitId(affairDisciplinaryAction.getUnitId());
				examAutoEvaluationJjg.setAssess("机关考评小组");
				examAutoEvaluationJjg.setRemark("每人次");
				examAutoEvaluationJjg.setModel("2020年公安局对局机关各处室及直属支队月度绩效考核公共扣分标准");
				examAutoEvaluationJjg.setModelId("7112140f6a7945a9ba1e32e7f06e7480");
				examAutoEvaluationJjg.setOption("受降级、撤销党内职务及以上处分的");
				examAutoEvaluationJjg.setOptionId("38f89551fb64438796e77571895c085a");
				examAutoEvaluationJjg.setScore("-40");
				examAutoEvaluationJjg.setTime(lrDate);
				examAutoEvaluationJjg.setHappenTime(lrDate);
				examAutoEvaluationJjg.setCheckTime(lrDate);
				examAutoEvaluationService.save(examAutoEvaluationJjg);
			}
		}
		// 处考处机关月度
		//处考处机关民警受到行政警告处分、党内警告处分的
		else if ((affairDisciplinaryAction.getUnit().contains("南宁处") || affairDisciplinaryAction.getUnit().contains("柳州处") || affairDisciplinaryAction.getUnit().contains("北海处")) && !affairDisciplinaryAction.getUnit().contains("支队") && !affairDisciplinaryAction.getUnit().contains("派出所")){
			if (("1".equals(affairDisciplinaryAction.getDisciplinaryType()) && "1".equals(affairDisciplinaryAction.getXzSubOption()) || ("2".equals(affairDisciplinaryAction.getDisciplinaryType()) && "1".equals(affairDisciplinaryAction.getDjSubOption()) && "1".equals(affairDisciplinaryAction.getRySubOption())))){
				String unit = affairDisciplinaryAction.getUnit();
				String code = affairTalkManagementService.findCodeByUnit(unit);
				String userId = affairTalkManagementService.findUserIdByCode(code);
				Date lrDate = affairDisciplinaryAction.getLrDate();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(lrDate);
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
				examAutoEvaluationJjg.setUnit(affairDisciplinaryAction.getUnit());
				examAutoEvaluationJjg.setUnitId(affairDisciplinaryAction.getUnitId());
				examAutoEvaluationJjg.setAssess("");
				examAutoEvaluationJjg.setRemark("");
				examAutoEvaluationJjg.setModel("");
				examAutoEvaluationJjg.setModelId("");
				examAutoEvaluationJjg.setOption("");
				examAutoEvaluationJjg.setOptionId("");
				examAutoEvaluationJjg.setScore("");
				examAutoEvaluationJjg.setTime(lrDate);
				examAutoEvaluationJjg.setHappenTime(lrDate);
				examAutoEvaluationJjg.setCheckTime(lrDate);
				examAutoEvaluationService.save(examAutoEvaluationJjg);
			}
			//处考处机关民警受到行政记过处分的
			else if ("1".equals(affairDisciplinaryAction.getDisciplinaryType()) && "2".equals(affairDisciplinaryAction.getXzSubOption())){
				String unit = affairDisciplinaryAction.getUnit();
				String code = affairTalkManagementService.findCodeByUnit(unit);
				String userId = affairTalkManagementService.findUserIdByCode(code);
				Date lrDate = affairDisciplinaryAction.getLrDate();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(lrDate);
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
				examAutoEvaluationJjg.setUnit(affairDisciplinaryAction.getUnit());
				examAutoEvaluationJjg.setUnitId(affairDisciplinaryAction.getUnitId());
				examAutoEvaluationJjg.setAssess("");
				examAutoEvaluationJjg.setRemark("");
				examAutoEvaluationJjg.setModel("");
				examAutoEvaluationJjg.setModelId("");
				examAutoEvaluationJjg.setOption("");
				examAutoEvaluationJjg.setOptionId("");
				examAutoEvaluationJjg.setScore("");
				examAutoEvaluationJjg.setTime(lrDate);
				examAutoEvaluationJjg.setHappenTime(lrDate);
				examAutoEvaluationJjg.setCheckTime(lrDate);
				examAutoEvaluationService.save(examAutoEvaluationJjg);
			}
			// 处考处机关民警受到行政记大过、党内严重警告、撤销党内职务及以上处分的
			else if (("1".equals(affairDisciplinaryAction.getDisciplinaryType()) && "3".equals(affairDisciplinaryAction.getXzSubOption()) || (("2".equals(affairDisciplinaryAction.getDisciplinaryType()) && "1".equals(affairDisciplinaryAction.getDjSubOption())) && ("2".equals(affairDisciplinaryAction.getRySubOption()) || "3".equals(affairDisciplinaryAction.getRySubOption()) || "4".equals(affairDisciplinaryAction.getRySubOption()) || "5".equals(affairDisciplinaryAction.getRySubOption()))))){
				String unit = affairDisciplinaryAction.getUnit();
				String code = affairTalkManagementService.findCodeByUnit(unit);
				String userId = affairTalkManagementService.findUserIdByCode(code);
				Date lrDate = affairDisciplinaryAction.getLrDate();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(lrDate);
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
				examAutoEvaluationJjg.setUnit(affairDisciplinaryAction.getUnit());
				examAutoEvaluationJjg.setUnitId(affairDisciplinaryAction.getUnitId());
				examAutoEvaluationJjg.setAssess("");
				examAutoEvaluationJjg.setRemark("");
				examAutoEvaluationJjg.setModel("");
				examAutoEvaluationJjg.setModelId("");
				examAutoEvaluationJjg.setOption("");
				examAutoEvaluationJjg.setOptionId("");
				examAutoEvaluationJjg.setScore("");
				examAutoEvaluationJjg.setTime(lrDate);
				examAutoEvaluationJjg.setHappenTime(lrDate);
				examAutoEvaluationJjg.setCheckTime(lrDate);
				examAutoEvaluationService.save(examAutoEvaluationJjg);
			}
			//处考处机关民警受到降级、撤销党内职务及以上处分的
			else if ("1".equals(affairDisciplinaryAction.getDisciplinaryType()) && "4".equals(affairDisciplinaryAction.getXzSubOption()) || "5".equals(affairDisciplinaryAction.getXzSubOption())) {
				String unit = affairDisciplinaryAction.getUnit();
				String code = affairTalkManagementService.findCodeByUnit(unit);
				String userId = affairTalkManagementService.findUserIdByCode(code);
				Date lrDate = affairDisciplinaryAction.getLrDate();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(lrDate);
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
				examAutoEvaluationJjg.setUnit(affairDisciplinaryAction.getUnit());
				examAutoEvaluationJjg.setUnitId(affairDisciplinaryAction.getUnitId());
				examAutoEvaluationJjg.setAssess("");
				examAutoEvaluationJjg.setRemark("");
				examAutoEvaluationJjg.setModel("");
				examAutoEvaluationJjg.setModelId("");
				examAutoEvaluationJjg.setOption("");
				examAutoEvaluationJjg.setOptionId("");
				examAutoEvaluationJjg.setScore("");
				examAutoEvaluationJjg.setTime(lrDate);
				examAutoEvaluationJjg.setHappenTime(lrDate);
				examAutoEvaluationJjg.setCheckTime(lrDate);
				examAutoEvaluationService.save(examAutoEvaluationJjg);
			}
			//处考处机关民警因违法违纪问题被辞退或责令辞职的
			else if ("1".equals(affairDisciplinaryAction.getDisciplinaryType()) && "6".equals(affairDisciplinaryAction.getXzSubOption())){
				String unit = affairDisciplinaryAction.getUnit();
				String code = affairTalkManagementService.findCodeByUnit(unit);
				String userId = affairTalkManagementService.findUserIdByCode(code);
				Date lrDate = affairDisciplinaryAction.getLrDate();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(lrDate);
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
				examAutoEvaluationJjg.setUnit(affairDisciplinaryAction.getUnit());
				examAutoEvaluationJjg.setUnitId(affairDisciplinaryAction.getUnitId());
				examAutoEvaluationJjg.setAssess("");
				examAutoEvaluationJjg.setRemark("");
				examAutoEvaluationJjg.setModel("");
				examAutoEvaluationJjg.setModelId("");
				examAutoEvaluationJjg.setOption("");
				examAutoEvaluationJjg.setOptionId("");
				examAutoEvaluationJjg.setScore("");
				examAutoEvaluationJjg.setTime(lrDate);
				examAutoEvaluationJjg.setHappenTime(lrDate);
				examAutoEvaluationJjg.setCheckTime(lrDate);
				examAutoEvaluationService.save(examAutoEvaluationJjg);
			}
		}
		// 处考队所
		else if (affairDisciplinaryAction.getUnit().contains("支队") || affairDisciplinaryAction.getUnit().contains("派出所")){
			//民警受到行政警告处分、党内警告处分的
			if ("1".equals(affairDisciplinaryAction.getDisciplinaryType()) && "1".equals(affairDisciplinaryAction.getXzSubOption())){
				String unit = affairDisciplinaryAction.getUnit();
				String code = affairTalkManagementService.findCodeByUnit(unit);
				String userId = affairTalkManagementService.findUserIdByCode(code);
				Date time = affairDisciplinaryAction.getLrDate();
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
			//民警受到行政记过处分的
			else if ("1".equals(affairDisciplinaryAction.getDisciplinaryType()) && "2".equals(affairDisciplinaryAction.getXzSubOption())){
				String unit = affairDisciplinaryAction.getUnit();
				String code = affairTalkManagementService.findCodeByUnit(unit);
				String userId = affairTalkManagementService.findUserIdByCode(code);
				Date time = affairDisciplinaryAction.getLrDate();
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
			//民警受到行政记大过、党内严重警告、撤销党内职务及以上处分的
			else if (("1".equals(affairDisciplinaryAction.getDisciplinaryType()) && "3".equals(affairDisciplinaryAction.getXzSubOption()) || (("2".equals(affairDisciplinaryAction.getDisciplinaryType()) && "1".equals(affairDisciplinaryAction.getDjSubOption())) && ("2".equals(affairDisciplinaryAction.getRySubOption()) || "3".equals(affairDisciplinaryAction.getRySubOption()) || "4".equals(affairDisciplinaryAction.getRySubOption()) || "5".equals(affairDisciplinaryAction.getRySubOption()))))){
				String unit = affairDisciplinaryAction.getUnit();
				String code = affairTalkManagementService.findCodeByUnit(unit);
				String userId = affairTalkManagementService.findUserIdByCode(code);
				Date time = affairDisciplinaryAction.getLrDate();
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
			//民警受到降级、撤销党内职务及以上处分的
			else if ("1".equals(affairDisciplinaryAction.getDisciplinaryType()) && "4".equals(affairDisciplinaryAction.getXzSubOption()) || "5".equals(affairDisciplinaryAction.getXzSubOption())) {
				String unit = affairDisciplinaryAction.getUnit();
				String code = affairTalkManagementService.findCodeByUnit(unit);
				String userId = affairTalkManagementService.findUserIdByCode(code);
				Date time = affairDisciplinaryAction.getLrDate();
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
			//民警因违法违纪问题被辞退或责令辞职的
			else if ("1".equals(affairDisciplinaryAction.getDisciplinaryType()) && "6".equals(affairDisciplinaryAction.getXzSubOption())){
				String unit = affairDisciplinaryAction.getUnit();
				String code = affairTalkManagementService.findCodeByUnit(unit);
				String userId = affairTalkManagementService.findUserIdByCode(code);
				Date time = affairDisciplinaryAction.getLrDate();
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
		affairDisciplinaryActionService.save(affairDisciplinaryAction);
		addMessage(redirectAttributes, "保存纪律处分成功");
		//在请求里添加saveRequest用于Form表单关闭页面
		request.setAttribute("saveResult","success");
		return "modules/affair/affairDisciplinaryActionForm";
	}
	
	@RequiresPermissions("affair:affairDisciplinaryAction:delete")
	@RequestMapping(value = "delete")
	public String delete(AffairDisciplinaryAction affairDisciplinaryAction, RedirectAttributes redirectAttributes) {
		affairDisciplinaryActionService.delete(affairDisciplinaryAction);
		addMessage(redirectAttributes, "删除纪律处分成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairDisciplinaryAction/?repage";
	}

	/**
	 * 详情
	 * @param affairDisciplinaryAction
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairDisciplinaryAction:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairDisciplinaryAction affairDisciplinaryAction, Model model) {
		model.addAttribute("affairDisciplinaryAction", affairDisciplinaryAction);
		if(affairDisciplinaryAction.getAnnex() != null && affairDisciplinaryAction.getAnnex().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairDisciplinaryAction.getAnnex());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairDisciplinaryActionFormDetail";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairDisciplinaryAction:delete")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairDisciplinaryActionService.deleteByIds(ids);
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
	public String exportExcelByTemplate(AffairDisciplinaryAction affairDisciplinaryAction, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			if ("6".equals(UserUtils.getUser().getOffice().getId())){
				affairDisciplinaryAction.setQxUnitId(UserUtils.getUser().getCompany().getId());
			}else {
				affairDisciplinaryAction.setQxUnitId(UserUtils.getUser().getOffice().getId());
			}
			affairDisciplinaryAction.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName");
			}
			Page<AffairDisciplinaryAction> page = null;
			String reason = request.getParameter("reason");//2 全部 3 其他年份
			if("3".equals(reason)){
				affairDisciplinaryAction.setStartDate(null);
				affairDisciplinaryAction.setEndDate(null);
			}else if("2".equals(reason)){
				//全部
				if(affairDisciplinaryAction.getStartDate()==null && affairDisciplinaryAction.getEndDate() == null){
					String year = DateUtils.getYear();//获取当前年
					affairDisciplinaryAction.setOtherYear(year);
				}else{
					affairDisciplinaryAction.setOtherYear(null);
				}
			}else{
				//默认显示当前年度数据
				String year =DateUtils.getYear();//获取当前年
				affairDisciplinaryAction.setOtherYear(year);
				affairDisciplinaryAction.setStartDate(null);
				affairDisciplinaryAction.setEndDate(null);
			}
			if(flag == true){
				page = affairDisciplinaryActionService.findPage(new Page<AffairDisciplinaryAction>(request, response), affairDisciplinaryAction);
			}else{
				page = affairDisciplinaryActionService.findPage(new Page<AffairDisciplinaryAction>(request, response,-1), affairDisciplinaryAction);
			}
			/*if(flag == true){
				if (affairDisciplinaryAction.getStartApprovalDate() != null || affairDisciplinaryAction.getEndApprovalDate() != null){
					page = affairDisciplinaryActionService.findPage(new Page<AffairDisciplinaryAction>(request, response), affairDisciplinaryAction);
				}else if (affairDisciplinaryAction.getStartApprovalDate() == null && affairDisciplinaryAction.getEndApprovalDate() == null){
					if ("".equals(affairDisciplinaryAction.getOtherYear()) || affairDisciplinaryAction.getOtherYear() == null){
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
						Date date = new Date();
						String nowDate = sdf.format(date);
						Date yearDate = null;
						try {
							yearDate = sdf.parse(nowDate);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						affairDisciplinaryAction.setStartYear(yearDate);
						page = affairDisciplinaryActionService.findPage(new Page<AffairDisciplinaryAction>(request, response), affairDisciplinaryAction);
					}

				}else if (affairDisciplinaryAction.getOtherYear() != null && !"".equals(affairDisciplinaryAction.getOtherYear())){
					page = affairDisciplinaryActionService.findPage(new Page<AffairDisciplinaryAction>(request, response), affairDisciplinaryAction);
				}
			}else {
				if (affairDisciplinaryAction.getStartApprovalDate() != null || affairDisciplinaryAction.getEndApprovalDate() != null){
					page = affairDisciplinaryActionService.findPage(new Page<AffairDisciplinaryAction>(request, response,-1), affairDisciplinaryAction);
				}else if (affairDisciplinaryAction.getStartApprovalDate() == null && affairDisciplinaryAction.getEndApprovalDate() == null){
					if ("".equals(affairDisciplinaryAction.getOtherYear()) || affairDisciplinaryAction.getOtherYear() == null){
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
						Date date = new Date();
						String nowDate = sdf.format(date);
						Date yearDate = null;
						try {
							yearDate = sdf.parse(nowDate);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						affairDisciplinaryAction.setStartYear(yearDate);
						page = affairDisciplinaryActionService.findPage(new Page<AffairDisciplinaryAction>(request, response,-1), affairDisciplinaryAction);
					}

				}else if (affairDisciplinaryAction.getOtherYear() != null && !"".equals(affairDisciplinaryAction.getOtherYear())){
					page = affairDisciplinaryActionService.findPage(new Page<AffairDisciplinaryAction>(request, response,-1), affairDisciplinaryAction);
				}
			}*/
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
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairDisciplinaryAction.class);
			exportExcelNew.setWb(wb);
			List<AffairDisciplinaryAction> list =page.getList();
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
		return "redirect:" + adminPath + "/affair/affairDisciplinaryAction?repage";
	}

	@RequestMapping(value = {"findFirstJiLvDetail"})
	public String findFirstJiLvDetail(AffairDisciplinaryAction affairDisciplinaryAction, HttpServletRequest request, HttpServletResponse response, Model model){
		Page<AffairDisciplinaryAction> page;
		if ("1".equals(affairDisciplinaryAction.getFlag())){
			page = affairDisciplinaryActionService.findJuInfoDetail(new Page<AffairDisciplinaryAction>(request, response), affairDisciplinaryAction);
		}else if ("2".equals(affairDisciplinaryAction.getFlag())){
			page = affairDisciplinaryActionService.findNncInfoDetail(new Page<AffairDisciplinaryAction>(request, response), affairDisciplinaryAction);
		}else if ("3".equals(affairDisciplinaryAction.getFlag())){
			page = affairDisciplinaryActionService.findLzcInfoDetail(new Page<AffairDisciplinaryAction>(request, response), affairDisciplinaryAction);
		}else{
			page = affairDisciplinaryActionService.findBhcInfoDetail(new Page<AffairDisciplinaryAction>(request, response), affairDisciplinaryAction);
		}
		model.addAttribute("page", page);
		model.addAttribute("affairDisciplinaryAction", affairDisciplinaryAction);
		return "modules/charts/monitorFirstJiLvDetail";
	}

	@RequestMapping(value = {"findInfoByNatureDetail"})
	public String findInfoByNatureDetail(AffairDisciplinaryAction affairDisciplinaryAction, HttpServletRequest request, HttpServletResponse response, Model model){
		Page<AffairDisciplinaryAction> page;
		page = affairDisciplinaryActionService.findInfoByNatureDetail(new Page<AffairDisciplinaryAction>(request, response), affairDisciplinaryAction);
		model.addAttribute("page", page);
		model.addAttribute("affairDisciplinaryAction", affairDisciplinaryAction);
		return "modules/charts/monitorSecondJiLvDetail";
	}

	@RequestMapping(value = {"findDetailByCfUnit"})
	public String findDetailByCfUnit(AffairDisciplinaryAction affairDisciplinaryAction, HttpServletRequest request, HttpServletResponse response, Model model){
		Page<AffairDisciplinaryAction> page;
		page = affairDisciplinaryActionService.findDetailByCfUnit(new Page<AffairDisciplinaryAction>(request, response), affairDisciplinaryAction);
		model.addAttribute("page", page);
		model.addAttribute("affairDisciplinaryAction", affairDisciplinaryAction);
		return "modules/charts/monitorThirdJiLvDetail";
	}

	@RequestMapping(value = {"findDetailInfoByChuFen"})
	public String findDetailInfoByChuFen(AffairDisciplinaryAction affairDisciplinaryAction, HttpServletRequest request, HttpServletResponse response, Model model){
		Page<AffairDisciplinaryAction> page;
		page = affairDisciplinaryActionService.findDetailInfoByChuFen(new Page<AffairDisciplinaryAction>(request, response), affairDisciplinaryAction);
		model.addAttribute("page", page);
		model.addAttribute("affairDisciplinaryAction", affairDisciplinaryAction);
		return "modules/charts/monitorforthJiLvDetail";
	}

	@RequestMapping(value = {"findDetailInfoByDjChuFen"})
	public String findDetailInfoByDjChuFen(AffairDisciplinaryAction affairDisciplinaryAction, HttpServletRequest request, HttpServletResponse response, Model model){
		Page<AffairDisciplinaryAction> page;
		page = affairDisciplinaryActionService.findDetailInfoByDjChuFen(new Page<AffairDisciplinaryAction>(request, response), affairDisciplinaryAction);
		model.addAttribute("page", page);
		model.addAttribute("affairDisciplinaryAction", affairDisciplinaryAction);
		return "modules/charts/monitorfifthJiLvDetail";
	}

	@RequestMapping(value = {"findDetailInfoByXzChuFen"})
	public String findDetailInfoByXzChuFen(AffairDisciplinaryAction affairDisciplinaryAction, HttpServletRequest request, HttpServletResponse response, Model model){
		Page<AffairDisciplinaryAction> page;
		page = affairDisciplinaryActionService.findDetailInfoByXzChuFen(new Page<AffairDisciplinaryAction>(request, response), affairDisciplinaryAction);
		model.addAttribute("page", page);
		model.addAttribute("affairDisciplinaryAction", affairDisciplinaryAction);
		return "modules/charts/monitorSixthJiLvDetail";
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
			List<AffairDisciplinaryAction> list = ei.getDataList(AffairDisciplinaryAction.class);
			for (AffairDisciplinaryAction affairDisciplinaryAction : list){
				try{
					affairDisciplinaryAction.setUnitId(officeService.findByName(affairDisciplinaryAction.getUnitId()));
					BeanValidators.validateWithException(validator, affairDisciplinaryAction);
					affairDisciplinaryActionService.save(affairDisciplinaryAction);
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