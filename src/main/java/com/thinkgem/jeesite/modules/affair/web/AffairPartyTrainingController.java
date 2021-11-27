/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.modules.affair.entity.AffairGeneralSituation;
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
import com.thinkgem.jeesite.modules.affair.entity.AffairPartyTraining;
import com.thinkgem.jeesite.modules.affair.service.AffairPartyTrainingService;
import com.thinkgem.jeesite.modules.affair.service.AffairGeneralSituationService;

import java.util.List;
import java.util.Map;

/**
 * 功能未完成 未用，使用AffairPartyTrainController
 *
 * 党内培训Controller
 * @author freeman
 * @version 2020-06-02
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairPartyTraining")
public class AffairPartyTrainingController extends BaseController {
	@Autowired
	private AffairPartyTrainingService affairPartyTrainingService;
	@Autowired
	private AffairGeneralSituationService affairGeneralSituationService;
	
	@ModelAttribute
	public AffairPartyTraining get(@RequestParam(required=false) String id) {
		AffairPartyTraining entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairPartyTrainingService.get(id);
		}
		if (entity == null){
			entity = new AffairPartyTraining();
		}
		return entity;
	}

//	@RequiresPermissions("affair:affairPartyTraining:view")
//	@RequestMapping(value = {""})
//	public String index(AffairPartyTraining affairPartyTraining) {
//		return "modules/affair/affairPartyTrainingIndex";
//	}
	
	@RequiresPermissions("affair:affairPartyTraining:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairPartyTraining affairPartyTraining, HttpServletRequest request, HttpServletResponse response, Model model) {
		//model.addAttribute("treeId", affairPartyTraining.getTreeId());
		Page<AffairPartyTraining> page = affairPartyTrainingService.findPage(new Page<AffairPartyTraining>(request, response), affairPartyTraining);
		model.addAttribute("page", page);
		return "modules/affair/affairPartyTrainingList";
	}

	@RequiresPermissions("affair:affairPartyTraining:view")
	@RequestMapping(value = "form")
	public String form(AffairPartyTraining affairPartyTraining, Model model) {
		model.addAttribute("affairPartyTraining", affairPartyTraining);
		return "modules/affair/affairPartyTrainingForm";
	}

	@RequiresPermissions("affair:affairPartyTraining:edit")
	@RequestMapping(value = "save")
	public String save(AffairPartyTraining affairPartyTraining, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, affairPartyTraining)){
			return form(affairPartyTraining, model);
		}
		affairPartyTrainingService.save(affairPartyTraining);
		addMessage(redirectAttributes, "保存党内培训成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairPartyTraining/?repage";
	}
	
	@RequiresPermissions("affair:affairPartyTraining:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairPartyTraining affairPartyTraining, RedirectAttributes redirectAttributes) {
		affairPartyTrainingService.delete(affairPartyTraining);
		addMessage(redirectAttributes, "删除党内培训成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairPartyTraining/?repage";
	}

}