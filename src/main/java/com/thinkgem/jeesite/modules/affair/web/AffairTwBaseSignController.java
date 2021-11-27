/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.affair.entity.AffairTjRegister;
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
import com.thinkgem.jeesite.modules.affair.entity.AffairTwBaseSign;
import com.thinkgem.jeesite.modules.affair.service.AffairTwBaseSignService;

/**
 * 团委（支部）基本信息关联Controller
 * @author cecil.li
 * @version 2019-12-04
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairTwBaseSign")
public class AffairTwBaseSignController extends BaseController {

	@Autowired
	private AffairTwBaseSignService affairTwBaseSignService;
	
	@ModelAttribute
	public AffairTwBaseSign get(@RequestParam(required=false) String id) {
		AffairTwBaseSign entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairTwBaseSignService.get(id);
		}
		if (entity == null){
			entity = new AffairTwBaseSign();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairTwBaseSign:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairTwBaseSign affairTwBaseSign, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairTwBaseSign> page = affairTwBaseSignService.findPage(new Page<AffairTwBaseSign>(request, response), affairTwBaseSign); 
		model.addAttribute("page", page);
		return "modules/affair/affairTwBaseSignList";
	}

	@RequiresPermissions("affair:affairTwBaseSign:view")
	@RequestMapping(value = "form")
	public String form(AffairTwBaseSign affairTwBaseSign, Model model) {
		model.addAttribute("affairTwBaseSign", affairTwBaseSign);
		return "modules/affair/affairTwBaseSignForm";
	}

	@RequiresPermissions("affair:affairTwBaseSign:edit")
	@RequestMapping(value = "save")
	public String save(AffairTwBaseSign affairTwBaseSign, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, affairTwBaseSign)){
			return form(affairTwBaseSign, model);
		}
		affairTwBaseSignService.save(affairTwBaseSign);
		addMessage(redirectAttributes, "保存团委（支部）基本信息关联成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairTwBaseSign/?repage";
	}
	
	@RequiresPermissions("affair:affairTwBaseSign:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairTwBaseSign affairTwBaseSign, RedirectAttributes redirectAttributes) {
		affairTwBaseSignService.delete(affairTwBaseSign);
		addMessage(redirectAttributes, "删除团委（支部）基本信息关联成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairTwBaseSign/?repage";
	}

	@RequestMapping("cadresDetail")
	public String cadresDetail(AffairTwBaseSign affairTwBaseSign, Model model, HttpServletRequest request, HttpServletResponse response){
		Page<AffairTwBaseSign> page = affairTwBaseSignService.findCadresPage(new Page<AffairTwBaseSign>(request, response), affairTwBaseSign);
		model.addAttribute("page", page);
		return "modules/charts/chartsTwBaseSignList";
	}

}