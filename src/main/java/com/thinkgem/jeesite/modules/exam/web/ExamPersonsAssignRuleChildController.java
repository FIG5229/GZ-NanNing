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
import com.thinkgem.jeesite.modules.exam.entity.ExamPersonsAssignRuleChild;
import com.thinkgem.jeesite.modules.exam.service.ExamPersonsAssignRuleChildService;

/**
 * 考评人员分配规则子表管理Controller
 * @author bradley.zhao
 * @version 2020-03-21
 */
@Controller
@RequestMapping(value = "${adminPath}/exam/examPersonsAssignRuleChild")
public class ExamPersonsAssignRuleChildController extends BaseController {

	@Autowired
	private ExamPersonsAssignRuleChildService examPersonsAssignRuleChildService;
	
	@ModelAttribute
	public ExamPersonsAssignRuleChild get(@RequestParam(required=false) String id) {
		ExamPersonsAssignRuleChild entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = examPersonsAssignRuleChildService.get(id);
		}
		if (entity == null){
			entity = new ExamPersonsAssignRuleChild();
		}
		return entity;
	}
	
	@RequiresPermissions("exam:examPersonsAssignRuleChild:view")
	@RequestMapping(value = {"list", ""})
	public String list(ExamPersonsAssignRuleChild examPersonsAssignRuleChild, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ExamPersonsAssignRuleChild> page = examPersonsAssignRuleChildService.findPage(new Page<ExamPersonsAssignRuleChild>(request, response), examPersonsAssignRuleChild); 
		model.addAttribute("page", page);
		return "modules/exam/examPersonsAssignRuleChildList";
	}

	@RequiresPermissions("exam:examPersonsAssignRuleChild:view")
	@RequestMapping(value = "form")
	public String form(ExamPersonsAssignRuleChild examPersonsAssignRuleChild, Model model) {
		model.addAttribute("examPersonsAssignRuleChild", examPersonsAssignRuleChild);
		return "modules/exam/examPersonsAssignRuleChildForm";
	}

	@RequiresPermissions("exam:examPersonsAssignRuleChild:edit")
	@RequestMapping(value = "save")
	public String save(ExamPersonsAssignRuleChild examPersonsAssignRuleChild, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, examPersonsAssignRuleChild)){
			return form(examPersonsAssignRuleChild, model);
		}
		examPersonsAssignRuleChildService.save(examPersonsAssignRuleChild);
		addMessage(redirectAttributes, "保存考评人员分配规则子表管理成功");
		return "redirect:"+Global.getAdminPath()+"/exam/examPersonsAssignRuleChild/?repage";
	}
	
	@RequiresPermissions("exam:examPersonsAssignRuleChild:edit")
	@RequestMapping(value = "delete")
	public String delete(ExamPersonsAssignRuleChild examPersonsAssignRuleChild, RedirectAttributes redirectAttributes) {
		examPersonsAssignRuleChildService.delete(examPersonsAssignRuleChild);
		addMessage(redirectAttributes, "删除考评人员分配规则子表管理成功");
		return "redirect:"+Global.getAdminPath()+"/exam/examPersonsAssignRuleChild/?repage";
	}

}