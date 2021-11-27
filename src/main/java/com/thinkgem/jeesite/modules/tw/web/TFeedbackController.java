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
import com.thinkgem.jeesite.modules.tw.entity.TFeedback;
import com.thinkgem.jeesite.modules.tw.service.TFeedbackService;

/**
 * 微信警情-自动考评Controller
 * @author alan.wu
 * @version 2020-10-16
 */
@Controller
@RequestMapping(value = "${adminPath}/tw/tFeedback")
public class TFeedbackController extends BaseController {

	@Autowired
	private TFeedbackService tFeedbackService;
	
	@ModelAttribute
	public TFeedback get(@RequestParam(required=false) String id) {
		TFeedback entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tFeedbackService.get(id);
		}
		if (entity == null){
			entity = new TFeedback();
		}
		return entity;
	}
	
	@RequiresPermissions("tw:tFeedback:view")
	@RequestMapping(value = {"list", ""})
	public String list(TFeedback tFeedback, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TFeedback> page = tFeedbackService.findPage(new Page<TFeedback>(request, response), tFeedback); 
		model.addAttribute("page", page);
		return "modules/tw/tFeedbackList";
	}

	@RequiresPermissions("tw:tFeedback:view")
	@RequestMapping(value = "form")
	public String form(TFeedback tFeedback, Model model) {
		model.addAttribute("tFeedback", tFeedback);
		return "modules/tw/tFeedbackForm";
	}

	@RequiresPermissions("tw:tFeedback:edit")
	@RequestMapping(value = "save")
	public String save(TFeedback tFeedback, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tFeedback)){
			return form(tFeedback, model);
		}
		tFeedbackService.save(tFeedback);
		addMessage(redirectAttributes, "保存微信警情-自动考评成功");
		return "redirect:"+Global.getAdminPath()+"/tw/tFeedback/?repage";
	}
	
	@RequiresPermissions("tw:tFeedback:edit")
	@RequestMapping(value = "delete")
	public String delete(TFeedback tFeedback, RedirectAttributes redirectAttributes) {
		tFeedbackService.delete(tFeedback);
		addMessage(redirectAttributes, "删除微信警情-自动考评成功");
		return "redirect:"+Global.getAdminPath()+"/tw/tFeedback/?repage";
	}

}