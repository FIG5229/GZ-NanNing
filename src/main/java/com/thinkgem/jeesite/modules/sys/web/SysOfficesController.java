/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.vo.Result;
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
import com.thinkgem.jeesite.modules.sys.entity.SysOffices;
import com.thinkgem.jeesite.modules.sys.service.SysOfficesService;

import java.util.List;

/**
 * 组织关系对应关系Controller
 * @author bradley.zhao
 * @version 2020-12-13
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysOffices")
public class SysOfficesController extends BaseController {

	@Autowired
	private SysOfficesService sysOfficesService;
	
	@ModelAttribute
	public SysOffices get(@RequestParam(required=false) String id) {
		SysOffices entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysOfficesService.get(id);
		}
		if (entity == null){
			entity = new SysOffices();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:sysOffices:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysOffices sysOffices, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysOffices> page = sysOfficesService.findPage(new Page<SysOffices>(request, response), sysOffices);
		model.addAttribute("page", page);
		return "modules/sys/sysOfficesList";
	}

	@RequiresPermissions("sys:sysOffices:view")
	@RequestMapping(value = "form")
	public String form(SysOffices sysOffices, Model model) {
		SysOffices sys = sysOfficesService.selectBeanById(sysOffices.getId());
		model.addAttribute("sysOffices", sys);
		return "modules/sys/sysOfficesForm";
	}


	@RequiresPermissions("sys:sysOffices:view")
	@RequestMapping(value = "newForm")
	public String newForm(SysOffices sysOffices, Model model) {
		model.addAttribute("sysOffices", sysOffices);
		return "modules/sys/sysOfficesSaveNewForm";
	}



	@RequiresPermissions("sys:sysOffices:edit")
	@RequestMapping(value = "save")
	public String save(HttpServletRequest request, SysOffices sysOffices, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysOffices)){
			return form(sysOffices, model);
		}
		sysOfficesService.save(sysOffices);
		addMessage(redirectAttributes, "保存组织关系对应关系成功");

		request.setAttribute("saveResult", "success");
		return "modules/sys/sysOfficesForm";
/*
		return "redirect:"+Global.getAdminPath()+"/sys/sysOffices/?repage";
*/
	}

	@RequiresPermissions("sys:sysOffices:edit")
	@RequestMapping(value = "saveNew")
	public String saveNew(HttpServletRequest request, SysOffices sysOffices, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysOffices)){
			return form(sysOffices, model);
		}
		sysOfficesService.saveNew(sysOffices);
		addMessage(redirectAttributes, "保存组织关系对应关系成功");

		request.setAttribute("saveResult", "success");
		return "modules/sys/sysOfficesForm";
/*
		return "redirect:"+Global.getAdminPath()+"/sys/sysOffices/?repage";
*/
	}


	@RequiresPermissions("sys:sysOffices:edit")
	@RequestMapping(value = "saveById")
	public String saveByCId(HttpServletRequest request, SysOffices sysOffices, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysOffices)){
			return form(sysOffices, model);
		}
		sysOfficesService.saveById(sysOffices);
		addMessage(redirectAttributes, "保存组织关系对应关系成功");

		request.setAttribute("saveResult", "success");
		return "modules/sys/sysOfficesForm";
/*
		return "redirect:"+Global.getAdminPath()+"/sys/sysOffices/?repage";
*/
	}

	@RequiresPermissions("sys:sysOffices:edit")
	@RequestMapping(value = "delete")
	public String delete(SysOffices sysOffices, RedirectAttributes redirectAttributes) {
		sysOfficesService.delete(sysOffices);
		addMessage(redirectAttributes, "删除组织关系对应关系成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysOffices/?repage";
	}

	@RequiresPermissions("sys:sysOffices:edit")
	@RequestMapping(value = "deleteById")
	public String deleteByCId(String id, RedirectAttributes redirectAttributes) {
		sysOfficesService.deleteById(id);
		addMessage(redirectAttributes, "删除组织关系对应关系成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysOffices/?repage";
	}

	@ResponseBody
	@RequiresPermissions("sys:sysOffices:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			sysOfficesService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}


}