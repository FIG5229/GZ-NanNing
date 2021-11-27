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
import com.thinkgem.jeesite.modules.exam.entity.ExamSystemInitCheack;
import com.thinkgem.jeesite.modules.exam.service.ExamSystemInitCheackService;

/**
 * 系统公示Controller
 * @author mason.xv
 * @version 2019-12-13
 */
@Controller
@RequestMapping(value = "${adminPath}/exam/examSystemInitCheack")
public class ExamSystemInitCheackController extends BaseController {

	@Autowired
	private ExamSystemInitCheackService examSystemInitCheackService;
	
	@ModelAttribute
	public ExamSystemInitCheack get(@RequestParam(required=false) String id) {
		ExamSystemInitCheack entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = examSystemInitCheackService.get(id);
		}
		if (entity == null){
			entity = new ExamSystemInitCheack();
		}
		return entity;
	}
	
	@RequiresPermissions("exam:examSystemInitCheack:view")
	@RequestMapping(value = {"list", ""})
	public String list(ExamSystemInitCheack examSystemInitCheack, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ExamSystemInitCheack> page = examSystemInitCheackService.findPage(new Page<ExamSystemInitCheack>(request, response), examSystemInitCheack); 
		model.addAttribute("page", page);
		return "modules/exam/examSystemInitCheackList";
	}

	@RequiresPermissions("exam:examSystemInitCheack:view")
	@RequestMapping(value = "form")
	public String form(ExamSystemInitCheack examSystemInitCheack, Model model) {
		model.addAttribute("examSystemInitCheack", examSystemInitCheack);
		return "modules/exam/examSystemInitCheackForm";
	}

	@RequiresPermissions("exam:examSystemInitCheack:edit")
	@RequestMapping(value = "save")
	public String save(ExamSystemInitCheack examSystemInitCheack, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, examSystemInitCheack)){
			return form(examSystemInitCheack, model);
		}
		examSystemInitCheackService.save(examSystemInitCheack);
		addMessage(redirectAttributes, "保存系统公示成功");
		return "redirect:"+Global.getAdminPath()+"/exam/examSystemInitCheack/?repage";
	}
	
	@RequiresPermissions("exam:examSystemInitCheack:edit")
	@RequestMapping(value = "delete")
	public String delete(ExamSystemInitCheack examSystemInitCheack, RedirectAttributes redirectAttributes) {
		examSystemInitCheackService.delete(examSystemInitCheack);
		addMessage(redirectAttributes, "删除系统公示成功");
		return "redirect:"+Global.getAdminPath()+"/exam/examSystemInitCheack/?repage";
	}

}