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
import com.thinkgem.jeesite.modules.affair.entity.AffairDisciplinaryRegulationReceive;
import com.thinkgem.jeesite.modules.affair.service.AffairDisciplinaryRegulationReceiveService;

/**
 * 纪律规定接收单位表Controller
 * @author eav.liu
 * @version 2020-04-09
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairDisciplinaryRegulationReceive")
public class AffairDisciplinaryRegulationReceiveController extends BaseController {

	@Autowired
	private AffairDisciplinaryRegulationReceiveService affairDisciplinaryRegulationReceiveService;
	
	@ModelAttribute
	public AffairDisciplinaryRegulationReceive get(@RequestParam(required=false) String id) {
		AffairDisciplinaryRegulationReceive entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairDisciplinaryRegulationReceiveService.get(id);
		}
		if (entity == null){
			entity = new AffairDisciplinaryRegulationReceive();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairDisciplinaryRegulationReceive:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairDisciplinaryRegulationReceive affairDisciplinaryRegulationReceive, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairDisciplinaryRegulationReceive> page = affairDisciplinaryRegulationReceiveService.findPage(new Page<AffairDisciplinaryRegulationReceive>(request, response), affairDisciplinaryRegulationReceive); 
		model.addAttribute("page", page);
		return "modules/affair/affairDisciplinaryRegulationReceiveList";
	}

	@RequiresPermissions("affair:affairDisciplinaryRegulationReceive:view")
	@RequestMapping(value = "form")
	public String form(AffairDisciplinaryRegulationReceive affairDisciplinaryRegulationReceive, Model model) {
		model.addAttribute("affairDisciplinaryRegulationReceive", affairDisciplinaryRegulationReceive);
		return "modules/affair/affairDisciplinaryRegulationReceiveForm";
	}

	@RequiresPermissions("affair:affairDisciplinaryRegulationReceive:edit")
	@RequestMapping(value = "save")
	public String save(AffairDisciplinaryRegulationReceive affairDisciplinaryRegulationReceive, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, affairDisciplinaryRegulationReceive)){
			return form(affairDisciplinaryRegulationReceive, model);
		}
		affairDisciplinaryRegulationReceiveService.save(affairDisciplinaryRegulationReceive);
		addMessage(redirectAttributes, "保存纪律规定接收单位成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairDisciplinaryRegulationReceive/?repage";
	}
	
	@RequiresPermissions("affair:affairDisciplinaryRegulationReceive:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairDisciplinaryRegulationReceive affairDisciplinaryRegulationReceive, RedirectAttributes redirectAttributes) {
		affairDisciplinaryRegulationReceiveService.delete(affairDisciplinaryRegulationReceive);
		addMessage(redirectAttributes, "删除纪律规定接收单位成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairDisciplinaryRegulationReceive/?repage";
	}

}