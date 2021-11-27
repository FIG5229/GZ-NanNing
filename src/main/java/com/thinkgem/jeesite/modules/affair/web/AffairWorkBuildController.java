/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.UploadController;
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
import com.thinkgem.jeesite.modules.affair.entity.AffairWorkBuild;
import com.thinkgem.jeesite.modules.affair.service.AffairWorkBuildService;

import java.util.List;
import java.util.Map;

/**
 * 党建工作Controller
 * @author Alan
 * @version 2020-06-03
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairWorkBuild")
public class AffairWorkBuildController extends BaseController {

	@Autowired
	private AffairWorkBuildService affairWorkBuildService;

	@Autowired
	private UploadController uploadController;
	
	@ModelAttribute
	public AffairWorkBuild get(@RequestParam(required=false) String id) {
		AffairWorkBuild entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairWorkBuildService.get(id);
		}
		if (entity == null){
			entity = new AffairWorkBuild();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairWorkBuild:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairWorkBuild affairWorkBuild, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("treeId", affairWorkBuild.getTreeId());
		Page<AffairWorkBuild> page = affairWorkBuildService.findPage(new Page<AffairWorkBuild>(request, response), affairWorkBuild);
		model.addAttribute("page", page);
		return "modules/affair/affairWorkBuildList";
	}

	@RequiresPermissions("affair:affairWorkBuild:view")
	@RequestMapping(value = "form")
	public String form(AffairWorkBuild affairWorkBuild, Model model) {
		model.addAttribute("affairWorkBuild", affairWorkBuild);
		return "modules/affair/affairWorkBuildForm";
	}

	@RequiresPermissions("affair:affairWorkBuild:edit")
	@RequestMapping(value = "save")
	public String save(AffairWorkBuild affairWorkBuild, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, affairWorkBuild)){
			return form(affairWorkBuild, model);
		}

		String status = affairWorkBuild.getStatus();
		if (StringUtils.isBlank(status)){
			affairWorkBuild.setStatus("1");
		}

		affairWorkBuildService.save(affairWorkBuild);
		addMessage(redirectAttributes, "保存党建工作成功");
		model.addAttribute("saveResult","sucess");
		return "modules/affair/affairWorkBuildForm";
	}
	
	@RequiresPermissions("affair:affairWorkBuild:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairWorkBuild affairWorkBuild, RedirectAttributes redirectAttributes) {
		affairWorkBuildService.delete(affairWorkBuild);
		addMessage(redirectAttributes, "删除党建工作成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairWorkBuild/list?repage&treeId="+affairWorkBuild.getTreeId();
	}

	/**
	 * 详情
	 *
	 * @param affairWorkBuild
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairWorkBuild:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairWorkBuild affairWorkBuild, Model model) {
		model.addAttribute("affairWorkBuild", affairWorkBuild);
		if (affairWorkBuild.getAdjunct() != null && affairWorkBuild.getAdjunct().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairWorkBuild.getAdjunct());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairWorkBuildFormDetail";
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairWorkBuild:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairWorkBuildService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	/*审核*/
	@RequiresPermissions("affair:affairWorkBuild:edit")
	@RequestMapping(value = "examine")
	public String examine(AffairWorkBuild affairWorkBuild, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairWorkBuild)){
			return form(affairWorkBuild, model);
		}
		affairWorkBuildService.save(affairWorkBuild);
		addMessage(redirectAttributes, "审核成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairWorkBuildForm";
	}

	@RequiresPermissions("affair:affairWorkBuild:edit")
	@RequestMapping(value = "report")
	public String report(AffairWorkBuild affairWorkBuild, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairWorkBuild)){
			return form(affairWorkBuild, model);
		}
		affairWorkBuild.setStatus("2");
		affairWorkBuildService.save(affairWorkBuild);
		addMessage(redirectAttributes, "上报成功");
		request.setAttribute("saveResult","sucess");
		return "redirect:"+Global.getAdminPath()+"/affair/affairWorkBuild/list?repage&treeId="+affairWorkBuild.getTreeId();
	}

	@RequiresPermissions("affair:affairWorkBuild:edit")
	@RequestMapping(value = {"shenHeDialog"})
	public String shenHeDialog() {
		return "modules/affair/affairWorkBuildCheckDialog";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairWorkBuild:edit")
	@RequestMapping(value = {"shenHeSave"})
	public Result shenHeSave(AffairWorkBuild affairWorkBuild) {
		affairWorkBuildService.shenHeSave(affairWorkBuild);
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("审核成功");
		return result;
	}

}