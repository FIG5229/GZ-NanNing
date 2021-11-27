/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.warn.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.warn.entity.Warning;
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
import com.thinkgem.jeesite.modules.warn.entity.WarnHistory;
import com.thinkgem.jeesite.modules.warn.service.WarnHistoryService;

/**
 * 预警历史记录Controller
 * @author kevin.jia
 * @version 2020-12-28
 */
@Controller
@RequestMapping(value = "${adminPath}/warn/warnHistory")
public class WarnHistoryController extends BaseController {

	@Autowired
	private WarnHistoryService warnHistoryService;
	
	@ModelAttribute
	public WarnHistory get(@RequestParam(required=false) String id) {
		WarnHistory entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = warnHistoryService.get(id);
		}
		if (entity == null){
			entity = new WarnHistory();
		}
		return entity;
	}
	
//	@RequiresPermissions("warn:warnHistory:view")
	@RequestMapping(value = {"list", ""})
	public String list(WarnHistory warnHistory, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WarnHistory> page = warnHistoryService.findPage(new Page<WarnHistory>(request, response), warnHistory); 
		model.addAttribute("page", page);
		return "modules/warn/warnHistoryList";
	}

	@RequiresPermissions("warn:warnHistory:view")
	@RequestMapping(value = "form")
	public String form(WarnHistory warnHistory, Model model) {
		model.addAttribute("warnHistory", warnHistory);
		return "modules/warn/warnHistoryForm";
	}

//	@RequiresPermissions("warn:warnHistory:edit")
	@RequestMapping(value = "save")
	public String save(WarnHistory warnHistory, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, warnHistory)){
			return form(warnHistory, model);
		}
		warnHistoryService.save(warnHistory);
		addMessage(redirectAttributes, "保存预警历史记录成功");
		return "redirect:"+Global.getAdminPath()+"/warn/warnHistory/?repage";
	}

	@RequestMapping(value = "delete")
	public String delete(WarnHistory warnHistory, RedirectAttributes redirectAttributes) {
		warnHistoryService.delete(warnHistory);
		addMessage(redirectAttributes, "删除预警历史记录成功");
		return "redirect:"+Global.getAdminPath()+"/warn/warnHistory/?repage";
	}

	@RequestMapping(value = "formDetail")
	public String formDetail(WarnHistory warnHistory, Model model) {
		model.addAttribute("warnHistory", warnHistory);
		return "modules/warn/warningHistoryFormDetail";
	}

}