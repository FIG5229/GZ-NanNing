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
import com.thinkgem.jeesite.modules.tw.entity.Punchingpointinfor;
import com.thinkgem.jeesite.modules.tw.service.PunchingpointinforService;

/**
 * 自动考评-打卡信息Controller
 * @author alan.wu
 * @version 2020-11-19
 */
@Controller
@RequestMapping(value = "${adminPath}/tw/punchingpointinfor")
public class PunchingpointinforController extends BaseController {

	@Autowired
	private PunchingpointinforService punchingpointinforService;
	
	@ModelAttribute
	public Punchingpointinfor get(@RequestParam(required=false) String id) {
		Punchingpointinfor entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = punchingpointinforService.get(id);
		}
		if (entity == null){
			entity = new Punchingpointinfor();
		}
		return entity;
	}
	
	@RequiresPermissions("tw:punchingpointinfor:view")
	@RequestMapping(value = {"list", ""})
	public String list(Punchingpointinfor punchingpointinfor, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Punchingpointinfor> page = punchingpointinforService.findPage(new Page<Punchingpointinfor>(request, response), punchingpointinfor); 
		model.addAttribute("page", page);
		return "modules/tw/punchingpointinforList";
	}

	@RequiresPermissions("tw:punchingpointinfor:view")
	@RequestMapping(value = "form")
	public String form(Punchingpointinfor punchingpointinfor, Model model) {
		model.addAttribute("punchingpointinfor", punchingpointinfor);
		return "modules/tw/punchingpointinforForm";
	}

	@RequiresPermissions("tw:punchingpointinfor:edit")
	@RequestMapping(value = "save")
	public String save(Punchingpointinfor punchingpointinfor, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, punchingpointinfor)){
			return form(punchingpointinfor, model);
		}
		punchingpointinforService.save(punchingpointinfor);
		addMessage(redirectAttributes, "保存自动考评-打卡信息成功");
		return "redirect:"+Global.getAdminPath()+"/tw/punchingpointinfor/?repage";
	}
	
	@RequiresPermissions("tw:punchingpointinfor:edit")
	@RequestMapping(value = "delete")
	public String delete(Punchingpointinfor punchingpointinfor, RedirectAttributes redirectAttributes) {
		punchingpointinforService.delete(punchingpointinfor);
		addMessage(redirectAttributes, "删除自动考评-打卡信息成功");
		return "redirect:"+Global.getAdminPath()+"/tw/punchingpointinfor/?repage";
	}

}