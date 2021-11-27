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
import com.thinkgem.jeesite.modules.affair.entity.AffairWorkHonest;
import com.thinkgem.jeesite.modules.affair.service.AffairWorkHonestService;

import java.util.List;
import java.util.Map;

/**
 * 党风廉政建设Controller
 * @author Alan
 * @version 2020-06-04
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairWorkHonest")
public class AffairWorkHonestController extends BaseController {

	@Autowired
	private AffairWorkHonestService affairWorkHonestService;

	@Autowired
	private UploadController uploadController;

	@ModelAttribute
	public AffairWorkHonest get(@RequestParam(required=false) String id) {
		AffairWorkHonest entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairWorkHonestService.get(id);
		}
		if (entity == null){
			entity = new AffairWorkHonest();
		}
		return entity;
	}

	@RequiresPermissions("affair:affairWorkHonest:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairWorkHonest affairWorkHonest, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("treeId", affairWorkHonest.getTreeId());
		Page<AffairWorkHonest> page = affairWorkHonestService.findPage(new Page<AffairWorkHonest>(request, response), affairWorkHonest);
		model.addAttribute("page", page);
		return "modules/affair/affairWorkHonestList";
	}

	@RequiresPermissions("affair:affairWorkHonest:view")
	@RequestMapping(value = "form")
	public String form(AffairWorkHonest affairWorkHonest, Model model) {
		model.addAttribute("affairWorkHonest", affairWorkHonest);
		return "modules/affair/affairWorkHonestForm";
	}

	@RequiresPermissions("affair:affairWorkHonest:edit")
	@RequestMapping(value = "save")
	public String save(AffairWorkHonest affairWorkHonest, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, affairWorkHonest)){
			return form(affairWorkHonest, model);
		}

		String status = affairWorkHonest.getStatus();
		if (StringUtils.isBlank(status)){
			affairWorkHonest.setStatus("1");
		}

		affairWorkHonestService.save(affairWorkHonest);
		addMessage(redirectAttributes, "保存党风廉政建设成功");
		model.addAttribute("saveResult","sucess");
		return "modules/affair/affairWorkHonestForm";
	}

	@RequiresPermissions("affair:affairWorkHonest:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairWorkHonest affairWorkHonest, RedirectAttributes redirectAttributes) {
		affairWorkHonestService.delete(affairWorkHonest);
		addMessage(redirectAttributes, "删除党风廉政建设成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairWorkHonest/list?repage&treeId="+affairWorkHonest.getTreeId();
	}

	/**
	 * 详情
	 *
	 * @param affairWorkHonest
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairWorkHonest:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairWorkHonest affairWorkHonest, Model model) {
		model.addAttribute("affairWorkHonest", affairWorkHonest);
		if (affairWorkHonest.getAdjunct() != null && affairWorkHonest.getAdjunct().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairWorkHonest.getAdjunct());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairWorkHonestFormDetail";
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairWorkHonest:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairWorkHonestService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	/*审核*/
	@RequiresPermissions("affair:affairWorkHonest:edit")
	@RequestMapping(value = "examine")
	public String examine(AffairWorkHonest affairWorkHonest, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairWorkHonest)){
			return form(affairWorkHonest, model);
		}
		affairWorkHonestService.save(affairWorkHonest);
		addMessage(redirectAttributes, "审核成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairWorkHonestForm";
	}

	@RequiresPermissions("affair:affairWorkHonest:edit")
	@RequestMapping(value = "report")
	public String report(AffairWorkHonest affairWorkHonest, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairWorkHonest)){
			return form(affairWorkHonest, model);
		}
		affairWorkHonest.setStatus("2");
		affairWorkHonestService.save(affairWorkHonest);
		addMessage(redirectAttributes, "上报成功");
		request.setAttribute("saveResult","sucess");
		return "redirect:"+Global.getAdminPath()+"/affair/affairWorkHonest/list?repage&treeId="+affairWorkHonest.getTreeId();
	}


	@RequiresPermissions("affair:affairWorkHonest:edit")
	@RequestMapping(value = {"shenHeDialog"})
	public String shenHeDialog() {
		return "modules/affair/affairWorkHonestCheckDialog";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairWorkHonest:edit")
	@RequestMapping(value = {"shenHeSave"})
	public Result shenHeSave(AffairWorkHonest affairWorkHonest) {
		affairWorkHonestService.shenHeSave(affairWorkHonest);
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("审核成功");
		return result;
	}
}