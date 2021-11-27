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
import com.thinkgem.jeesite.modules.tw.entity.Patrolrecord;
import com.thinkgem.jeesite.modules.tw.service.PatrolrecordService;

/**
 * 自动考评-打卡异常信息Controller
 * @author alan.wu
 * @version 2020-11-19
 */
@Controller
@RequestMapping(value = "${adminPath}/tw/patrolrecord")
public class PatrolrecordController extends BaseController {

	@Autowired
	private PatrolrecordService patrolrecordService;
	
	@ModelAttribute
	public Patrolrecord get(@RequestParam(required=false) String id) {
		Patrolrecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = patrolrecordService.get(id);
		}
		if (entity == null){
			entity = new Patrolrecord();
		}
		return entity;
	}
	
	@RequiresPermissions("tw:patrolrecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(Patrolrecord patrolrecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Patrolrecord> page = patrolrecordService.findPage(new Page<Patrolrecord>(request, response), patrolrecord); 
		model.addAttribute("page", page);
		return "modules/tw/patrolrecordList";
	}

	@RequiresPermissions("tw:patrolrecord:view")
	@RequestMapping(value = "form")
	public String form(Patrolrecord patrolrecord, Model model) {
		model.addAttribute("patrolrecord", patrolrecord);
		return "modules/tw/patrolrecordForm";
	}

	@RequiresPermissions("tw:patrolrecord:edit")
	@RequestMapping(value = "save")
	public String save(Patrolrecord patrolrecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, patrolrecord)){
			return form(patrolrecord, model);
		}
		patrolrecordService.save(patrolrecord);
		addMessage(redirectAttributes, "保存自动考评-打卡异常信息成功");
		return "redirect:"+Global.getAdminPath()+"/tw/patrolrecord/?repage";
	}
	
	@RequiresPermissions("tw:patrolrecord:edit")
	@RequestMapping(value = "delete")
	public String delete(Patrolrecord patrolrecord, RedirectAttributes redirectAttributes) {
		patrolrecordService.delete(patrolrecord);
		addMessage(redirectAttributes, "删除自动考评-打卡异常信息成功");
		return "redirect:"+Global.getAdminPath()+"/tw/patrolrecord/?repage";
	}

}