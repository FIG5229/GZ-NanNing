/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.tw.web;

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
import com.thinkgem.jeesite.modules.tw.entity.RenKz016;
import com.thinkgem.jeesite.modules.tw.service.RenKz016Service;

/**
 * 自动考评-精神病患者信息拓展Controller
 * @author alan.wu
 * @version 2020-11-20
 */
@Controller
@RequestMapping(value = "${adminPath}/tw/renKz016")
public class RenKz016Controller extends BaseController {

	@Autowired
	private RenKz016Service renKz016Service;
	
	@ModelAttribute
	public RenKz016 get(@RequestParam(required=false) String id) {
		RenKz016 entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = renKz016Service.get(id);
		}
		if (entity == null){
			entity = new RenKz016();
		}
		return entity;
	}
	
	@RequiresPermissions("tw:renKz016:view")
	@RequestMapping(value = {"list", ""})
	public String list(RenKz016 renKz016, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<RenKz016> page = renKz016Service.findPage(new Page<RenKz016>(request, response), renKz016); 
		model.addAttribute("page", page);
		return "modules/tw/renKz016List";
	}

	@RequiresPermissions("tw:renKz016:view")
	@RequestMapping(value = "form")
	public String form(RenKz016 renKz016, Model model) {
		model.addAttribute("renKz016", renKz016);
		return "modules/tw/renKz016Form";
	}

	@RequiresPermissions("tw:renKz016:edit")
	@RequestMapping(value = "save")
	public String save(RenKz016 renKz016, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, renKz016)){
			return form(renKz016, model);
		}
		renKz016Service.save(renKz016);
		addMessage(redirectAttributes, "保存自动考评-精神病患者信息拓展成功");
		return "redirect:"+Global.getAdminPath()+"/tw/renKz016/?repage";
	}
	
	@RequiresPermissions("tw:renKz016:edit")
	@RequestMapping(value = "delete")
	public String delete(RenKz016 renKz016, RedirectAttributes redirectAttributes) {
		renKz016Service.delete(renKz016);
		addMessage(redirectAttributes, "删除自动考评-精神病患者信息拓展成功");
		return "redirect:"+Global.getAdminPath()+"/tw/renKz016/?repage";
	}

}