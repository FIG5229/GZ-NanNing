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
import com.thinkgem.jeesite.modules.tw.entity.TPoliceMessage;
import com.thinkgem.jeesite.modules.tw.service.TPoliceMessageService;

/**
 * 警情预警-自动考评Controller
 * @author alan.wu
 * @version 2020-10-16
 */
@Controller
@RequestMapping(value = "${adminPath}/tw/tPoliceMessage")
public class TPoliceMessageController extends BaseController {

	@Autowired
	private TPoliceMessageService tPoliceMessageService;
	
	@ModelAttribute
	public TPoliceMessage get(@RequestParam(required=false) String id) {
		TPoliceMessage entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tPoliceMessageService.get(id);
		}
		if (entity == null){
			entity = new TPoliceMessage();
		}
		return entity;
	}
	
	@RequiresPermissions("tw:tPoliceMessage:view")
	@RequestMapping(value = {"list", ""})
	public String list(TPoliceMessage tPoliceMessage, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TPoliceMessage> page = tPoliceMessageService.findPage(new Page<TPoliceMessage>(request, response), tPoliceMessage); 
		model.addAttribute("page", page);
		return "modules/tw/tPoliceMessageList";
	}

	@RequiresPermissions("tw:tPoliceMessage:view")
	@RequestMapping(value = "form")
	public String form(TPoliceMessage tPoliceMessage, Model model) {
		model.addAttribute("tPoliceMessage", tPoliceMessage);
		return "modules/tw/tPoliceMessageForm";
	}

	@RequiresPermissions("tw:tPoliceMessage:edit")
	@RequestMapping(value = "save")
	public String save(TPoliceMessage tPoliceMessage, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tPoliceMessage)){
			return form(tPoliceMessage, model);
		}
		tPoliceMessageService.save(tPoliceMessage);
		addMessage(redirectAttributes, "保存警情预警-自动考评成功");
		return "redirect:"+Global.getAdminPath()+"/tw/tPoliceMessage/?repage";
	}
	
	@RequiresPermissions("tw:tPoliceMessage:edit")
	@RequestMapping(value = "delete")
	public String delete(TPoliceMessage tPoliceMessage, RedirectAttributes redirectAttributes) {
		tPoliceMessageService.delete(tPoliceMessage);
		addMessage(redirectAttributes, "删除警情预警-自动考评成功");
		return "redirect:"+Global.getAdminPath()+"/tw/tPoliceMessage/?repage";
	}

}