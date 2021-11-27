/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cms.service;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.cms.dao.ArticleDao;
import com.thinkgem.jeesite.modules.cms.dao.ArticleDataDao;
import com.thinkgem.jeesite.modules.cms.dao.CategoryDao;
import com.thinkgem.jeesite.modules.cms.entity.Article;
import com.thinkgem.jeesite.modules.cms.entity.ArticleData;
import com.thinkgem.jeesite.modules.cms.entity.Category;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 文章Service
 * @author ThinkGem
 * @version 2013-05-15
 */
@Service
@Transactional(readOnly = true)
public class ArticleService extends CrudService<ArticleDao, Article> {

	@Autowired
	private ArticleDataDao articleDataDao;
	@Autowired
	private CategoryDao categoryDao;

	@Autowired
	private ArticleDao articleDao;

	@Autowired
	private UserDao userDao;

	@Transactional(readOnly = false)
	public Page<Article> findPage(Page<Article> page, Article article, boolean isDataScopeFilter) {

		// 更新过期的权重，间隔为“6”个小时
		Date updateExpiredWeightDate =  (Date)CacheUtils.get("updateExpiredWeightDateByArticle");
		if (updateExpiredWeightDate == null || (updateExpiredWeightDate != null 
				&& updateExpiredWeightDate.getTime() < new Date().getTime())){
			dao.updateExpiredWeight(article);
			CacheUtils.put("updateExpiredWeightDateByArticle", DateUtils.addHours(new Date(), 6));
		}
//		DetachedCriteria dc = dao.createDetachedCriteria();
//		dc.createAlias("category", "category");
//		dc.createAlias("category.site", "category.site");
		if (article.getCategory()!=null && StringUtils.isNotBlank(article.getCategory().getId()) && !Category.isRoot(article.getCategory().getId())){
			Category category = categoryDao.get(article.getCategory().getId());
			if (category==null){
				category = new Category();
			}
			category.setParentIds(category.getId());
			category.setSite(category.getSite());
			article.setCategory(category);
		}
		else{
			article.setCategory(new Category());
		}
//		if (StringUtils.isBlank(page.getOrderBy())){
//			page.setOrderBy("a.weight,a.update_date desc");
//		}
//		return dao.find(page, dc);
	//	article.getSqlMap().put("dsf", dataScopeFilter(article.getCurrentUser(), "o", "u"));
		return super.findPage(page, article);
		
	}

