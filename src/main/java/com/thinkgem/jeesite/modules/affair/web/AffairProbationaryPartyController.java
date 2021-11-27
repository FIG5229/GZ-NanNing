/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.modules.affair.entity.AffairGeneralSituation;
import com.thinkgem.jeesite.modules.affair.entity.AffairPartyMember;
import com.thinkgem.jeesite.modules.affair.service.AffairGeneralSituationService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.affair.entity.AffairProbationaryParty;
import com.thinkgem.jeesite.modules.affair.service.AffairProbationaryPartyService;

import java.util.List;
import java.util.Map;

/**
 * 预备党员转正Controller
 * @author freeman
 * @version 2020-06-02
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairProbationaryParty")
public class AffairProbationaryPartyController extends BaseController {

	@Autowired
	private AffairProbationaryPartyService affairProbationaryPartyService;
	@Autowired
	private AffairGeneralSituationService affairGeneralSituationService;
	
	@ModelAttribute
	public AffairProbationaryParty get(@RequestParam(required=false) String id) {
		AffairProbationaryParty entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairProbationaryPartyService.get(id);
		}
		if (entity == null){
			entity = new AffairProbationaryParty();
		}
		return entity;
	}

//	@RequiresPermissions("affair:affairProbationaryParty:view")
//	@RequestMapping(value = {""})
//	public String index(AffairProbationaryParty affairProbationaryParty) {
//		return "modules/affair/affairProbationaryPartyIndex";
//	}
	
	@RequiresPermissions("affair:affairProbationaryParty:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairProbationaryParty affairProbationaryParty, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("treeId", affairProbationaryParty.getTreeId());
		Page<AffairProbationaryParty> page = affairProbationaryPartyService.findPage(new Page<AffairProbationaryParty>(request, response), affairProbationaryParty);
		model.addAttribute("page", page);
		return "modules/affair/affairProbationaryPartyList";
	}

	@RequiresPermissions("affair:affairProbationaryParty:view")
	@RequestMapping(value = "form")
	public String form(AffairProbationaryParty affairProbationaryParty, Model model) {
		model.addAttribute("affairProbationaryParty", affairProbationaryParty);
		return "modules/affair/affairProbationaryPartyForm";
	}

	@RequiresPermissions("affair:affairProbationaryParty:edit")
	@RequestMapping(value = "save")
	public String save(AffairProbationaryParty affairProbationaryParty, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, affairProbationaryParty)){
			return form(affairProbationaryParty, model);
		}
		affairProbationaryPartyService.save(affairProbationaryParty);
		addMessage(redirectAttributes, "保存预备党员转正成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairProbationaryParty/?repage";
	}
	
	@RequiresPermissions("affair:affairProbationaryParty:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairProbationaryParty affairProbationaryParty, RedirectAttributes redirectAttributes) {
		affairProbationaryPartyService.delete(affairProbationaryParty);
		addMessage(redirectAttributes, "删除预备党员转正成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairProbationaryParty/?repage";
	}
}