/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.affair.entity.AffairDestroyMeterial;
import com.thinkgem.jeesite.modules.affair.service.AffairDestroyMeterialService;
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
 * 销毁清册Controller
 * @author mason.xv
 * @version 2019-11-05
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairDestroyMeterial")
public class AffairDestroyMeterialController extends BaseController {

	@Autowired
	private AffairDestroyMeterialService affairDestroyMeterialService;
	
	@ModelAttribute
	public AffairDestroyMeterial get(@RequestParam(required=false) String id) {
		AffairDestroyMeterial entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairDestroyMeterialService.get(id);
		}
		if (entity == null){
			entity = new AffairDestroyMeterial();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairDestroyMeterial:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairDestroyMeterial affairDestroyMeterial, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairDestroyMeterial> page = affairDestroyMeterialService.findPage(new Page<AffairDestroyMeterial>(request, response), affairDestroyMeterial); 
		model.addAttribute("page", page);
		return "modules/affair/affairDestroyMeterialList";
	}

	@RequiresPermissions("affair:affairDestroyMeterial:view")
	@RequestMapping(value = "form")
	public String form(AffairDestroyMeterial affairDestroyMeterial, Model model) {
		model.addAttribute("affairDestroyMeterial", affairDestroyMeterial);
		return "modules/affair/affairDestroyMeterialForm";
	}

	@RequiresPermissions("affair:affairDestroyMeterial:edit")
	@RequestMapping(value = "save")
	public String save(AffairDestroyMeterial affairDestroyMeterial, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairDestroyMeterial)){
			return form(affairDestroyMeterial, model);
		}
		affairDestroyMeterialService.save(affairDestroyMeterial);
		addMessage(redirectAttributes, "保存销毁清册成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairDestroyMeterialForm";
	}
	
	@RequiresPermissions("affair:affairDestroyMeterial:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairDestroyMeterial affairDestroyMeterial, RedirectAttributes redirectAttributes) {
		affairDestroyMeterialService.delete(affairDestroyMeterial);
		addMessage(redirectAttributes, "删除销毁清册成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairDestroyMeterial/?repage";
	}
	@ResponseBody
	@RequiresPermissions("affair:affairDestroyMeterial:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairDestroyMeterialService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	@RequiresPermissions("affair:affairDestroyMeterial:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairDestroyMeterial affairDestroyMeterial, Model model) {
		model.addAttribute("affairDestroyMeterial", affairDestroyMeterial);
		return "modules/affair/affairDestroyMeterialFormDetail";
	}
}