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
import com.thinkgem.jeesite.modules.exam.entity.ExamSystemDisplayCheck;
import com.thinkgem.jeesite.modules.exam.service.ExamSystemDisplayCheckService;

/**
 * 系统最终审核公示Controller
 * @author mason.xv
 * @version 2019-12-13
 */
@Controller
@RequestMapping(value = "${adminPath}/exam/examSystemDisplayCheck")
public class ExamSystemDisplayCheckController extends BaseController {

	@Autowired
	private ExamSystemDisplayCheckService examSystemDisplayCheckService;
	
	@ModelAttribute
	public ExamSystemDisplayCheck get(@RequestParam(required=false) String id) {
		ExamSystemDisplayCheck entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = examSystemDisplayCheckService.get(id);
		}
		if (entity == null){
			entity = new ExamSystemDisplayCheck();
		}
		return entity;
	}
	
	@RequiresPermissions("exam:examSystemDisplayCheck:view")
	@RequestMapping(value = {"list", ""})
	public String list(ExamSystemDisplayCheck examSystemDisplayCheck, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ExamSystemDisplayCheck> page = examSystemDisplayCheckService.findPage(new Page<ExamSystemDisplayCheck>(request, response), examSystemDisplayCheck); 
		model.addAttribute("page", page);
		return "modules/exam/examSystemDisplayCheckList";
	}

	@RequiresPermissions("exam:examSystemDisplayCheck:view")
	@RequestMapping(value = "form")
	public String form(ExamSystemDisplayCheck examSystemDisplayCheck, Model model) {
		model.addAttribute("examSystemDisplayCheck", examSystemDisplayCheck);
		return "modules/exam/examSystemDisplayCheckForm";
	}

	@RequiresPermissions("exam:examSystemDisplayCheck:edit")
	@RequestMapping(value = "save")
	public String save(ExamSystemDisplayCheck examSystemDisplayCheck, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, examSystemDisplayCheck)){
			return form(examSystemDisplayCheck, model);
		}
		examSystemDisplayCheckService.save(examSystemDisplayCheck);
		addMessage(redirectAttributes, "保存系统最终审核公示成功");
		return "redirect:"+Global.getAdminPath()+"/exam/examSystemDisplayCheck/?repage";
	}
	
	@RequiresPermissions("exam:examSystemDisplayCheck:edit")
	@RequestMapping(value = "delete")
	public String delete(ExamSystemDisplayCheck examSystemDisplayCheck, RedirectAttributes redirectAttributes) {
		examSystemDisplayCheckService.delete(examSystemDisplayCheck);
		addMessage(redirectAttributes, "删除系统最终审核公示成功");
		return "redirect:"+Global.getAdminPath()+"/exam/examSystemDisplayCheck/?repage";
	}

}