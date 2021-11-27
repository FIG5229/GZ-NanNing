/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.thinkgem.jeesite.modules.exam.entity.ExamObjectType;
import com.thinkgem.jeesite.modules.exam.service.ExamObjectTypeService;

import java.util.List;

/**
 * 被考评对象类别关系表Controller
 * @author kevin.jia
 * @version 2021-02-22
 * 被考评对象类别关系-用于维护各考评类别被考评对象
 */
@Controller
@RequestMapping(value = "${adminPath}/exam/examObjectType")
public class ExamObjectTypeController extends BaseController {

	@Autowired
	private ExamObjectTypeService examObjectTypeService;
	
	@ModelAttribute
	public ExamObjectType get(@RequestParam(required=false) String id) {
		ExamObjectType entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = examObjectTypeService.get(id);
		}
		if (entity == null){
			entity = new ExamObjectType();
		}
		return entity;
	}
	
	@RequiresPermissions("exam:examObjectType:view")
	@RequestMapping(value = {"list", ""})
	public String list(ExamObjectType examObjectType, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ExamObjectType> page = examObjectTypeService.findPage(new Page<ExamObjectType>(request, response), examObjectType); 
		model.addAttribute("page", page);
		return "modules/exam/examObjectTypeList";
	}

	@RequiresPermissions("exam:examObjectType:view")
	@RequestMapping(value = "form")
	public String form(ExamObjectType examObjectType, Model model) {
		model.addAttribute("examObjectType", examObjectType);
		return "modules/exam/examObjectTypeForm";
	}

	@RequiresPermissions("exam:examObjectType:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(ExamObjectType examObjectType, Model model) {
		model.addAttribute("examObjectType", examObjectType);
		return "modules/exam/examObjectTypeFormDetail";
	}

	@RequiresPermissions("exam:examObjectType:edit")
	@RequestMapping(value = "save")
	public String save(ExamObjectType examObjectType, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, examObjectType)){
			return form(examObjectType, model);
		}
		examObjectTypeService.save(examObjectType);
		addMessage(redirectAttributes, "保存被考评对象类别关系表成功");
		model.addAttribute("examObjectType", examObjectType);
		model.addAttribute("saveResult","success");
		return "modules/exam/examObjectTypeForm";
	}
	
	@RequiresPermissions("exam:examObjectType:edit")
	@RequestMapping(value = "delete")
	public String delete(ExamObjectType examObjectType, RedirectAttributes redirectAttributes) {
		examObjectTypeService.delete(examObjectType);
		addMessage(redirectAttributes, "删除被考评对象类别关系表成功");
		return "redirect:"+Global.getAdminPath()+"/exam/examObjectType/?repage";
	}

	@ResponseBody
	@RequiresPermissions("exam:examObjectType:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			examObjectTypeService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

}