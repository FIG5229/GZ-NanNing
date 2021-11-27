/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.tw.web;

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
import com.thinkgem.jeesite.modules.tw.entity.AcReport;
import com.thinkgem.jeesite.modules.tw.service.AcReportService;

/**
 * 自动考评-警情信息Controller
 * @author alan.wu
 * @version 2020-11-19
 */
@Controller
@RequestMapping(value = "${adminPath}/tw/acReport")
public class AcReportController extends BaseController {

	@Autowired
	private AcReportService acReportService;
	
	@ModelAttribute
	public AcReport get(@RequestParam(required=false) String id) {
		AcReport entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = acReportService.get(id);
		}
		if (entity == null){
			entity = new AcReport();
		}
		return entity;
	}
	
	@RequiresPermissions("tw:acReport:view")
	@RequestMapping(value = {"list", ""})
	public String list(AcReport acReport, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AcReport> page = acReportService.findPage(new Page<AcReport>(request, response), acReport); 
		model.addAttribute("page", page);
		return "modules/tw/acReportList";
	}

	@RequiresPermissions("tw:acReport:view")
	@RequestMapping(value = "form")
	public String form(AcReport acReport, Model model) {
		model.addAttribute("acReport", acReport);
		return "modules/tw/acReportForm";
	}

	@RequiresPermissions("tw:acReport:edit")
	@RequestMapping(value = "save")
	public String save(AcReport acReport, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, acReport)){
			return form(acReport, model);
		}
		acReportService.save(acReport);
		addMessage(redirectAttributes, "保存自动考评-警情信息成功");
		return "redirect:"+Global.getAdminPath()+"/tw/acReport/?repage";
	}
	
	@RequiresPermissions("tw:acReport:edit")
	@RequestMapping(value = "delete")
	public String delete(AcReport acReport, RedirectAttributes redirectAttributes) {
		acReportService.delete(acReport);
		addMessage(redirectAttributes, "删除自动考评-警情信息成功");
		return "redirect:"+Global.getAdminPath()+"/tw/acReport/?repage";
	}

}