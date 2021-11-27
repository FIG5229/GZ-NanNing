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
import com.thinkgem.jeesite.modules.tw.entity.Dutywarning;
import com.thinkgem.jeesite.modules.tw.service.DutywarningService;

/**
 * 自动考评-打卡预警Controller
 * @author alan.wu
 * @version 2020-11-19
 */
@Controller
@RequestMapping(value = "${adminPath}/tw/dutywarning")
public class DutywarningController extends BaseController {

	@Autowired
	private DutywarningService dutywarningService;
	
	@ModelAttribute
	public Dutywarning get(@RequestParam(required=false) String id) {
		Dutywarning entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = dutywarningService.get(id);
		}
		if (entity == null){
			entity = new Dutywarning();
		}
		return entity;
	}
	
	@RequiresPermissions("tw:dutywarning:view")
	@RequestMapping(value = {"list", ""})
	public String list(Dutywarning dutywarning, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Dutywarning> page = dutywarningService.findPage(new Page<Dutywarning>(request, response), dutywarning); 
		model.addAttribute("page", page);
		return "modules/tw/dutywarningList";
	}

	@RequiresPermissions("tw:dutywarning:view")
	@RequestMapping(value = "form")
	public String form(Dutywarning dutywarning, Model model) {
		model.addAttribute("dutywarning", dutywarning);
		return "modules/tw/dutywarningForm";
	}

	@RequiresPermissions("tw:dutywarning:edit")
	@RequestMapping(value = "save")
	public String save(Dutywarning dutywarning, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, dutywarning)){
			return form(dutywarning, model);
		}
		dutywarningService.save(dutywarning);
		addMessage(redirectAttributes, "保存自动考评-打卡预警成功");
		return "redirect:"+Global.getAdminPath()+"/tw/dutywarning/?repage";
	}
	
	@RequiresPermissions("tw:dutywarning:edit")
	@RequestMapping(value = "delete")
	public String delete(Dutywarning dutywarning, RedirectAttributes redirectAttributes) {
		dutywarningService.delete(dutywarning);
		addMessage(redirectAttributes, "删除自动考评-打卡预警成功");
		return "redirect:"+Global.getAdminPath()+"/tw/dutywarning/?repage";
	}

}