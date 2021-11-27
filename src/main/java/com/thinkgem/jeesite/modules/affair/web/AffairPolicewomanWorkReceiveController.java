/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

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
import com.thinkgem.jeesite.modules.affair.entity.AffairPolicewomanWorkReceive;
import com.thinkgem.jeesite.modules.affair.service.AffairPolicewomanWorkReceiveService;

/**
 * 女警工作管理关联表Controller
 * @author eav.liu
 * @version 2020-03-26
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairPolicewomanWorkReceive")
public class AffairPolicewomanWorkReceiveController extends BaseController {

	@Autowired
	private AffairPolicewomanWorkReceiveService affairPolicewomanWorkReceiveService;
	
	@ModelAttribute
	public AffairPolicewomanWorkReceive get(@RequestParam(required=false) String id) {
		AffairPolicewomanWorkReceive entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairPolicewomanWorkReceiveService.get(id);
		}
		if (entity == null){
			entity = new AffairPolicewomanWorkReceive();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairPolicewomanWorkReceive:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairPolicewomanWorkReceive affairPolicewomanWorkReceive, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairPolicewomanWorkReceive> page = affairPolicewomanWorkReceiveService.findPage(new Page<AffairPolicewomanWorkReceive>(request, response), affairPolicewomanWorkReceive); 
		model.addAttribute("page", page);
		return "modules/affair/affairPolicewomanWorkReceiveList";
	}

	@RequiresPermissions("affair:affairPolicewomanWorkReceive:view")
	@RequestMapping(value = "form")
	public String form(AffairPolicewomanWorkReceive affairPolicewomanWorkReceive, Model model) {
		model.addAttribute("affairPolicewomanWorkReceive", affairPolicewomanWorkReceive);
		return "modules/affair/affairPolicewomanWorkReceiveForm";
	}

	@RequiresPermissions("affair:affairPolicewomanWorkReceive:edit")
	@RequestMapping(value = "save")
	public String save(AffairPolicewomanWorkReceive affairPolicewomanWorkReceive, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, affairPolicewomanWorkReceive)){
			return form(affairPolicewomanWorkReceive, model);
		}
		affairPolicewomanWorkReceiveService.save(affairPolicewomanWorkReceive);
		addMessage(redirectAttributes, "保存女警工作管理关联表成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairPolicewomanWorkReceive/?repage";
	}
	
	@RequiresPermissions("affair:affairPolicewomanWorkReceive:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairPolicewomanWorkReceive affairPolicewomanWorkReceive, RedirectAttributes redirectAttributes) {
		affairPolicewomanWorkReceiveService.delete(affairPolicewomanWorkReceive);
		addMessage(redirectAttributes, "删除女警工作管理关联表成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairPolicewomanWorkReceive/?repage";
	}

}