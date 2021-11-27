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
import com.thinkgem.jeesite.modules.affair.entity.AffairMemberPartyCommittee;
import com.thinkgem.jeesite.modules.affair.service.AffairMemberPartyCommitteeService;

import java.util.List;
import java.util.Map;

/**
 * 党委委员Controller
 * @author daniel.liu
 * @version 2020-06-02
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairMemberPartyCommittee")
public class AffairMemberPartyCommitteeController extends BaseController {

	@Autowired
	private AffairMemberPartyCommitteeService affairMemberPartyCommitteeService;

	@Autowired
	private UploadController uploadController;
	
	@ModelAttribute
	public AffairMemberPartyCommittee get(@RequestParam(required=false) String id) {
		AffairMemberPartyCommittee entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairMemberPartyCommitteeService.get(id);
		}
		if (entity == null){
			entity = new AffairMemberPartyCommittee();
		}
		return entity;
	}

	@RequiresPermissions("affair:affairMemberPartyCommittee:view")
	@RequestMapping(value = {""})
	public String index() {
		return "modules/affair/affairMemberPartyCommitteeIndex";
	}

	
	@RequiresPermissions("affair:affairMemberPartyCommittee:view")
	@RequestMapping(value = {"list"})
	public String list(AffairMemberPartyCommittee affairMemberPartyCommittee, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("treeId", affairMemberPartyCommittee.getTreeId());
		model.addAttribute("parentId", request.getParameter("parentId"));
		Page<AffairMemberPartyCommittee> page = affairMemberPartyCommitteeService.findPage(new Page<AffairMemberPartyCommittee>(request, response), affairMemberPartyCommittee); 
		model.addAttribute("page", page);
		return "modules/affair/affairMemberPartyCommitteeList";
	}

	@RequiresPermissions("affair:affairMemberPartyCommittee:view")
	@RequestMapping(value = "form")
	public String form(AffairMemberPartyCommittee affairMemberPartyCommittee, Model model) {
		model.addAttribute("affairMemberPartyCommittee", affairMemberPartyCommittee);
		return "modules/affair/affairMemberPartyCommitteeForm";
	}

	@RequiresPermissions("affair:affairMemberPartyCommittee:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairMemberPartyCommittee affairMemberPartyCommittee, Model model) {
		model.addAttribute("affairMemberPartyCommittee", affairMemberPartyCommittee);
		/*附件合并到rrFilePathList 相关附件
		if(affairMemberPartyCommittee.getPcFilePath() != null && affairMemberPartyCommittee.getPcFilePath().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairMemberPartyCommittee.getPcFilePath());
			model.addAttribute("pcFilePathList", filePathList);
		}
		if(affairMemberPartyCommittee.getApFilePath() != null && affairMemberPartyCommittee.getApFilePath().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairMemberPartyCommittee.getApFilePath());
			model.addAttribute("apFilePathList", filePathList);
		}*/
		if(affairMemberPartyCommittee.getRrFilePath() != null && affairMemberPartyCommittee.getRrFilePath().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairMemberPartyCommittee.getRrFilePath());
			model.addAttribute("rrFilePathList", filePathList);
		}
		return "modules/affair/affairMemberPartyFormDetail";
	}

	@RequiresPermissions("affair:affairMemberPartyCommittee:edit")
	@RequestMapping(value = "save")
	public String save(AffairMemberPartyCommittee affairMemberPartyCommittee, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairMemberPartyCommittee)){
			return form(affairMemberPartyCommittee, model);
		}

		String status = affairMemberPartyCommittee.getStatus();
		if (StringUtils.isBlank(status)){
			affairMemberPartyCommittee.setStatus("1");
		}


		affairMemberPartyCommitteeService.save(affairMemberPartyCommittee);
		addMessage(redirectAttributes, "保存党委委员成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairMemberPartyCommitteeForm";
//		return "modules/affair/affairMemberPartyCommitteeList/list?treeId="+affairMemberPartyCommittee.getTreeId();
	}
	
	@RequiresPermissions("affair:affairMemberPartyCommittee:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairMemberPartyCommittee affairMemberPartyCommittee, RedirectAttributes redirectAttributes) {
		affairMemberPartyCommitteeService.delete(affairMemberPartyCommittee);
		addMessage(redirectAttributes, "删除党委委员成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairMemberPartyCommittee/list?repage&treeId="+affairMemberPartyCommittee.getTreeId();	}

	@ResponseBody
	@RequiresPermissions("affair:affairMemberPartyCommittee:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result res = new Result();
		if(ids != null && ids.size() > 0){
			affairMemberPartyCommitteeService.deleteByIds(ids);
			res.setSuccess(true);
			res.setMessage("删除成功");
		}else{
			res.setSuccess(false);
			res.setMessage("请先选择要删除的内容");
		}
		return res;
	}

	/*审核*/
	@RequiresPermissions("affair:affairMemberPartyCommittee:edit")
	@RequestMapping(value = "examine")
	public String examine(AffairMemberPartyCommittee affairMemberPartyCommittee, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairMemberPartyCommittee)){
			return form(affairMemberPartyCommittee, model);
		}
		affairMemberPartyCommitteeService.save(affairMemberPartyCommittee);
		addMessage(redirectAttributes, "审核成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairMemberPartyCommitteeForm";
	}

	@RequiresPermissions("affair:affairMemberPartyCommittee:edit")
	@RequestMapping(value = "report")
	public String report(AffairMemberPartyCommittee affairMemberPartyCommittee, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairMemberPartyCommittee)){
			return form(affairMemberPartyCommittee, model);
		}
		affairMemberPartyCommittee.setStatus("2");
		affairMemberPartyCommitteeService.save(affairMemberPartyCommittee);
		addMessage(redirectAttributes, "上报成功");
		request.setAttribute("saveResult","sucess");
		return "redirect:"+Global.getAdminPath()+"/affair/affairMemberPartyCommittee/list?repage&treeId="+affairMemberPartyCommittee.getTreeId();
	}

	@RequiresPermissions("affair:affairMemberPartyCommittee:edit")
	@RequestMapping(value = {"shenHeDialog"})
	public String shenHeDialog() {
		return "modules/affair/affairMemberPartyCommitteeCheckDialog";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairMemberPartyCommittee:edit")
	@RequestMapping(value = {"shenHeSave"})
	public Result shenHeSave(AffairMemberPartyCommittee affairMemberPartyCommittee) {
		affairMemberPartyCommitteeService.shenHeSave(affairMemberPartyCommittee);
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("审核成功");
		return result;
	}


}