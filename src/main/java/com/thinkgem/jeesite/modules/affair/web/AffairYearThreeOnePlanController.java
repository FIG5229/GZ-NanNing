/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.web.UploadController;
import com.thinkgem.jeesite.modules.affair.entity.AffairYearThreeOnePlan;
import com.thinkgem.jeesite.modules.affair.service.AffairYearThreeOnePlanService;
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
import java.util.Map;

/**
 * 年度&ldquo;三会一课&rdquo;计划Controller
 * @author eav.liu
 * @version 2019-11-07
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairYearThreeOnePlan")
public class AffairYearThreeOnePlanController extends BaseController {

	@Autowired
	private AffairYearThreeOnePlanService affairYearThreeOnePlanService;

	@Autowired
	private UploadController uploadController;
	
	@ModelAttribute
	public AffairYearThreeOnePlan get(@RequestParam(required=false) String id) {
		AffairYearThreeOnePlan entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairYearThreeOnePlanService.get(id);
		}
		if (entity == null){
			entity = new AffairYearThreeOnePlan();
		}
		return entity;
	}

	@RequiresPermissions("affair:affairYearThreeOnePlan:view")
	@RequestMapping(value = {""})
	public String index(AffairYearThreeOnePlan affairYearThreeOnePlan) {
		return "modules/affair/affairYearThreeOnePlanIndex";
	}

	@RequiresPermissions("affair:affairYearThreeOnePlan:view")
	@RequestMapping(value = {"list"})
	public String list(AffairYearThreeOnePlan affairYearThreeOnePlan, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("treeId", affairYearThreeOnePlan.getTreeId());
		model.addAttribute("parentId", request.getParameter("parentId"));
		affairYearThreeOnePlan.setHasAuth(false);
		Page<AffairYearThreeOnePlan> page = affairYearThreeOnePlanService.findPage(new Page<AffairYearThreeOnePlan>(request, response), affairYearThreeOnePlan); 
		model.addAttribute("page", page);
		return "modules/affair/affairYearThreeOnePlanList";
	}

	@RequiresPermissions("affair:affairYearThreeOnePlan:view")
	@RequestMapping(value = "form")
	public String form(AffairYearThreeOnePlan affairYearThreeOnePlan, Model model) {
		model.addAttribute("affairYearThreeOnePlan", affairYearThreeOnePlan);
		return "modules/affair/affairYearThreeOnePlanForm";
	}

	@RequiresPermissions("affair:affairYearThreeOnePlan:edit")
	@RequestMapping(value = "save")
	public String save(AffairYearThreeOnePlan affairYearThreeOnePlan, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairYearThreeOnePlan)){
			return form(affairYearThreeOnePlan, model);
		}
		affairYearThreeOnePlanService.save(affairYearThreeOnePlan);
		addMessage(redirectAttributes, "保存年度&ldquo;三会一课&rdquo;计划成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairYearThreeOnePlanForm";
	}
	
	@RequiresPermissions("affair:affairYearThreeOnePlan:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairYearThreeOnePlan affairYearThreeOnePlan, RedirectAttributes redirectAttributes) {
		affairYearThreeOnePlanService.delete(affairYearThreeOnePlan);
		addMessage(redirectAttributes, "删除年度&ldquo;三会一课&rdquo;计划成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairYearThreeOnePlan/list?repage&treeId="+affairYearThreeOnePlan.getTreeId();
	}

	/**
	 * 管理页面
	 * @param affairYearThreeOnePlan
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairYearThreeOnePlan:manage")
	@RequestMapping(value = {"manageList"})
	public String manageList(AffairYearThreeOnePlan affairYearThreeOnePlan, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("treeId", affairYearThreeOnePlan.getTreeId());
		affairYearThreeOnePlan.setHasAuth(true);
		Page<AffairYearThreeOnePlan> page = affairYearThreeOnePlanService.findPage(new Page<AffairYearThreeOnePlan>(request, response), affairYearThreeOnePlan);
		model.addAttribute("page", page);
		return "modules/affair/affairYearThreeOnePlanManage";
	}

	@RequiresPermissions("affair:affairYearThreeOnePlan:manage")
	@RequestMapping(value = {"shenHeDialog"})
	public String shenHeDialog() {
		return "modules/affair/affairYearThreeOnePlanDialog";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairYearThreeOnePlan:manage")
	@RequestMapping(value = {"shenHeSave"})
	public Result shenHeSave(AffairYearThreeOnePlan affairYearThreeOnePlan) {
		affairYearThreeOnePlanService.shenHeSave(affairYearThreeOnePlan);
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("审核成功");
		return result;
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairYearThreeOnePlan:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairYearThreeOnePlanService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	/**
	 * 详情
	 * @param affairYearThreeOnePlan
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairYearThreeOnePlan:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairYearThreeOnePlan affairYearThreeOnePlan, Model model) {
		model.addAttribute("affairYearThreeOnePlan", affairYearThreeOnePlan);
		if (affairYearThreeOnePlan.getFilePath() != null && affairYearThreeOnePlan.getFilePath().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairYearThreeOnePlan.getFilePath());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairYearThreeOnePlanFormDetail";
	}
}