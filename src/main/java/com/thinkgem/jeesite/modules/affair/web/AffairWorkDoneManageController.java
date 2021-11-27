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
import com.thinkgem.jeesite.modules.affair.entity.AffairWorkDoneManage;
import com.thinkgem.jeesite.modules.affair.service.AffairWorkDoneManageService;
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
 * 工作总结管理Controller
 * @author Alan.wu
 * @version 2020-06-12
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairWorkDoneManage")
public class AffairWorkDoneManageController extends BaseController {

	@Autowired
	private AffairWorkDoneManageService affairWorkDoneManageService;

	@Autowired
	private UploadController uploadController;

	@ModelAttribute
	public AffairWorkDoneManage get(@RequestParam(required=false) String id) {
		AffairWorkDoneManage entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairWorkDoneManageService.get(id);
		}
		if (entity == null){
			entity = new AffairWorkDoneManage();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairWorkDoneManage:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairWorkDoneManage affairWorkDoneManage, HttpServletRequest request, HttpServletResponse response, Model model) {

		model.addAttribute("treeId", affairWorkDoneManage.getTreeId());
		model.addAttribute("parentId", request.getParameter("parentId"));

		Page<AffairWorkDoneManage> page = affairWorkDoneManageService.findPage(new Page<AffairWorkDoneManage>(request, response), affairWorkDoneManage);
		model.addAttribute("page", page);
		return "modules/affair/affairWorkDoneManageList";
	}

	@RequiresPermissions("affair:affairWorkDoneManage:view")
	@RequestMapping(value = "form")
	public String form(AffairWorkDoneManage affairWorkDoneManage, Model model) {
		model.addAttribute("affairWorkDoneManage", affairWorkDoneManage);
		return "modules/affair/affairWorkDoneManageForm";
	}

	@RequiresPermissions("affair:affairWorkDoneManage:edit")
	@RequestMapping(value = "save")
	public String save(AffairWorkDoneManage affairWorkDoneManage, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, affairWorkDoneManage)){
			return form(affairWorkDoneManage, model);
		}
		affairWorkDoneManage.setStatus("1");
		affairWorkDoneManageService.save(affairWorkDoneManage);
		addMessage(redirectAttributes, "保存工作总结管理成功");
		model.addAttribute("saveResult","sucess");
		return "modules/affair/affairWorkDoneManageForm";
	}
	
	@RequiresPermissions("affair:affairWorkDoneManage:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairWorkDoneManage affairWorkDoneManage, RedirectAttributes redirectAttributes) {
		affairWorkDoneManageService.delete(affairWorkDoneManage);
		addMessage(redirectAttributes, "删除工作总结管理成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairWorkDoneManage/list?repage&treeId="+affairWorkDoneManage.getTreeId();
	}


	/**
	 * 详情
	 *
	 * @param affairWorkDoneManage
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairWorkDoneManage:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairWorkDoneManage affairWorkDoneManage, Model model) {
		model.addAttribute("affairWorkDoneManage", affairWorkDoneManage);
		if (affairWorkDoneManage.getAdjunct() != null && affairWorkDoneManage.getAdjunct().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairWorkDoneManage.getAdjunct());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairWorkDoneManageFormDetail";
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairWorkDoneManage:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairWorkDoneManageService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	/**
	 * 上报
	 * @param affairWorkDoneManage
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("affair:affairWorkDoneManage:edit")
	@RequestMapping(value = "report")
	public String report(AffairWorkDoneManage affairWorkDoneManage, RedirectAttributes redirectAttributes) {
		affairWorkDoneManage.setStatus("2");
		affairWorkDoneManageService.save(affairWorkDoneManage);
		addMessage(redirectAttributes, "上报成功");
		return "redirect:" + adminPath + "/affair/affairWorkDoneManage/list?repage&treeId="+affairWorkDoneManage.getTreeId();
	}

	@RequiresPermissions("affair:affairWorkDoneManage:edit")
	@RequestMapping(value = {"shenHeDialog"})
	public String shenHeDialog() {
		return "modules/affair/affairWorkDoneManageCheckDialog";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairWorkDoneManage:edit")
	@RequestMapping(value = {"shenHeSave"})
	public Result shenHeSave(AffairWorkDoneManage affairWorkDoneManage) {
		affairWorkDoneManageService.shenHeSave(affairWorkDoneManage);
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("审核成功");
		return result;
	}

}