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
import com.thinkgem.jeesite.modules.exam.entity.ExamPersonsAssignRule;
import com.thinkgem.jeesite.modules.exam.service.ExamPersonsAssignRuleService;

/**
 * 考评人员分配规则管理Controller
 * @author bradley.zhao
 * @version 2020-03-21
 */
@Controller
@RequestMapping(value = "${adminPath}/exam/examPersonsAssignRule")
public class ExamPersonsAssignRuleController extends BaseController {

	@Autowired
	private ExamPersonsAssignRuleService examPersonsAssignRuleService;
	
	@ModelAttribute
	public ExamPersonsAssignRule get(@RequestParam(required=false) String id) {
		ExamPersonsAssignRule entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = examPersonsAssignRuleService.get(id);
		}
		if (entity == null){
			entity = new ExamPersonsAssignRule();
		}
		return entity;
	}
	
	@RequiresPermissions("exam:examPersonsAssignRule:view")
	@RequestMapping(value = {"list", ""})
	public String list(ExamPersonsAssignRule examPersonsAssignRule, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ExamPersonsAssignRule> page = examPersonsAssignRuleService.findPage(new Page<ExamPersonsAssignRule>(request, response), examPersonsAssignRule); 
		model.addAttribute("page", page);
		return "modules/exam/examPersonsAssignRuleList";
	}

	@RequiresPermissions("exam:examPersonsAssignRule:view")
	@RequestMapping(value = "form")
	public String form(ExamPersonsAssignRule examPersonsAssignRule, Model model) {
		model.addAttribute("examPersonsAssignRule", examPersonsAssignRule);
		return "modules/exam/examPersonsAssignRuleForm";
	}

	@RequiresPermissions("exam:examPersonsAssignRule:edit")
	@RequestMapping(value = "save")
	public String save(ExamPersonsAssignRule examPersonsAssignRule, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, examPersonsAssignRule)){
			return form(examPersonsAssignRule, model);
		}
		examPersonsAssignRuleService.save(examPersonsAssignRule);
		addMessage(redirectAttributes, "保存考评人员分配规则管理成功");
		return "redirect:"+Global.getAdminPath()+"/exam/examPersonsAssignRule/?repage";
	}
	
	@RequiresPermissions("exam:examPersonsAssignRule:edit")
	@RequestMapping(value = "delete")
	public String delete(ExamPersonsAssignRule examPersonsAssignRule, RedirectAttributes redirectAttributes) {
		examPersonsAssignRuleService.delete(examPersonsAssignRule);
		addMessage(redirectAttributes, "删除考评人员分配规则管理成功");
		return "redirect:"+Global.getAdminPath()+"/exam/examPersonsAssignRule/?repage";
	}

}