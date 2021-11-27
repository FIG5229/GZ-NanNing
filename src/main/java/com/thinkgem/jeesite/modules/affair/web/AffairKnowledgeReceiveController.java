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
import com.thinkgem.jeesite.modules.affair.entity.AffairKnowledgeReceive;
import com.thinkgem.jeesite.modules.affair.service.AffairKnowledgeReceiveService;

/**
 * 党规党章及党建知识关联Controller
 * @author eav.liu
 * @version 2019-11-04
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairKnowledgeReceive")
public class AffairKnowledgeReceiveController extends BaseController {

	@Autowired
	private AffairKnowledgeReceiveService affairKnowledgeReceiveService;
	
	@ModelAttribute
	public AffairKnowledgeReceive get(@RequestParam(required=false) String id) {
		AffairKnowledgeReceive entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairKnowledgeReceiveService.get(id);
		}
		if (entity == null){
			entity = new AffairKnowledgeReceive();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairKnowledgeReceive:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairKnowledgeReceive affairKnowledgeReceive, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairKnowledgeReceive> page = affairKnowledgeReceiveService.findPage(new Page<AffairKnowledgeReceive>(request, response), affairKnowledgeReceive); 
		model.addAttribute("page", page);
		return "modules/affair/affairKnowledgeReceiveList";
	}

	@RequiresPermissions("affair:affairKnowledgeReceive:view")
	@RequestMapping(value = "form")
	public String form(AffairKnowledgeReceive affairKnowledgeReceive, Model model) {
		model.addAttribute("affairKnowledgeReceive", affairKnowledgeReceive);
		return "modules/affair/affairKnowledgeReceiveForm";
	}

	@RequiresPermissions("affair:affairKnowledgeReceive:edit")
	@RequestMapping(value = "save")
	public String save(AffairKnowledgeReceive affairKnowledgeReceive, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, affairKnowledgeReceive)){
			return form(affairKnowledgeReceive, model);
		}
		affairKnowledgeReceiveService.save(affairKnowledgeReceive);
		addMessage(redirectAttributes, "保存党规党章及党建知识成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairKnowledgeReceive/?repage";
	}
	
	@RequiresPermissions("affair:affairKnowledgeReceive:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairKnowledgeReceive affairKnowledgeReceive, RedirectAttributes redirectAttributes) {
		affairKnowledgeReceiveService.delete(affairKnowledgeReceive);
		addMessage(redirectAttributes, "删除党规党章及党建知识成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairKnowledgeReceive/?repage";
	}

}