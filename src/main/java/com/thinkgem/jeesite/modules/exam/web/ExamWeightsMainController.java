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
import com.thinkgem.jeesite.modules.exam.entity.ExamWeightsMain;
import com.thinkgem.jeesite.modules.exam.service.ExamWeightsMainService;

/**
 * 权重Controller
 * @author cecil.li
 * @version 2020-01-17
 */
@Controller
@RequestMapping(value = "${adminPath}/exam/examWeightsMain")
public class ExamWeightsMainController extends BaseController {

	@Autowired
	private ExamWeightsMainService examWeightsMainService;
	
	@ModelAttribute
	public ExamWeightsMain get(@RequestParam(required=false) String id) {
		ExamWeightsMain entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = examWeightsMainService.get(id);
		}
		if (entity == null){
			entity = new ExamWeightsMain();
		}
		return entity;
	}
	
	@RequiresPermissions("exam:examWeightsMain:view")
	@RequestMapping(value = {"list", ""})
	public String list(ExamWeightsMain examWeightsMain, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ExamWeightsMain> page = examWeightsMainService.findPage(new Page<ExamWeightsMain>(request, response), examWeightsMain); 
		model.addAttribute("page", page);
		return "modules/exam/examWeightsMainList";
	}

	@RequiresPermissions("exam:examWeightsMain:view")
	@RequestMapping(value = "form")
	public String form(ExamWeightsMain examWeightsMain, Model model) {
		model.addAttribute("examWeightsMain", examWeightsMain);
		return "modules/exam/examWeightsMainForm";
	}

	@RequiresPermissions("exam:examWeightsMain:edit")
	@RequestMapping(value = "save")
	public String save(ExamWeightsMain examWeightsMain, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, examWeightsMain)){
			return form(examWeightsMain, model);
		}
		examWeightsMainService.save(examWeightsMain);
		addMessage(redirectAttributes, "保存权重成功");
		return "redirect:"+Global.getAdminPath()+"/exam/examWeightsMain/?repage";
	}
	
	@RequiresPermissions("exam:examWeightsMain:edit")
	@RequestMapping(value = "delete")
	public String delete(ExamWeightsMain examWeightsMain, RedirectAttributes redirectAttributes) {
		examWeightsMainService.delete(examWeightsMain);
		addMessage(redirectAttributes, "删除权重成功");
		return "redirect:"+Global.getAdminPath()+"/exam/examWeightsMain/?repage";
	}

}