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
import com.thinkgem.jeesite.modules.tw.entity.AcMobileData;
import com.thinkgem.jeesite.modules.tw.service.AcMobileDataService;

/**
 * 自动考评-警情信息Controller
 * @author alan.wu
 * @version 2020-11-19
 */
@Controller
@RequestMapping(value = "${adminPath}/tw/acMobileData")
public class AcMobileDataController extends BaseController {

	@Autowired
	private AcMobileDataService acMobileDataService;
	
	@ModelAttribute
	public AcMobileData get(@RequestParam(required=false) String id) {
		AcMobileData entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = acMobileDataService.get(id);
		}
		if (entity == null){
			entity = new AcMobileData();
		}
		return entity;
	}
	
	@RequiresPermissions("tw:acMobileData:view")
	@RequestMapping(value = {"list", ""})
	public String list(AcMobileData acMobileData, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AcMobileData> page = acMobileDataService.findPage(new Page<AcMobileData>(request, response), acMobileData); 
		model.addAttribute("page", page);
		return "modules/tw/acMobileDataList";
	}

	@RequiresPermissions("tw:acMobileData:view")
	@RequestMapping(value = "form")
	public String form(AcMobileData acMobileData, Model model) {
		model.addAttribute("acMobileData", acMobileData);
		return "modules/tw/acMobileDataForm";
	}

	@RequiresPermissions("tw:acMobileData:edit")
	@RequestMapping(value = "save")
	public String save(AcMobileData acMobileData, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, acMobileData)){
			return form(acMobileData, model);
		}
		acMobileDataService.save(acMobileData);
		addMessage(redirectAttributes, "保存自动考评-警情信息成功");
		return "redirect:"+Global.getAdminPath()+"/tw/acMobileData/?repage";
	}
	
	@RequiresPermissions("tw:acMobileData:edit")
	@RequestMapping(value = "delete")
	public String delete(AcMobileData acMobileData, RedirectAttributes redirectAttributes) {
		acMobileDataService.delete(acMobileData);
		addMessage(redirectAttributes, "删除自动考评-警情信息成功");
		return "redirect:"+Global.getAdminPath()+"/tw/acMobileData/?repage";
	}

}