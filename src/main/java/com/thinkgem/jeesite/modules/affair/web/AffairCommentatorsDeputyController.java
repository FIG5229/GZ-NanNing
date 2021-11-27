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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.affair.entity.AffairCommentatorsDeputy;
import com.thinkgem.jeesite.modules.affair.service.AffairCommentatorsDeputyService;

/**
 * 网评员管理-副表Controller
 * @author alan.wu
 * @version 2020-06-22
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairCommentatorsDeputy")
public class AffairCommentatorsDeputyController extends BaseController {

	@Autowired
	private AffairCommentatorsDeputyService affairCommentatorsDeputyService;
	
	@ModelAttribute
	public AffairCommentatorsDeputy get(@RequestParam(required=false) String id) {
		AffairCommentatorsDeputy entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairCommentatorsDeputyService.get(id);
		}
		if (entity == null){
			entity = new AffairCommentatorsDeputy();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairCommentatorsDeputy:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairCommentatorsDeputy affairCommentatorsDeputy, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairCommentatorsDeputy> page = affairCommentatorsDeputyService.findPage(new Page<AffairCommentatorsDeputy>(request, response), affairCommentatorsDeputy); 
		model.addAttribute("page", page);
		return "modules/affair/affairCommentatorsDeputyList";
	}

	@RequiresPermissions("affair:affairCommentatorsDeputy:view")
	@RequestMapping(value = "form")
	public String form(AffairCommentatorsDeputy affairCommentatorsDeputy, Model model) {

		model.addAttribute("affairCommentatorsDeputy", affairCommentatorsDeputy);
		return "modules/affair/affairCommentatorsDeputyForm";
	}

	@RequiresPermissions("affair:affairCommentatorsDeputy:edit")
	@RequestMapping(value = "save")
	public String save(AffairCommentatorsDeputy affairCommentatorsDeputy, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, affairCommentatorsDeputy)){
			return form(affairCommentatorsDeputy, model);
		}
		affairCommentatorsDeputyService.save(affairCommentatorsDeputy);
		addMessage(redirectAttributes, "保存网评员管理-副表成功");
		model.addAttribute("saveResult","success");
		return "modules/affair/affairCommentatorsDeputyForm";
	}
	
	@RequiresPermissions("affair:affairCommentatorsDeputy:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairCommentatorsDeputy affairCommentatorsDeputy, RedirectAttributes redirectAttributes) {
		affairCommentatorsDeputyService.delete(affairCommentatorsDeputy);
		addMessage(redirectAttributes, "删除网评员管理-副表成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairCommentatorsDeputy/?repage";
	}

	@RequiresPermissions("affair:affairCommentatorsDeputy:edit")
	@RequestMapping(value = "saved")
	public String saved(AffairCommentatorsDeputy affairCommentatorsDeputy, Model model, RedirectAttributes redirectAttributes) {

		affairCommentatorsDeputyService.save(affairCommentatorsDeputy);

		String idNumber = affairCommentatorsDeputy.getIdNumber();

		addMessage(redirectAttributes, "保存网评员管理-副表成功");
		model.addAttribute("saveResult","success");

		return "modules/affair/affairCommentatorsDeputyForm";
	}

}