/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.exam.entity.ExamSystemDisplay;
import com.thinkgem.jeesite.modules.exam.service.ExamSystemDisplayService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
 * 系统公示Controller
 * @author mason.xv
 * @version 2019-12-13
 */
@Controller
@RequestMapping(value = "${adminPath}/exam/examSystemDisplay")
public class ExamSystemDisplayController extends BaseController {

	@Autowired
	private ExamSystemDisplayService examSystemDisplayService;
	
	@ModelAttribute
	public ExamSystemDisplay get(@RequestParam(required=false) String id) {
		ExamSystemDisplay entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = examSystemDisplayService.get(id);
		}
		if (entity == null){
			entity = new ExamSystemDisplay();
		}
		return entity;
	}
	
	@RequiresPermissions("exam:examSystemDisplay:view")
	@RequestMapping(value = {"list", ""})
	public String list(ExamSystemDisplay examSystemDisplay, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ExamSystemDisplay> page = examSystemDisplayService.findPage(new Page<ExamSystemDisplay>(request, response), examSystemDisplay);
		model.addAttribute("page", page);
		return "modules/exam/examSystemDisplayList";
	}

	@RequiresPermissions("exam:examSystemDisplay:view")
	@RequestMapping(value = "form")
	public String form(ExamSystemDisplay examSystemDisplay, Model model) {
		model.addAttribute("examSystemDisplay", examSystemDisplay);
		return "modules/exam/examSystemDisplayForm";
	}

	@RequiresPermissions("exam:examSystemDisplay:edit")
	@RequestMapping(value = "save")
	public String save(ExamSystemDisplay examSystemDisplay, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, examSystemDisplay)){
			return form(examSystemDisplay, model);
		}
		examSystemDisplayService.save(examSystemDisplay);
		addMessage(redirectAttributes, "保存系统公示成功");
		return "redirect:"+Global.getAdminPath()+"/exam/examSystemDisplay/?repage";
	}
	
	@RequiresPermissions("exam:examSystemDisplay:edit")
	@RequestMapping(value = "delete")
	public String delete(ExamSystemDisplay examSystemDisplay, RedirectAttributes redirectAttributes) {
		examSystemDisplayService.delete(examSystemDisplay);
		addMessage(redirectAttributes, "删除系统公示成功");
		return "redirect:"+Global.getAdminPath()+"/exam/examSystemDisplay/?repage";
	}

}