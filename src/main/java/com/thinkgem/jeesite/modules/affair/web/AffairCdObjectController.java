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
import com.thinkgem.jeesite.modules.affair.entity.AffairCdObject;
import com.thinkgem.jeesite.modules.affair.service.AffairCdObjectService;

/**
 * 查(借)阅审批Controller
 * @author mason.xv
 * @version 2019-11-30
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairCdObject")
public class AffairCdObjectController extends BaseController {

	@Autowired
	private AffairCdObjectService affairCdObjectService;
	
	@ModelAttribute
	public AffairCdObject get(@RequestParam(required=false) String id) {
		AffairCdObject entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairCdObjectService.get(id);
		}
		if (entity == null){
			entity = new AffairCdObject();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairCdObject:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairCdObject affairCdObject, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairCdObject> page = affairCdObjectService.findPage(new Page<AffairCdObject>(request, response), affairCdObject); 
		model.addAttribute("page", page);
		return "modules/affair/affairCdObjectList";
	}

	@RequiresPermissions("affair:affairCdObject:view")
	@RequestMapping(value = "form")
	public String form(AffairCdObject affairCdObject, Model model) {
		model.addAttribute("affairCdObject", affairCdObject);
		return "modules/affair/affairCdObjectForm";
	}

	@RequiresPermissions("affair:affairCdObject:edit")
	@RequestMapping(value = "save")
	public String save(AffairCdObject affairCdObject, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, affairCdObject)){
			return form(affairCdObject, model);
		}
		affairCdObjectService.save(affairCdObject);
		addMessage(redirectAttributes, "保存查(借)阅审批成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairCdObject/?repage";
	}
	
	@RequiresPermissions("affair:affairCdObject:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairCdObject affairCdObject, RedirectAttributes redirectAttributes) {
		affairCdObjectService.delete(affairCdObject);
		addMessage(redirectAttributes, "删除查(借)阅审批成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairCdObject/?repage";
	}

}