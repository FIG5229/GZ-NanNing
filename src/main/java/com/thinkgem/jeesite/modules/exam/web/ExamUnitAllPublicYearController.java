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
import com.thinkgem.jeesite.modules.exam.entity.ExamUnitAllPublicYear;
import com.thinkgem.jeesite.modules.exam.service.ExamUnitAllPublicYearService;

/**
 * 单位年度考评结果Controller
 * @author bradley.zhao
 * @version 2020-02-19
 */
@Controller
@RequestMapping(value = "${adminPath}/exam/examUnitAllPublicYear")
public class ExamUnitAllPublicYearController extends BaseController {

	@Autowired
	private ExamUnitAllPublicYearService examUnitAllPublicYearService;
	
	@ModelAttribute
	public ExamUnitAllPublicYear get(@RequestParam(required=false) String id) {
		ExamUnitAllPublicYear entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = examUnitAllPublicYearService.get(id);
		}
		if (entity == null){
			entity = new ExamUnitAllPublicYear();
		}
		return entity;
	}
	
	@RequiresPermissions("exam:examUnitAllPublicYear:view")
	@RequestMapping(value = {"list", ""})
	public String list(ExamUnitAllPublicYear examUnitAllPublicYear, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ExamUnitAllPublicYear> page = examUnitAllPublicYearService.findPage(new Page<ExamUnitAllPublicYear>(request, response), examUnitAllPublicYear); 
		model.addAttribute("page", page);
		return "modules/exam/examUnitAllPublicYearList";
	}

	@RequiresPermissions("exam:examUnitAllPublicYear:view")
	@RequestMapping(value = "form")
	public String form(ExamUnitAllPublicYear examUnitAllPublicYear, Model model) {
		model.addAttribute("examUnitAllPublicYear", examUnitAllPublicYear);
		return "modules/exam/examUnitAllPublicYearForm";
	}

	@RequiresPermissions("exam:examUnitAllPublicYear:edit")
	@RequestMapping(value = "save")
	public String save(ExamUnitAllPublicYear examUnitAllPublicYear, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, examUnitAllPublicYear)){
			return form(examUnitAllPublicYear, model);
		}
		examUnitAllPublicYearService.save(examUnitAllPublicYear);
		addMessage(redirectAttributes, "保存单位年度考评结果成功");
		return "redirect:"+Global.getAdminPath()+"/exam/examUnitAllPublicYear/?repage";
	}
	
	@RequiresPermissions("exam:examUnitAllPublicYear:edit")
	@RequestMapping(value = "delete")
	public String delete(ExamUnitAllPublicYear examUnitAllPublicYear, RedirectAttributes redirectAttributes) {
		examUnitAllPublicYearService.delete(examUnitAllPublicYear);
		addMessage(redirectAttributes, "删除单位年度考评结果成功");
		return "redirect:"+Global.getAdminPath()+"/exam/examUnitAllPublicYear/?repage";
	}

}