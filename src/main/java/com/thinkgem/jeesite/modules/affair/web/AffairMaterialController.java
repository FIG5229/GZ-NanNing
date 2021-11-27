/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.affair.entity.AffairMaterial;
import com.thinkgem.jeesite.modules.affair.service.AffairMaterialService;
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
 * 干部档案材料收集Controller
 * @author mason.xv
 * @version 2019-11-04
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairMaterial")
public class AffairMaterialController extends BaseController {

	@Autowired
	private AffairMaterialService affairMaterialService;
	
	@ModelAttribute
	public AffairMaterial get(@RequestParam(required=false) String id) {
		AffairMaterial entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairMaterialService.get(id);
		}
		if (entity == null){
			entity = new AffairMaterial();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairMaterial:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairMaterial affairMaterial, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairMaterial> page = affairMaterialService.findPage(new Page<AffairMaterial>(request, response), affairMaterial); 
		model.addAttribute("page", page);
		return "modules/affair/affairMaterialList";
	}

	@RequiresPermissions("affair:affairMaterial:view")
	@RequestMapping(value = "form")
	public String form(AffairMaterial affairMaterial, Model model) {
		model.addAttribute("affairMaterial", affairMaterial);
		return "modules/affair/affairMaterialForm";
	}

	@RequiresPermissions("affair:affairMaterial:edit")
	@RequestMapping(value = "save")
	public String save(AffairMaterial affairMaterial, Model model, RedirectAttributes redirectAttributes ,HttpServletRequest request) {
		if (!beanValidator(model, affairMaterial)){
			return form(affairMaterial, model);
		}
		affairMaterialService.save(affairMaterial);
		addMessage(redirectAttributes, "保存干部档案材料收集成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairMaterialForm";
	}
	
	@RequiresPermissions("affair:affairMaterial:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairMaterial affairMaterial, RedirectAttributes redirectAttributes) {
		affairMaterialService.delete(affairMaterial);
		addMessage(redirectAttributes, "删除干部档案材料收集成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairMaterial/?repage";
	}
	@ResponseBody
	@RequiresPermissions("affair:affairMaterial:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairMaterialService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	@RequiresPermissions("affair:affairMaterial:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairMaterial affairMaterial, Model model) {
		model.addAttribute("affairMaterial", affairMaterial);
		return "modules/affair/affairMaterialFormDetail";
	}
}