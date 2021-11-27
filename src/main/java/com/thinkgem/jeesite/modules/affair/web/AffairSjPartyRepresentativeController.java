/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.affair.entity.AffairClassMember;
import com.thinkgem.jeesite.modules.affair.entity.AffairSjPartyRepresentative;
import com.thinkgem.jeesite.modules.affair.entity.AffairWorkBuild;
import com.thinkgem.jeesite.modules.affair.service.AffairSjPartyRepresentativeService;
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
 * 上级党代表Controller
 * @author cecil.li
 * @version 2020-06-09
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairSjPartyRepresentative")
public class AffairSjPartyRepresentativeController extends BaseController {

	@Autowired
	private AffairSjPartyRepresentativeService affairSjPartyRepresentativeService;
	
	@ModelAttribute
	public AffairSjPartyRepresentative get(@RequestParam(required=false) String id) {
		AffairSjPartyRepresentative entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairSjPartyRepresentativeService.get(id);
		}
		if (entity == null){
			entity = new AffairSjPartyRepresentative();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairSjPartyRepresentative:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairSjPartyRepresentative affairSjPartyRepresentative, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("treeId", affairSjPartyRepresentative.getTreeId());
		Page<AffairSjPartyRepresentative> page = affairSjPartyRepresentativeService.findPage(new Page<AffairSjPartyRepresentative>(request, response), affairSjPartyRepresentative); 
		model.addAttribute("page", page);
		return "modules/affair/affairSjPartyRepresentativeList";
	}

	@RequiresPermissions("affair:affairSjPartyRepresentative:view")
	@RequestMapping(value = "form")
	public String form(AffairSjPartyRepresentative affairSjPartyRepresentative, Model model) {
		model.addAttribute("affairSjPartyRepresentative", affairSjPartyRepresentative);
		return "modules/affair/affairSjPartyRepresentativeForm";
	}

	@RequiresPermissions("affair:affairSjPartyRepresentative:edit")
	@RequestMapping(value = "save")
	public String save(AffairSjPartyRepresentative affairSjPartyRepresentative, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairSjPartyRepresentative)){
			return form(affairSjPartyRepresentative, model);
		}


		String status = affairSjPartyRepresentative.getStatus();
		if (StringUtils.isBlank(status)){
			affairSjPartyRepresentative.setStatus("1");
		}
		affairSjPartyRepresentativeService.save(affairSjPartyRepresentative);
		addMessage(redirectAttributes, "保存上级党代表成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairSjPartyRepresentativeForm";
	}
	
	@RequiresPermissions("affair:affairSjPartyRepresentative:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairSjPartyRepresentative affairSjPartyRepresentative, RedirectAttributes redirectAttributes) {
		affairSjPartyRepresentativeService.delete(affairSjPartyRepresentative);
		addMessage(redirectAttributes, "删除上级党代表成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairSjPartyRepresentative/list?repage&treeId="+affairSjPartyRepresentative.getTreeId();
	}

	@ResponseBody
	@RequiresPermissions("affair:affairSjPartyRepresentative:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairSjPartyRepresentativeService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	/*审核*/
	@RequiresPermissions("affair:affairSjPartyRepresentative:edit")
	@RequestMapping(value = "examine")
	public String examine(AffairSjPartyRepresentative affairSjPartyRepresentative, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairSjPartyRepresentative)){
			return form(affairSjPartyRepresentative, model);
		}
		affairSjPartyRepresentativeService.save(affairSjPartyRepresentative);
		addMessage(redirectAttributes, "审核成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairSjPartyRepresentativeForm";
	}

	@RequiresPermissions("affair:affairSjPartyRepresentative:edit")
	@RequestMapping(value = "report")
	public String report(AffairSjPartyRepresentative affairSjPartyRepresentative, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairSjPartyRepresentative)){
			return form(affairSjPartyRepresentative, model);
		}
		affairSjPartyRepresentative.setStatus("2");
		affairSjPartyRepresentativeService.save(affairSjPartyRepresentative);
		addMessage(redirectAttributes, "上报成功");
		request.setAttribute("saveResult","sucess");
		return "redirect:"+Global.getAdminPath()+"/affair/affairSjPartyRepresentative/list?repage&treeId="+affairSjPartyRepresentative.getTreeId();
	}

	@RequiresPermissions("affair:affairSjPartyRepresentative:edit")
	@RequestMapping(value = {"shenHeDialog"})
	public String shenHeDialog() {
		return "modules/affair/affairSjPartyRepresentativeCheckDialog";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairSjPartyRepresentative:edit")
	@RequestMapping(value = {"shenHeSave"})
	public Result shenHeSave(AffairSjPartyRepresentative affairSjPartyRepresentative) {
		affairSjPartyRepresentativeService.shenHeSave(affairSjPartyRepresentative);
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("审核成功");
		return result;
	}

}