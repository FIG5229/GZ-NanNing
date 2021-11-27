/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.affair.entity.AffairCourseUnit;
import com.thinkgem.jeesite.modules.affair.service.AffairCourseUnitService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 课程资源Controller
 * @author alan.wu
 * @version 2020-07-31
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairCourseUnit")
public class AffairCourseUnitController extends BaseController {

	@Autowired
	private AffairCourseUnitService affairCourseUnitService;

	
	@ModelAttribute
	public AffairCourseUnit get(@RequestParam(required=false) String id) {
		AffairCourseUnit entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairCourseUnitService.get(id);
		}
		if (entity == null){
			entity = new AffairCourseUnit();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairCourseResource:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairCourseUnit affairCourseUnit, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairCourseUnit> page = affairCourseUnitService.findPage(new Page<AffairCourseUnit>(request, response), affairCourseUnit);
		model.addAttribute("page", page);
		return "modules/affair/affairCourseResourceGXForm";
	}

	@RequiresPermissions("affair:affairCourseResource:edit")
	@RequestMapping(value = "save")
	public String save(AffairCourseUnit affairCourseUnit, Model model, RedirectAttributes redirectAttributes,String type) {

		affairCourseUnitService.save(affairCourseUnit);
		addMessage(redirectAttributes, "保存课程资源成功");
		model.addAttribute("saveResult","success");

		return "modules/affair/affairCourseResourceForm";
	}


}