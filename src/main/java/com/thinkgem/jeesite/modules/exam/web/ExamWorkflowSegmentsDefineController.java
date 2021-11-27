/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.exam.entity.ExamWorkflowSegmentsDefine;
import com.thinkgem.jeesite.modules.exam.service.ExamWorkflowSegmentsDefineService;
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
 * 标准考评流程环节定义Controller
 * @author eav.liu
 * @version 2019-12-09
 */
@Controller
@RequestMapping(value = "${adminPath}/exam/examWorkflowSegmentsDefine")
public class ExamWorkflowSegmentsDefineController extends BaseController {

	@Autowired
	private ExamWorkflowSegmentsDefineService examWorkflowSegmentsDefineService;
	
	@ModelAttribute
	public ExamWorkflowSegmentsDefine get(@RequestParam(required=false) String id) {
		ExamWorkflowSegmentsDefine entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = examWorkflowSegmentsDefineService.get(id);
		}
		if (entity == null){
			entity = new ExamWorkflowSegmentsDefine();
		}
		return entity;
	}

	@RequiresPermissions("exam:examWorkflowSegmentsDefine:view")
	@RequestMapping(value = {"list", ""})
	public String list(ExamWorkflowSegmentsDefine examWorkflowSegmentsDefine, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ExamWorkflowSegmentsDefine> page = examWorkflowSegmentsDefineService.findPage(new Page<ExamWorkflowSegmentsDefine>(request, response), examWorkflowSegmentsDefine);
		model.addAttribute("page", page);
		return "modules/exam/examWorkflowSegmentsDefineList";
	}

	@RequiresPermissions("exam:examWorkflowSegmentsDefine:view")
	@RequestMapping(value = "form")
	public String form(ExamWorkflowSegmentsDefine examWorkflowSegmentsDefine, Model model) {
		model.addAttribute("examWorkflowSegmentsDefine", examWorkflowSegmentsDefine);
		return "modules/exam/examWorkflowSegmentsDefineForm";
	}

	@RequiresPermissions("exam:examWorkflowSegmentsDefine:edit")
	@RequestMapping(value = "save")
	public String save(ExamWorkflowSegmentsDefine examWorkflowSegmentsDefine, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, examWorkflowSegmentsDefine)){
			return form(examWorkflowSegmentsDefine, model);
		}
		examWorkflowSegmentsDefineService.save(examWorkflowSegmentsDefine);
		addMessage(redirectAttributes, "保存标准考评流程环节定义成功");
		return "redirect:"+Global.getAdminPath()+"/exam/examWorkflowSegmentsDefine/?repage";
	}
	
	@RequiresPermissions("exam:examWorkflowSegmentsDefine:edit")
	@RequestMapping(value = "delete")
	public String delete(ExamWorkflowSegmentsDefine examWorkflowSegmentsDefine, RedirectAttributes redirectAttributes) {
		examWorkflowSegmentsDefineService.delete(examWorkflowSegmentsDefine);
		addMessage(redirectAttributes, "删除标准考评流程环节定义成功");
		return "redirect:"+Global.getAdminPath()+"/exam/examWorkflowSegmentsDefine/?repage";
	}

	/**
	 * 根据流程类型type返回环节集合
	 * @param type
	 * @return
	 */
	//权限改为"exam:examWorkflowDefine:view"
	@ResponseBody
	@RequiresPermissions("exam:examWorkflowDefine:view")
	@RequestMapping(value = {"findByType"})
	public Result findByType(String type) {
		List<ExamWorkflowSegmentsDefine> list = examWorkflowSegmentsDefineService.findByType(type);
		Result result = new Result();
		result.setSuccess(true);
		result.setResult(list);
		return result;
	}
}