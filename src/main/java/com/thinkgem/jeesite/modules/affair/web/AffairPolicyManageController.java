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
import com.thinkgem.jeesite.modules.affair.entity.AffairPolicyManage;
import com.thinkgem.jeesite.modules.affair.service.AffairPolicyManageService;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
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
 * 政策管理Controller
 * @author cecil.li
 * @version 2020-05-29
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairPolicyManage")
public class AffairPolicyManageController extends BaseController {

	@Autowired
	private OfficeService officeService;

	@Autowired
	private AffairPolicyManageService affairPolicyManageService;

	@Autowired
	private UploadController uploadController;
	
	@ModelAttribute
	public AffairPolicyManage get(@RequestParam(required=false) String id) {
		AffairPolicyManage entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairPolicyManageService.get(id);
		}
		if (entity == null){
			entity = new AffairPolicyManage();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairPolicyManage:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairPolicyManage affairPolicyManage, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairPolicyManage> page = affairPolicyManageService.findPage(new Page<AffairPolicyManage>(request, response), affairPolicyManage); 
		model.addAttribute("page", page);
		return "modules/affair/affairPolicyManageList";
	}

	@RequiresPermissions("affair:affairPolicyManage:view")
	@RequestMapping(value = "form")
	public String form(AffairPolicyManage affairPolicyManage, Model model) {
		model.addAttribute("affairPolicyManage", affairPolicyManage);
		return "modules/affair/affairPolicyManageForm";
	}

	@RequiresPermissions("affair:affairPolicyManage:edit")
	@RequestMapping(value = "save")
	public String save(AffairPolicyManage affairPolicyManage, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairPolicyManage)){
			return form(affairPolicyManage, model);
		}
		affairPolicyManageService.save(affairPolicyManage);
		addMessage(redirectAttributes, "保存政策管理成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairPolicyManageForm";
	}
	
	@RequiresPermissions("affair:affairPolicyManage:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairPolicyManage affairPolicyManage, RedirectAttributes redirectAttributes) {
		affairPolicyManageService.delete(affairPolicyManage);
		addMessage(redirectAttributes, "删除政策管理成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairPolicyManage/?repage";
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairPolicyManage:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairPolicyManageService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	/**
	 * 详情
	 * @param affairPolicyManage
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairPolicyManage:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairPolicyManage affairPolicyManage, Model model) {
		model.addAttribute("affairPolicyManage", affairPolicyManage);
		if(affairPolicyManage.getAnnex() != null && affairPolicyManage.getAnnex().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairPolicyManage.getAnnex());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairPolicyManageFormDetail";
	}

}