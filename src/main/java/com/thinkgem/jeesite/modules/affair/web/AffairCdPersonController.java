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
import com.thinkgem.jeesite.modules.affair.entity.AffairCdPerson;
import com.thinkgem.jeesite.modules.affair.service.AffairCdPersonService;

/**
 * 查(借)阅审批Controller
 * @author mason.xv
 * @version 2019-11-30
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairCdPerson")
public class AffairCdPersonController extends BaseController {

	@Autowired
	private AffairCdPersonService affairCdPersonService;
	
	@ModelAttribute
	public AffairCdPerson get(@RequestParam(required=false) String id) {
		AffairCdPerson entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairCdPersonService.get(id);
		}
		if (entity == null){
			entity = new AffairCdPerson();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairCdPerson:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairCdPerson affairCdPerson, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairCdPerson> page = affairCdPersonService.findPage(new Page<AffairCdPerson>(request, response), affairCdPerson); 
		model.addAttribute("page", page);
		return "modules/affair/affairCdPersonList";
	}

	@RequiresPermissions("affair:affairCdPerson:view")
	@RequestMapping(value = "form")
	public String form(AffairCdPerson affairCdPerson, Model model) {
		model.addAttribute("affairCdPerson", affairCdPerson);
		return "modules/affair/affairCdPersonForm";
	}

	@RequiresPermissions("affair:affairCdPerson:edit")
	@RequestMapping(value = "save")
	public String save(AffairCdPerson affairCdPerson, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, affairCdPerson)){
			return form(affairCdPerson, model);
		}
		affairCdPersonService.save(affairCdPerson);
		addMessage(redirectAttributes, "保存查(借)阅审批成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairCdPerson/?repage";
	}
	
	@RequiresPermissions("affair:affairCdPerson:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairCdPerson affairCdPerson, RedirectAttributes redirectAttributes) {
		affairCdPersonService.delete(affairCdPerson);
		addMessage(redirectAttributes, "删除查(借)阅审批成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairCdPerson/?repage";
	}

}