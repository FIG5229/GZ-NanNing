/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cms.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.affair.entity.AffairTnActivityRecord;
import com.thinkgem.jeesite.modules.affair.service.AffairTnActivityRecordService;
import com.thinkgem.jeesite.modules.cms.entity.Article;
import com.thinkgem.jeesite.modules.cms.entity.Category;
import com.thinkgem.jeesite.modules.cms.entity.Site;
import com.thinkgem.jeesite.modules.cms.service.*;
import com.thinkgem.jeesite.modules.cms.utils.CmsUtils;
import com.thinkgem.jeesite.modules.cms.utils.TplUtils;
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
import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * 文章Controller
 * @author ThinkGem
 * @version 2013-3-23
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/article")
public class ArticleController extends BaseController {

	@Autowired
	private ArticleService articleService;
	@Autowired
	private ArticleDataService articleDataService;
	@Autowired
	private CategoryService categoryService;
    @Autowired
   	private FileTplService fileTplService;
    @Autowired
   	private SiteService siteService;
	@Autowired
	private AffairTnActivityRecordService affairTnActivityRecordService;//团内活动记录

	@ModelAttribute
	public Article get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return articleService.get(id);
		}else{
			return new Article();
		}
	}

	@RequiresPermissions("cms:article:view")
	@RequestMapping(value = {"list", ""})
	public String list(Article article, HttpServletRequest request, HttpServletResponse response, Model model) {
//		for (int i=0; i<10000000; i++){
//			Article a = new Article();
//			a.setCategory(new Category(article.getCategory().getId()));
//			a.setTitle("测试测试测试测试测试测试测试测试"+a.getCategory().getId());
//			a.setArticleData(new ArticleData());
//			a.getArticleData().setContent(a.getTitle());
//			articleService.save(a);
//		}
        Page<Article> page = articleService.findPage(new Page<Article>(request, response), article, true);
        model.addAttribute("page", page);
		return "modules/cms/articleList";
	}
	@RequiresPermissions("affair:education:view")
	@RequestMapping(value = {"education"})
	public String education(Article article, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Article> page = articleService.findPage(new Page<Article>(request, response), article, true);
        model.addAttribute("page", page);
		return "modules/cms/articleList";
	}

	@RequiresPermissions("affair:alarm:view")
	@RequestMapping(value = {"alarm"})
	public String alarm(Article article, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Article> page = articleService.findPage(new Page<Article>(request, response), article, true);
		model.addAttribute("page", page);
		return "modules/cms/articleList";
	}

	@RequiresPermissions("affair:shenhe:view")
	@RequestMapping(value = {"view"})
	public String view(Article article, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Article> page = articleService.findColumnPage(new Page<Article>(request, response), article, true);
		model.addAttribute("page", page);
		//栏目审核列表
		return "modules/cms/columnAuditList";
	}
	//新建 页面（未复用 以免与下边页面冲突）
	@RequiresPermissions("affair:shenhe:view")
	@RequestMapping(value = "viewForm")
	public String viewForm(Article article, Model model) {
		// 如果当前传参有子节点，则选择取消传参选择
		if (article.getCategory()!=null && StringUtils.isNotBlank(article.getCategory().getId())){
			List<Category> list = categoryService.findByParentId(article.getCategory().getId(), Site.getCurrentSiteId());
			if (list.size() > 0){
				article.setCategory(null);
			}else{
				article.setCategory(categoryService.get(article.getCategory().getId()));
			}
		}
		article.setArticleData(articleDataService.get(article.getId()));
//		}
		model.addAttribute("contentViewList",getTplContent());
		model.addAttribute("article_DEFAULT_TEMPLATE",Article.DEFAULT_TEMPLATE);
		model.addAttribute("article", article);
		CmsUtils.addViewConfigAttribute(model, article.getCategory());
		return "modules/cms/columnAudiForm";
	}

	@RequiresPermissions("cms:article:view")
	@RequestMapping(value = "form")
	public String form(Article article, Model model) {
		// 如果当前传参有子节点，则选择取消传参选择
		if (article.getCategory()!=null && StringUtils.isNotBlank(article.getCategory().getId())){
			List<Category> list = categoryService.findByParentId(article.getCategory().getId(), Site.getCurrentSiteId());
			if (list.size() > 0){
				article.setCategory(null);
			}else{
				article.setCategory(categoryService.get(article.getCategory().getId()));
			}
		}
		article.setArticleData(articleDataService.get(article.getId()));
//		if (article.getCategory()=null && StringUtils.isNotBlank(article.getCategory().getId())){
//			Category category = categoryService.get(article.getCategory().getId());
//		}
        model.addAttribute("contentViewList",getTplContent());
        model.addAttribute("article_DEFAULT_TEMPLATE",Article.DEFAULT_TEMPLATE);
		model.addAttribute("article", article);
		CmsUtils.addViewConfigAttribute(model, article.getCategory());
		return "modules/cms/articleForm";
	}

	@RequiresPermissions("cms:article:edit")
	@RequestMapping(value = "save")
	public String save(Article article, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, article)){
			return form(article, model);
		}
		articleService.save(article);
		addMessage(redirectAttributes, "保存文章'" + StringUtils.abbr(article.getTitle(),50) + "'成功");
		String categoryId = article.getCategory()!=null?article.getCategory().getId():null;
		/*如果是从信息审核中修改保存 跳到信息审核的修改  不进行重定向*/
		if ("true".equals(request.getParameter("isColumnAudi"))){
			request.setAttribute("saveResult","success");
			return "modules/cms/columnAudiForm";
		}
		return "redirect:" + adminPath + "/cms/article/?repage&category.id="+(categoryId!=null?categoryId:"");
	}

	/*@RequiresPermissions("cms:article:edit")*/

	/**
	 * 推送到首页相应的栏目
	 * @param article
	 * @param model
	 * @param redirectAttributes
	 * @param request
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "saves")
	public String saves(Article article, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request, String type) {
//		String categoryIds = article.getCategory()!=null?article.getCategory().getId():null;

        String[] categoryIds=article.getCategoryArr();
		for (String cate : categoryIds) {
			//保存一次后id不为空 无法完成多栏目推送 需要设置为空
			article.setId(null);
			if (StringUtils.isEmpty(cate)) {
				continue;
			}

			Category i = new Category();

			i.setId(cate);
			article.setCategory(i);
			if (type != null && !"".equals(type)) {
				article.setDelFlag("0");
			} else {
				article.setDelFlag("2");
			}
			/*政工要闻*/    /*政工概况*/
			String userId = UserUtils.getUser().getId();
			if (cate.equals("5d7f5d61b84243ddb53ce5c359e6c2f2") || cate.equals("2")) {
				if (userId.equals("c86a5e277ebb44c584972a81e039f890") || userId.equals("8d498ce4a66642f2ac57fd557269fa5c") ||
						userId.equals("bcb9ae19e88a478fb66bd47322c9b637") || userId.equals("54e8fb917a8241c08c04bb3dbe4dee46")) {
					article.setDelFlag("0");
					article.getCategory().setIsAudit("0");
				} else {
					article.getCategory().setIsAudit("1");
					article.setDelFlag("2");
				}
			}
			/*青年阵地*/
			if (cate.equals("220c0482c9a5442b9c6317ffdefc7381")) {
				if (userId.equals("f4c06ae75fbe49679974f6ecad12aa3c") || userId.equals("45e43fc5465d4dbf849c6ec50609ecf4") ||
						userId.equals("78d0e07ed2e14ca0b6c73e14c11f4d55") || userId.equals("498ac9fc6773461d8d984a468c67ee2b") ||
						userId.equals("11d94fe57ede47a9bae4bffa36af487c") || userId.equals("28f59642a1e74d0588f0d515fe462775") || userId.equals("ff7f9fe2597b40429ded58f8b76a2f65")) {
					article.setDelFlag("0");
					article.getCategory().setIsAudit("0");
				} else {
					article.getCategory().setIsAudit("1");
					article.setDelFlag("2");
				}
			}
			/*纪检监察*/
			if (cate.equals("44c5b3f7d9e94c9897af22ef67d02292")) {

			}
			/*警务督察*/
			if (cate.equals("f0db6af079714a65a8ec11e3b08329c6")) {
				if (userId.equals("70c09eacfdbf4e37bcbbd6d73d8000c3") || userId.equals("055973da711f46a798437e9a499e1c39") ||
						userId.equals("71b6ca5d1a7145f3b263b0ab48436fe1") || userId.equals("b6de4151cd01411da212432ede766a05")) {
					article.getCategory().setIsAudit("0");
					article.getCategory().setIsAudit("1");
					article.setDelFlag("0");
				} else {
					article.getCategory().setIsAudit("1");
					article.setDelFlag("2");
				}
			}
			/*工会保障*/
			if (cate.equals("335c69c108d243ca9fded21331dcad52")) {
				if (userId.equals("ca07f09482154be0b10136171b19b90e") || userId.equals("b91d9ac0c32847c4ab6f21e910959198") ||
						userId.equals("73607645d2e040359dbcb66640f92e07") || userId.equals("1d6572cc99604313b93905e5ff3f47a4")) {
					article.getCategory().setIsAudit("0");
					article.setDelFlag("0");
				} else {
					article.getCategory().setIsAudit("1");
					article.setDelFlag("2");
				}
			}
			/*廉政教育*/
			if (cate.equals("c55f7521049a4a1bbaf9031c929fc142")) {

			}
			articleService.save(article);

		}
		if (!beanValidator(model, article))
			return form(article, model);
