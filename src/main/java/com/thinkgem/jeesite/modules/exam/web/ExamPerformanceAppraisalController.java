/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.exam.entity.ExamPerformanceAppraisal;
import com.thinkgem.jeesite.modules.exam.service.ExamPerformanceAppraisalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 部门绩效考核情况Controller
 * @author cecil.li
 * @version 2020-01-14
 */
@Controller
@RequestMapping(value = "${adminPath}/exam/examPerformanceAppraisal")
public class ExamPerformanceAppraisalController extends BaseController {

	@Autowired
	private ExamPerformanceAppraisalService examPerformanceAppraisalService;
	
	@ModelAttribute
	public ExamPerformanceAppraisal get(@RequestParam(required=false) String id) {
		ExamPerformanceAppraisal entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = examPerformanceAppraisalService.get(id);
		}
		if (entity == null){
			entity = new ExamPerformanceAppraisal();
		}
		return entity;
	}
	
//	@RequiresPermissions("exam:examPerformanceAppraisal:view")
	@RequestMapping(value = {"list", ""})
	public String list(ExamPerformanceAppraisal examPerformanceAppraisal, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ExamPerformanceAppraisal> page = examPerformanceAppraisalService.findPage(new Page<ExamPerformanceAppraisal>(request, response), examPerformanceAppraisal); 
		model.addAttribute("page", page);
		return "modules/exam/examPerformanceAppraisalList";
	}

//	@RequiresPermissions("exam:examPerformanceAppraisal:view")
	@RequestMapping(value = "form")
	public String form(ExamPerformanceAppraisal examPerformanceAppraisal, Model model) {
		model.addAttribute("examPerformanceAppraisal", examPerformanceAppraisal);
		return "modules/exam/examPerformanceAppraisalForm";
	}

//	@RequiresPermissions("exam:examPerformanceAppraisal:edit")
	@RequestMapping(value = "save")
	public String save(ExamPerformanceAppraisal examPerformanceAppraisal, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, examPerformanceAppraisal)){
			return form(examPerformanceAppraisal, model);
		}
		examPerformanceAppraisalService.save(examPerformanceAppraisal);
		addMessage(redirectAttributes, "保存部门绩效考核情况成功");
		request.setAttribute("saveResult","success");
		return "modules/exam/examPerformanceAppraisalForm";
	}
	
//	@RequiresPermissions("exam:examPerformanceAppraisal:edit")
	@RequestMapping(value = "delete")
	public String delete(ExamPerformanceAppraisal examPerformanceAppraisal, RedirectAttributes redirectAttributes) {
		examPerformanceAppraisalService.delete(examPerformanceAppraisal);
		addMessage(redirectAttributes, "删除部门绩效考核情况成功");
		return "redirect:"+Global.getAdminPath()+"/exam/examPerformanceAppraisal/?repage";
	}

}