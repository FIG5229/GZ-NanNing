/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.UploadController;
import com.thinkgem.jeesite.modules.affair.entity.AffairClassManage;
import com.thinkgem.jeesite.modules.affair.service.AffairClassManageService;
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
import com.thinkgem.jeesite.modules.affair.entity.AffairSitemAnagement;
import com.thinkgem.jeesite.modules.affair.service.AffairSitemAnagementService;

import java.util.List;
import java.util.Map;

/**
 * 场地管理Controller
 * @author tom.Fu
 * @version 2020-07-30
 */
@Controller
	@RequestMapping(value = "${adminPath}/affair/affairSitemAnagement")
public class AffairSitemAnagementController extends BaseController {

	@Autowired
	private AffairSitemAnagementService affairSitemAnagementService;
	@Autowired
	private UploadController uploadController;
	@Autowired
	private AffairClassManageService affairClassManageService;

	@ModelAttribute
	public AffairSitemAnagement get(@RequestParam(required=false) String id) {
		AffairSitemAnagement entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairSitemAnagementService.get(id);
		}
		if (entity == null){
			entity = new AffairSitemAnagement();
		}
		return entity;
	}




    @RequiresPermissions("affair:affairSitemAnagement:view")
	@RequestMapping(value = "form")
	public String form(AffairSitemAnagement affairSitemAnagement, Model model) {
		model.addAttribute("affairSitemAnagement", affairSitemAnagement);
		return "modules/affair/affairSitemAnagementForm";
	}

	@RequiresPermissions("affair:affairSitemAnagement:edit")
	@RequestMapping(value = "save")
	public String save(AffairSitemAnagement affairSitemAnagement, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairSitemAnagement)){
			return form(affairSitemAnagement, model);
		}
		affairSitemAnagementService.save(affairSitemAnagement);
		addMessage(redirectAttributes, "保存场地管理成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairSitemAnagementForm";
	}
	
	@RequiresPermissions("affair:affairSitemAnagement:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairSitemAnagement affairSitemAnagement, RedirectAttributes redirectAttributes) {
		affairSitemAnagementService.delete(affairSitemAnagement);
		addMessage(redirectAttributes, "删除场地管理成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairSitemAnagement/?repage";
	}

	/**
	 * 查看详情
	 * @param affairSitemAnagement
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairSitemAnagement:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairSitemAnagement affairSitemAnagement, Model model) {
		model.addAttribute("affairSitemAnagement", affairSitemAnagement);
		if(affairSitemAnagement.getAccessoryFile() != null && affairSitemAnagement.getAccessoryFile().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairSitemAnagement.getAccessoryFile());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairSitemAnagementFormDetail";
	}
	@ResponseBody
	@RequiresPermissions("affair:affairSitemAnagement:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairSitemAnagementService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	@RequiresPermissions("affair:affairSitemAnagement:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairSitemAnagement affairSitemAnagement, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairSitemAnagement> page = affairSitemAnagementService.findPage(new Page<AffairSitemAnagement>(request, response), affairSitemAnagement);

		model.addAttribute("page", page);
		return "modules/affair/affairSitemAnagementList";
	}

	/**
	 * 查询所有数据内容
	 * */
	@RequiresPermissions("affair:affairSitemAnagement:edit")
	@RequestMapping("/findAllaffairUsageRecordlist")
	public String findAllaffairUsageRecordList(AffairClassManage affairClassManage, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairClassManage> UsageRecordPage = affairClassManageService.findPage(new Page<AffairClassManage>(request, response), affairClassManage);

		model.addAttribute("UsageRecordPage", UsageRecordPage);
		return "modules/affair/affairUsageRecordList";
	}
	/**
	 * 查看详情
	 * @param
	 * @param model id
	 * @return
	 */
	@RequiresPermissions("affair:affairClassManage:view")
	@RequestMapping("/formDetailTwo")
	public String formDetailTwo(Model model,@RequestParam(value = "id") String id) {

        AffairClassManage affairClassManage = affairSitemAnagementService.findOne(id);

        model.addAttribute("affairClassManage", affairClassManage);

        if(affairClassManage.getUpdateIdNo() != null && affairClassManage.getUpdateIdNo().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairClassManage.getUpdateIdNo());

			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairUsageRecordFormDetail";
	}


}