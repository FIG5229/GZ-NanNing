/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.UploadController;
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
import com.thinkgem.jeesite.modules.affair.entity.AffairSystemConstruction;
import com.thinkgem.jeesite.modules.affair.service.AffairSystemConstructionService;

import java.util.List;
import java.util.Map;

/**
 * 制度建设Controller
 * @author daniel.liu
 * @version 2020-07-07
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairSystemConstruction")
public class AffairSystemConstructionController extends BaseController {

	@Autowired
	private AffairSystemConstructionService affairSystemConstructionService;

	@Autowired
	private UploadController uploadController;

	@ModelAttribute
	public AffairSystemConstruction get(@RequestParam(required=false) String id) {
		AffairSystemConstruction entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairSystemConstructionService.get(id);
		}
		if (entity == null){
			entity = new AffairSystemConstruction();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairSystemConstruction:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairSystemConstruction affairSystemConstruction, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("treeId", affairSystemConstruction.getTreeId());
		model.addAttribute("parentId", request.getParameter("parentId"));
		Page<AffairSystemConstruction> page = affairSystemConstructionService.findPage(new Page<AffairSystemConstruction>(request, response), affairSystemConstruction); 
		model.addAttribute("page", page);
		return "modules/affair/affairSystemConstructionList";
	}

	@RequiresPermissions("affair:affairSystemConstruction:view")
	@RequestMapping(value = "form")
	public String form(AffairSystemConstruction affairSystemConstruction, Model model) {
		model.addAttribute("affairSystemConstruction", affairSystemConstruction);
		return "modules/affair/affairSystemConstructionForm";
	}
	@RequiresPermissions("affair:affairSystemConstruction:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairSystemConstruction affairSystemConstruction, Model model) {
		model.addAttribute("affairSystemConstruction", affairSystemConstruction);
		if(affairSystemConstruction.getFilePath() != null && affairSystemConstruction.getFilePath().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairSystemConstruction.getFilePath());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairSystemConstructionFormDetail";
	}

	@RequiresPermissions("affair:affairSystemConstruction:edit")
	@RequestMapping(value = "save")
	public String save(AffairSystemConstruction affairSystemConstruction, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairSystemConstruction)){
			return form(affairSystemConstruction, model);
		}
		/*审核状态为空时 默认设置为 未上报*/
		if (StringUtils.isBlank(affairSystemConstruction.getExamineStatus())){
			affairSystemConstruction.setExamineStatus("1");
		}
		affairSystemConstructionService.save(affairSystemConstruction);
		addMessage(redirectAttributes, "保存制度建设成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairSystemConstructionForm";
	}
	
	@RequiresPermissions("affair:affairSystemConstruction:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairSystemConstruction affairSystemConstruction, RedirectAttributes redirectAttributes) {
		affairSystemConstructionService.delete(affairSystemConstruction);
		addMessage(redirectAttributes, "删除制度建设成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairSystemConstruction/?repage&treeId="+affairSystemConstruction.getTreeId();
	}

	@ResponseBody
	@RequiresPermissions("affair:affairSystemConstruction:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairSystemConstructionService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}
	/**
	 * 审核页面
	 * @return
	 */
	@RequestMapping("examineView")
	public String examineView(){
		return "modules/affair/affairSystemConstructionExamineDialog";
	}

	/*审核*/
	@RequiresPermissions("affair:affairSystemConstruction:edit")
	@RequestMapping(value = "examine")
	public String examine(AffairSystemConstruction affairSystemConstruction, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairSystemConstruction)){
			return form(affairSystemConstruction, model);
		}
		affairSystemConstructionService.save(affairSystemConstruction);
		addMessage(redirectAttributes, "审核制度建设成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairSystemConstructionForm";
	}

	@RequiresPermissions("affair:affairSystemConstruction:edit")
	@RequestMapping(value = "report")
	public String report(AffairSystemConstruction affairSystemConstruction, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairSystemConstruction)){
			return form(affairSystemConstruction, model);
		}
		affairSystemConstruction.setExamineStatus("2");
		affairSystemConstructionService.save(affairSystemConstruction);
		addMessage(redirectAttributes, "上报制度建设成功");
		request.setAttribute("saveResult","sucess");
		return "redirect:"+Global.getAdminPath()+"/affair/affairSystemConstruction/list?repage&treeId="+affairSystemConstruction.getTreeId();
	}

}