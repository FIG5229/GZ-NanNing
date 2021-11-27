/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.exam.service.ExamWorkflowDefineService;
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
import com.thinkgem.jeesite.modules.exam.entity.ExamAutoStandardRelevance;
import com.thinkgem.jeesite.modules.exam.service.ExamAutoStandardRelevanceService;

import java.util.List;

/**
 * 自动考评考评标准关联Controller
 * @author kevin.jia
 * @version 2021-01-20
 */
@Controller
@RequestMapping(value = "${adminPath}/exam/examAutoStandardRelevance")
public class ExamAutoStandardRelevanceController extends BaseController {

	@Autowired
	private ExamAutoStandardRelevanceService examAutoStandardRelevanceService;

	@Autowired
	private ExamWorkflowDefineService examWorkflowDefineService;

	@ModelAttribute
	public ExamAutoStandardRelevance get(@RequestParam(required=false) String id) {
		ExamAutoStandardRelevance entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = examAutoStandardRelevanceService.get(id);
		}
		if (entity == null){
			entity = new ExamAutoStandardRelevance();
		}
		return entity;
	}
	
	@RequiresPermissions("exam:examAutoStandardRelevance:view")
	@RequestMapping(value = {"list", ""})
	public String list(ExamAutoStandardRelevance examAutoStandardRelevance, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ExamAutoStandardRelevance> page = examAutoStandardRelevanceService.findPage(new Page<ExamAutoStandardRelevance>(request, response), examAutoStandardRelevance);
		List<String> itemList = examAutoStandardRelevanceService.findAllItems();
		model.addAttribute("page", page);
		model.addAttribute("itemList",itemList);
		model.addAttribute("templateFile", examWorkflowDefineService.templateFile());
		return "modules/exam/examAutoStandardRelevanceList";
	}

	@RequiresPermissions("exam:examAutoStandardRelevance:view")
	@RequestMapping(value = "form")
	public String form(ExamAutoStandardRelevance examAutoStandardRelevance, Model model) {
		model.addAttribute("examAutoStandardRelevance", examAutoStandardRelevance);
		return "modules/exam/examAutoStandardRelevanceForm";
	}
	@RequestMapping(value = "template")
	public String template(ExamAutoStandardRelevance examAutoStandardRelevance, Model model) {
		model.addAttribute("examAutoStandardRelevance", examAutoStandardRelevance);
		return "modules/exam/examAutoStandardRelevanceTemplate";
	}
	@RequestMapping(value = "templateSave")
	public String templateSave(ExamAutoStandardRelevance examAutoStandardRelevance, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, examAutoStandardRelevance)){
			return form(examAutoStandardRelevance, model);
		}
		examAutoStandardRelevanceService.save(examAutoStandardRelevance);
		addMessage(redirectAttributes, "保存自动考评考评标准关联成功");
		request.setAttribute("saveResult","success");
		return "modules/exam/examAutoStandardRelevanceTemplate";
	}


	@RequiresPermissions("exam:examAutoStandardRelevance:edit")
	@RequestMapping(value = "save")
	public String save(ExamAutoStandardRelevance examAutoStandardRelevance, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, examAutoStandardRelevance)){
			return form(examAutoStandardRelevance, model);
		}
		examAutoStandardRelevanceService.save(examAutoStandardRelevance);
		addMessage(redirectAttributes, "保存自动考评考评标准关联成功");
		model.addAttribute("saveResult","success");
		return "modules/exam/examAutoStandardRelevanceForm";
		//return "redirect:"+Global.getAdminPath()+"/exam/examAutoStandardRelevance/?repage";
	}
	
	@RequiresPermissions("exam:examAutoStandardRelevance:edit")
	@RequestMapping(value = "delete")
	public String delete(ExamAutoStandardRelevance examAutoStandardRelevance, RedirectAttributes redirectAttributes) {
		examAutoStandardRelevanceService.delete(examAutoStandardRelevance);
		addMessage(redirectAttributes, "删除自动考评考评标准关联成功");
		return "redirect:"+Global.getAdminPath()+"/exam/examAutoStandardRelevance/?repage";
	}

}