/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.affair.entity.AffairCopy;
import com.thinkgem.jeesite.modules.affair.service.AffairCopyService;
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
 * 复印档案登记Controller
 * @author mason.xv
 * @version 2019-11-05
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairCopy")
public class AffairCopyController extends BaseController {

	@Autowired
	private AffairCopyService affairCopyService;
	
	@ModelAttribute
	public AffairCopy get(@RequestParam(required=false) String id) {
		AffairCopy entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairCopyService.get(id);
		}
		if (entity == null){
			entity = new AffairCopy();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairCopy:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairCopy affairCopy, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairCopy> page = affairCopyService.findPage(new Page<AffairCopy>(request, response), affairCopy); 
		model.addAttribute("page", page);
		return "modules/affair/affairCopyList";
	}

	@RequiresPermissions("affair:affairCopy:view")
	@RequestMapping(value = "form")
	public String form(AffairCopy affairCopy, Model model) {
		model.addAttribute("affairCopy", affairCopy);
		return "modules/affair/affairCopyForm";
	}

	@RequiresPermissions("affair:affairCopy:edit")
	@RequestMapping(value = "save")
	public String save(AffairCopy affairCopy, Model model, RedirectAttributes redirectAttributes ,HttpServletRequest request) {
		if (!beanValidator(model, affairCopy)){
			return form(affairCopy, model);
		}
		affairCopyService.save(affairCopy);
		addMessage(redirectAttributes, "保存复印档案登记成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairCopyForm";
	}
	
	@RequiresPermissions("affair:affairCopy:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairCopy affairCopy, RedirectAttributes redirectAttributes) {
		affairCopyService.delete(affairCopy);
		addMessage(redirectAttributes, "删除复印档案登记成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairCopy/?repage";
	}
	@ResponseBody
	@RequiresPermissions("affair:affairCopy:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairCopyService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}
	@RequiresPermissions("affair:affairCopy:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairCopy affairCopy, Model model) {
		model.addAttribute("affairCopy", affairCopy);
		return "modules/affair/affairCopyFormDetail";
	}
}