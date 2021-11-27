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
import com.thinkgem.jeesite.modules.affair.entity.AffairDocClassify;
import com.thinkgem.jeesite.modules.affair.entity.AffairDocManagement;
import com.thinkgem.jeesite.modules.affair.service.AffairDocClassifyService;
import com.thinkgem.jeesite.modules.affair.service.AffairDocManagementService;
import com.thinkgem.jeesite.modules.cms.entity.Article;
import com.thinkgem.jeesite.modules.cms.entity.ArticleData;
import com.thinkgem.jeesite.modules.cms.entity.Category;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.ibatis.annotations.Param;
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
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 文档管理Controller
 * @author kevin.jia
 * @version 2020-07-30
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairDocManagement")
public class AffairDocManagementController extends BaseController {

	@Autowired
	private AffairDocManagementService affairDocManagementService;

	@Autowired
	private AffairDocClassifyService affairDocClassifyService;

	@Autowired
	private UploadController uploadController;

	/*@Autowired
	private DictDao dictDao;*/

	@ModelAttribute
	public AffairDocManagement get(@RequestParam(required=false) String id) {
		AffairDocManagement entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairDocManagementService.get(id);
		}
		if (entity == null){
			entity = new AffairDocManagement();
		}
		return entity;
	}

	@RequiresPermissions("affair:affairDocManagement:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairDocManagement affairDocManagement, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairDocManagement> page = affairDocManagementService.findPage(new Page<AffairDocManagement>(request, response), affairDocManagement);
		/*//获取创建人的name并传到页面
		List<AffairDocManagement> list = page.getList();
		List<User> userList = new ArrayList<>();
		for(AffairDocManagement docManagement : list){
			User user = UserUtils.get(docManagement.getCreateBy().getId());
			userList.add(user);
		}*/
		model.addAttribute("page", page);
		//model.addAttribute("userList",userList);
		return "modules/affair/affairDocManagementList";
	}

	@RequiresPermissions("affair:affairDocManagement:view")
	@RequestMapping(value = "form")
	public String form(AffairDocManagement affairDocManagement, Model model) {
		List<AffairDocClassify> affairDocClassifyList = affairDocClassifyService.findTreeData();
		model.addAttribute("affairDocManagement", affairDocManagement);
		model.addAttribute("affairDocClassifyList", affairDocClassifyList);
		return "modules/affair/affairDocManagementForm";
	}

	/*
	* 查看文档详情
	* */
	@RequiresPermissions("affair:affairDocManagement:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairDocManagement affairDocManagement, Model model,@Param("type") String type) {
		List<AffairDocClassify> affairDocClassifyList = affairDocClassifyService.findTreeData();
		if(affairDocManagement.getAppendfile() != null && affairDocManagement.getAppendfile().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairDocManagement.getAppendfile());
			model.addAttribute("filePathList", filePathList);
		}
		model.addAttribute("affairDocManagement", affairDocManagement);
		model.addAttribute("affairDocClassifyList", affairDocClassifyList);
		model.addAttribute("myType",type);
		return "modules/affair/affairDocManagementFormDetail";
	}

	@RequiresPermissions("affair:affairDocManagement:edit")
	@RequestMapping(value = "save")
	public String save(AffairDocManagement affairDocManagement, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairDocManagement)){
			return form(affairDocManagement, model);
		}
		affairDocManagementService.save(affairDocManagement);
		addMessage(redirectAttributes, "保存文档管理成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairDocManagementForm";

	}

	@RequiresPermissions("affair:affairDocManagement:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairDocManagement affairDocManagement, RedirectAttributes redirectAttributes) {
		affairDocManagementService.delete(affairDocManagement);
		addMessage(redirectAttributes, "删除文档管理成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairDocManagement/?repage";
	}

	/*
	* 批量删除
	*
	* */
	@ResponseBody
	@RequiresPermissions("affair:affairDocManagement:edit")
	@RequestMapping(value = "deleteByIds")
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairDocManagementService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;

	}
	/*
	 * 发布文档
	 * @param affairDocManagement
	 * @param model
	 * @param releaseStatus
	 * @return
	 * */
	@RequiresPermissions("affair:affairDocManagement:view")
	@RequestMapping(value = "propelling")
	public String propelling(AffairDocManagement affairDocManagement, Model model) {
		//栏目
		Category category = new Category();
		category.setId(affairDocManagement.getId());
		//文章副表
		ArticleData articleData = new ArticleData();
		//内容-->文档主要内容
		articleData.setContent(affairDocManagement.getMainContent());
		//
		Article article = new Article();
		article.setCategory(category);
		//设置文章副表
		article.setArticleData(articleData);
		//标题-->文档名称
		article.setTitle(affairDocManagement.getDocName());
		//创建时间-->文档创建时间
		article.setCreateDate(affairDocManagement.getCreateDate());
		//附件-->附件
		article.setAppendfile("/politics"+affairDocManagement.getAppendfile());
		model.addAttribute("article", article);
		model.addAttribute("docManagementId",affairDocManagement.getId());
		return "modules/affair/affairDocManagementPropelling";
	}

	/*
	* 更新文档的发布状态字段
	* */
	@RequestMapping(value = "updateDocManageRS")
	@ResponseBody
	public Result updateDocManageRS(AffairDocManagement affairDocManagement,Model model,String releaseStatus){
		Result result = new Result();
		affairDocManagement.setReleaseStatus(releaseStatus);
		affairDocManagementService.save(affairDocManagement);
		result.setSuccess(true);
		return result;
	}

	/**
	 * 批量提交
	 * @param request
	 * @return
	 */
	@RequestMapping(value = {"submitByIds"})
	public String submitByIds(HttpServletRequest request) {
		User user = UserUtils.getUser();
		//接受前台传过来的ids
		String idsStr = request.getParameter("ids");
		//字符串转数组
		String[] idsArray = idsStr.split(",");
		//数组转集合
		List<String> ids = new ArrayList<String>();
		String oneCheckId = request.getParameter("oneCheckId");
		/*String oneCheckMan = dictDao.findLabelByValue(oneCheckId);*/
		String oneCheckMan = request.getParameter("oneCheckMan");
		Collections.addAll(ids,idsArray);
		List <AffairDocManagement> list = affairDocManagementService.findByIds(ids);
		for (AffairDocManagement affairDocManagement: list){
			affairDocManagement.setCheckType("2");
			affairDocManagement.setOneCheckMan(oneCheckMan);
			affairDocManagement.setOneCheckId(oneCheckId);
			affairDocManagement.setSubmitId(user.getId());
			affairDocManagement.setSubmitMan(user.getName());
			affairDocManagementService.save(affairDocManagement);
		}
		return "redirect:"+Global.getAdminPath()+"/affair/affairDocManagement/list";
	}

	/*
	 * 审核页面
	 * */
	@RequiresPermissions("affair:affairDocManagement:shenhe")
	@RequestMapping(value = {"shenHeDialog"})
	public String shenHeDialog() {
		return "modules/affair/affairDocManagementCheckDialog";
	}

	/*
	 * 审核
	 * @param affairDocManagement
	 * @param request
	 * @return
	 * */
	@ResponseBody
	@RequiresPermissions("affair:affairDocManagement:shenhe")
	@RequestMapping(value = "shenHe")
	public Result shenHe(AffairDocManagement affairDocManagement, HttpServletRequest request) {
		/*if (!affairDocManagement.getTwoCheckId().isEmpty()) {
			//如果审核方式为转为上一级,则通过twoCheckId,为twoCheckMan字段赋值
			String twoCheckMan = dictDao.findLabelByValue(affairDocManagement.getTwoCheckId());
			affairDocManagement.setTwoCheckMan(twoCheckMan);
		}*/
		affairDocManagementService.save(affairDocManagement);
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("审核成功");
		return result;
	}

	@RequiresPermissions("affair:affairCourseResource:view")
	@RequestMapping(value = "selectAll")
	public String selectAll(AffairDocManagement affairDocManagement, Model model, String classId, HttpSession session){

		session.setAttribute("classId",classId);

		List<AffairDocManagement> list = affairDocManagementService.selectAll();
		model.addAttribute("list",list);

		return "modules/affair/affairCourseDocumentForm";
	}
}