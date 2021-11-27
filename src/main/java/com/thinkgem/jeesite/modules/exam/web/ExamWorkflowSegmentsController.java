/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.exam.entity.ExamWorflowSegments;
import com.thinkgem.jeesite.modules.exam.service.ExamWorkflowSegmentsService;

/**
 * 考评环节Controller
 * @author bradley.zhao
 * @version 2019-12-20
 */
@Controller
@RequestMapping(value = "${adminPath}/exam/examWorflowSegments")
public class ExamWorkflowSegmentsController extends BaseController {

	@Autowired
	private ExamWorkflowSegmentsService examWorflowSegmentsService;
	
	@ModelAttribute
	public ExamWorflowSegments get(@RequestParam(required=false) String id) {
		ExamWorflowSegments entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = examWorflowSegmentsService.get(id);
		}
		if (entity == null){
			entity = new ExamWorflowSegments();
		}
		return entity;
	}
	
	@RequiresPermissions("exam:examWorflowSegments:view")
	@RequestMapping(value = {"list", ""})
	public String list(ExamWorflowSegments examWorflowSegments, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ExamWorflowSegments> page = examWorflowSegmentsService.findPage(new Page<ExamWorflowSegments>(request, response), examWorflowSegments); 
		model.addAttribute("page", page);
		return "modules/exam/examWorflowSegmentsList";
	}

	@RequiresPermissions("exam:examWorflowSegments:view")
	@RequestMapping(value = "form")
	public String form(ExamWorflowSegments examWorflowSegments, Model model) {
		model.addAttribute("examWorflowSegments", examWorflowSegments);
		return "modules/exam/examWorflowSegmentsForm";
	}

	@RequiresPermissions("exam:examWorflowSegments:edit")
	@RequestMapping(value = "save")
	public String save(ExamWorflowSegments examWorflowSegments, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, examWorflowSegments)){
			return form(examWorflowSegments, model);
		}
		examWorflowSegmentsService.save(examWorflowSegments);
		addMessage(redirectAttributes, "保存考评环节成功");
		return "redirect:"+Global.getAdminPath()+"/exam/examWorflowSegments/?repage";
	}
	
	@RequiresPermissions("exam:examWorflowSegments:edit")
	@RequestMapping(value = "delete")
	public String delete(ExamWorflowSegments examWorflowSegments, RedirectAttributes redirectAttributes) {
		examWorflowSegmentsService.delete(examWorflowSegments);
		addMessage(redirectAttributes, "删除考评环节成功");
		return "redirect:"+Global.getAdminPath()+"/exam/examWorflowSegments/?repage";
	}

}