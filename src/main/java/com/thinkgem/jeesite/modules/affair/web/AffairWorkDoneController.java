/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.UploadController;
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
import com.thinkgem.jeesite.modules.affair.entity.AffairWorkDone;
import com.thinkgem.jeesite.modules.affair.service.AffairWorkDoneService;

import java.util.List;
import java.util.Map;

/**
 * 主体责任落实中的工作总结Controller
 * @author Alan.wu
 * @version 2020-05-29
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairWorkDone")
public class AffairWorkDoneController extends BaseController {

	@Autowired
	private AffairWorkDoneService affairWorkDoneService;

	@Autowired
	private UploadController uploadController;
	
	@ModelAttribute
	public AffairWorkDone get(@RequestParam(required=false) String id) {
		AffairWorkDone entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairWorkDoneService.get(id);
		}
		if (entity == null){
			entity = new AffairWorkDone();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairWorkDone:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairWorkDone affairWorkDone, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("treeId", affairWorkDone.getTreeId());
		model.addAttribute("parentId", request.getParameter("parentId"));
		Page<AffairWorkDone> page = affairWorkDoneService.findPage(new Page<AffairWorkDone>(request, response), affairWorkDone);
		model.addAttribute("page", page);
		return "modules/affair/affairWorkDoneList";
	}


	@RequiresPermissions("affair:affairWorkDone:view")
	@RequestMapping(value = "form")
	public String form(AffairWorkDone affairWorkDone, Model model) {
		model.addAttribute("affairWorkDone", affairWorkDone);
		return "modules/affair/affairWorkDoneForm";
	}


	@RequiresPermissions("affair:affairWorkDone:edit")
	@RequestMapping(value = "save")
	public String save(AffairWorkDone affairWorkDone, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, affairWorkDone)){
			return form(affairWorkDone, model);
		}

		String status = affairWorkDone.getStatus();
		if (StringUtils.isBlank(status)){
			affairWorkDone.setStatus("1");
		}

		affairWorkDoneService.save(affairWorkDone);
		addMessage(redirectAttributes, "保存工作总结成功");
		model.addAttribute("saveResult","sucess");
		return "modules/affair/affairWorkDoneForm";
	}
	
	@RequiresPermissions("affair:affairWorkDone:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairWorkDone affairWorkDone, RedirectAttributes redirectAttributes) {
		affairWorkDoneService.delete(affairWorkDone);
		addMessage(redirectAttributes, "删除工作总结成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairWorkDone/list?repage&treeId="+affairWorkDone.getTreeId();
	}

	/**
	 * 详情
	 *
	 * @param affairWorkDone
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairWorkDone:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairWorkDone affairWorkDone, Model model) {
		model.addAttribute("affairWorkDone", affairWorkDone);
		if (affairWorkDone.getAdjunct() != null && affairWorkDone.getAdjunct().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairWorkDone.getAdjunct());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairWorkDoneFormDetail";
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairWorkDone:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairWorkDoneService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}


	/*审核*/
	@RequiresPermissions("affair:affairWorkDone:edit")
	@RequestMapping(value = "examine")
	public String examine(AffairWorkDone affairWorkDone, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairWorkDone)){
			return form(affairWorkDone, model);
		}
		affairWorkDoneService.save(affairWorkDone);
		addMessage(redirectAttributes, "审核成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairWorkDoneForm";
	}

	@RequiresPermissions("affair:affairWorkDone:edit")
	@RequestMapping(value = "report")
	public String report(AffairWorkDone affairWorkDone, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairWorkDone)){
			return form(affairWorkDone, model);
		}
		affairWorkDone.setStatus("2");
		affairWorkDoneService.save(affairWorkDone);
		addMessage(redirectAttributes, "上报成功");
		request.setAttribute("saveResult","sucess");
		return "redirect:"+Global.getAdminPath()+"/affair/affairWorkDone/list?repage&treeId="+affairWorkDone.getTreeId();
	}


	@RequiresPermissions("affair:affairWorkDone:edit")
	@RequestMapping(value = {"shenHeDialog"})
	public String affairWorkDone() {
		return "modules/affair/affairWorkDoneCheckDialog";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairWorkDone:edit")
	@RequestMapping(value = {"shenHeSave"})
	public Result shenHeSave(AffairWorkDone affairWorkDone) {
		affairWorkDoneService.shenHeSave(affairWorkDone);
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("审核成功");
		return result;
	}
}