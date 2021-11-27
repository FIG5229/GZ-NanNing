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
import com.thinkgem.jeesite.modules.exam.entity.ExamScoreWorkItem;
import com.thinkgem.jeesite.modules.exam.service.ExamScoreWorkItemService;

import java.util.List;
import java.util.Map;

/**
 * 得分制工作项管理Controller
 * @author tom.fu
 * @version 2021-03-04
 */
@Controller
@RequestMapping(value = "${adminPath}/exam/examScoreWorkItem")
public class ExamScoreWorkItemController extends BaseController {

	@Autowired
	private ExamScoreWorkItemService examScoreWorkItemService;
	
	@ModelAttribute
	public ExamScoreWorkItem get(@RequestParam(required=false) String id) {
		ExamScoreWorkItem entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = examScoreWorkItemService.get(id);
		}
		if (entity == null){
			entity = new ExamScoreWorkItem();
		}
		return entity;
	}
	
	@RequiresPermissions("exam:examScoreWorkItem:view")
	@RequestMapping(value = {"list", ""})
	public String list(ExamScoreWorkItem examScoreWorkItem, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ExamScoreWorkItem> page = examScoreWorkItemService.findPage(new Page<ExamScoreWorkItem>(request, response), examScoreWorkItem);
		List<ExamScoreWorkItem> list = page.getList();
		Page<ExamScoreWorkItem> examScoreWorkItemPage = page.setList(list);
		model.addAttribute("page", examScoreWorkItemPage);
		return "modules/exam/examScoreWorkItemList";
	}

	@RequiresPermissions("exam:examScoreWorkItem:view")
	@RequestMapping(value = "form")
	public String form(ExamScoreWorkItem examScoreWorkItem, Model model) {
		model.addAttribute("item","12");
		model.addAttribute("item","23");
		model.addAttribute("examScoreWorkItem", examScoreWorkItem);
		return "modules/exam/examScoreWorkItemForm";
	}

	@RequiresPermissions("exam:examScoreWorkItem:edit")
	@RequestMapping(value = "save")
	public String save(ExamScoreWorkItem examScoreWorkItem, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, examScoreWorkItem)){
			return form(examScoreWorkItem, model);
		}
		examScoreWorkItemService.save(examScoreWorkItem);
		addMessage(redirectAttributes, "保存得分制工作项管理成功");
		request.setAttribute("saveResult","sucess");
		return "modules/exam/examScoreWorkItemForm";
	}
	
	@RequiresPermissions("exam:examScoreWorkItem:edit")
	@RequestMapping(value = "delete")
	public String delete(ExamScoreWorkItem examScoreWorkItem, RedirectAttributes redirectAttributes) {
		examScoreWorkItemService.delete(examScoreWorkItem);
		addMessage(redirectAttributes, "删除得分制工作项管理成功");
		return "redirect:"+Global.getAdminPath()+"/exam/examScoreWorkItem/?repage";
	}

	/**
	 * 查看详情
	 * @param examScoreWorkItem
	 * @param model
	 * @return
	 */
	@RequiresPermissions("exam:examScoreWorkItem:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(ExamScoreWorkItem examScoreWorkItem, Model model) {
		model.addAttribute("examScoreWorkItem", examScoreWorkItem);
		return "modules/exam/examScoreWorkItemFormDetail";
	}

	@ResponseBody
	@RequiresPermissions("exam:examScoreWorkItem:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			examScoreWorkItemService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}


}