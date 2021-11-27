/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.thinkgem.jeesite.modules.affair.entity.AffairCourseTeacher;
import com.thinkgem.jeesite.modules.affair.service.AffairCourseTeacherService;

import java.util.List;

/**
 * 辅导教官Controller
 * @author alan.wu
 * @version 2020-08-05
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairCourseTeacher")
public class AffairCourseTeacherController extends BaseController {

	@Autowired
	private AffairCourseTeacherService affairCourseTeacherService;

	@Autowired
	private IdGen idGen;

	@ModelAttribute
	public AffairCourseTeacher get(@RequestParam(required=false) String id) {
		AffairCourseTeacher entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairCourseTeacherService.get(id);
		}
		if (entity == null){
			entity = new AffairCourseTeacher();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairCourseResource:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairCourseTeacher affairCourseTeacher, HttpServletRequest request, HttpServletResponse response, Model model,HttpSession session) {



		String classId = affairCourseTeacher.getId();
		affairCourseTeacher.setClassId(classId);

		Object classId1 = session.getAttribute("classId");
		String ci1 = String.valueOf(classId1);

		if (StringUtils.isBlank(affairCourseTeacher.getClassId())){
			affairCourseTeacher.setClassId(ci1);
		}
		Page<AffairCourseTeacher> page = affairCourseTeacherService.findPage(new Page<AffairCourseTeacher>(request, response), affairCourseTeacher);

			List<AffairCourseTeacher> list = page.getList();
			for (int y = 0;y < list.size(); y++){
				AffairCourseTeacher aff = list.get(y);
				aff.setClassId(classId);
			}

		model.addAttribute("page", page);
		return "modules/affair/affairCourseResourceFDForm";
	}

	@RequiresPermissions("affair:affairCourseResource:view")
	@RequestMapping(value = "form")
	public String form(AffairCourseTeacher affairCourseTeacher, Model model) {
		String id = affairCourseTeacher.getId();
		affairCourseTeacher.setClassId(id);
		model.addAttribute("affairCourseTeacher", affairCourseTeacher);
		return "modules/affair/affairCourseTeacherForm";
	}

	@RequiresPermissions("affair:affairCourseResource:edit")
	@RequestMapping(value = "save")
	public String save(AffairCourseTeacher affairCourseTeacher, Model model, RedirectAttributes redirectAttributes, HttpSession session) {
		if (!beanValidator(model, affairCourseTeacher)){
			return form(affairCourseTeacher, model);
		}
		String classId = affairCourseTeacher.getClassId();
		session.setAttribute("classId",classId);
		affairCourseTeacher.setIsNewRecord(true);
		String id = idGen.getNextId();
		affairCourseTeacher.setId(id);
		affairCourseTeacherService.save(affairCourseTeacher);
		addMessage(redirectAttributes, "保存辅导教官成功");
		model.addAttribute("saveResult","success");

		return "modules/affair/affairCourseResourceFDForm";
	}
	
	@RequiresPermissions("affair:affairCourseResource:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairCourseTeacher affairCourseTeacher, RedirectAttributes redirectAttributes) {
		affairCourseTeacherService.delete(affairCourseTeacher);
		addMessage(redirectAttributes, "删除辅导教官成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairCourseTeacher/?repage";
	}


	@ResponseBody
	@RequiresPermissions("affair:affairCourseResource:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairCourseTeacherService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}
}