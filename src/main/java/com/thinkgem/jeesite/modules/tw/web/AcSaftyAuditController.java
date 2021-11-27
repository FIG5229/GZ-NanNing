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
import com.thinkgem.jeesite.modules.tw.entity.AcSaftyAudit;
import com.thinkgem.jeesite.modules.tw.service.AcSaftyAuditService;

/**
 * 自动考评-隐患信息Controller
 * @author alan.wu
 * @version 2020-11-19
 */
@Controller
@RequestMapping(value = "${adminPath}/tw/acSaftyAudit")
public class AcSaftyAuditController extends BaseController {

	@Autowired
	private AcSaftyAuditService acSaftyAuditService;
	
	@ModelAttribute
	public AcSaftyAudit get(@RequestParam(required=false) String id) {
		AcSaftyAudit entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = acSaftyAuditService.get(id);
		}
		if (entity == null){
			entity = new AcSaftyAudit();
		}
		return entity;
	}
	
	@RequiresPermissions("tw:acSaftyAudit:view")
	@RequestMapping(value = {"list", ""})
	public String list(AcSaftyAudit acSaftyAudit, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AcSaftyAudit> page = acSaftyAuditService.findPage(new Page<AcSaftyAudit>(request, response), acSaftyAudit); 
		model.addAttribute("page", page);
		return "modules/tw/acSaftyAuditList";
	}

	@RequiresPermissions("tw:acSaftyAudit:view")
	@RequestMapping(value = "form")
	public String form(AcSaftyAudit acSaftyAudit, Model model) {
		model.addAttribute("acSaftyAudit", acSaftyAudit);
		return "modules/tw/acSaftyAuditForm";
	}

	@RequiresPermissions("tw:acSaftyAudit:edit")
	@RequestMapping(value = "save")
	public String save(AcSaftyAudit acSaftyAudit, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, acSaftyAudit)){
			return form(acSaftyAudit, model);
		}
		acSaftyAuditService.save(acSaftyAudit);
		addMessage(redirectAttributes, "保存自动考评-隐患信息成功");
		return "redirect:"+Global.getAdminPath()+"/tw/acSaftyAudit/?repage";
	}
	
	@RequiresPermissions("tw:acSaftyAudit:edit")
	@RequestMapping(value = "delete")
	public String delete(AcSaftyAudit acSaftyAudit, RedirectAttributes redirectAttributes) {
		acSaftyAuditService.delete(acSaftyAudit);
		addMessage(redirectAttributes, "删除自动考评-隐患信息成功");
		return "redirect:"+Global.getAdminPath()+"/tw/acSaftyAudit/?repage";
	}

}