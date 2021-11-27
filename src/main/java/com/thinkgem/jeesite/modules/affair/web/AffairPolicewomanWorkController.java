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
import com.thinkgem.jeesite.modules.affair.entity.AffairPolicewomanWork;
import com.thinkgem.jeesite.modules.affair.service.AffairPolicewomanWorkService;
import com.thinkgem.jeesite.modules.cms.entity.Article;
import com.thinkgem.jeesite.modules.cms.entity.ArticleData;
import com.thinkgem.jeesite.modules.cms.entity.Category;
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
 * 女警工作管理Controller
 * @author eav.liu
 * @version 2020-03-26
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairPolicewomanWork")
public class AffairPolicewomanWorkController extends BaseController {

	@Autowired
	private AffairPolicewomanWorkService affairPolicewomanWorkService;

	@Autowired
	private UploadController uploadController;
	
	@ModelAttribute
	public AffairPolicewomanWork get(@RequestParam(required=false) String id) {
		AffairPolicewomanWork entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairPolicewomanWorkService.get(id);
		}
		if (entity == null){
			entity = new AffairPolicewomanWork();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairPolicewomanWork:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairPolicewomanWork affairPolicewomanWork, HttpServletRequest request, HttpServletResponse response, Model model) {
		affairPolicewomanWork.setHasAuth(false);
		Page<AffairPolicewomanWork> page = affairPolicewomanWorkService.findPage(new Page<AffairPolicewomanWork>(request, response), affairPolicewomanWork); 
		model.addAttribute("page", page);
		return "modules/affair/affairPolicewomanWorkList";
	}

	@RequiresPermissions("affair:affairPolicewomanWork:view")
	@RequestMapping(value = "form")
	public String form(AffairPolicewomanWork affairPolicewomanWork, Model model) {
		model.addAttribute("affairPolicewomanWork", affairPolicewomanWork);
		return "modules/affair/affairPolicewomanWorkForm";
	}

	@RequiresPermissions("affair:affairPolicewomanWork:manage")
	@RequestMapping(value = "save")
	public String save(AffairPolicewomanWork affairPolicewomanWork, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairPolicewomanWork)){
			return form(affairPolicewomanWork, model);
		}
		affairPolicewomanWorkService.save(affairPolicewomanWork);
		addMessage(redirectAttributes, "保存女警工作成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairPolicewomanWorkForm";
	}
	
	@RequiresPermissions("affair:affairPolicewomanWork:manage")
	@RequestMapping(value = "delete")
	public String delete(AffairPolicewomanWork affairPolicewomanWork, RedirectAttributes redirectAttributes) {
		affairPolicewomanWorkService.delete(affairPolicewomanWork);
		addMessage(redirectAttributes, "删除女警工作成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairPolicewomanWork/manageList?repage";
	}

	/**
	 * 管理页面
	 * @param affairPolicewomanWork
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairPolicewomanWork:manage")
	@RequestMapping(value = {"manageList"})
	public String manageList(AffairPolicewomanWork affairPolicewomanWork, HttpServletRequest request, HttpServletResponse response, Model model) {
		affairPolicewomanWork.setHasAuth(true);
		Page<AffairPolicewomanWork> page = affairPolicewomanWorkService.findPage(new Page<AffairPolicewomanWork>(request, response), affairPolicewomanWork);
		model.addAttribute("page", page);
		return "modules/affair/affairPolicewomanWorkManage";
	}
	/**
	 * 批量发布
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairPolicewomanWork:manage")
	@RequestMapping(value = {"publishByIds"})
	public Result publishByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairPolicewomanWorkService.publishByIds(ids);
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
	@RequiresPermissions("affair:affairPolicewomanWork:manage")
	@RequestMapping(value = {"cancelByIds"})
	public Result cancelByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairPolicewomanWorkService.cancelByIds(ids);
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
	 * @param affairPolicewomanWork
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairPolicewomanWork:view")
	@RequestMapping(value = {"detail"})
	public String detail(AffairPolicewomanWork affairPolicewomanWork, Model model) {
		model.addAttribute("affairPolicewomanWork", affairPolicewomanWork);
		if (affairPolicewomanWork.getFilePath() != null && affairPolicewomanWork.getFilePath().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairPolicewomanWork.getFilePath());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairPolicewomanWorkDetail";
	}

	/**
	 * 管理页面form表单详情
	 * @param affairPolicewomanWork
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairPolicewomanWork:manage")
	@RequestMapping(value = {"formDetail"})
	public String formDetail(AffairPolicewomanWork affairPolicewomanWork, Model model) {
		model.addAttribute("affairPolicewomanWork", affairPolicewomanWork);
		if (affairPolicewomanWork.getFilePath() != null && affairPolicewomanWork.getFilePath().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairPolicewomanWork.getFilePath());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairPolicewomanWorkFormDetail";
	}
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("affair:affairPolicewomanWork:manage")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairPolicewomanWorkService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	/*
	 * 推送页面
	 * @param affairPolicewomanWork
	 * @param model
	 * @return
	 * */
	@RequiresPermissions("affair:affairPolicewomanWork:manage")
	@RequestMapping(value = "propelling")
	public String propelling(AffairPolicewomanWork affairPolicewomanWork, Model model) {
		//栏目
		Category category = new Category();
		category.setId(affairPolicewomanWork.getId());
		//文章副表
		ArticleData articleData = new ArticleData();
		//内容-->作品内容
		articleData.setContent(affairPolicewomanWork.getContent());
		//创建文章Entity
		Article article = new Article();
		article.setCategory(category);
		//设置文章副表
		article.setArticleData(articleData);
		//标题
		article.setTitle(affairPolicewomanWork.getTitle());
		//创建时间
		article.setCreateDate(affairPolicewomanWork.getCreateDate());
		//附件
		article.setAppendfile("/politics"+affairPolicewomanWork.getFilePath());
		model.addAttribute("article", article);
		return "modules/affair/affairPolicewomanWorkPropelling";
	}
}