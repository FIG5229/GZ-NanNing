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
import com.thinkgem.jeesite.modules.affair.entity.AffairResearchArticle;
import com.thinkgem.jeesite.modules.affair.service.AffairResearchArticleService;
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
 * 党支部-专报简报/调研文章Controller
 * @author eav.liu
 * @version 2019-11-09
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairResearchArticle")
public class AffairResearchArticleController extends BaseController {

	@Autowired
	private AffairResearchArticleService affairResearchArticleService;

	@Autowired
	private UploadController uploadController;
	
	@ModelAttribute
	public AffairResearchArticle get(@RequestParam(required=false) String id) {
		AffairResearchArticle entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairResearchArticleService.get(id);
		}
		if (entity == null){
			entity = new AffairResearchArticle();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairResearchArticle:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairResearchArticle affairResearchArticle, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("treeId", affairResearchArticle.getTreeId());
		Page<AffairResearchArticle> page = affairResearchArticleService.findPage(new Page<AffairResearchArticle>(request, response), affairResearchArticle); 
		model.addAttribute("page", page);
		return "modules/affair/affairResearchArticleList";
	}

	@RequiresPermissions("affair:affairResearchArticle:view")
	@RequestMapping(value = "form")
	public String form(AffairResearchArticle affairResearchArticle, Model model) {
		model.addAttribute("affairResearchArticle", affairResearchArticle);
		return "modules/affair/affairResearchArticleForm";
	}

	@RequiresPermissions("affair:affairResearchArticle:edit")
	@RequestMapping(value = "save")
	public String save(AffairResearchArticle affairResearchArticle, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairResearchArticle)){
			return form(affairResearchArticle, model);
		}
		affairResearchArticleService.save(affairResearchArticle);
		if("1".equals(affairResearchArticle.getFlag())){
			addMessage(redirectAttributes, "保存专报简报成功");
		}else {
			addMessage(redirectAttributes, "保存调研文章成功");
		}
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairResearchArticleForm";
	}
	
	@RequiresPermissions("affair:affairResearchArticle:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairResearchArticle affairResearchArticle, RedirectAttributes redirectAttributes) {
		affairResearchArticleService.delete(affairResearchArticle);
		if("1".equals(affairResearchArticle.getFlag())){
			addMessage(redirectAttributes, "保存专报简报成功");
		}else {
			addMessage(redirectAttributes, "保存调研文章成功");
		}
		return "redirect:"+Global.getAdminPath()+"/affair/affairResearchArticle/?repage";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairResearchArticle:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairResearchArticleService.deleteByIds(ids);
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
	 * @param affairResearchArticle
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairResearchArticle:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairResearchArticle affairResearchArticle, Model model) {
		model.addAttribute("affairResearchArticle", affairResearchArticle);
		if (affairResearchArticle.getFilePath() != null && affairResearchArticle.getFilePath().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairResearchArticle.getFilePath());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairResearchArticleFormDetail";
	}
}