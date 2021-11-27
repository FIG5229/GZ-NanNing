/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.affair.entity.AffairConsult;
import com.thinkgem.jeesite.modules.affair.service.AffairConsultService;
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
 * 台账查阅表Controller
 * @author mason.xv
 * @version 2019-11-05
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairConsult")
public class AffairConsultController extends BaseController {

	@Autowired
	private AffairConsultService affairConsultService;
	
	@ModelAttribute
	public AffairConsult get(@RequestParam(required=false) String id) {
		AffairConsult entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairConsultService.get(id);
		}
		if (entity == null){
			entity = new AffairConsult();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairConsult:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairConsult affairConsult, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairConsult> page = affairConsultService.findPage(new Page<AffairConsult>(request, response), affairConsult); 
		model.addAttribute("page", page);
		return "modules/affair/affairConsultList";
	}

	@RequiresPermissions("affair:affairConsult:view")
	@RequestMapping(value = "form")
	public String form(AffairConsult affairConsult, Model model) {
		model.addAttribute("affairConsult", affairConsult);
		return "modules/affair/affairConsultForm";
	}

	@RequiresPermissions("affair:affairConsult:edit")
	@RequestMapping(value = "save")
	public String save(AffairConsult affairConsult, Model model, RedirectAttributes redirectAttributes  ,HttpServletRequest request) {
		if (!beanValidator(model, affairConsult)){
			return form(affairConsult, model);
		}
		affairConsultService.save(affairConsult);
		addMessage(redirectAttributes, "保存台账查阅表成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairConsultForm";
	}
	
	@RequiresPermissions("affair:affairConsult:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairConsult affairConsult, RedirectAttributes redirectAttributes) {
		affairConsultService.delete(affairConsult);
		addMessage(redirectAttributes, "删除台账查阅表成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairConsult/?repage";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairConsult:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairConsultService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	@RequiresPermissions("affair:affairConsult:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairConsult affairConsult, Model model) {
		model.addAttribute("affairConsult", affairConsult);
		return "modules/affair/affairConsultFormDetail";
	}

}