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
import com.thinkgem.jeesite.modules.exam.entity.ExamWorkflowSegmentGuanlian;
import com.thinkgem.jeesite.modules.exam.service.ExamWorkflowSegmentGuanlianService;

/**
 * 考评流程环节关联Controller
 * @author eav.liu
 * @version 2019-12-10
 */
@Controller
@RequestMapping(value = "${adminPath}/exam/examWorkflowSegmentGuanlian")
public class ExamWorkflowSegmentGuanlianController extends BaseController {

	@Autowired
	private ExamWorkflowSegmentGuanlianService examWorkflowSegmentGuanlianService;
	
	@ModelAttribute
	public ExamWorkflowSegmentGuanlian get(@RequestParam(required=false) String id) {
		ExamWorkflowSegmentGuanlian entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = examWorkflowSegmentGuanlianService.get(id);
		}
		if (entity == null){
			entity = new ExamWorkflowSegmentGuanlian();
		}
		return entity;
	}
	
	@RequiresPermissions("exam:examWorkflowSegmentGuanlian:view")
	@RequestMapping(value = {"list", ""})
	public String list(ExamWorkflowSegmentGuanlian examWorkflowSegmentGuanlian, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ExamWorkflowSegmentGuanlian> page = examWorkflowSegmentGuanlianService.findPage(new Page<ExamWorkflowSegmentGuanlian>(request, response), examWorkflowSegmentGuanlian); 
		model.addAttribute("page", page);
		return "modules/exam/examWorkflowSegmentGuanlianList";
	}

	@RequiresPermissions("exam:examWorkflowSegmentGuanlian:view")
	@RequestMapping(value = "form")
	public String form(ExamWorkflowSegmentGuanlian examWorkflowSegmentGuanlian, Model model) {
		model.addAttribute("examWorkflowSegmentGuanlian", examWorkflowSegmentGuanlian);
		return "modules/exam/examWorkflowSegmentGuanlianForm";
	}

	@RequiresPermissions("exam:examWorkflowSegmentGuanlian:edit")
	@RequestMapping(value = "save")
	public String save(ExamWorkflowSegmentGuanlian examWorkflowSegmentGuanlian, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, examWorkflowSegmentGuanlian)){
			return form(examWorkflowSegmentGuanlian, model);
		}
		examWorkflowSegmentGuanlianService.save(examWorkflowSegmentGuanlian);
		addMessage(redirectAttributes, "保存考评流程环节关联成功");
		return "redirect:"+Global.getAdminPath()+"/exam/examWorkflowSegmentGuanlian/?repage";
	}
	
	@RequiresPermissions("exam:examWorkflowSegmentGuanlian:edit")
	@RequestMapping(value = "delete")
	public String delete(ExamWorkflowSegmentGuanlian examWorkflowSegmentGuanlian, RedirectAttributes redirectAttributes) {
		examWorkflowSegmentGuanlianService.delete(examWorkflowSegmentGuanlian);
		addMessage(redirectAttributes, "删除考评流程环节关联成功");
		return "redirect:"+Global.getAdminPath()+"/exam/examWorkflowSegmentGuanlian/?repage";
	}

}