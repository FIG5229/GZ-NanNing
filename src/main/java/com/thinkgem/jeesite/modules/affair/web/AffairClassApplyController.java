/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.affair.entity.AffairClassManage;
import com.thinkgem.jeesite.modules.affair.service.AffairClassManageService;
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
import com.thinkgem.jeesite.modules.affair.entity.AffairClassApply;
import com.thinkgem.jeesite.modules.affair.service.AffairClassApplyService;

import java.util.ArrayList;
import java.util.List;

/**
 * 班级报名Controller
 * @author kevin.jia
 * @version 2020-07-29
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairClassApply")
public class AffairClassApplyController extends BaseController {

	@Autowired
	private AffairClassApplyService affairClassApplyService;

	@Autowired
	private AffairClassManageService affairClassManageService;//培训班管理
	@ModelAttribute
	public AffairClassApply get(@RequestParam(required=false) String id) {
		AffairClassApply entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairClassApplyService.get(id);
		}
		if (entity == null){
			entity = new AffairClassApply();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairClassApply:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairClassManage affairClassManage, HttpServletRequest request, HttpServletResponse response, Model model) {
		//Page<AffairClassApply> page = affairClassApplyService.findPage(new Page<AffairClassApply>(request, response), affairClassApply);
		Page<AffairClassManage> page = affairClassManageService.findPage(new Page<AffairClassManage>(request, response), affairClassManage);
		model.addAttribute("page", page);
		return "modules/affair/affairClassApplyList";
	}

	@RequiresPermissions("affair:affairClassApply:view")
	@RequestMapping(value = "form")
	public String form(AffairClassApply affairClassApply, Model model,String classId) {
		model.addAttribute("affairClassApply", affairClassApply);
		model.addAttribute("classId",classId);
		List<String> ids = new ArrayList<>();
		ids.add(classId);
		List<AffairClassManage> affairClassManageList = affairClassManageService.findByIds(ids);
		model.addAttribute("affairClassManage",affairClassManageList.get(0));
		return "modules/affair/affairClassApplyForm";
	}

	@RequiresPermissions("affair:affairClassApply:edit")
	@RequestMapping(value = "save")
	public String save(AffairClassApply affairClassApply, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairClassApply)){
			return form(affairClassApply, model,affairClassApply.getClassId());
		}
		affairClassApplyService.save(affairClassApply);
		addMessage(redirectAttributes, "报名成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairClassApplyForm";
	}
	
	@RequiresPermissions("affair:affairClassApply:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairClassApply affairClassApply, RedirectAttributes redirectAttributes) {
		affairClassApplyService.delete(affairClassApply);
		addMessage(redirectAttributes, "删除班级报名成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairClassApply/?repage";
	}

}