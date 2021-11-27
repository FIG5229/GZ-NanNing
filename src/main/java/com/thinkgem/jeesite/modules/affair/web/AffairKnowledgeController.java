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
import com.thinkgem.jeesite.modules.affair.entity.AffairKnowledge;
import com.thinkgem.jeesite.modules.affair.service.AffairKnowledgeService;
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
 * 党章党规及党建知识Controller
 * @author eav.liu
 * @version 2019-11-04
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairKnowledge")
public class AffairKnowledgeController extends BaseController {

	@Autowired
	private AffairKnowledgeService affairKnowledgeService;

	@Autowired
	private UploadController uploadController;
	
	@ModelAttribute
	public AffairKnowledge get(@RequestParam(required=false) String id) {
		AffairKnowledge entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairKnowledgeService.get(id);
		}
		if (entity == null){
			entity = new AffairKnowledge();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairKnowledge:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairKnowledge affairKnowledge, HttpServletRequest request, HttpServletResponse response, Model model) {
		affairKnowledge.setHasAuth(false);
		Page<AffairKnowledge> page = affairKnowledgeService.findPage(new Page<AffairKnowledge>(request, response), affairKnowledge); 
		model.addAttribute("page", page);
		return "modules/affair/affairKnowledgeList";
	}

	@RequiresPermissions("affair:affairKnowledge:view")
	@RequestMapping(value = "form")
	public String form(AffairKnowledge affairKnowledge, Model model) {
		model.addAttribute("affairKnowledge", affairKnowledge);
		return "modules/affair/affairKnowledgeForm";
	}

	@RequiresPermissions("affair:affairKnowledge:manage")
	@RequestMapping(value = "save")
	public String save(AffairKnowledge affairKnowledge, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairKnowledge)){
			return form(affairKnowledge, model);
		}
		affairKnowledgeService.save(affairKnowledge);
		addMessage(redirectAttributes, "保存党章党规及党建知识成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairKnowledgeForm";
	}
	
	@RequiresPermissions("affair:affairKnowledge:manage")
	@RequestMapping(value = "delete")
	public String delete(AffairKnowledge affairKnowledge, RedirectAttributes redirectAttributes) {
		affairKnowledgeService.delete(affairKnowledge);
		addMessage(redirectAttributes, "删除党章党规及党建知识成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairKnowledge/manageList?repage";
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairKnowledge:manage")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairKnowledgeService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	/**
	 * 管理页面
	 * @param affairKnowledge
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
    @RequiresPermissions("affair:affairKnowledge:manage")
    @RequestMapping(value = {"manageList"})
    public String manageList(AffairKnowledge affairKnowledge, HttpServletRequest request, HttpServletResponse response, Model model) {
        affairKnowledge.setHasAuth(true);
        Page<AffairKnowledge> page = affairKnowledgeService.findPage(new Page<AffairKnowledge>(request, response), affairKnowledge);
        model.addAttribute("page", page);
        return "modules/affair/affairKnowledgeManage";
    }

	/**
	 * 批量发布
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairKnowledge:manage")
	@RequestMapping(value = {"publishByIds"})
	public Result publishByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairKnowledgeService.publishByIds(ids);
			result.setSuccess(true);
			result.setMessage("发布成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要发布的内容");
		}
		return result;
	}

	/**
	 * 批量取消发布
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairKnowledge:manage")
	@RequestMapping(value = {"cancelByIds"})
	public Result cancelByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairKnowledgeService.cancelByIds(ids);
			result.setSuccess(true);
			result.setMessage("取消发布成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要取消发布的内容");
		}
		return result;
	}

	/**
	 * 查看页面的详情
	 * @param affairKnowledge
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairKnowledge:view")
	@RequestMapping(value = {"detail"})
	public String detail(AffairKnowledge affairKnowledge, Model model) {
		model.addAttribute("affairKnowledge", affairKnowledge);
		if (affairKnowledge.getFilePath() != null && affairKnowledge.getFilePath().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairKnowledge.getFilePath());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairKnowledgeDetail";
	}

	/**
	 * 管理页面form表单详情
	 * @param affairKnowledge
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairKnowledge:manage")
	@RequestMapping(value = {"formDetail"})
	public String formDetail(AffairKnowledge affairKnowledge, Model model) {
		model.addAttribute("affairKnowledge", affairKnowledge);
		if (affairKnowledge.getFilePath() != null && affairKnowledge.getFilePath().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairKnowledge.getFilePath());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairKnowledgeFormDetail";
	}
}