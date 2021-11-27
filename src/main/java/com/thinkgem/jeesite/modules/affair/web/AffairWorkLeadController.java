/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.UploadController;
import com.thinkgem.jeesite.modules.affair.entity.AffairClassMember;
import com.thinkgem.jeesite.modules.affair.entity.AffairMonthStudy;
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
import com.thinkgem.jeesite.modules.affair.entity.AffairWorkLead;
import com.thinkgem.jeesite.modules.affair.service.AffairWorkLeadService;

import java.util.List;
import java.util.Map;

/**
 * 党建工作领导小组成员Controller
 * @author alan
 * @version 2020-06-03
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairWorkLead")
public class AffairWorkLeadController extends BaseController {

	@Autowired
	private AffairWorkLeadService affairWorkLeadService;

	@Autowired
	UploadController uploadController;
	
	@ModelAttribute
	public AffairWorkLead get(@RequestParam(required=false) String id) {
		AffairWorkLead entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairWorkLeadService.get(id);
		}
		if (entity == null){
			entity = new AffairWorkLead();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairWorkLead:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairWorkLead affairWorkLead, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("treeId", affairWorkLead.getTreeId());
		affairWorkLead.setpId(affairWorkLead.getTreeId());
		Page<AffairWorkLead> page = affairWorkLeadService.findPage(new Page<AffairWorkLead>(request, response), affairWorkLead);
		model.addAttribute("page", page);
		return "modules/affair/affairWorkLeadList";
	}

	@RequiresPermissions("affair:affairWorkLead:view")
	@RequestMapping(value = "form")
	public String form(AffairWorkLead affairWorkLead, Model model) {
		model.addAttribute("affairWorkLead", affairWorkLead);
		return "modules/affair/affairWorkLeadForm";
	}

	@RequiresPermissions("affair:affairWorkLead:edit")
	@RequestMapping(value = "save")
	public String save(AffairWorkLead affairWorkLead, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, affairWorkLead)){
			return form(affairWorkLead, model);
		}

		String status = affairWorkLead.getStatus();
		if (StringUtils.isBlank(status)){
			affairWorkLead.setStatus("1");
		}
		affairWorkLeadService.save(affairWorkLead);
		addMessage(redirectAttributes, "保存党建工作领导小组成功");
		model.addAttribute("saveResult","sucess");
		return "modules/affair/affairWorkLeadForm";
	}
	
	@RequiresPermissions("affair:affairWorkLead:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairWorkLead affairWorkLead, RedirectAttributes redirectAttributes) {
		affairWorkLeadService.delete(affairWorkLead);
		addMessage(redirectAttributes, "删除党建工作领导小组成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairWorkLead/list?repage&treeId="+affairWorkLead.getTreeId();
	}

	/**
	 * 详情
	 *
	 * @param affairWorkLead
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairWorkLead:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairWorkLead affairWorkLead, Model model) {
		model.addAttribute("affairWorkLead", affairWorkLead);
		if (affairWorkLead.getAdjunct() != null && affairWorkLead.getAdjunct().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairWorkLead.getAdjunct());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairWorkLeadFormDetail";
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairWorkLead:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairWorkLeadService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}


	/*审核*/
	@RequiresPermissions("affair:affairWorkLead:edit")
	@RequestMapping(value = "examine")
	public String examine(AffairWorkLead affairWorkLead, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairWorkLead)){
			return form(affairWorkLead, model);
		}
		affairWorkLeadService.save(affairWorkLead);
		addMessage(redirectAttributes, "审核成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairWorkLeadForm";
	}

	@RequiresPermissions("affair:affairWorkLead:edit")
	@RequestMapping(value = "report")
	public String report(AffairWorkLead affairWorkLead, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairWorkLead)){
			return form(affairWorkLead, model);
		}
		affairWorkLead.setStatus("2");
		affairWorkLeadService.save(affairWorkLead);
		addMessage(redirectAttributes, "上报成功");
		request.setAttribute("saveResult","sucess");
		return "redirect:"+Global.getAdminPath()+"/affair/affairWorkLead/list?repage&treeId="+affairWorkLead.getTreeId();
	}

	@RequiresPermissions("affair:affairWorkLead:edit")
	@RequestMapping(value = {"shenHeDialog"})
	public String shenHeDialog() {
		return "modules/affair/affairWorkLeadCheckDialog";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairWorkLead:edit")
	@RequestMapping(value = {"shenHeSave"})
	public Result shenHeSave(AffairWorkLead affairWorkLead) {
		affairWorkLeadService.shenHeSave(affairWorkLead);
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("审核成功");
		return result;
	}
}