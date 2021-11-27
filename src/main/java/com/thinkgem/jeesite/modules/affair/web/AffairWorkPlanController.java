/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.UploadController;
import com.thinkgem.jeesite.modules.affair.entity.AffairClassMember;
import com.thinkgem.jeesite.modules.affair.entity.AffairWorkBuild;
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
import com.thinkgem.jeesite.modules.affair.entity.AffairWorkPlan;
import com.thinkgem.jeesite.modules.affair.service.AffairWorkPlanService;

import java.util.List;
import java.util.Map;

/**
 * 年度工作安排Controller
 * @author wll
 * @version 2020-06-02
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairWorkPlan")
public class AffairWorkPlanController extends BaseController {

	@Autowired
	private AffairWorkPlanService affairWorkPlanService;

	@Autowired
	private UploadController uploadController;
	
	@ModelAttribute
	public AffairWorkPlan get(@RequestParam(required=false) String id) {
		AffairWorkPlan entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairWorkPlanService.get(id);
		}
		if (entity == null){
			entity = new AffairWorkPlan();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairWorkPlan:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairWorkPlan affairWorkPlan, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("treeId", affairWorkPlan.getTreeId());
		Page<AffairWorkPlan> page = affairWorkPlanService.findPage(new Page<AffairWorkPlan>(request, response), affairWorkPlan);
		model.addAttribute("page", page);
		return "modules/affair/affairWorkPlanList";
	}

	@RequiresPermissions("affair:affairWorkPlan:view")
	@RequestMapping(value = "form")
	public String form(AffairWorkPlan affairWorkPlan, Model model) {
		model.addAttribute("affairWorkPlan", affairWorkPlan);
		return "modules/affair/affairWorkPlanForm";
	}

	@RequiresPermissions("affair:affairWorkPlan:edit")
	@RequestMapping(value = "save")
	public String save(AffairWorkPlan affairWorkPlan, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, affairWorkPlan)){
			return form(affairWorkPlan, model);
		}

		String status = affairWorkPlan.getStatus();
		if (StringUtils.isBlank(status)){
			affairWorkPlan.setStatus("1");
		}

		affairWorkPlanService.save(affairWorkPlan);
		addMessage(redirectAttributes, "保存年度工作安排成功");
		model.addAttribute("saveResult","sucess");
		return "modules/affair/affairWorkPlanForm";	}
	
	@RequiresPermissions("affair:affairWorkPlan:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairWorkPlan affairWorkPlan, RedirectAttributes redirectAttributes) {
		affairWorkPlanService.delete(affairWorkPlan);
		addMessage(redirectAttributes, "删除年度工作安排成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairWorkPlan/list?repage&treeId="+affairWorkPlan.getTreeId();
	}

	/**
	 * 详情
	 *
	 * @param affairWorkPlan
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairWorkPlan:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairWorkPlan affairWorkPlan, Model model) {
		model.addAttribute("affairWorkPlan", affairWorkPlan);
		if (affairWorkPlan.getAdjunct() != null && affairWorkPlan.getAdjunct().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairWorkPlan.getAdjunct());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairWorkPlanFormDetail";
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairWorkPlan:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairWorkPlanService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	/*审核*/
	@RequiresPermissions("affair:affairWorkPlan:edit")
	@RequestMapping(value = "examine")
	public String examine(AffairWorkPlan affairWorkPlan, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairWorkPlan)){
			return form(affairWorkPlan, model);
		}
		affairWorkPlanService.save(affairWorkPlan);
		addMessage(redirectAttributes, "审核成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairWorkPlanForm";
	}

	@RequiresPermissions("affair:affairWorkPlan:edit")
	@RequestMapping(value = "report")
	public String report(AffairWorkPlan affairWorkPlan, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairWorkPlan)){
			return form(affairWorkPlan, model);
		}
		affairWorkPlan.setStatus("2");
		affairWorkPlanService.save(affairWorkPlan);
		addMessage(redirectAttributes, "上报成功");
		request.setAttribute("saveResult","sucess");
		return "redirect:"+Global.getAdminPath()+"/affair/affairWorkPlan/list?repage&treeId="+affairWorkPlan.getTreeId();
	}


	@RequiresPermissions("affair:affairWorkPlan:edit")
	@RequestMapping(value = {"shenHeDialog"})
	public String shenHeDialog() {
		return "modules/affair/affairWorkPlanCheckDialog";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairWorkPlan:edit")
	@RequestMapping(value = {"shenHeSave"})
	public Result shenHeSave(AffairWorkPlan affairWorkPlan) {
		affairWorkPlanService.shenHeSave(affairWorkPlan);
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("审核成功");
		return result;
	}


}