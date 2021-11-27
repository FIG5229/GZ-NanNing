/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairTuanzuzhiResearchArticleDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairTuanzuzhiResearchArticle;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 调研文章Service
 * @author cecil.li
 * @version 2019-11-07
 */
@Service
@Transactional(readOnly = true)
public class AffairTuanzuzhiResearchArticleService extends CrudService<AffairTuanzuzhiResearchArticleDao, AffairTuanzuzhiResearchArticle> {

	public AffairTuanzuzhiResearchArticle get(String id) {
		return super.get(id);
	}
	
	public List<AffairTuanzuzhiResearchArticle> findList(AffairTuanzuzhiResearchArticle affairTuanzuzhiResearchArticle) {
		return super.findList(affairTuanzuzhiResearchArticle);
	}
	
	public Page<AffairTuanzuzhiResearchArticle> findPage(Page<AffairTuanzuzhiResearchArticle> page, AffairTuanzuzhiResearchArticle affairTuanzuzhiResearchArticle) {
		affairTuanzuzhiResearchArticle.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairTuanzuzhiResearchArticle);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairTuanzuzhiResearchArticle affairTuanzuzhiResearchArticle) {
		affairTuanzuzhiResearchArticle.setMainContent(StringEscapeUtils.unescapeHtml4(affairTuanzuzhiResearchArticle.getMainContent()));
		super.save(affairTuanzuzhiResearchArticle);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairTuanzuzhiResearchArticle affairTuanzuzhiResearchArticle) {
		super.delete(affairTuanzuzhiResearchArticle);
	}

	public Map<String, Object> findInfoByCreateOrgId(String id) {
		return dao.findInfoByCreateOrgId(id);
	}

	public Map<String, Object> findInfoByCreateOrgIds(List<String> ids) {
		return dao.findInfoByCreateOrgIds(ids);
	}
}