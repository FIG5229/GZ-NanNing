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
import com.thinkgem.jeesite.modules.affair.entity.AffairOneHelpOneMain;
import com.thinkgem.jeesite.modules.affair.service.AffairOneHelpOneMainService;

/**
 * 一帮一明细Controller
 * @author daniel.liu
 * @version 2020-06-23
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairOneHelpOneMain")
public class AffairOneHelpOneMainController extends BaseController {

	@Autowired
	private AffairOneHelpOneMainService affairOneHelpOneMainService;
	
	@ModelAttribute
	public AffairOneHelpOneMain get(@RequestParam(required=false) String id) {
		AffairOneHelpOneMain entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairOneHelpOneMainService.get(id);
		}
		if (entity == null){
			entity = new AffairOneHelpOneMain();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairOneHelpOne:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairOneHelpOneMain affairOneHelpOneMain, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairOneHelpOneMain> page = affairOneHelpOneMainService.findPage(new Page<AffairOneHelpOneMain>(request, response), affairOneHelpOneMain); 
		model.addAttribute("page", page);
		return "modules/affair/affairOneHelpOneMainList";
	}

	@RequiresPermissions("affair:affairOneHelpOne:view")
	@RequestMapping(value = "form")
	public String form(AffairOneHelpOneMain affairOneHelpOneMain, Model model) {
		model.addAttribute("affairOneHelpOneMain", affairOneHelpOneMain);
		return "modules/affair/affairOneHelpOneMainForm";
	}

	@RequiresPermissions("affair:affairOneHelpOne:edit")
	@RequestMapping(value = "save")
	public String save(AffairOneHelpOneMain affairOneHelpOneMain, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairOneHelpOneMain)){
			return form(affairOneHelpOneMain, model);
		}
		affairOneHelpOneMainService.save(affairOneHelpOneMain);
		addMessage(redirectAttributes, "保存一帮一明细成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairOneHelpOneMainForm";
//		return "redirect:"+Global.getAdminPath()+"/affair/affairOneHelpOne/form?repage";
	}
	
	@RequiresPermissions("affair:affairOneHelpOne:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairOneHelpOneMain affairOneHelpOneMain, RedirectAttributes redirectAttributes) {
		affairOneHelpOneMainService.delete(affairOneHelpOneMain);
		addMessage(redirectAttributes, "删除一帮一明细成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairOneHelpOne/formDetail?id="+affairOneHelpOneMain.getMainId();
	}

}