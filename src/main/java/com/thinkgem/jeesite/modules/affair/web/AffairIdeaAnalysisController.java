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
import com.thinkgem.jeesite.modules.affair.entity.AffairIdeaAnalysis;
import com.thinkgem.jeesite.modules.affair.service.AffairIdeaAnalysisService;
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
 * 党员队伍思想状况分析Controller
 * @author eav.liu
 * @version 2019-11-09
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairIdeaAnalysis")
public class AffairIdeaAnalysisController extends BaseController {

	@Autowired
	private AffairIdeaAnalysisService affairIdeaAnalysisService;

	@Autowired
	private UploadController uploadController;
	
	@ModelAttribute
	public AffairIdeaAnalysis get(@RequestParam(required=false) String id) {
		AffairIdeaAnalysis entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairIdeaAnalysisService.get(id);
		}
		if (entity == null){
			entity = new AffairIdeaAnalysis();
		}
		return entity;
	}

	@RequiresPermissions("affair:affairIdeaAnalysis:view")
	@RequestMapping(value = {""})
	public String index() {
		return "modules/affair/affairIdeaAnalysisIndex";
	}

	@RequiresPermissions("affair:affairIdeaAnalysis:view")
	@RequestMapping(value = {"list"})
	public String list(AffairIdeaAnalysis affairIdeaAnalysis, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("treeId", affairIdeaAnalysis.getTreeId());
		model.addAttribute("parentId", request.getParameter("parentId"));
		Page<AffairIdeaAnalysis> page = affairIdeaAnalysisService.findPage(new Page<AffairIdeaAnalysis>(request, response), affairIdeaAnalysis);
		model.addAttribute("page", page);
		return "modules/affair/affairIdeaAnalysisList";
	}

	@RequiresPermissions("affair:affairIdeaAnalysis:view")
	@RequestMapping(value = "form")
	public String form(AffairIdeaAnalysis affairIdeaAnalysis, Model model) {
		model.addAttribute("affairIdeaAnalysis", affairIdeaAnalysis);
		return "modules/affair/affairIdeaAnalysisForm";
	}

	@RequiresPermissions("affair:affairIdeaAnalysis:edit")
	@RequestMapping(value = "save")
	public String save(AffairIdeaAnalysis affairIdeaAnalysis, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairIdeaAnalysis)){
			return form(affairIdeaAnalysis, model);
		}
		affairIdeaAnalysis.setStatus("1");
		affairIdeaAnalysisService.save(affairIdeaAnalysis);
		addMessage(redirectAttributes, "保存党员队伍思想状况分析成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairIdeaAnalysisForm";
	}
	
	@RequiresPermissions("affair:affairIdeaAnalysis:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairIdeaAnalysis affairIdeaAnalysis, RedirectAttributes redirectAttributes) {
		affairIdeaAnalysisService.delete(affairIdeaAnalysis);
		addMessage(redirectAttributes, "删除党员队伍思想状况分析成功");
		return "redirect:" + Global.getAdminPath() + "/affair/affairIdeaAnalysis/list?repage&treeId="+affairIdeaAnalysis.getTreeId();
	}

	@ResponseBody
	@RequiresPermissions("affair:affairIdeaAnalysis:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairIdeaAnalysisService.deleteByIds(ids);
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
	 * @param affairIdeaAnalysis
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairIdeaAnalysis:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairIdeaAnalysis affairIdeaAnalysis, Model model) {
		model.addAttribute("affairIdeaAnalysis", affairIdeaAnalysis);
		if (affairIdeaAnalysis.getFilePath() != null && affairIdeaAnalysis.getFilePath().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairIdeaAnalysis.getFilePath());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairIdeaAnalysisFormDetail";
	}

	/**
	 * 上报
	 * @param affairIdeaAnalysis
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("affair:affairIdeaAnalysis:edit")
	@RequestMapping(value = "report")
	public String report(AffairIdeaAnalysis affairIdeaAnalysis, RedirectAttributes redirectAttributes) {
		affairIdeaAnalysis.setStatus("2");
		affairIdeaAnalysisService.save(affairIdeaAnalysis);
		addMessage(redirectAttributes, "上报成功");
		return "redirect:" + adminPath + "/affair/affairIdeaAnalysis/list?repage&treeId="+affairIdeaAnalysis.getTreeId();
	}

	@RequiresPermissions("affair:affairIdeaAnalysis:edit")
	@RequestMapping(value = {"shenHeDialog"})
	public String shenHeDialog() {
		return "modules/affair/affairIdeaAnalysisCheckDialog";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairIdeaAnalysis:edit")
	@RequestMapping(value = {"shenHeSave"})
	public Result shenHeSave(AffairIdeaAnalysis affairIdeaAnalysis) {
		affairIdeaAnalysisService.shenHeSave(affairIdeaAnalysis);
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("审核成功");
		return result;
	}
}