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
import com.thinkgem.jeesite.modules.tw.entity.Ren001;
import com.thinkgem.jeesite.modules.tw.service.Ren001Service;

/**
 * 自动考评-精神病患者基本信息Controller
 * @author alan.wu
 * @version 2020-11-20
 */
@Controller
@RequestMapping(value = "${adminPath}/tw/ren001")
public class Ren001Controller extends BaseController {

	@Autowired
	private Ren001Service ren001Service;
	
	@ModelAttribute
	public Ren001 get(@RequestParam(required=false) String id) {
		Ren001 entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = ren001Service.get(id);
		}
		if (entity == null){
			entity = new Ren001();
		}
		return entity;
	}
	
	@RequiresPermissions("tw:ren001:view")
	@RequestMapping(value = {"list", ""})
	public String list(Ren001 ren001, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Ren001> page = ren001Service.findPage(new Page<Ren001>(request, response), ren001); 
		model.addAttribute("page", page);
		return "modules/tw/ren001List";
	}

	@RequiresPermissions("tw:ren001:view")
	@RequestMapping(value = "form")
	public String form(Ren001 ren001, Model model) {
		model.addAttribute("ren001", ren001);
		return "modules/tw/ren001Form";
	}

	@RequiresPermissions("tw:ren001:edit")
	@RequestMapping(value = "save")
	public String save(Ren001 ren001, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, ren001)){
			return form(ren001, model);
		}
		ren001Service.save(ren001);
		addMessage(redirectAttributes, "保存自动考评-精神病患者基本信息成功");
		return "redirect:"+Global.getAdminPath()+"/tw/ren001/?repage";
	}
	
	@RequiresPermissions("tw:ren001:edit")
	@RequestMapping(value = "delete")
	public String delete(Ren001 ren001, RedirectAttributes redirectAttributes) {
		ren001Service.delete(ren001);
		addMessage(redirectAttributes, "删除自动考评-精神病患者基本信息成功");
		return "redirect:"+Global.getAdminPath()+"/tw/ren001/?repage";
	}

}