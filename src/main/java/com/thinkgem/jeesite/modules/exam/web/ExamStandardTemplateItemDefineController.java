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
import com.thinkgem.jeesite.modules.exam.entity.ExamStandardTemplateItemDefine;
import com.thinkgem.jeesite.modules.exam.service.ExamStandardTemplateItemDefineService;

/**
 * 模板项管理Controller
 * @author bradley.zhao
 * @version 2019-12-12
 */
@Controller
@RequestMapping(value = "${adminPath}/exam/examStandardTemplateItemDefine")
public class ExamStandardTemplateItemDefineController extends BaseController {

	@Autowired
	private ExamStandardTemplateItemDefineService examStandardTemplateItemDefineService;
	
	@ModelAttribute
	public ExamStandardTemplateItemDefine get(@RequestParam(required=false) String id) {
		ExamStandardTemplateItemDefine entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = examStandardTemplateItemDefineService.get(id);
		}
		if (entity == null){
			entity = new ExamStandardTemplateItemDefine();
		}
		return entity;
	}
	
	@RequiresPermissions("exam:examStandardTemplateItemDefine:view")
	@RequestMapping(value = {"list", ""})
	public String list(ExamStandardTemplateItemDefine examStandardTemplateItemDefine, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ExamStandardTemplateItemDefine> page = examStandardTemplateItemDefineService.findPage(new Page<ExamStandardTemplateItemDefine>(request, response), examStandardTemplateItemDefine); 
		model.addAttribute("page", page);
		return "modules/exam/examStandardTemplateItemDefineList";
	}

	@RequiresPermissions("exam:examStandardTemplateItemDefine:view")
	@RequestMapping(value = "form")
	public String form(ExamStandardTemplateItemDefine examStandardTemplateItemDefine, Model model) {
		model.addAttribute("examStandardTemplateItemDefine", examStandardTemplateItemDefine);
		return "modules/exam/examStandardTemplateItemDefineForm";
	}

	@RequiresPermissions("exam:examStandardTemplateItemDefine:edit")
	@RequestMapping(value = "save")
	public String save(ExamStandardTemplateItemDefine examStandardTemplateItemDefine, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, examStandardTemplateItemDefine)){
			return form(examStandardTemplateItemDefine, model);
		}
		examStandardTemplateItemDefineService.save(examStandardTemplateItemDefine);
		addMessage(redirectAttributes, "保存模板项管理成功");
		return "redirect:"+Global.getAdminPath()+"/exam/examStandardTemplateItemDefine/?repage";
	}
	
	@RequiresPermissions("exam:examStandardTemplateItemDefine:edit")
	@RequestMapping(value = "delete")
	public String delete(ExamStandardTemplateItemDefine examStandardTemplateItemDefine, RedirectAttributes redirectAttributes) {
		examStandardTemplateItemDefineService.delete(examStandardTemplateItemDefine);
		addMessage(redirectAttributes, "删除模板项管理成功");
		return "redirect:"+Global.getAdminPath()+"/exam/examStandardTemplateItemDefine/?repage";
	}

}