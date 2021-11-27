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
import com.thinkgem.jeesite.modules.tw.entity.UserPView;
import com.thinkgem.jeesite.modules.tw.service.UserPViewService;

/**
 * 自动考评-人员组织情况Controller
 * @author alan.wu
 * @version 2020-11-19
 */
@Controller
@RequestMapping(value = "${adminPath}/tw/userPView")
public class UserPViewController extends BaseController {

	@Autowired
	private UserPViewService userPViewService;
	
	@ModelAttribute
	public UserPView get(@RequestParam(required=false) String id) {
		UserPView entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = userPViewService.get(id);
		}
		if (entity == null){
			entity = new UserPView();
		}
		return entity;
	}
	
	@RequiresPermissions("tw:userPView:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserPView userPView, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UserPView> page = userPViewService.findPage(new Page<UserPView>(request, response), userPView); 
		model.addAttribute("page", page);
		return "modules/tw/userPViewList";
	}

	@RequiresPermissions("tw:userPView:view")
	@RequestMapping(value = "form")
	public String form(UserPView userPView, Model model) {
		model.addAttribute("userPView", userPView);
		return "modules/tw/userPViewForm";
	}

	@RequiresPermissions("tw:userPView:edit")
	@RequestMapping(value = "save")
	public String save(UserPView userPView, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, userPView)){
			return form(userPView, model);
		}
		userPViewService.save(userPView);
		addMessage(redirectAttributes, "保存自动考评-人员组织情况成功");
		return "redirect:"+Global.getAdminPath()+"/tw/userPView/?repage";
	}
	
	@RequiresPermissions("tw:userPView:edit")
	@RequestMapping(value = "delete")
	public String delete(UserPView userPView, RedirectAttributes redirectAttributes) {
		userPViewService.delete(userPView);
		addMessage(redirectAttributes, "删除自动考评-人员组织情况成功");
		return "redirect:"+Global.getAdminPath()+"/tw/userPView/?repage";
	}

}