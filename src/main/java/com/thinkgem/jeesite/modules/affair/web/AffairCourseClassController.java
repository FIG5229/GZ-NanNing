/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.modules.affair.dao.AffairCourseClassDao;
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
import com.thinkgem.jeesite.modules.affair.entity.AffairCourseClass;
import com.thinkgem.jeesite.modules.affair.service.AffairCourseClassService;

import java.util.List;

/**
 * 课程研发Controller
 * @author alan.wu
 * @version 2020-08-06
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairCourseClass")
public class AffairCourseClassController extends BaseController {

	@Autowired
	private AffairCourseClassService affairCourseClassService;

	@Autowired
	private AffairCourseClassDao affairCourseClassDao;

	@Autowired
	private IdGen idGen;
	
	@ModelAttribute
	public AffairCourseClass get(@RequestParam(required=false) String id) {
		AffairCourseClass entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairCourseClassService.get(id);
		}
		if (entity == null){
			entity = new AffairCourseClass();
		}
		return entity;
	}


	@RequiresPermissions("affair:affairCourseResource:view")
	@RequestMapping(value = {"deputyList", ""})
	public String deputyList(AffairCourseClass affairCourseClass, HttpServletRequest request, HttpServletResponse response, Model model,HttpSession session) {

		String id = affairCourseClass.getClassId();
		List<AffairCourseClass> affairCourseClassList = affairCourseClassDao.selectByClassDeputyId(id);
		model.addAttribute("affairCourseClassList", affairCourseClassList);
		return "modules/affair/affairCourseClassDeputyList";
	}

	@RequiresPermissions("affair:affairCourseResource:view")
	@RequestMapping(value = "form")
	public String form(AffairCourseClass affairCourseClass, Model model) {
		model.addAttribute("affairCourseClass", affairCourseClass);
		return "modules/affair/affairCourseClassForm";
	}
	@RequiresPermissions("affair:affairCourseResource:view")
	@RequestMapping(value = "deputyForm")
	public String deputyForm(AffairCourseClass affairCourseClass, Model model) {
		String id = affairCourseClass.getClassId();

		model.addAttribute("affairCourseClass", affairCourseClass);
		return "modules/affair/affairCourseClassDeputyForm";
	}

	@RequiresPermissions("affair:affairCourseResource:edit")
	@RequestMapping(value = "save")
	public String save(AffairCourseClass affairCourseClass, Model model, RedirectAttributes redirectAttributes ) {
		if (!beanValidator(model, affairCourseClass)){
			return form(affairCourseClass, model);
		}
		String id = idGen.getNextId();
		affairCourseClass.setId(id);
		affairCourseClass.setIsNewRecord(true);
		affairCourseClassService.save(affairCourseClass);
		addMessage(redirectAttributes, "保存课程研发成功");
		model.addAttribute("saveResult","success");

		return "modules/affair/affairCourseClassForm";
	}
	
	@RequiresPermissions("affair:affairCourseResource:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairCourseClass affairCourseClass, RedirectAttributes redirectAttributes) {
		affairCourseClassService.delete(affairCourseClass);
		addMessage(redirectAttributes, "删除课程研发成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairCourseClass/?repage";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairCourseResource:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairCourseClassDao.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}
}