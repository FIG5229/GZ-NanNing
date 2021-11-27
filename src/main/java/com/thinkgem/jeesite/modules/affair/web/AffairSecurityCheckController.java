/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.affair.entity.AffairSecurityCheck;
import com.thinkgem.jeesite.modules.affair.service.AffairSecurityCheckService;
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
 * 安全检查Controller
 * @author mason.xv
 * @version 2019-11-05
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairSecurityCheck")
public class AffairSecurityCheckController extends BaseController {

	@Autowired
	private AffairSecurityCheckService affairSecurityCheckService;
	
	@ModelAttribute
	public AffairSecurityCheck get(@RequestParam(required=false) String id) {
		AffairSecurityCheck entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairSecurityCheckService.get(id);
		}
		if (entity == null){
			entity = new AffairSecurityCheck();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairSecurityCheck:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairSecurityCheck affairSecurityCheck, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairSecurityCheck> page = affairSecurityCheckService.findPage(new Page<AffairSecurityCheck>(request, response), affairSecurityCheck); 
		model.addAttribute("page", page);
		return "modules/affair/affairSecurityCheckList";
	}

	@RequiresPermissions("affair:affairSecurityCheck:view")
	@RequestMapping(value = "form")
	public String form(AffairSecurityCheck affairSecurityCheck, Model model) {
		model.addAttribute("affairSecurityCheck", affairSecurityCheck);
		return "modules/affair/affairSecurityCheckForm";
	}

	@RequiresPermissions("affair:affairSecurityCheck:edit")
	@RequestMapping(value = "save")
	public String save(AffairSecurityCheck affairSecurityCheck, Model model, RedirectAttributes redirectAttributes ,HttpServletRequest request) {
		if (!beanValidator(model, affairSecurityCheck)){
			return form(affairSecurityCheck, model);
		}
		affairSecurityCheckService.save(affairSecurityCheck);
		addMessage(redirectAttributes, "保存安全检查成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairSecurityCheckForm";
	}
	
	@RequiresPermissions("affair:affairSecurityCheck:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairSecurityCheck affairSecurityCheck, RedirectAttributes redirectAttributes) {
		affairSecurityCheckService.delete(affairSecurityCheck);
		addMessage(redirectAttributes, "删除安全检查成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairSecurityCheck/?repage";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairSecurityCheck:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairSecurityCheckService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}
	@RequiresPermissions("affair:affairSecurityCheck:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairSecurityCheck affairSecurityCheck, Model model) {
		model.addAttribute("affairSecurityCheck", affairSecurityCheck);
		return "modules/affair/affairSecurityCheckFormDetail";
	}
}