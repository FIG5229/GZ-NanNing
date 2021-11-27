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
import com.thinkgem.jeesite.modules.affair.entity.AffairOrganziationBuildSign;
import com.thinkgem.jeesite.modules.affair.service.AffairOrganziationBuildSignService;

/**
 * 组织建设关联表Controller
 * @author cecil.li
 * @version 2019-12-16
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairOrganziationBuildSign")
public class AffairOrganziationBuildSignController extends BaseController {

	@Autowired
	private AffairOrganziationBuildSignService affairOrganziationBuildSignService;
	
	@ModelAttribute
	public AffairOrganziationBuildSign get(@RequestParam(required=false) String id) {
		AffairOrganziationBuildSign entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairOrganziationBuildSignService.get(id);
		}
		if (entity == null){
			entity = new AffairOrganziationBuildSign();
		}
		return entity;
	}
	
	//@RequiresPermissions("affair:affairOrganziationBuildSign:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairOrganziationBuildSign affairOrganziationBuildSign, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairOrganziationBuildSign> page = affairOrganziationBuildSignService.findPage(new Page<AffairOrganziationBuildSign>(request, response), affairOrganziationBuildSign); 
		model.addAttribute("page", page);
		return "modules/affair/affairOrganziationBuildSignList";
	}

	@RequiresPermissions("affair:affairOrganziationBuildSign:view")
	@RequestMapping(value = "form")
	public String form(AffairOrganziationBuildSign affairOrganziationBuildSign, Model model) {
		model.addAttribute("affairOrganziationBuildSign", affairOrganziationBuildSign);
		return "modules/affair/affairOrganziationBuildSignForm";
	}

	@RequiresPermissions("affair:affairOrganziationBuildSign:edit")
	@RequestMapping(value = "save")
	public String save(AffairOrganziationBuildSign affairOrganziationBuildSign, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, affairOrganziationBuildSign)){
			return form(affairOrganziationBuildSign, model);
		}
		affairOrganziationBuildSignService.save(affairOrganziationBuildSign);
		addMessage(redirectAttributes, "保存组织建设关联表成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairOrganziationBuildSign/?repage";
	}
	
	@RequiresPermissions("affair:affairOrganziationBuildSign:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairOrganziationBuildSign affairOrganziationBuildSign, RedirectAttributes redirectAttributes) {
		affairOrganziationBuildSignService.delete(affairOrganziationBuildSign);
		addMessage(redirectAttributes, "删除组织建设关联表成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairOrganziationBuildSign/?repage";
	}

}