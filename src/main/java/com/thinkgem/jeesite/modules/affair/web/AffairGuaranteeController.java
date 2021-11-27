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
import com.thinkgem.jeesite.modules.affair.entity.AffairGuarantee;
import com.thinkgem.jeesite.modules.affair.service.AffairGuaranteeService;

/**
 * 医保金Controller
 * @author mason.xv
 * @version 2019-11-28
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairGuarantee")
public class AffairGuaranteeController extends BaseController {

	@Autowired
	private AffairGuaranteeService affairGuaranteeService;
	
	@ModelAttribute
	public AffairGuarantee get(@RequestParam(required=false) String id) {
		AffairGuarantee entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairGuaranteeService.get(id);
		}
		if (entity == null){
			entity = new AffairGuarantee();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairGuarantee:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairGuarantee affairGuarantee, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairGuarantee> page = affairGuaranteeService.findPage(new Page<AffairGuarantee>(request, response), affairGuarantee); 
		model.addAttribute("page", page);
		return "modules/affair/affairGuaranteeList";
	}

	@RequiresPermissions("affair:affairGuarantee:view")
	@RequestMapping(value = "form")
	public String form(AffairGuarantee affairGuarantee, Model model) {
		model.addAttribute("affairGuarantee", affairGuarantee);
		return "modules/affair/affairGuaranteeForm";
	}

	@RequiresPermissions("affair:affairGuarantee:edit")
	@RequestMapping(value = "save")
	public String save(AffairGuarantee affairGuarantee, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, affairGuarantee)){
			return form(affairGuarantee, model);
		}
		affairGuaranteeService.save(affairGuarantee);
		addMessage(redirectAttributes, "保存医保金成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairGuarantee/?repage";
	}
	
	@RequiresPermissions("affair:affairGuarantee:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairGuarantee affairGuarantee, RedirectAttributes redirectAttributes) {
		affairGuaranteeService.delete(affairGuarantee);
		addMessage(redirectAttributes, "删除医保金成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairGuarantee/?repage";
	}

}