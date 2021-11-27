/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.exam.entity.ExamLdPerformanceAppraisal;
import com.thinkgem.jeesite.modules.exam.entity.ExamLdScore;
import com.thinkgem.jeesite.modules.exam.entity.ExamLdScoreMonth;
import com.thinkgem.jeesite.modules.exam.service.ExamLdPerformanceAppraisalService;
import com.thinkgem.jeesite.modules.exam.service.ExamLdScoreMonthService;
import com.thinkgem.jeesite.modules.exam.service.ExamLdScoreService;
import com.thinkgem.jeesite.modules.personnel.service.PersonnelBaseService;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 绩效考核情况Controller
 * @author cecil.li
 * @version 2020-01-14
 */
@Controller
@RequestMapping(value = "${adminPath}/exam/examLdPerformanceAppraisal")
public class ExamLdPerformanceAppraisalController extends BaseController {

	@Autowired
	private ExamLdPerformanceAppraisalService examLdPerformanceAppraisalService;

	@Autowired
	private ExamLdScoreMonthService examLdScoreMonthService;

	@Autowired
	OfficeService officeService;

	@Autowired
	private ExamLdScoreService  examLdScoreService;

	@Autowired
	private PersonnelBaseService personnelBaseService;
	
	@ModelAttribute
	public ExamLdPerformanceAppraisal get(@RequestParam(required=false) String id) {
		ExamLdPerformanceAppraisal entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = examLdPerformanceAppraisalService.get(id);
		}
		if (entity == null){
			entity = new ExamLdPerformanceAppraisal();
		}
		return entity;
	}
	
