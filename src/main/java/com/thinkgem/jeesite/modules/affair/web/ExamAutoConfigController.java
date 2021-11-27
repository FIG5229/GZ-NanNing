/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.exam.entity.ExamStandardBaseInfo;
import com.thinkgem.jeesite.modules.exam.service.ExamStandardBaseInfoService;
import com.thinkgem.jeesite.modules.exam.service.ExamWorkflowDefineService;
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
import com.thinkgem.jeesite.modules.affair.entity.ExamAutoConfig;
import com.thinkgem.jeesite.modules.affair.service.ExamAutoConfigService;

/**
 * 自动考评配置Controller
 * @author danil.liu
 * @version 2020-12-15
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/examAutoConfig")
public class ExamAutoConfigController extends BaseController {

	@Autowired
	private ExamAutoConfigService examAutoConfigService;

	@Autowired
	private ExamWorkflowDefineService examWorkflowDefineService;

	@Autowired
	private ExamStandardBaseInfoService examStandardBaseInfoService;

	@ModelAttribute
	public ExamAutoConfig get(@RequestParam(required=false) String id) {
		ExamAutoConfig entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = examAutoConfigService.get(id);
		}
		if (entity == null){
			entity = new ExamAutoConfig();
		}
		return entity;
	}
//	http://localhost:8080/politics/a/affair/examAutoConfig
//	@RequiresPermissions("affair:examAutoConfig:view")
	@RequestMapping(value = {"list", ""})
	public String list(ExamAutoConfig examAutoConfig, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ExamAutoConfig> page = examAutoConfigService.findPage(new Page<ExamAutoConfig>(request, response), examAutoConfig); 
		model.addAttribute("page", page);
		return "modules/affair/examAutoConfigList";
	}

//	@RequiresPermissions("affair:examAutoConfig:view")
	@RequestMapping(value = "form")
	public String form(ExamAutoConfig examAutoConfig, Model model) {
		model.addAttribute("examAutoConfig", examAutoConfig);
		model.addAttribute("templateList", examWorkflowDefineService.templateFile());
		return "modules/affair/examAutoConfigForm";
	}

//	@RequiresPermissions("affair:examAutoConfig:edit")
	@RequestMapping(value = "save")
	public String save(ExamAutoConfig examAutoConfig, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, examAutoConfig)){
			return form(examAutoConfig, model);
		}
		ExamStandardBaseInfo standardBaseInfo = examStandardBaseInfoService.get(examAutoConfig.getStandardId());
		examAutoConfig.setStandard(standardBaseInfo.getName());
		examAutoConfigService.save(examAutoConfig);
		addMessage(redirectAttributes, "保存自动考评配置成功");
		return "redirect:"+Global.getAdminPath()+"/affair/examAutoConfig/?repage";
	}
	
//	@RequiresPermissions("affair:examAutoConfig:edit")
	@RequestMapping(value = "delete")
	public String delete(ExamAutoConfig examAutoConfig, RedirectAttributes redirectAttributes) {
		examAutoConfigService.delete(examAutoConfig);
		addMessage(redirectAttributes, "删除自动考评配置成功");
		return "redirect:"+Global.getAdminPath()+"/affair/examAutoConfig/?repage";
	}

}