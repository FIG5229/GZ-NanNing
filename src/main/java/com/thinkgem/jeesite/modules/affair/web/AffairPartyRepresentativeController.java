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
import com.thinkgem.jeesite.modules.affair.entity.AffairPartyRepresentative;
import com.thinkgem.jeesite.modules.affair.entity.AffairSjPartyRepresentative;
import com.thinkgem.jeesite.modules.affair.service.AffairPartyRepresentativeService;
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
 * 党代表Controller
 * @author cecil.li
 * @version 2020-06-09
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairPartyRepresentative")
public class AffairPartyRepresentativeController extends BaseController {

	@Autowired
	private AffairPartyRepresentativeService affairPartyRepresentativeService;
	
	@ModelAttribute
	public AffairPartyRepresentative get(@RequestParam(required=false) String id) {
		AffairPartyRepresentative entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairPartyRepresentativeService.get(id);
		}
		if (entity == null){
			entity = new AffairPartyRepresentative();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairPartyRepresentative:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairPartyRepresentative affairPartyRepresentative, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("treeId", affairPartyRepresentative.getTreeId());
		Page<AffairPartyRepresentative> page = affairPartyRepresentativeService.findPage(new Page<AffairPartyRepresentative>(request, response), affairPartyRepresentative); 
		model.addAttribute("page", page);
		return "modules/affair/affairPartyRepresentativeList";
	}

	@RequiresPermissions("affair:affairPartyRepresentative:view")
	@RequestMapping(value = "form")
	public String form(AffairPartyRepresentative affairPartyRepresentative, Model model) {
		model.addAttribute("affairPartyRepresentative", affairPartyRepresentative);
		return "modules/affair/affairPartyRepresentativeForm";
	}

	@RequiresPermissions("affair:affairPartyRepresentative:edit")
	@RequestMapping(value = "save")
	public String save(AffairPartyRepresentative affairPartyRepresentative, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairPartyRepresentative)){
			return form(affairPartyRepresentative, model);
		}
		String status = affairPartyRepresentative.getStatus();
		if (StringUtils.isBlank(status)){
			affairPartyRepresentative.setStatus("1");
		}
		affairPartyRepresentativeService.save(affairPartyRepresentative);
		addMessage(redirectAttributes, "保存党代表成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairPartyRepresentativeForm";
	}
	
	@RequiresPermissions("affair:affairPartyRepresentative:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairPartyRepresentative affairPartyRepresentative, RedirectAttributes redirectAttributes) {
		affairPartyRepresentativeService.delete(affairPartyRepresentative);
		addMessage(redirectAttributes, "删除党代表成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairPartyRepresentative/list?repage&treeId="+affairPartyRepresentative.getTreeId();
	}

	@ResponseBody
	@RequiresPermissions("affair:affairPartyRepresentative:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairPartyRepresentativeService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}
	/*审核*/
	@RequiresPermissions("affair:affairPartyRepresentative:edit")
	@RequestMapping(value = "examine")
	public String examine(AffairPartyRepresentative affairPartyRepresentative, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairPartyRepresentative)){
			return form(affairPartyRepresentative, model);
		}
		affairPartyRepresentativeService.save(affairPartyRepresentative);
		addMessage(redirectAttributes, "审核成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairPartyRepresentativeForm";
	}

	@RequiresPermissions("affair:affairPartyRepresentative:edit")
	@RequestMapping(value = "report")
	public String report(AffairPartyRepresentative affairPartyRepresentative, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairPartyRepresentative)){
			return form(affairPartyRepresentative, model);
		}
		affairPartyRepresentative.setStatus("2");
		affairPartyRepresentativeService.save(affairPartyRepresentative);
		addMessage(redirectAttributes, "上报成功");
		request.setAttribute("saveResult","sucess");
		return "redirect:"+Global.getAdminPath()+"/affair/affairPartyRepresentative/list?repage&treeId="+affairPartyRepresentative.getTreeId();
	}


	@RequiresPermissions("affair:affairPartyRepresentative:edit")
	@RequestMapping(value = {"shenHeDialog"})
	public String shenHeDialog() {
		return "modules/affair/affairPartyRepresentativeCheckDialog";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairPartyRepresentative:edit")
	@RequestMapping(value = {"shenHeSave"})
	public Result shenHeSave(AffairPartyRepresentative affairPartyRepresentative) {
		affairPartyRepresentativeService.shenHeSave(affairPartyRepresentative);
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("审核成功");
		return result;
	}
}