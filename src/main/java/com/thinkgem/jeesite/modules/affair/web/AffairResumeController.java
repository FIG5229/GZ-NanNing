/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.affair.entity.AffairResume;
import com.thinkgem.jeesite.modules.affair.service.AffairResumeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 履历信息管理Controller
 * @author mason.xv
 * @version 2019-11-06
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairResume")
public class AffairResumeController extends BaseController {

	@Autowired
	private AffairResumeService affairResumeService;
	
	@ModelAttribute
	public AffairResume get(@RequestParam(required=false) String id) {
		AffairResume entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairResumeService.get(id);
		}
		if (entity == null){
			entity = new AffairResume();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairResume:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairResume affairResume, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairResume> page = affairResumeService.findPage(new Page<AffairResume>(request, response), affairResume); 
		model.addAttribute("page", page);
		return "modules/affair/affairResumeList";
	}

	@RequiresPermissions("affair:affairResume:view")
	@RequestMapping(value = "form")
	public String form(AffairResume affairResume, Model model) {
		model.addAttribute("affairResume", affairResume);
		return "modules/affair/affairResumeForm";
	}

	@RequiresPermissions("affair:affairResume:edit")
	@RequestMapping(value = "save")
	public String save(AffairResume affairResume, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairResume)){
			return form(affairResume, model);
		}
		affairResumeService.save(affairResume);
		addMessage(redirectAttributes, "保存履历信息管理成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairResumeForm";
	}
	
	@RequiresPermissions("affair:affairResume:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairResume affairResume, RedirectAttributes redirectAttributes) {
		affairResumeService.delete(affairResume);
		addMessage(redirectAttributes, "删除履历信息管理成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairResume/?repage";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairResume:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairResumeService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}
	@RequiresPermissions("affair:affairResume:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairResume affairResume, Model model) {
		model.addAttribute("affairResume", affairResume);
		return "modules/affair/affairResumeFormDetail";
	}
}