/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.warn.web;

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
import com.thinkgem.jeesite.modules.warn.entity.WarnReceive;
import com.thinkgem.jeesite.modules.warn.service.WarnReceiveService;

/**
 * 预警接收部门Controller
 * @author eav.liu
 * @version 2019-11-28
 */
@Controller
@RequestMapping(value = "${adminPath}/warn/warnReceive")
public class WarnReceiveController extends BaseController {

	@Autowired
	private WarnReceiveService warnReceiveService;
	
	@ModelAttribute
	public WarnReceive get(@RequestParam(required=false) String id) {
		WarnReceive entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = warnReceiveService.get(id);
		}
		if (entity == null){
			entity = new WarnReceive();
		}
		return entity;
	}
	
	@RequiresPermissions("warn:warnReceive:view")
	@RequestMapping(value = {"list", ""})
	public String list(WarnReceive warnReceive, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WarnReceive> page = warnReceiveService.findPage(new Page<WarnReceive>(request, response), warnReceive); 
		model.addAttribute("page", page);
		return "modules/warn/warnReceiveList";
	}

	@RequiresPermissions("warn:warnReceive:view")
	@RequestMapping(value = "form")
	public String form(WarnReceive warnReceive, Model model) {
		model.addAttribute("warnReceive", warnReceive);
		return "modules/warn/warnReceiveForm";
	}

	@RequiresPermissions("warn:warnReceive:edit")
	@RequestMapping(value = "save")
	public String save(WarnReceive warnReceive, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, warnReceive)){
			return form(warnReceive, model);
		}
		warnReceiveService.save(warnReceive);
		addMessage(redirectAttributes, "保存预警接收部门成功");
		return "redirect:"+Global.getAdminPath()+"/warn/warnReceive/?repage";
	}
	
	@RequiresPermissions("warn:warnReceive:edit")
	@RequestMapping(value = "delete")
	public String delete(WarnReceive warnReceive, RedirectAttributes redirectAttributes) {
		warnReceiveService.delete(warnReceive);
		addMessage(redirectAttributes, "删除预警接收部门成功");
		return "redirect:"+Global.getAdminPath()+"/warn/warnReceive/?repage";
	}

}