/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.web.UploadController;
import com.thinkgem.jeesite.modules.affair.entity.AffairCultureActivity;
import com.thinkgem.jeesite.modules.affair.service.AffairCultureActivityService;
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
import java.util.Map;

/**
 * 警营文化活动Controller
 * @author cecil.li
 * @version 2019-11-04
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairCultureActivity")
public class AffairCultureActivityController extends BaseController {

	@Autowired
	private AffairCultureActivityService affairCultureActivityService;

	@Autowired
	private UploadController uploadController;
	
	@ModelAttribute
	public AffairCultureActivity get(@RequestParam(required=false) String id) {
		AffairCultureActivity entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairCultureActivityService.get(id);
		}
		if (entity == null){
			entity = new AffairCultureActivity();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairCultureActivity:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairCultureActivity affairCultureActivity, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairCultureActivity> page = affairCultureActivityService.findPage(new Page<AffairCultureActivity>(request, response), affairCultureActivity); 
		model.addAttribute("page", page);
		model.addAttribute("affairCultureActivity", affairCultureActivity);
		return "modules/affair/affairCultureActivityList";
	}

	@RequiresPermissions("affair:affairCultureActivity:view")
	@RequestMapping(value = "form")
	public String form(AffairCultureActivity affairCultureActivity, Model model) {
		model.addAttribute("affairCultureActivity", affairCultureActivity);
		return "modules/affair/affairCultureActivityForm";
	}

	@RequiresPermissions("affair:affairCultureActivity:edit")
	@RequestMapping(value = "save")
	public String save(AffairCultureActivity affairCultureActivity, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairCultureActivity)){
			return form(affairCultureActivity, model);
		}
		affairCultureActivityService.save(affairCultureActivity);
		addMessage(redirectAttributes, "保存警营文化活动成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairCultureActivityForm";
	}
	
	@RequiresPermissions("affair:affairCultureActivity:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairCultureActivity affairCultureActivity, RedirectAttributes redirectAttributes) {
		affairCultureActivityService.delete(affairCultureActivity);
		addMessage(redirectAttributes, "删除警营文化活动成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairCultureActivity/?repage";
	}

	/**
	 * 详情
	 * @param affairCultureActivity
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairCultureActivity:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairCultureActivity affairCultureActivity, Model model) {
		model.addAttribute("affairCultureActivity", affairCultureActivity);
		if(affairCultureActivity.getMaterial() != null && affairCultureActivity.getMaterial().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairCultureActivity.getMaterial());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairCultureActivityFormDetail";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairCultureActivity:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairCultureActivityService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}
}