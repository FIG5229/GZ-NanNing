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
import com.thinkgem.jeesite.modules.exam.entity.ExamUnitAllPublic;
import com.thinkgem.jeesite.modules.exam.service.ExamUnitAllPublicService;

/**
 * 单位-全局公示Controller
 * @author eav.liu
 * @version 2020-02-14
 */
@Controller
@RequestMapping(value = "${adminPath}/exam/examUnitAllPublic")
public class ExamUnitAllPublicController extends BaseController {

	@Autowired
	private ExamUnitAllPublicService examUnitAllPublicService;
	
	@ModelAttribute
	public ExamUnitAllPublic get(@RequestParam(required=false) String id) {
		ExamUnitAllPublic entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = examUnitAllPublicService.get(id);
		}
		if (entity == null){
			entity = new ExamUnitAllPublic();
		}
		return entity;
	}
	
	@RequiresPermissions("exam:examUnitAllPublic:view")
	@RequestMapping(value = {"list", ""})
	public String list(ExamUnitAllPublic examUnitAllPublic, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ExamUnitAllPublic> page = examUnitAllPublicService.findPage(new Page<ExamUnitAllPublic>(request, response), examUnitAllPublic); 
		model.addAttribute("page", page);
		return "modules/exam/examUnitAllPublicList";
	}

	@RequiresPermissions("exam:examUnitAllPublic:view")
	@RequestMapping(value = "form")
	public String form(ExamUnitAllPublic examUnitAllPublic, Model model) {
		model.addAttribute("examUnitAllPublic", examUnitAllPublic);
		return "modules/exam/examUnitAllPublicForm";
	}

	@RequiresPermissions("exam:examUnitAllPublic:edit")
	@RequestMapping(value = "save")
	public String save(ExamUnitAllPublic examUnitAllPublic, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, examUnitAllPublic)){
			return form(examUnitAllPublic, model);
		}
		examUnitAllPublicService.save(examUnitAllPublic);
		addMessage(redirectAttributes, "保存全局公示成功");
		return "redirect:"+Global.getAdminPath()+"/exam/examUnitAllPublic/?repage";
	}
	
	@RequiresPermissions("exam:examUnitAllPublic:edit")
	@RequestMapping(value = "delete")
	public String delete(ExamUnitAllPublic examUnitAllPublic, RedirectAttributes redirectAttributes) {
		examUnitAllPublicService.delete(examUnitAllPublic);
		addMessage(redirectAttributes, "删除全局公示成功");
		return "redirect:"+Global.getAdminPath()+"/exam/examUnitAllPublic/?repage";
	}

}