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
import com.thinkgem.jeesite.modules.affair.entity.AffairOrganizationBuildSing2;
import com.thinkgem.jeesite.modules.affair.service.AffairOrganizationBuildSing2Service;

/**
 * 组织建设关联表2Controller
 * @author cecil.li
 * @version 2019-12-16
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairOrganizationBuildSing2")
public class AffairOrganizationBuildSing2Controller extends BaseController {

	@Autowired
	private AffairOrganizationBuildSing2Service affairOrganizationBuildSing2Service;
	
	@ModelAttribute
	public AffairOrganizationBuildSing2 get(@RequestParam(required=false) String id) {
		AffairOrganizationBuildSing2 entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairOrganizationBuildSing2Service.get(id);
		}
		if (entity == null){
			entity = new AffairOrganizationBuildSing2();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairOrganizationBuildSing2:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairOrganizationBuildSing2 affairOrganizationBuildSing2, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairOrganizationBuildSing2> page = affairOrganizationBuildSing2Service.findPage(new Page<AffairOrganizationBuildSing2>(request, response), affairOrganizationBuildSing2); 
		model.addAttribute("page", page);
		return "modules/affair/affairOrganizationBuildSing2List";
	}

	@RequiresPermissions("affair:affairOrganizationBuildSing2:view")
	@RequestMapping(value = "form")
	public String form(AffairOrganizationBuildSing2 affairOrganizationBuildSing2, Model model) {
		model.addAttribute("affairOrganizationBuildSing2", affairOrganizationBuildSing2);
		return "modules/affair/affairOrganizationBuildSing2Form";
	}

	@RequiresPermissions("affair:affairOrganizationBuildSing2:edit")
	@RequestMapping(value = "save")
	public String save(AffairOrganizationBuildSing2 affairOrganizationBuildSing2, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, affairOrganizationBuildSing2)){
			return form(affairOrganizationBuildSing2, model);
		}
		affairOrganizationBuildSing2Service.save(affairOrganizationBuildSing2);
		addMessage(redirectAttributes, "保存组织建设关联表2成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairOrganizationBuildSing2/?repage";
	}
	
	@RequiresPermissions("affair:affairOrganizationBuildSing2:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairOrganizationBuildSing2 affairOrganizationBuildSing2, RedirectAttributes redirectAttributes) {
		affairOrganizationBuildSing2Service.delete(affairOrganizationBuildSing2);
		addMessage(redirectAttributes, "删除组织建设关联表2成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairOrganizationBuildSing2/?repage";
	}

}