	@Transactional(readOnly = false)
	public void save(Article article) {
		if (article.getArticleData() != null){
			if (article.getArticleData().getContent()!=null){
				article.getArticleData().setContent(StringEscapeUtils.unescapeHtml4(
						article.getArticleData().getContent()));
			}
		}
		// 如果没有审核权限，则将当前内容改为待审核状态
		if (!UserUtils.getSubject().isPermitted("cms:article:audit")){
			article.setDelFlag(Article.DEL_FLAG_AUDIT);
		}
		// 如果栏目不需要审核，则将该内容设为发布状态
		if (article.getCategory()!=null&&StringUtils.isNotBlank(article.getCategory().getId())){
			Category category = categoryDao.get(article.getCategory().getId());
			if (!Global.YES.equals(category.getIsAudit())){
				article.setDelFlag(Article.DEL_FLAG_NORMAL);
			}
		}
		/*如果是政办中的审核发布 则发布（不管有没有审核权限）*/
		/*checkType 为 0时则审核通过并发布*/
		if (StringUtils.isNotBlank(article.getCheckType()) && article.getCheckType().equals("0")){
			article.setDelFlag("0");
		}
		article.setUpdateBy(UserUtils.getUser());
		article.setUpdateDate(new Date());
        if (StringUtils.isNotBlank(article.getViewConfig())){
            article.setViewConfig(StringEscapeUtils.unescapeHtml4(article.getViewConfig()));
        }
        
        ArticleData articleData = new ArticleData();
		if (StringUtils.isBlank(article.getId())){
			article.preInsert();
			articleData = article.getArticleData()==null?new ArticleData():article.getArticleData();
			articleData.setId(article.getId());
			/*事务信息政办使用 保存时 添加单位领导审核人*/
			if (article.getCategory().getId().equals("127650291835482991a8b19ddd29029c") || article.getCategory().getId().equals("f72a5b908848442cb5606c32c65d4632") ||
					article.getCategory().getId().equals("3cac8ac633dd43e2845f6c45b0c9c4ea") || article.getCategory().getId().equals("d82d42b9d0ab4c34b5405dbb8e398020")){
				if (StringUtils.isBlank(article.getOfficeExamineId())){
					/*去掉单位领导，直接由办公室开始审核*/
					article.setOfficeExamineId("54e8fb917a8241c08c04bb3dbe4dee46");
					article.setOfficeExamine("南宁局办公室");
					article.setCheckType("1");

					/*UserUtils.getUser();
					User u = new User();
					u.setOffice(new Office(UserUtils.getUser().getOffice().getId()));
					u.setRole(new Role("0a79a08359624dbb8f3800e990adc7f5"));
					List<User> leaderList =  userDao.findUserByOfficeIdRoleId(u);
					if (leaderList!= null && leaderList.size()>0){
						u = leaderList.stream().findFirst().get();
						article.setUnitExamineId(u.getId());
						article.setUnitExamine(u.getName());
					}*/
				}
				String userId = UserUtils.getUser().getId();
				if (userId.equals("9c38dc31ac364600a6a52179c7af267d")) {
					article.setOfficeExamineId(userId);
					article.setOfficeExamine(UserUtils.getUser().getName());
					article.setCheckType("2");
				}
				if (userId.equals("44bec9ee797c4f0c9600416332efb530") || userId.equals("90e2377d44be4d58a5e4b297df273c89") || userId.equals("373a31be22524878855667db6af7a34a")) {
					article.setOfficeExamineId(userId);
					article.setOfficeExamine(UserUtils.getUser().getName());
					article.setCheckType("3");
				}
			}
			dao.insert(article);
			articleDataDao.insert(articleData);
		}else{
			article.preUpdate();
			articleData = article.getArticleData();
			articleData.setId(article.getId());
			dao.update(article);
			articleDataDao.update(article.getArticleData());
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(Article article, Boolean isRe) {
//		dao.updateDelFlag(id, isRe!=null&&isRe?Article.DEL_FLAG_NORMAL:Article.DEL_FLAG_DELETE);
		// 使用下面方法，以便更新索引。
		//Article article = dao.get(id);
		//article.setDelFlag(isRe!=null&&isRe?Article.DEL_FLAG_NORMAL:Article.DEL_FLAG_DELETE);
		//dao.insert(article);
		super.delete(article);
	}
	
	/**
	 * 通过编号获取内容标题
	 * @return new Object[]{栏目Id,文章Id,文章标题}
	 */
	public List<Object[]> findByIds(String ids) {
		if(ids == null){
			return new ArrayList<Object[]>();
		}
		List<Object[]> list = Lists.newArrayList();
		String[] idss = StringUtils.split(ids,",");
		Article e = null;
		for(int i=0;(idss.length-i)>0;i++){
			e = dao.get(idss[i]);
			list.add(new Object[]{e.getCategory().getId(),e.getId(),StringUtils.abbr(e.getTitle(),50)});
		}
		return list;
	}
	
	/**
	 * 点击数加一
	 */
	@Transactional(readOnly = false)
	public void updateHitsAddOne(String id) {
		dao.updateHitsAddOne(id);
	}
	
	/**
	 * 更新索引
	 */
	public void createIndex(){
		//dao.createIndex();
	}
	
	/**
	 * 全文检索
	 */
	//FIXME 暂不提供检索功能
	public Page<Article> search(Page<Article> page, String q, String categoryId, String beginDate, String endDate){
		
		// 设置查询条件
//		BooleanQuery query = dao.getFullTextQuery(q, "title","keywords","description","articleData.content");
//		
//		// 设置过滤条件
//		List<BooleanClause> bcList = Lists.newArrayList();
//
//		bcList.add(new BooleanClause(new TermQuery(new Term(Article.FIELD_DEL_FLAG, Article.DEL_FLAG_NORMAL)), Occur.MUST));
//		if (StringUtils.isNotBlank(categoryId)){
//			bcList.add(new BooleanClause(new TermQuery(new Term("category.ids", categoryId)), Occur.MUST));
//		}
//		
//		if (StringUtils.isNotBlank(beginDate) && StringUtils.isNotBlank(endDate)) {   
//			bcList.add(new BooleanClause(new TermRangeQuery("updateDate", beginDate.replaceAll("-", ""),
//					endDate.replaceAll("-", ""), true, true), Occur.MUST));
//		}   
		
		//BooleanQuery queryFilter = dao.getFullTextQuery((BooleanClause[])bcList.toArray(new BooleanClause[bcList.size()]));

//		System.out.println(queryFilter);
		
		// 设置排序（默认相识度排序）
		//FIXME 暂时不提供lucene检索
		//Sort sort = null;//new Sort(new SortField("updateDate", SortField.DOC, true));
		// 全文检索
		//dao.search(page, query, queryFilter, sort);
		// 关键字高亮
		//dao.keywordsHighlight(query, page.getList(), 30, "title");
		//dao.keywordsHighlight(query, page.getList(), 130, "description","articleData.content");
		
		return page;
	}

	public Page<Article> findColumnPage(Page<Article> articlePage, Article article, boolean b) {
		article.setPage(articlePage);
		articlePage.setList(dao.findColumnList(article));

		return articlePage;
	}

	public List<Map<String,Object>> columnSatistics(Article article) {
		return dao.columnSatistics(article);
	}

	@Transactional(readOnly = false)
	public void push(Article article) {
		articleDao.push(article);
	}

	public Page<Article> findGovernmentDetailPage(Page<Article> page, Article article) {
		String dateType = Optional.ofNullable(article.getDateType()).orElseGet(() -> {return "";});
		if (dateType.equals("1")){	//月度查询
			article.setYear(null);
			article.setBeginDate(null);
			article.setEndDate(null);
		}else if (dateType.equals("3")){	//时间段查询
			article.setYear(null);
			article.setMonth(null);
			article.setBeginDate(com.thinkgem.jeesite.common.utils.DateUtils.parseDate(article.getDateStart()));
			article.setEndDate(com.thinkgem.jeesite.common.utils.DateUtils.parseDate(article.getDateEnd()));
		}else {	//年度查询
			article.setMonth(null);
			article.setBeginDate(null);
			article.setEndDate(null);
		}

		switch (article.getCompany()){
			case "北海处":
				article.setCompany("156");
				break;
			case "柳州处":
				article.setCompany("95");
				break;
			case "南宁处":
				article.setCompany("34");
				break;
				default:
					article.setCompany("top");
					break;
		}

		article.setPage(page);
		page.setList(dao.findGovernmentList(article));
		return page;
	}

	/**
	 * 统计分析 统计保密工作
	 * @param article
	 * @return
	 */
	public List<Map<String, Object>> countSecrecy(Article article) {

		return dao.countSecrecy(article);
	}

	/**
	 * 统计分析	保密工作明细
	 * @param page
	 * @param article
	 * @return
	 */
	public Page<Article> findSecrecyDetailPage(Page<Article> page, Article article) {
		String dateType = Optional.ofNullable(article.getDateType()).orElseGet(() -> {return "";});
		if (dateType.equals("1")){	//月度查询
			article.setYear(null);
			article.setBeginDate(null);
			article.setEndDate(null);
		}else if (dateType.equals("3")){	//时间段查询
			article.setYear(null);
			article.setMonth(null);
			article.setBeginDate(com.thinkgem.jeesite.common.utils.DateUtils.parseDate(article.getDateStart()));
			article.setEndDate(com.thinkgem.jeesite.common.utils.DateUtils.parseDate(article.getDateEnd()));
		}else {	//年度查询
			article.setMonth(null);
			article.setBeginDate(null);
			article.setEndDate(null);
		}

		switch (article.getCompany()){
			case "北海处":
				article.setCompany("156");
				break;
			case "柳州处":
				article.setCompany("95");
				break;
			case "南宁处":
				article.setCompany("34");
				break;
			default:
				article.setCompany("top");
				break;
		}

		article.setPage(page);
		page.setList(dao.findSecrecyDetailList(article));
		return page;
	}

	public List<String> selectAllYear(){
		return articleDao.selectAllYear();
	}

	public Integer selectNumber(String dyId,String yearL,String yearT,String idNumber){
		return articleDao.selectNumber(dyId,yearL,yearT,idNumber);
	}
}
