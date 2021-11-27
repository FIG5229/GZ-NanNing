/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.affair.entity.AffairCheck;
import com.thinkgem.jeesite.modules.affair.service.AffairCheckService;
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
 * 检查核对干部档案登记Controller
 * @author mason.xv
 * @version 2019-11-05
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairCheck")
public class AffairCheckController extends BaseController {

	@Autowired
	private AffairCheckService affairCheckService;
	
	@ModelAttribute
	public AffairCheck get(@RequestParam(required=false) String id) {
		AffairCheck entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairCheckService.get(id);
		}
		if (entity == null){
			entity = new AffairCheck();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairCheck:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairCheck affairCheck, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairCheck> page = affairCheckService.findPage(new Page<AffairCheck>(request, response), affairCheck); 
		model.addAttribute("page", page);
		return "modules/affair/affairCheckList";
	}

	@RequiresPermissions("affair:affairCheck:view")
	@RequestMapping(value = "form")
	public String form(AffairCheck affairCheck, Model model) {
		model.addAttribute("affairCheck", affairCheck);
		return "modules/affair/affairCheckForm";
	}

	@RequiresPermissions("affair:affairCheck:edit")
	@RequestMapping(value = "save")
	public String save(AffairCheck affairCheck, Model model, RedirectAttributes redirectAttributes ,HttpServletRequest request) {
		if (!beanValidator(model, affairCheck)){
			return form(affairCheck, model);
		}
		affairCheckService.save(affairCheck);
		addMessage(redirectAttributes, "保存检查核对干部档案登记成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairCheckForm";
	}
	
	@RequiresPermissions("affair:affairCheck:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairCheck affairCheck, RedirectAttributes redirectAttributes) {
		affairCheckService.delete(affairCheck);
		addMessage(redirectAttributes, "删除检查核对干部档案登记成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairCheck/?repage";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairCheck:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairCheckService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}
	@RequiresPermissions("affair:affairCheck:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairCheck affairCheck, Model model) {
		model.addAttribute("affairCheck", affairCheck);
		return "modules/affair/affairCheckFormDetail";
	}
}