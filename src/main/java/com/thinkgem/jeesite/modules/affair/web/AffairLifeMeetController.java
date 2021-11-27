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
import com.thinkgem.jeesite.modules.affair.entity.AffairLifeMeet;
import com.thinkgem.jeesite.modules.affair.service.AffairLifeMeetService;
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
 * 民主（组织）生活会Controller
 * @author eav.liu
 * @version 2019-11-09
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairLifeMeet")
public class AffairLifeMeetController extends BaseController {

	@Autowired
	private AffairLifeMeetService affairLifeMeetService;

	@Autowired
	private UploadController uploadController;
	
	@ModelAttribute
	public AffairLifeMeet get(@RequestParam(required=false) String id) {
		AffairLifeMeet entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairLifeMeetService.get(id);
		}
		if (entity == null){
			entity = new AffairLifeMeet();
		}
		return entity;
	}

	@RequiresPermissions("affair:affairLifeMeet:view")
    @RequestMapping(value = {""})
    public String index() {
        return "modules/affair/affairLifeMeetIndex";
    }

    @RequiresPermissions("affair:affairLifeMeet:view")
    @RequestMapping(value = {"list"})
	public String list(AffairLifeMeet affairLifeMeet, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("treeId", affairLifeMeet.getTreeId());
		model.addAttribute("parentId", request.getParameter("parentId"));
		affairLifeMeet.setHasAuth(false);
		Page<AffairLifeMeet> page = affairLifeMeetService.findPage(new Page<AffairLifeMeet>(request, response), affairLifeMeet); 
		model.addAttribute("page", page);
		return "modules/affair/affairLifeMeetList";
	}

	@RequiresPermissions("affair:affairLifeMeet:view")
	@RequestMapping(value = "form")
	public String form(AffairLifeMeet affairLifeMeet, Model model) {
		model.addAttribute("affairLifeMeet", affairLifeMeet);
		return "modules/affair/affairLifeMeetForm";
	}

	@RequiresPermissions("affair:affairLifeMeet:edit")
	@RequestMapping(value = "save")
	public String save(AffairLifeMeet affairLifeMeet, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairLifeMeet)){
			return form(affairLifeMeet, model);
		}
		affairLifeMeetService.save(affairLifeMeet);
		addMessage(redirectAttributes, "保存民主（组织）生活会成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairLifeMeetForm";
	}
	
	@RequiresPermissions("affair:affairLifeMeet:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairLifeMeet affairLifeMeet, RedirectAttributes redirectAttributes) {
		affairLifeMeetService.delete(affairLifeMeet);
		addMessage(redirectAttributes, "删除民主（组织）生活会成功");
        return "redirect:" + Global.getAdminPath() + "/affair/affairLifeMeet/list?repage&treeId="+affairLifeMeet.getTreeId();
	}

	/**
	 * 管理页面
	 * @param affairLifeMeet
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairLifeMeet:manage")
	@RequestMapping(value = {"manageList"})
	public String manageList(AffairLifeMeet affairLifeMeet, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("treeId", affairLifeMeet.getTreeId());
		affairLifeMeet.setHasAuth(true);
		Page<AffairLifeMeet> page = affairLifeMeetService.findPage(new Page<AffairLifeMeet>(request, response), affairLifeMeet);
		model.addAttribute("page", page);
		return "modules/affair/affairLifeMeetManage";
	}

	@RequiresPermissions("affair:affairLifeMeet:manage")
	@RequestMapping(value = {"shenHeDialog"})
	public String shenHeDialog() {
		return "modules/affair/affairLifeMeetDialog";
	}

	/**
	 * 审核保存
	 * @param affairLifeMeet
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairLifeMeet:manage")
	@RequestMapping(value = {"shenHeSave"})
	public Result shenHeSave(AffairLifeMeet affairLifeMeet) {
		affairLifeMeetService.shenHeSave(affairLifeMeet);
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("审核成功");
		return result;
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairLifeMeet:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairLifeMeetService.deleteByIds(ids);
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
	 * @param affairLifeMeet
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairLifeMeet:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairLifeMeet affairLifeMeet, Model model) {
		model.addAttribute("affairLifeMeet", affairLifeMeet);
		if (affairLifeMeet.getFilePath() != null && affairLifeMeet.getFilePath().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairLifeMeet.getFilePath());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairLifeMeetFormDetail";
	}
}