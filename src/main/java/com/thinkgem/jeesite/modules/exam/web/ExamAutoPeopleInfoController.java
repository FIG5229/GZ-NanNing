/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.exam.entity.ExamAutoPeopleInfo;
import com.thinkgem.jeesite.modules.exam.service.ExamAutoPeopleInfoService;
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
 * 人员身份Controller
 * @author cecil.li
 * @version 2020-08-26
 */
@Controller
@RequestMapping(value = "${adminPath}/exam/examAutoPeopleInfo")
public class ExamAutoPeopleInfoController extends BaseController {

	@Autowired
	private ExamAutoPeopleInfoService examAutoPeopleInfoService;
	
	@ModelAttribute
	public ExamAutoPeopleInfo get(@RequestParam(required=false) String id) {
		ExamAutoPeopleInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = examAutoPeopleInfoService.get(id);
		}
		if (entity == null){
			entity = new ExamAutoPeopleInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("exam:examAutoPeopleInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(ExamAutoPeopleInfo examAutoPeopleInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ExamAutoPeopleInfo> page = examAutoPeopleInfoService.findPage(new Page<ExamAutoPeopleInfo>(request, response), examAutoPeopleInfo); 
		model.addAttribute("page", page);
		return "modules/exam/examAutoPeopleInfoList";
	}

	@RequiresPermissions("exam:examAutoPeopleInfo:view")
	@RequestMapping(value = "form")
	public String form(ExamAutoPeopleInfo examAutoPeopleInfo, Model model) {
		model.addAttribute("examAutoPeopleInfo", examAutoPeopleInfo);
		return "modules/exam/examAutoPeopleInfoForm";
	}

	@RequiresPermissions("exam:examAutoPeopleInfo:edit")
	@RequestMapping(value = "save")
	public String save(ExamAutoPeopleInfo examAutoPeopleInfo, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, examAutoPeopleInfo)){
			return form(examAutoPeopleInfo, model);
		}
		examAutoPeopleInfoService.save(examAutoPeopleInfo);
		addMessage(redirectAttributes, "保存人员身份成功");
		request.setAttribute("saveResult", "success");
		return "modules/exam/examAutoPeopleInfoForm";
	}
	
	@RequiresPermissions("exam:examAutoPeopleInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(ExamAutoPeopleInfo examAutoPeopleInfo, RedirectAttributes redirectAttributes) {
		examAutoPeopleInfoService.delete(examAutoPeopleInfo);
		addMessage(redirectAttributes, "删除人员身份成功");
		return "redirect:"+Global.getAdminPath()+"/exam/examAutoPeopleInfo/?repage";
	}

	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:examAutoPeopleInfo:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if (ids != null && ids.size() > 0) {
			examAutoPeopleInfoService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		} else {
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

}