/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.web.UploadController;
import com.thinkgem.jeesite.modules.affair.entity.AffairRightsProtection;
import com.thinkgem.jeesite.modules.affair.service.AffairRightsProtectionService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
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
import java.util.Map;

/**
 * 民警维权Controller
 * @author cecil.li
 * @version 2020-06-16
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairRightsProtection")
public class AffairRightsProtectionController extends BaseController {

	@Autowired
	private UploadController uploadController;

	@Autowired
	private AffairRightsProtectionService affairRightsProtectionService;
	
	@ModelAttribute
	public AffairRightsProtection get(@RequestParam(required=false) String id) {
		AffairRightsProtection entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairRightsProtectionService.get(id);
		}
		if (entity == null){
			entity = new AffairRightsProtection();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairRightsProtection:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairRightsProtection affairRightsProtection, HttpServletRequest request, HttpServletResponse response, Model model) {
		if("1".equals(UserUtils.getUser().getCompany().getId())){
			affairRightsProtection.setResponsibleUnitId(UserUtils.getUser().getCompany().getId());
		}else {
			affairRightsProtection.setResponsibleUnitId(UserUtils.getUser().getOffice().getId());
		}
		affairRightsProtection.setCreateBy(UserUtils.getUser());
		affairRightsProtection.setRange(UserUtils.getUser().getId());
		Page<AffairRightsProtection> page = affairRightsProtectionService.findPage(new Page<AffairRightsProtection>(request, response), affairRightsProtection);
		model.addAttribute("page", page);
		return "modules/affair/affairRightsProtectionList";
	}

	@RequiresPermissions("affair:affairRightsProtection:view")
	@RequestMapping(value = "form")
	public String form(AffairRightsProtection affairRightsProtection, Model model) {
		if (affairRightsProtection.getRange() != null && affairRightsProtection.getRange().length() >0){
			String[] problemCategoryArr = affairRightsProtection.getRange().split(",");
			affairRightsProtection.setProblemCategoryArr(problemCategoryArr);
		}
		model.addAttribute("affairRightsProtection", affairRightsProtection);
		return "modules/affair/affairRightsProtectionForm";
	}

	@RequiresPermissions("affair:affairRightsProtection:edit")
	@RequestMapping(value = "save")
	public String save(AffairRightsProtection affairRightsProtection, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairRightsProtection)){
			return form(affairRightsProtection, model);
		}
		if(null != affairRightsProtection.getProblemCategoryArr()){
			affairRightsProtection.setRange(StringUtils.join(affairRightsProtection.getProblemCategoryArr(),","));
		}
		affairRightsProtectionService.save(affairRightsProtection);
		addMessage(redirectAttributes, "保存民警维权成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairRightsProtectionForm";
	}
	
	@RequiresPermissions("affair:affairRightsProtection:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairRightsProtection affairRightsProtection, RedirectAttributes redirectAttributes) {
		affairRightsProtectionService.delete(affairRightsProtection);
		addMessage(redirectAttributes, "删除民警维权成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairRightsProtection/?repage";
	}

	/**
	 * 详情
	 * @param affairRightsProtection
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairRightsProtection:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairRightsProtection affairRightsProtection, Model model) {
		model.addAttribute("affairRightsProtection", affairRightsProtection);
		if(affairRightsProtection.getAnnex() != null && affairRightsProtection.getAnnex().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairRightsProtection.getAnnex());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairRightsProtectionFormDetail";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairRightsProtection:delete")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairRightsProtectionService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

}