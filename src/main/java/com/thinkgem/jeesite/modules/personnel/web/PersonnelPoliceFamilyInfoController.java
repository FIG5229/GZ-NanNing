/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.web;

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
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelPoliceFamilyInfo;
import com.thinkgem.jeesite.modules.personnel.service.PersonnelPoliceFamilyInfoService;

/**
 * 民警家庭Controller
 * @author cecil.li
 * @version 2020-11-04
 */
@Controller
@RequestMapping(value = "${adminPath}/personnel/personnelPoliceFamilyInfo")
public class PersonnelPoliceFamilyInfoController extends BaseController {

	@Autowired
	private PersonnelPoliceFamilyInfoService personnelPoliceFamilyInfoService;
	
	@ModelAttribute
	public PersonnelPoliceFamilyInfo get(@RequestParam(required=false) String id) {
		PersonnelPoliceFamilyInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = personnelPoliceFamilyInfoService.get(id);
		}
		if (entity == null){
			entity = new PersonnelPoliceFamilyInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("personnel:personnelPoliceFamilyInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(PersonnelPoliceFamilyInfo personnelPoliceFamilyInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PersonnelPoliceFamilyInfo> page = personnelPoliceFamilyInfoService.findPage(new Page<PersonnelPoliceFamilyInfo>(request, response), personnelPoliceFamilyInfo); 
		model.addAttribute("page", page);
		return "modules/personnel/personnelPoliceFamilyInfoList";
	}

	@RequiresPermissions("personnel:personnelPoliceFamilyInfo:view")
	@RequestMapping(value = "form")
	public String form(PersonnelPoliceFamilyInfo personnelPoliceFamilyInfo, Model model) {
		model.addAttribute("personnelPoliceFamilyInfo", personnelPoliceFamilyInfo);
		return "modules/personnel/personnelPoliceFamilyInfoForm";
	}

	@RequiresPermissions("personnel:personnelPoliceFamilyInfo:edit")
	@RequestMapping(value = "save")
	public String save(PersonnelPoliceFamilyInfo personnelPoliceFamilyInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, personnelPoliceFamilyInfo)){
			return form(personnelPoliceFamilyInfo, model);
		}
		personnelPoliceFamilyInfoService.save(personnelPoliceFamilyInfo);
		addMessage(redirectAttributes, "保存民警家庭成功");
		return "redirect:"+Global.getAdminPath()+"/personnel/personnelPoliceFamilyInfo/?repage";
	}
	
	@RequiresPermissions("personnel:personnelPoliceFamilyInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(PersonnelPoliceFamilyInfo personnelPoliceFamilyInfo, RedirectAttributes redirectAttributes) {
		personnelPoliceFamilyInfoService.delete(personnelPoliceFamilyInfo);
		addMessage(redirectAttributes, "删除民警家庭成功");
		return "redirect:"+Global.getAdminPath()+"/personnel/personnelPoliceFamilyInfo/?repage";
	}

}