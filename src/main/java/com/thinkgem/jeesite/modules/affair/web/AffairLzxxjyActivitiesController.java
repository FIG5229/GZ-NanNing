/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.web.UploadController;
import com.thinkgem.jeesite.modules.affair.entity.AffairLzxxjyActivities;
import com.thinkgem.jeesite.modules.affair.service.AffairLzxxjyActivitiesService;
import com.thinkgem.jeesite.modules.cms.entity.Article;
import com.thinkgem.jeesite.modules.cms.entity.ArticleData;
import com.thinkgem.jeesite.modules.cms.entity.Category;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 廉政学习教育活动Controller
 * @author cecil.li
 * @version 2019-11-08
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairLzxxjyActivities")
public class AffairLzxxjyActivitiesController extends BaseController {

	@Autowired
	private AffairLzxxjyActivitiesService affairLzxxjyActivitiesService;
	@Autowired
	private UploadController uploadController;
	
	@ModelAttribute
	public AffairLzxxjyActivities get(@RequestParam(required=false) String id) {
		AffairLzxxjyActivities entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairLzxxjyActivitiesService.get(id);
		}
		if (entity == null){
			entity = new AffairLzxxjyActivities();
		}
		return entity;
	}
	
//	@RequiresPermissions("affair:affairLzxxjyActivities:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairLzxxjyActivities affairLzxxjyActivities, HttpServletRequest request, HttpServletResponse response, Model model) {
		/*if ("1".equals(UserUtils.getUser().getOffice().getId())){
			affairLzxxjyActivities.setQxUnitId(UserUtils.getUser().getCompany().getId());
		}else if ("31".equals(UserUtils.getUser().getOffice().getId()) || "146".equals(UserUtils.getUser().getOffice().getId()) || "266".equals(UserUtils.getUser().getOffice().getId())){
			affairLzxxjyActivities.setQxUnitId("1");
		}else {
			affairLzxxjyActivities.setQxUnitId(UserUtils.getUser().getOffice().getId());
		}*/
		affairLzxxjyActivities.setHasAuth(false);
		if ("1".equals(UserUtils.getUser().getCompany().getId())){
			affairLzxxjyActivities.setQxUnitId(UserUtils.getUser().getCompany().getId());
		}else if ("31".equals(UserUtils.getUser().getOffice().getId()) || "146".equals(UserUtils.getUser().getOffice().getId()) || "266".equals(UserUtils.getUser().getOffice().getId())){
			affairLzxxjyActivities.setQxUnitId("1");
		}else if ("34".equals(UserUtils.getUser().getCompany().getId())){
			affairLzxxjyActivities.setQxUnitId("34");
		}else if ("95".equals(UserUtils.getUser().getCompany().getId())){
			affairLzxxjyActivities.setQxUnitId("95");
		}else if ("156".equals(UserUtils.getUser().getCompany().getId())){
			affairLzxxjyActivities.setQxUnitId("156");
		}else {
			affairLzxxjyActivities.setQxUnitId(UserUtils.getUser().getOffice().getId());
		}
		affairLzxxjyActivities.setCreateBy(UserUtils.getUser());
		String reason = request.getParameter("reason");//2 全部 3 其他年份
		model.addAttribute("reason",reason);
		if("3".equals(reason)){
			affairLzxxjyActivities.setStartDate(null);
			affairLzxxjyActivities.setEndDate(null);
		}else if("2".equals(reason)){
			//全部
			if(affairLzxxjyActivities.getStartDate()==null && affairLzxxjyActivities.getEndDate() == null){
				String year = DateUtils.getYear();//获取当前年
				affairLzxxjyActivities.setOtherYear(year);
			}else{
				affairLzxxjyActivities.setOtherYear(null);
			}
		}else{
			//默认显示当前年度数据
			String year =DateUtils.getYear();//获取当前年
			affairLzxxjyActivities.setOtherYear(year);
			affairLzxxjyActivities.setStartDate(null);
			affairLzxxjyActivities.setEndDate(null);
		}
		Page<AffairLzxxjyActivities> page = affairLzxxjyActivitiesService.findPage(new Page<AffairLzxxjyActivities>(request, response), affairLzxxjyActivities);
		model.addAttribute("page", page);
	/*	if (affairLzxxjyActivities.getStartDate() != null || affairLzxxjyActivities.getEndDate() != null){
			Page<AffairLzxxjyActivities> page = affairLzxxjyActivitiesService.findPage(new Page<AffairLzxxjyActivities>(request, response), affairLzxxjyActivities);
			model.addAttribute("page", page);
		}else if (affairLzxxjyActivities.getStartDate() == null && affairLzxxjyActivities.getEndDate() == null){
			if ("".equals(affairLzxxjyActivities.getOtherYear()) || affairLzxxjyActivities.getOtherYear() == null){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
				Date date = new Date();
				String nowDate = sdf.format(date);
				Date yearDate = null;
				try {
					yearDate = sdf.parse(nowDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				affairLzxxjyActivities.setStartYear(yearDate);
				model.addAttribute("startYear", yearDate);
				Page<AffairLzxxjyActivities> page = affairLzxxjyActivitiesService.findPage(new Page<AffairLzxxjyActivities>(request, response), affairLzxxjyActivities);
				model.addAttribute("page", page);
			}
		}else if (affairLzxxjyActivities.getOtherYear() != null && !"".equals(affairLzxxjyActivities.getOtherYear())){
			Page<AffairLzxxjyActivities> page = affairLzxxjyActivitiesService.findPage(new Page<AffairLzxxjyActivities>(request, response), affairLzxxjyActivities);
			model.addAttribute("page", page);
		}*/
		return "modules/affair/affairLzxxjyActivitiesList";
	}

	/**
	 * 管理页面
	 * @param affairLzxxjyActivities
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	//@RequiresPermissions("affair:affairFileNotice:manage")
	@RequestMapping(value = {"manageList"})
	public String manageList(AffairLzxxjyActivities affairLzxxjyActivities, HttpServletRequest request, HttpServletResponse response, Model model) {
		affairLzxxjyActivities.setHasAuth(true);
		if ("1".equals(UserUtils.getUser().getCompany().getId())){
			affairLzxxjyActivities.setQxUnitId(UserUtils.getUser().getCompany().getId());
		}else if ("31".equals(UserUtils.getUser().getOffice().getId()) || "146".equals(UserUtils.getUser().getOffice().getId()) || "266".equals(UserUtils.getUser().getOffice().getId())){
			affairLzxxjyActivities.setQxUnitId("1");
		}else if ("34".equals(UserUtils.getUser().getCompany().getId())){
			affairLzxxjyActivities.setQxUnitId("34");
		}else if ("95".equals(UserUtils.getUser().getCompany().getId())){
			affairLzxxjyActivities.setQxUnitId("95");
		}else if ("156".equals(UserUtils.getUser().getCompany().getId())){
			affairLzxxjyActivities.setQxUnitId("156");
		}else {
			affairLzxxjyActivities.setQxUnitId(UserUtils.getUser().getOffice().getId());
		}
		affairLzxxjyActivities.setCreateBy(UserUtils.getUser());
		String reason = request.getParameter("reason");//2 全部 3 其他年份
		model.addAttribute("reason",reason);
		if("3".equals(reason)){
			affairLzxxjyActivities.setStartDate(null);
			affairLzxxjyActivities.setEndDate(null);
		}else if("2".equals(reason)){
			//全部
			if(affairLzxxjyActivities.getStartDate()==null && affairLzxxjyActivities.getEndDate() == null){
				String year = DateUtils.getYear();//获取当前年
				affairLzxxjyActivities.setOtherYear(year);
			}else{
				affairLzxxjyActivities.setOtherYear(null);
			}
		}else{
			//默认显示当前年度数据
			String year =DateUtils.getYear();//获取当前年
			affairLzxxjyActivities.setOtherYear(year);
			affairLzxxjyActivities.setStartDate(null);
			affairLzxxjyActivities.setEndDate(null);
		}
		Page<AffairLzxxjyActivities> page = affairLzxxjyActivitiesService.findPage(new Page<AffairLzxxjyActivities>(request, response), affairLzxxjyActivities);
		model.addAttribute("page", page);
		/*if (affairLzxxjyActivities.getStartDate() != null || affairLzxxjyActivities.getEndDate() != null){
			Page<AffairLzxxjyActivities> page = affairLzxxjyActivitiesService.findPage(new Page<AffairLzxxjyActivities>(request, response), affairLzxxjyActivities);
			model.addAttribute("page", page);
		}else if (affairLzxxjyActivities.getStartDate() == null && affairLzxxjyActivities.getEndDate() == null){
			if ("".equals(affairLzxxjyActivities.getOtherYear()) || affairLzxxjyActivities.getOtherYear() == null){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
				Date date = new Date();
				String nowDate = sdf.format(date);
				Date yearDate = null;
				try {
					yearDate = sdf.parse(nowDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				affairLzxxjyActivities.setStartYear(yearDate);
				model.addAttribute("startYear", yearDate);
				Page<AffairLzxxjyActivities> page = affairLzxxjyActivitiesService.findPage(new Page<AffairLzxxjyActivities>(request, response), affairLzxxjyActivities);
				model.addAttribute("page", page);
			}
		}else if (affairLzxxjyActivities.getOtherYear() != null && !"".equals(affairLzxxjyActivities.getOtherYear())){
			Page<AffairLzxxjyActivities> page = affairLzxxjyActivitiesService.findPage(new Page<AffairLzxxjyActivities>(request, response), affairLzxxjyActivities);
			model.addAttribute("page", page);
		}*/
		return "modules/affair/affairLzxxjyActivitiesManage";
	}