//	@RequiresPermissions("exam:examLdPerformanceAppraisal:view")
/*	@RequestMapping(value = {"list", ""})
	public String list(ExamLdPerformanceAppraisal examLdPerformanceAppraisal, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ExamLdPerformanceAppraisal> page = examLdPerformanceAppraisalService.findPage(new Page<ExamLdPerformanceAppraisal>(request, response), examLdPerformanceAppraisal); 
		model.addAttribute("page", page);
		return "modules/exam/examLdPerformanceAppraisalList";
	}*/

	/*public ModelAndView performance(String id) {
		ModelAndView view = new ModelAndView();
		view.addObject("id", id);
		view.addObject("dateType", "1");
		view.addObject("year", DateUtils.getYear());
		view.addObject("month", DateUtils.getYear()+DateUtils.getMonth());
		view.setViewName("modules/exam/examLdPerformanceAppraisalList");
		return view;
	}*/
	@RequestMapping(value = {"list", ""})
	public String list(ExamLdScoreMonth examLdScoreMonth, HttpServletRequest request, HttpServletResponse response, Model model) {
//		Page<ExamLdScoreMonth> page = examLdScoreMonthService.findPage(new Page<ExamLdScoreMonth>(request, response), examLdScoreMonth);
//		List<String> ids = examLdScoreMonthService.findAllChildIdById(id);
		String unitId = request.getParameter("unitId");
		String unitType = request.getParameter("unitType");
		List<ExamLdScoreMonth> list = examLdScoreMonthService.findListInfo(examLdScoreMonth);
		List<String> bmhjzList = personnelBaseService.findBmhjzByUnitId(unitId);
		List<String> jobAbbreviationList = personnelBaseService.findjobAbbreviationByUnitId(unitId);
		model.addAttribute("unitType",unitType);
		model.addAttribute("bmhjzList",bmhjzList);
		model.addAttribute("jobAbbreviationList",jobAbbreviationList);
		model.addAttribute("list", list);
		model.addAttribute("unitId", unitId);
		model.addAttribute("year", DateUtils.getYear());
		model.addAttribute("month", DateUtils.getYear()+DateUtils.getMonth());
//		model.addAttribute("ids", ids);
		return "modules/exam/examLdPerformanceAppraisalList";
	}

	//具体数据通过页面中的iframe进行展示
	@RequestMapping(value = {"policemanList"})
	public String policemanList(ExamLdScoreMonth examLdScoreMonth, HttpServletRequest request, HttpServletResponse response, Model model,String id) {
		//List<ExamLdScoreMonth> list = examLdScoreMonthService.findListInfo(examLdScoreMonth);
		String year = request.getParameter("year");//年
		String month = request.getParameter("month");//月
		String unitId = request.getParameter("unitId");//选择单位id
		String reasonType = request.getParameter("reasonType");//考评类别  1. 处领导考评 ，2.中基层领导考评  3. 民警考评
		String dateType = request.getParameter("dateType");//考评周期类型 1 月度  2 年度
		String selUnitid = request.getParameter("selUnitId");//选择的tab标签
		String jz = request.getParameter("jz");//警种
		String zw = request.getParameter("zw");//职务
		String ageStart = request.getParameter("ageStart");//年龄1
		String ageEnd = request.getParameter("ageEnd");//年龄1
		if("1".equals(dateType)){
		 //月度
            Map<String,Object> resultMap;
            if("1".equals(reasonType)|| "2".equals(reasonType)){
                //考评类别  1. 处领导考评 ，2.中基层领导考评
                resultMap =  examLdScoreMonthService.findPersonnelMonthExamList(new Page<ExamLdScoreMonth>(request,response,-1),month,reasonType,selUnitid,unitId,jz,zw,ageStart,ageEnd);

            }else{
                resultMap =  examLdScoreMonthService.findPersonnelMonthExamList(new Page<ExamLdScoreMonth>(request,response),month,reasonType,selUnitid,unitId,jz,zw,ageStart,ageEnd);
            }
			//Map<String,Object> resultMap =  examLdScoreMonthService.findPersonnelMonthExamList(new Page<ExamLdScoreMonth>(request,response),month,reasonType,selUnitid,unitId,jz,zw,ageStart,ageEnd);
			Page<ExamLdScoreMonth> page = (Page<ExamLdScoreMonth>) resultMap.get("page");
			List<Map<String,String>> unitList = (List<Map<String, String>>) resultMap.get("unitList");
			String selUnitId = (String) resultMap.get("selUnitId");
			model.addAttribute("page", page);
			model.addAttribute("unitList", unitList);
			model.addAttribute("selUnitId", selUnitId);
		}else{
			//年度
            Map<String,Object> resultMap;
            if("1".equals(reasonType)|| "2".equals(reasonType)) {
                //考评类别  1. 处领导考评 ，2.中基层领导考评
                resultMap = examLdScoreService.findPersonnelYearExamList(new Page<ExamLdScore>(request,response,-1),year,reasonType,selUnitid,unitId,jz,zw,ageStart,ageEnd);
            }else{
                resultMap = examLdScoreService.findPersonnelYearExamList(new Page<ExamLdScore>(request,response),year,reasonType,selUnitid,unitId,jz,zw,ageStart,ageEnd);
            }
			//Map<String,Object> resultMap = examLdScoreService.findPersonnelYearExamList(new Page<ExamLdScore>(request,response),year,reasonType,selUnitid,unitId,jz,zw,ageStart,ageEnd);
			Page<ExamLdScore> page = (Page<ExamLdScore>) resultMap.get("page");
			List<Map<String,String>> unitList = (List<Map<String, String>>) resultMap.get("unitList");
			String selUnitId = (String) resultMap.get("selUnitId");
			model.addAttribute("page", page);
			model.addAttribute("unitList", unitList);
			model.addAttribute("selUnitId", selUnitId);
		}
		//model.addAttribute("list", list);
		model.addAttribute("year",year);
		model.addAttribute("month",month);
		model.addAttribute("reasonType",reasonType);
		model.addAttribute("dateType",dateType);
		model.addAttribute("unitId",unitId);
		model.addAttribute("jz",jz);
		model.addAttribute("zw",zw);
		model.addAttribute("ageStart",ageStart);
		model.addAttribute("ageEnd",ageEnd);
		return "modules/exam/examLdPerformanceAppraisalPolicemanList";
	}

//	@RequiresPermissions("exam:examLdPerformanceAppraisal:view")
	@RequestMapping(value = "form")
	public String form(ExamLdPerformanceAppraisal examLdPerformanceAppraisal, Model model) {
		model.addAttribute("examLdPerformanceAppraisal", examLdPerformanceAppraisal);
		return "modules/exam/examLdPerformanceAppraisalForm";
	}

//	@RequiresPermissions("exam:examLdPerformanceAppraisal:edit")
	@RequestMapping(value = "save")
	public String save(ExamLdPerformanceAppraisal examLdPerformanceAppraisal, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, examLdPerformanceAppraisal)){
			return form(examLdPerformanceAppraisal, model);
		}
		examLdPerformanceAppraisalService.save(examLdPerformanceAppraisal);
		addMessage(redirectAttributes, "保存绩效考核情况成功");
		request.setAttribute("saveResult","success");
		return "modules/exam/examLdPerformanceAppraisalForm";
	}
	
//	@RequiresPermissions("exam:examLdPerformanceAppraisal:edit")
	@RequestMapping(value = "delete")
	public String delete(ExamLdPerformanceAppraisal examLdPerformanceAppraisal, RedirectAttributes redirectAttributes) {
		examLdPerformanceAppraisalService.delete(examLdPerformanceAppraisal);
		addMessage(redirectAttributes, "删除绩效考核情况成功");
		return "redirect:"+Global.getAdminPath()+"/exam/examLdPerformanceAppraisal/?repage";
	}

}