//		for(String categoryId : request.getParameterValues("category"))
//			System.out.println(categoryId);
//		articleService.save(article);
		model.addAttribute("result","success");
		addMessage(redirectAttributes, "保存文章'" + StringUtils.abbr(article.getTitle(),50) + "'成功");
		String url = "";
		switch (type){
			case "affairGhActivityRecord":
				url = "modules/affair/affairGhActivityRecordPropelling";
				break;
			case "affairDcwtLibrary":
				url = "modules/affair/affairDcwtLibraryPropelling";
				break;
			case "affairLzxxjyActivities":
				url = "modules/affair/affairLzxxjyActivitiesPropelling";
				break;
			case "affairTnActivityRecord":
				//团内活动
				try {
					String affairTnActivityRecordId = request.getParameter("affairTnActivityRecordId");
					AffairTnActivityRecord affairTnActivityRecord = affairTnActivityRecordService.get(affairTnActivityRecordId);
					affairTnActivityRecord.setPush("1");
					affairTnActivityRecordService.save(affairTnActivityRecord);
				}catch (Exception e){
					e.printStackTrace();
					logger.error("团内活动记录，推送后，更新活动表push字段错误；错误信息："+e.toString());
				}
				url = "modules/affair/affairTnActivityRecordPropelling";
				break;
			case "affairActivityMien":
				url = "modules/affair/affairActivityMienPropelling";
				break;
			case "affairDocManagement":
				String docManagementId = request.getParameter("docManagementId");
				model.addAttribute("docManagementId",docManagementId);
				url = "modules/affair/affairDocManagementPropelling";
				break;
			case "affairWenyi":
				url = "modules/affair/affairWenyiPropelling";
				break;
			case "affairWenhua":
				url = "modules/affair/affairWenhuaPropelling";
				break;
			case "affairPolicewomanWork":
				url = "modules/affair/affairPolicewomanWorkPropelling";
				break;
			case "affairPolicewomanTalent":
				url = "modules/affair/affairPolicewomanTalentPropelling";
				break;
			default:
				break;
		}
		return url;
	}

	@RequiresPermissions("cms:article:edit")
	@RequestMapping(value = "delete")
	public String delete(Article article, String categoryId, @RequestParam(required=false) Boolean isRe, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		// 如果没有审核权限，则不允许删除或发布。
		if (!UserUtils.getSubject().isPermitted("cms:article:audit")){
			addMessage(redirectAttributes, "你没有删除或发布权限");
		}
		articleService.delete(article, isRe);
		addMessage(redirectAttributes, (article.getDelFlag().equals("0")?"发布":"删除")+"文章成功");
		/*如果是从信息审核中修改保存 跳到信息审核的修改  不进行重定向*/
		if ("true".equals(request.getParameter("isColumnAudi"))){

			return "redirect:" + adminPath + "/cms/article/view?repage";
		}
		return "redirect:" + adminPath + "/cms/article/?repage&category.id="+(categoryId!=null?categoryId:"");
	}

	@RequiresPermissions("cms:article:edit")
	@RequestMapping(value = "push")
	public String push(Article article, String categoryId, @RequestParam(required=false) Boolean isRe, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		// 如果没有审核权限，则不允许删除或发布。
		if (!UserUtils.getSubject().isPermitted("cms:article:audit")){
			addMessage(redirectAttributes, "你没有删除或发布权限");
		}
		articleService.push(article);
		addMessage(redirectAttributes, "发布文章成功");
		/*如果是从信息审核中修改保存 跳到信息审核的修改  不进行重定向*/

		return "redirect:" + adminPath + "/cms/article/view?repage";

	}

	/**
	 * 文章选择列表
	 */
	@RequiresPermissions("cms:article:view")
	@RequestMapping(value = "selectList")
	public String selectList(Article article, HttpServletRequest request, HttpServletResponse response, Model model) {
        list(article, request, response, model);
		return "modules/cms/articleSelectList";
	}

	/**
	 * 通过编号获取文章标题
	 */
	@RequiresPermissions("cms:article:view")
	@ResponseBody
	@RequestMapping(value = "findByIds")
	public String findByIds(String ids) {
		List<Object[]> list = articleService.findByIds(ids);
		return JsonMapper.nonDefaultMapper().toJson(list);
	}

    private List<String> getTplContent() {
   		List<String> tplList = fileTplService.getNameListByPrefix(siteService.get(Site.getCurrentSiteId()).getSolutionPath());
   		tplList = TplUtils.tplTrim(tplList, Article.DEFAULT_TEMPLATE, "");
   		return tplList;
   	}

	@RequestMapping(value = "video")
   	public String video(String path,HttpServletRequest request, HttpServletResponse response){
		request.setAttribute("path",path);
		//response.setHeader("Content-Range","");
		return "modules/cms/video";
	}

	@RequestMapping(value = "pics")
	public String pics(String paths, String cpath, HttpServletRequest request){
		String[] pathsArray = null;
		if(null != paths && paths.indexOf(";")>-1){
			pathsArray = paths.split(";");
		}else if(null != cpath){
			pathsArray =new String[1];
			pathsArray[0] = cpath;
		}else if (null != paths){
			pathsArray =new String[1];
			pathsArray[0] = paths;
		}
		request.setAttribute("paths",paths);
		request.setAttribute("pathsArray",pathsArray);
		if(null != cpath){
			request.setAttribute("cpath",cpath);
		}else if(null != pathsArray){
			request.setAttribute("cpath",pathsArray[0]);
		}
		return "modules/cms/pics";
	}

	@RequestMapping(value = "pic")
	public String pic(String paths,HttpServletRequest request, HttpServletResponse response){
		request.setAttribute("path",paths);
		return "modules/cms/pic";
	}

	/***
	 *  返回Mp4视频
	 *
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/GetFile")
	@ResponseBody
	public void getFile(HttpServletRequest request , HttpServletResponse response) throws IOException {

		String filePath = request.getParameter("aid");
		String userDir= Global.getUserfilesBaseDir();
		if (filePath !=null) {
			filePath = userDir + filePath.substring(filePath.indexOf("userfiles"));
			File file = new File(filePath);
			BufferedInputStream bis = null;
			try {
				if (file.exists()) {
					long p = 0L;
					long toLength = 0L;
					long contentLength = 0L;
					int rangeSwitch = 0; // 0,从头开始的全文下载；1,从某字节开始的下载（bytes=27000-）；2,从某字节开始到某字节结束的下载（bytes=27000-39000）
					long fileLength;
					String rangBytes = "";
					fileLength = file.length();

					// get file content
					InputStream ins = new FileInputStream(file);
					bis = new BufferedInputStream(ins);

					// tell the client to allow accept-ranges
					response.reset();
					response.setHeader("Accept-Ranges", "bytes");

					// client requests a file block download start byte
					String range = request.getHeader("Range");
					if (range != null && range.trim().length() > 0 && !"null".equals(range)) {
						response.setStatus(javax.servlet.http.HttpServletResponse.SC_PARTIAL_CONTENT);
						rangBytes = range.replaceAll("bytes=", "");
						if (rangBytes.endsWith("-")) { // bytes=270000-
							rangeSwitch = 1;
							p = Long.parseLong(rangBytes.substring(0, rangBytes.indexOf("-")));
							contentLength = fileLength - p; // 客户端请求的是270000之后的字节（包括bytes下标索引为270000的字节）
						} else { // bytes=270000-320000
							rangeSwitch = 2;
							String temp1 = rangBytes.substring(0, rangBytes.indexOf("-"));
							String temp2 = rangBytes.substring(rangBytes.indexOf("-") + 1);
							p = Long.parseLong(temp1);
							toLength = Long.parseLong(temp2);
							contentLength = toLength - p + 1; // 客户端请求的是 270000-320000 之间的字节
						}
					} else {
						contentLength = fileLength;
					}

					// 如果设设置了Content-Length，则客户端会自动进行多线程下载。如果不希望支持多线程，则不要设置这个参数。
					// Content-Length: [文件的总大小] - [客户端请求的下载的文件块的开始字节]
					response.setHeader("Content-Length", new Long(contentLength).toString());

					// 断点开始
					// 响应的格式是:
					// Content-Range: bytes [文件块的开始字节]-[文件的总大小 - 1]/[文件的总大小]
					if (rangeSwitch == 1) {
						String contentRange = new StringBuffer("bytes ").append(new Long(p).toString()).append("-")
								.append(new Long(fileLength - 1).toString()).append("/")
								.append(new Long(fileLength).toString()).toString();
						response.setHeader("Content-Range", contentRange);
						bis.skip(p);
					} else if (rangeSwitch == 2) {
						String contentRange = range.replace("=", " ") + "/" + new Long(fileLength).toString();
						response.setHeader("Content-Range", contentRange);
						bis.skip(p);
					} else {
						String contentRange = new StringBuffer("bytes ").append("0-").append(fileLength - 1).append("/")
								.append(fileLength).toString();
						response.setHeader("Content-Range", contentRange);
					}

					String fileName = file.getName();
					response.setContentType("video/mp4;charset=UTF-8");
					response.addHeader("Content-Disposition", "attachment;filename=" + fileName);

					OutputStream out = response.getOutputStream();
					int n = 0;
					long readLength = 0;
					int bsize = 1024;
					byte[] bytes = new byte[bsize];
					if (rangeSwitch == 2) {
						// 针对 bytes=27000-39000 的请求，从27000开始写数据
						while (readLength <= contentLength - bsize) {
							n = bis.read(bytes);
							readLength += n;
							out.write(bytes, 0, n);
						}
						if (readLength <= contentLength) {
							n = bis.read(bytes, 0, (int) (contentLength - readLength));
							out.write(bytes, 0, n);
						}
					} else {
						while ((n = bis.read(bytes)) != -1) {
							out.write(bytes, 0, n);
						}
					}
					out.flush();
					out.close();
					bis.close();
				}
			} catch (IOException ie) {
				// 忽略 ClientAbortException 之类的异常
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 栏目统计
	 * @param article
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("cms:article:statistics")
	@RequestMapping(value = "columnStatistics")
	public String columnSatistics(Article article, HttpServletRequest request, HttpServletResponse response, Model model){
		//object: survey调研文章统计 report政工简报统计 xuanjiao宣传教育统计 total合计 companyId公司id
		article.setDateType("2");
		List<Map<String,Object>> result=articleService.columnSatistics(article);
		model.addAttribute("page", result);
		model.addAttribute("article", article);
		return "modules/cms/columnStatisticsList";
	}

	@RequestMapping("governmentDetail")
	public String governmentDetail(Article article, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Article> page = articleService.findGovernmentDetailPage(new Page<Article>(request, response), article);
		model.addAttribute("page", page);
		return "modules/charts/chartArticleList";
	}
	@RequestMapping("secrecyDetail")
	public String secrecyDetail(Article article, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Article> page = articleService.findSecrecyDetailPage(new Page<Article>(request, response), article);
		model.addAttribute("page", page);
		return "modules/charts/chartArticleList";
	}

}
