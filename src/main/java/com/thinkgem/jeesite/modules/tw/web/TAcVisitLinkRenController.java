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
import com.thinkgem.jeesite.modules.tw.entity.TAcVisitLinkRen;
import com.thinkgem.jeesite.modules.tw.service.TAcVisitLinkRenService;

/**
 * 自动考评-回访记录和人员关联信息Controller
 * @author alan.wu
 * @version 2020-11-20
 */
@Controller
@RequestMapping(value = "${adminPath}/tw/tAcVisitLinkRen")
public class TAcVisitLinkRenController extends BaseController {

	@Autowired
	private TAcVisitLinkRenService tAcVisitLinkRenService;
	
	@ModelAttribute
	public TAcVisitLinkRen get(@RequestParam(required=false) String id) {
		TAcVisitLinkRen entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tAcVisitLinkRenService.get(id);
		}
		if (entity == null){
			entity = new TAcVisitLinkRen();
		}
		return entity;
	}
	
	@RequiresPermissions("tw:tAcVisitLinkRen:view")
	@RequestMapping(value = {"list", ""})
	public String list(TAcVisitLinkRen tAcVisitLinkRen, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TAcVisitLinkRen> page = tAcVisitLinkRenService.findPage(new Page<TAcVisitLinkRen>(request, response), tAcVisitLinkRen); 
		model.addAttribute("page", page);
		return "modules/tw/tAcVisitLinkRenList";
	}

	@RequiresPermissions("tw:tAcVisitLinkRen:view")
	@RequestMapping(value = "form")
	public String form(TAcVisitLinkRen tAcVisitLinkRen, Model model) {
		model.addAttribute("tAcVisitLinkRen", tAcVisitLinkRen);
		return "modules/tw/tAcVisitLinkRenForm";
	}

	@RequiresPermissions("tw:tAcVisitLinkRen:edit")
	@RequestMapping(value = "save")
	public String save(TAcVisitLinkRen tAcVisitLinkRen, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tAcVisitLinkRen)){
			return form(tAcVisitLinkRen, model);
		}
		tAcVisitLinkRenService.save(tAcVisitLinkRen);
		addMessage(redirectAttributes, "保存自动考评-回访记录和人员关联信息成功");
		return "redirect:"+Global.getAdminPath()+"/tw/tAcVisitLinkRen/?repage";
	}
	
	@RequiresPermissions("tw:tAcVisitLinkRen:edit")
	@RequestMapping(value = "delete")
	public String delete(TAcVisitLinkRen tAcVisitLinkRen, RedirectAttributes redirectAttributes) {
		tAcVisitLinkRenService.delete(tAcVisitLinkRen);
		addMessage(redirectAttributes, "删除自动考评-回访记录和人员关联信息成功");
		return "redirect:"+Global.getAdminPath()+"/tw/tAcVisitLinkRen/?repage";
	}

}