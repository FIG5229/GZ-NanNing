/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.UploadController;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
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
import com.thinkgem.jeesite.modules.affair.entity.AffairSpiritualCivilization;
import com.thinkgem.jeesite.modules.affair.service.AffairSpiritualCivilizationService;

import java.util.List;
import java.util.Map;

/**
 * 精神文明单位管理Controller
 * @author Alan.wu
 * @version 2020-06-12
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairSpiritualCivilization")
public class AffairSpiritualCivilizationController extends BaseController {

	@Autowired
	private AffairSpiritualCivilizationService affairSpiritualCivilizationService;

	private UploadController uploadController;
	
	@ModelAttribute
	public AffairSpiritualCivilization get(@RequestParam(required=false) String id) {
		AffairSpiritualCivilization entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairSpiritualCivilizationService.get(id);
		}
		if (entity == null){
			entity = new AffairSpiritualCivilization();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairSpiritualCivilization:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairSpiritualCivilization affairSpiritualCivilization, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairSpiritualCivilization> page = affairSpiritualCivilizationService.findPage(new Page<AffairSpiritualCivilization>(request, response), affairSpiritualCivilization); 
		model.addAttribute("page", page);
		return "modules/affair/affairSpiritualCivilizationList";
	}

	@RequiresPermissions("affair:affairSpiritualCivilization:view")
	@RequestMapping(value = "form")
	public String form(AffairSpiritualCivilization affairSpiritualCivilization, Model model) {
		model.addAttribute("affairSpiritualCivilization", affairSpiritualCivilization);
		return "modules/affair/affairSpiritualCivilizationForm";
	}

	@RequiresPermissions("affair:affairSpiritualCivilization:edit")
	@RequestMapping(value = "save")
	public String save(AffairSpiritualCivilization affairSpiritualCivilization, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, affairSpiritualCivilization)){
			return form(affairSpiritualCivilization, model);
		}
		affairSpiritualCivilizationService.save(affairSpiritualCivilization);
		addMessage(redirectAttributes, "保存精神文明单位管理成功");
		model.addAttribute("saveResult","sucess");
		return "modules/affair/affairSpiritualCivilizationForm";
	}
	
	@RequiresPermissions("affair:affairSpiritualCivilization:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairSpiritualCivilization affairSpiritualCivilization, RedirectAttributes redirectAttributes) {
		affairSpiritualCivilizationService.delete(affairSpiritualCivilization);
		addMessage(redirectAttributes, "删除精神文明单位管理成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairSpiritualCivilization/?repage";
	}

	/**
	 * 详情
	 *
	 * @param affairSpiritualCivilization
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairSpiritualCivilization:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairSpiritualCivilization affairSpiritualCivilization, Model model) {
		model.addAttribute("affairSpiritualCivilization", affairSpiritualCivilization);
		if (affairSpiritualCivilization.getAdjunct() != null && affairSpiritualCivilization.getAdjunct().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairSpiritualCivilization.getAdjunct());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairSpiritualCivilizationFormDetail";
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairSpiritualCivilization:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairSpiritualCivilizationService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}



}