//	@RequiresPermissions("affair:affairLzxxjyActivities:view")
	@RequestMapping(value = "propelling")
	public String propelling(AffairLzxxjyActivities affairLzxxjyActivities, Model model) {
		//
		Category category = new Category();
		category.setId(affairLzxxjyActivities.getId());
		//
		ArticleData articleData = new ArticleData();
		articleData.setContent(affairLzxxjyActivities.getActivites());
		//
		Article article = new Article();
		article.setCategory(category);
		article.setArticleData(articleData);
		article.setTitle(affairLzxxjyActivities.getEventName());
		article.setDescription(affairLzxxjyActivities.getSummary());
		article.setAppendfile(affairLzxxjyActivities.getAnnex());
		article.setCreateDate(affairLzxxjyActivities.getCreateDate());
		article.setAppendfile("/politics"+affairLzxxjyActivities.getAnnex());
		//
		model.addAttribute("article", article);
		return "modules/affair/affairLzxxjyActivitiesPropelling";
	}

//	@RequiresPermissions("affair:affairLzxxjyActivities:add")
	@RequestMapping(value = "form")
	public String form(AffairLzxxjyActivities affairLzxxjyActivities, Model model) {
		model.addAttribute("affairLzxxjyActivities", affairLzxxjyActivities);
		return "modules/affair/affairLzxxjyActivitiesForm";
	}

	/**
	 * 详情
	 * @param affairLzxxjyActivities
	 * @param model
	 * @return
	 */
	@RequiresPermissions("affair:affairLzxxjyActivities:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairLzxxjyActivities affairLzxxjyActivities, Model model) {
		model.addAttribute("affairLzxxjyActivities", affairLzxxjyActivities);
		if(affairLzxxjyActivities.getAnnex() != null && affairLzxxjyActivities.getAnnex().length() > 0){
			List<Map<String, String>> filePathList = uploadController.filePathHandle(affairLzxxjyActivities.getAnnex());
			model.addAttribute("filePathList", filePathList);
		}
		return "modules/affair/affairLzxxjyActivitiesFormDetail";
	}

