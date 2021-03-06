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
import com.thinkgem.jeesite.modules.affair.entity.AffairTransferSh;
import com.thinkgem.jeesite.modules.affair.service.AffairTransferShService;

/**
 * 系统内组织关系移交转接关联表Controller
 * @author eav.liu
 * @version 2019-11-12
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairTransferSh")
public class AffairTransferShController extends BaseController {

	@Autowired
	private AffairTransferShService affairTransferShService;
	
	@ModelAttribute
	public AffairTransferSh get(@RequestParam(required=false) String id) {
		AffairTransferSh entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairTransferShService.get(id);
		}
		if (entity == null){
			entity = new AffairTransferSh();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairTransferSh:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairTransferSh affairTransferSh, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairTransferSh> page = affairTransferShService.findPage(new Page<AffairTransferSh>(request, response), affairTransferSh); 
		model.addAttribute("page", page);
		return "modules/affair/affairTransferShList";
	}

	@RequiresPermissions("affair:affairTransferSh:view")
	@RequestMapping(value = "form")
	public String form(AffairTransferSh affairTransferSh, Model model) {
		model.addAttribute("affairTransferSh", affairTransferSh);
		return "modules/affair/affairTransferShForm";
	}

	@RequiresPermissions("affair:affairTransferSh:edit")
	@RequestMapping(value = "save")
	public String save(AffairTransferSh affairTransferSh, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, affairTransferSh)){
			return form(affairTransferSh, model);
		}
		affairTransferShService.save(affairTransferSh);
		addMessage(redirectAttributes, "保存系统内组织关系移交转接关联表成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairTransferSh/?repage";
	}
	
	@RequiresPermissions("affair:affairTransferSh:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairTransferSh affairTransferSh, RedirectAttributes redirectAttributes) {
		affairTransferShService.delete(affairTransferSh);
		addMessage(redirectAttributes, "删除系统内组织关系移交转接关联表成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairTransferSh/?repage";
	}

}