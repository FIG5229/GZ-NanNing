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
import com.thinkgem.jeesite.modules.tw.entity.Clockexceptioninfor;
import com.thinkgem.jeesite.modules.tw.service.ClockexceptioninforService;

/**
 * 自动考评-打卡异常记录Controller
 * @author alan.wu
 * @version 2020-11-19
 */
@Controller
@RequestMapping(value = "${adminPath}/tw/clockexceptioninfor")
public class ClockexceptioninforController extends BaseController {

	@Autowired
	private ClockexceptioninforService clockexceptioninforService;
	
	@ModelAttribute
	public Clockexceptioninfor get(@RequestParam(required=false) String id) {
		Clockexceptioninfor entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = clockexceptioninforService.get(id);
		}
		if (entity == null){
			entity = new Clockexceptioninfor();
		}
		return entity;
	}
	
	@RequiresPermissions("tw:clockexceptioninfor:view")
	@RequestMapping(value = {"list", ""})
	public String list(Clockexceptioninfor clockexceptioninfor, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Clockexceptioninfor> page = clockexceptioninforService.findPage(new Page<Clockexceptioninfor>(request, response), clockexceptioninfor); 
		model.addAttribute("page", page);
		return "modules/tw/clockexceptioninforList";
	}

	@RequiresPermissions("tw:clockexceptioninfor:view")
	@RequestMapping(value = "form")
	public String form(Clockexceptioninfor clockexceptioninfor, Model model) {
		model.addAttribute("clockexceptioninfor", clockexceptioninfor);
		return "modules/tw/clockexceptioninforForm";
	}

	@RequiresPermissions("tw:clockexceptioninfor:edit")
	@RequestMapping(value = "save")
	public String save(Clockexceptioninfor clockexceptioninfor, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, clockexceptioninfor)){
			return form(clockexceptioninfor, model);
		}
		clockexceptioninforService.save(clockexceptioninfor);
		addMessage(redirectAttributes, "保存自动考评-打卡异常记录成功");
		return "redirect:"+Global.getAdminPath()+"/tw/clockexceptioninfor/?repage";
	}
	
	@RequiresPermissions("tw:clockexceptioninfor:edit")
	@RequestMapping(value = "delete")
	public String delete(Clockexceptioninfor clockexceptioninfor, RedirectAttributes redirectAttributes) {
		clockexceptioninforService.delete(clockexceptioninfor);
		addMessage(redirectAttributes, "删除自动考评-打卡异常记录成功");
		return "redirect:"+Global.getAdminPath()+"/tw/clockexceptioninfor/?repage";
	}

}