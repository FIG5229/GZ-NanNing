/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.modules.affair.entity.AffairCourseResource;
import com.thinkgem.jeesite.modules.affair.entity.AffairDocManagement;
import com.thinkgem.jeesite.modules.affair.service.AffairDocManagementService;
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
import com.thinkgem.jeesite.modules.affair.entity.AffairCourseDocument;
import com.thinkgem.jeesite.modules.affair.service.AffairCourseDocumentService;

import java.util.List;

/**
 * 参考文档Controller
 * @author alan.wu
 * @version 2020-08-05
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairCourseDocument")
public class AffairCourseDocumentController extends BaseController {

	@Autowired
	private AffairCourseDocumentService affairCourseDocumentService;

	private AffairDocManagementService affairDocManagementService;
	
	@ModelAttribute
	public AffairCourseDocument get(@RequestParam(required=false) String id) {
		AffairCourseDocument entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairCourseDocumentService.get(id);
		}
		if (entity == null){
			entity = new AffairCourseDocument();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairCourseResource:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairCourseDocument affairCourseDocument, HttpServletRequest request, HttpServletResponse response, Model model) {

		Page<AffairCourseDocument> page = affairCourseDocumentService.findPage(new Page<AffairCourseDocument>(request, response), affairCourseDocument);
		model.addAttribute("page", page);
		return "modules/affair/affairCourseDocumentForm";
	}

	@RequiresPermissions("affair:affairCourseResource:view")
	@RequestMapping(value = "form")
	public String form(AffairCourseDocument affairCourseDocument, Model model) {


		model.addAttribute("affairCourseDocument", affairCourseDocument);
		return "modules/affair/affairCourseDocumentForm";
	}

	@RequiresPermissions("affair:affairCourseResource:edit")
	@RequestMapping(value = "save")
	public String save(AffairCourseDocument affairCourseDocument, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, affairCourseDocument)){
			return form(affairCourseDocument, model);
		}
		affairCourseDocumentService.save(affairCourseDocument);
		addMessage(redirectAttributes, "保存参考文档成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairCourseDocument/?repage";
	}
	
	@RequiresPermissions("affair:affairCourseResource:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairCourseDocument affairCourseDocument, RedirectAttributes redirectAttributes) {
		affairCourseDocumentService.delete(affairCourseDocument);
		addMessage(redirectAttributes, "删除参考文档成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairCourseDocument/?repage";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairCourseResource:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairCourseDocumentService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}
}