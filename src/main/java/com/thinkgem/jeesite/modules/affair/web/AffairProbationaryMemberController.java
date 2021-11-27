/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.affair.entity.AffairPartyMember;
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
import com.thinkgem.jeesite.modules.affair.entity.AffairProbationaryMember;
import com.thinkgem.jeesite.modules.affair.service.AffairProbationaryMemberService;

/**
 * 预备党员Controller
 * @author cecil.li
 * @version 2020-12-21
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairProbationaryMember")
public class AffairProbationaryMemberController extends BaseController {

	@Autowired
	private AffairProbationaryMemberService affairProbationaryMemberService;
	
	@ModelAttribute
	public AffairProbationaryMember get(@RequestParam(required=false) String id) {
		AffairProbationaryMember entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairProbationaryMemberService.get(id);
		}
		if (entity == null){
			entity = new AffairProbationaryMember();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairProbationaryMember:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairProbationaryMember affairProbationaryMember, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("treeId", affairProbationaryMember.getTreeId());
		String partyBranchId = affairProbationaryMember.getTreeId();
		Page<AffairProbationaryMember> page = affairProbationaryMemberService.findPage(new Page<AffairProbationaryMember>(request, response), affairProbationaryMember); 
		model.addAttribute("page", page);
		return "modules/affair/affairProbationaryMemberList";
	}

	@RequiresPermissions("affair:affairProbationaryMember:view")
	@RequestMapping(value = "form")
	public String form(AffairProbationaryMember affairProbationaryMember, Model model) {
		model.addAttribute("affairProbationaryMember", affairProbationaryMember);
		return "modules/affair/affairProbationaryMemberForm";
	}

	@RequiresPermissions("affair:affairProbationaryMember:edit")
	@RequestMapping(value = "save")
	public String save(AffairProbationaryMember affairProbationaryMember, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairProbationaryMember)){
			return form(affairProbationaryMember, model);
		}
		affairProbationaryMemberService.save(affairProbationaryMember);
		addMessage(redirectAttributes, "保存预备党员成功");
		request.setAttribute("saveResult", "sucess");
		return "modules/affair/affairProbationaryMemberForm";
	}
	
	@RequiresPermissions("affair:affairProbationaryMember:edit")
	@RequestMapping(value = "deletePerson")
	public String delete(AffairProbationaryMember affairProbationaryMember, RedirectAttributes redirectAttributes) {
		affairProbationaryMemberService.delete(affairProbationaryMember);
		addMessage(redirectAttributes, "删除预备党员信息成功");
		return "redirect:"+ Global.getAdminPath()+"/affair/affairProbationaryMember/list?repage&treeId="+affairProbationaryMember.getTreeId();
	}

	@RequiresPermissions("affair:affairProbationaryMember:view")
	@RequestMapping(value = {"formDetail"})
	public String formDetail(AffairProbationaryMember affairProbationaryMember, Model model) {
		model.addAttribute("affairProbationaryMember", affairProbationaryMember);
		return "modules/affair/affairProbationaryMemberFormDetail";
	}

	@RequiresPermissions("affair:affairProbationaryMember:manage")
	@RequestMapping(value = {"dialog"})
	public String dialog(AffairProbationaryMember affairProbationaryMember, HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/affair/affairProbationaryMemberCheckDialog";
	}

	@RequiresPermissions("affair:affairProbationaryMember:manage")
	@RequestMapping(value = "shenHe")
	public String shenHe(AffairProbationaryMember affairProbationaryMember, RedirectAttributes redirectAttributes) {
		affairProbationaryMemberService.shenHe(affairProbationaryMember);
		addMessage(redirectAttributes, "审核预备党员成功");
		return "modules/affair/affairProbationaryMemberCheckDialog";
	}

}