//	@RequiresPermissions("affair:affairLzxxjyActivities:edit")
	@RequestMapping(value = "save")
	public String save(AffairLzxxjyActivities affairLzxxjyActivities, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairLzxxjyActivities)){
			return form(affairLzxxjyActivities, model);
		}
		affairLzxxjyActivitiesService.save(affairLzxxjyActivities);
		addMessage(redirectAttributes, "保存廉政学习教育活动成功");
		request.setAttribute("saveResult","success");
		return "modules/affair/affairLzxxjyActivitiesForm";
	}
	
//	@RequiresPermissions("affair:affairLzxxjyActivities:delete")
	@RequestMapping(value = "delete")
	public String delete(AffairLzxxjyActivities affairLzxxjyActivities, RedirectAttributes redirectAttributes) {
		affairLzxxjyActivitiesService.delete(affairLzxxjyActivities);
		addMessage(redirectAttributes, "删除廉政学习教育活动成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairLzxxjyActivities/manageList?repage";
	}

	@ResponseBody
//	@RequiresPermissions("affair:affairLzxxjyActivities:delete")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairLzxxjyActivitiesService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	/**
	 * 批量发布
	 * @param ids
	 * @return
	 */
	@ResponseBody
	//@RequiresPermissions("affair:affairFileNotice:manage")
	@RequestMapping(value = {"publishByIds"})
	public Result publishByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairLzxxjyActivitiesService.publishByIds(ids);
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
	//@RequiresPermissions("affair:affairFileNotice:manage")
	@RequestMapping(value = {"cancelByIds"})
	public Result cancelByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairLzxxjyActivitiesService.cancelByIds(ids);
			result.setSuccess(true);
			result.setMessage("取消发布成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要取消发布的内容");
		}
		return result;
	}

	/**
	 * 转发
 	 * @param affairLzxxjyActivities
	 * @param model
	 * @param redirectAttributes
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "zhuanfa")
	public String zhuanfa(AffairLzxxjyActivities affairLzxxjyActivities, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		AffairLzxxjyActivities affairLzxxjyActivities1 = new AffairLzxxjyActivities();
		affairLzxxjyActivities1.setEventName(affairLzxxjyActivities.getEventName());
		affairLzxxjyActivities1.setUnit(affairLzxxjyActivities.getUnit());
		affairLzxxjyActivities1.setUnitId(affairLzxxjyActivities.getUnitId());
		affairLzxxjyActivities1.setType(affairLzxxjyActivities.getType());
		affairLzxxjyActivities1.setEventDate(affairLzxxjyActivities.getEventDate());
		affairLzxxjyActivities1.setSummary(affairLzxxjyActivities.getSummary());
		affairLzxxjyActivities1.setAnnex(affairLzxxjyActivities.getAnnex());
		affairLzxxjyActivities1.setQxUnitId(affairLzxxjyActivities.getQxUnitId());
		affairLzxxjyActivities1.setFlag(affairLzxxjyActivities.getFlag());
		affairLzxxjyActivities1.setStatus(null);
		affairLzxxjyActivities1.setPublishDep(UserUtils.getUser().getOffice().getName());
		affairLzxxjyActivities1.setPublisher(UserUtils.getUser().getName());
		affairLzxxjyActivities1.setPublishOrgId(UserUtils.getUser().getOffice().getId());
		affairLzxxjyActivities1.setRemarks(affairLzxxjyActivities.getRemarks());
		model.addAttribute("affairLzxxjyActivities", affairLzxxjyActivities1);
		return "modules/affair/affairLzxxjyActivitiesForm";
	}

}