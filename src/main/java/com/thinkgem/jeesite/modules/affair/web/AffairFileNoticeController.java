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
import com.thinkgem.jeesite.modules.affair.entity.AffairFileNotice;
import com.thinkgem.jeesite.modules.affair.service.AffairFileNoticeService;
import com.thinkgem.jeesite.modules.cms.entity.Article;
import com.thinkgem.jeesite.modules.cms.entity.ArticleData;
import com.thinkgem.jeesite.modules.cms.entity.Category;
import com.thinkgem.jeesite.modules.cms.service.ArticleService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
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
 * 党建文件通知通报Controllerf
 * @author eav.liu
 * @version 2019-11-01
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairFileNotice")
public class AffairFileNoticeController extends BaseController {

	@Autowired
	private AffairFileNoticeService affairFileNoticeService;

	@Autowired
	private UploadController uploadController;

	@Autowired
	private ArticleService articleService;

	@ModelAttribute
	public AffairFileNotice get(@RequestParam(required=false) String id) {
		AffairFileNotice entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairFileNoticeService.get(id);
		}
		if (entity == null){
			entity = new AffairFileNotice();
		}
		return entity;
	}

	/**
	 * 查询页面
	 * @param affairFileNotice
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	//@RequiresPermissions("affair:affairFileNotice:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairFileNotice affairFileNotice, HttpServletRequest request, HttpServletResponse response, Model model) {
		affairFileNotice.setHasAuth(false);
		Page<AffairFileNotice> page = affairFileNoticeService.findPage(new Page<AffairFileNotice>(request, response), affairFileNotice); 
		model.addAttribute("page", page);
		/**
		 * 1:党建通知通报文件 2：工会通知通报 3：团委通知通报 4:警营文化制度和通知通报 5：绩效考核通知通报  6：绩效考核公文交换 7:纪检监察通知通报
		 */
		String url = "";
		switch(affairFileNotice.getFlag()){
			case "1":
				url = "modules/affair/affairFileNoticeList";
				break;
			case "2":
				url = "modules/affair/affairGongHuiNoticeList";
				break;
			case "3":
				url = "modules/affair/affairTuanWeiNoticeList";
				break;
			case "4":
				url = "modules/affair/affairCultureActivityNoticeList";
				break;
			case "5":
				url = "modules/affair/affairExamNoticeList";
				break;
			case "6":
				url = "modules/affair/affairDocExchangeList";
				break;
			case "7":
				url = "modules/affair/affairJiJianJianChaNoticeList";
				break;
			default:
				break;
		}
		return url;
	}

	/**
	 * 管理页面
	 * @param affairFileNotice
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	//@RequiresPermissions("affair:affairFileNotice:manage")
	@RequestMapping(value = {"manageList"})
	public String manageList(AffairFileNotice affairFileNotice, HttpServletRequest request, HttpServletResponse response, Model model) {
		affairFileNotice.setHasAuth(true);
		Page<AffairFileNotice> page = affairFileNoticeService.findPage(new Page<AffairFileNotice>(request, response), affairFileNotice);
		model.addAttribute("page", page);
		/**
		 * 1:党建通知通报文件 2：工会通知通报 3：团委通知通报 4:警营文化制度和通知通报 5：绩效考核通知通报  6：绩效考核公文交换 7:纪检监察通知通报
		 */
		String url = "";
		switch(affairFileNotice.getFlag()){
			case "1":
				url = "modules/affair/affairFileNoticeManage";
				break;
			case "2":
				url = "modules/affair/affairGongHuiNoticeManage";
				break;
			case "3":
				url = "modules/affair/affairTuanWeiNoticeManage";
				break;
			case "4":
				url = "modules/affair/affairCultureActivityNoticeManage";
				break;
			case "5":
				url = "modules/affair/affairExamNoticeManage";
				break;
			case "6":
				url = "modules/affair/affairDocExchangeManage";
				break;
			case "7":
				url = "modules/affair/affairJiJianJianChaNoticeManage";
				break;
			default:
				break;
		}
		return url;
	}

	//@RequiresPermissions("affair:affairFileNotice:manage")
	@RequestMapping(value = "form")
	public String form(AffairFileNotice affairFileNotice, Model model) {
		model.addAttribute("affairFileNotice", affairFileNotice);
		String url = "";
		/**
		 * 1、4jsp页面复用  5、6页面复用
		 * 1:党建通知通报文件  4:警营文化制度和通知通报
		 * 2：工会通知通报  3：团委通知通报
		 * 5：绩效考核通知通报  6：绩效考核公文交换
		 * 7：纪检监察通知通报
		 */
		switch(affairFileNotice.getFlag()){
			case "1":
				url = "modules/affair/affairFileNoticeForm";
				break;
			case "2":
				url = "modules/affair/affairGongHuiNoticeForm";
				break;
			case "3":
				url = "modules/affair/affairTuanWeiNoticeForm";
				break;
			case "4":
				url = "modules/affair/affairFileNoticeForm";
				break;
			case "5":
				url = "modules/affair/affairDocExchangeForm";
				model.addAttribute("flag", 5);
				break;
			case "6":
				url = "modules/affair/affairDocExchangeForm";
				break;
			case "7":
				url = "modules/affair/affairJiJianJianChaNoticeForm";
				break;
			default:
				break;
		}
		return url;
	}

	//@RequiresPermissions("affair:affairFileNotice:manage")
	@RequestMapping(value = "save")
	public String save(AffairFileNotice affairFileNotice, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairFileNotice)){
			return form(affairFileNotice, model);
		}
		affairFileNoticeService.save(affairFileNotice);
		addMessage(redirectAttributes, "保存通知通报成功");
		request.setAttribute("saveResult","sucess");
		String url = "";
		/**
		 * 1、4jsp页面复用   5、6页面复用
		 * 1:党建通知通报文件  4:警营文化制度和通知通报
		 * 2：工会通知通报  3：团委通知通报
		 * 5：绩效考核通知通报  6：绩效考核公文交换
		 * 7：纪检监察通知通报
		 */
		switch(affairFileNotice.getFlag()){
			case "1":
				url = "modules/affair/affairFileNoticeForm";
				break;
			case "2":
				url = "modules/affair/affairGongHuiNoticeForm";
				break;
			case "3":
				url = "modules/affair/affairTuanWeiNoticeForm";
				break;
			case "4":
				url = "modules/affair/affairFileNoticeForm";
				break;
			case "5":
				url = "modules/affair/affairDocExchangeForm";
				break;
			case "6":
				url = "modules/affair/affairDocExchangeForm";
				break;
			case "7":
				url = "modules/affair/affairJiJianJianChaNoticeForm";
				break;
			default:
				break;
		}
		return url;
	}

	//@RequiresPermissions("affair:affairFileNotice:manage")
	@RequestMapping(value = "delete")
	public String delete(AffairFileNotice affairFileNotice, RedirectAttributes redirectAttributes) {
		affairFileNoticeService.delete(affairFileNotice);
		addMessage(redirectAttributes, "删除通知通报成功");
		/**
		 * 1:党建通知通报文件 2：工会通知通报 3：团委通知通报 4:警营文化制度和通知通报 5：绩效考核通知通报  6：绩效考核公文交换 7：纪检监察通知通报
		 */
		String url = "";
		switch(affairFileNotice.getFlag()){
			case "1":
				url = "redirect:"+Global.getAdminPath()+"/affair/affairFileNotice/manageList?repage"+"&flag=1";
				break;
			case "2":
				url = "redirect:"+Global.getAdminPath()+"/affair/affairFileNotice/manageList?repage"+"&flag=2";
				break;
			case "3":
				url = "redirect:"+Global.getAdminPath()+"/affair/affairFileNotice/manageList?repage"+"&flag=3";
				break;
			case "4":
				url = "redirect:"+Global.getAdminPath()+"/affair/affairFileNotice/manageList?repage"+"&flag=4";
				break;
			case "5":
				url = "redirect:"+Global.getAdminPath()+"/affair/affairFileNotice/manageList?repage"+"&flag=5";
				break;
			case "6":
				url = "redirect:"+Global.getAdminPath()+"/affair/affairFileNotice/manageList?repage"+"&flag=6";
				break;
			case "7":
				url = "redirect:"+Global.getAdminPath()+"/affair/affairJiJianJianChaNotice/manageList?repage"+"&flag=7";
				break;
			default:
				break;
		}
		return url;
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	//@RequiresPermissions("affair:affairFileNotice:manage")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairFileNoticeService.deleteByIds(ids);
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
			affairFileNoticeService.publishByIds(ids);
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
			affairFileNoticeService.cancelByIds(ids);
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
	 * @param affairFileNotice
	 * @param model
	 * @return
	 */
	//@RequiresPermissions("affair:affairFileNotice:view")
	@RequestMapping(value = {"detail"})
	public String detail(AffairFileNotice affairFileNotice, Model model) {
		model.addAttribute("affairFileNotice", affairFileNotice);
		if(affairFileNotice.getFilePath() != null && affairFileNotice.getFilePath().length() > 0){
            List<Map<String, String>> filePathList = uploadController.filePathHandle(affairFileNotice.getFilePath());
            model.addAttribute("filePathList", filePathList);
        }
		return "modules/affair/affairFileNoticeDetail";
	}

	/**
	 * 管理页面form表单详情
	 * @param affairFileNotice
	 * @param model
	 * @return
	 */
	//@RequiresPermissions("affair:affairFileNotice:manage")
	@RequestMapping(value = {"formDetail"})
	public String formDetail(AffairFileNotice affairFileNotice, Model model) {
		model.addAttribute("affairFileNotice", affairFileNotice);
		String url = "";
		/**
		 * 1、4jsp页面复用   5、6页面复用
		 * 1:党建通知通报文件  4:警营文化制度和通知通报
		 * 2：工会通知通报  3：团委通知通报
		 * 5：绩效考核通知通报  6：绩效考核公文交换
		 * 7：纪检监察通知通报
		 */
		switch(affairFileNotice.getFlag()){
			case "1":
				url = "modules/affair/affairFileNoticeFormDetail";
				break;
			case "2":
				url = "modules/affair/affairGongHuiNoticeFormDetail";
				break;
			case "3":
				url = "modules/affair/affairTuanWeiNoticeFormDetail";
				break;
			case "4":
				url = "modules/affair/affairFileNoticeFormDetail";
				break;
			case "5":
				url = "modules/affair/affairDocExchangeFormDetail";
				model.addAttribute("flag", 5);
				break;
			case "6":
				url = "modules/affair/affairDocExchangeFormDetail";
				break;
			case "7":
				url = "modules/affair/affairJiJianJianChaNoticeFormDetail";
				break;
			default:
				break;
		}
        if(affairFileNotice.getFilePath() != null && affairFileNotice.getFilePath().length() > 0){
            List<Map<String, String>> filePathList = uploadController.filePathHandle(affairFileNotice.getFilePath());
            model.addAttribute("filePathList", filePathList);
        }
		return url;
	}

	@RequestMapping(value = "pushNotification")
	public String pushNotification(AffairFileNotice affairFileNotice){
		Article article = new Article();
		//标题
		article.setTitle(affairFileNotice.getTitle());
		//发布内容和发布部门
		ArticleData articleData = new ArticleData();
		articleData.setContent(affairFileNotice.getContent());
		articleData.setCopyfrom(affairFileNotice.getPublishDep());
		//附件
		article.setAppendfile(affairFileNotice.getFilePath());
		//审核状态
		/*9.9 及时反馈修改，直接发布到首页*/


		//通知通报类型
		Category category = new Category();
		category.setId("f72a5b908848442cb5606c32c65d4632");
		//需要审核
		String userId = UserUtils.getUser().getId();
		if (userId.equals("c86a5e277ebb44c584972a81e039f890") || userId.equals("8d498ce4a66642f2ac57fd557269fa5c") ||
				userId.equals("bcb9ae19e88a478fb66bd47322c9b637") || userId.equals("54e8fb917a8241c08c04bb3dbe4dee46")) {
			category.setIsAudit("0");
			article.setDelFlag("0");
		} else {
			category.setIsAudit("1");
			article.setDelFlag("2");
		}


		article.setArticleData(articleData);
		article.setCategory(category);

		articleService.save(article);
		return  "redirect:"+Global.getAdminPath()+"/affair/affairFileNotice/manageList?repage"+"&flag=1";
	}
}