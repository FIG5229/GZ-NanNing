/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairResearchArticleDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairResearchArticle;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 党支部-专报简报/调研文章Service
 * @author eav.liu
 * @version 2019-11-09
 */
@Service
@Transactional(readOnly = true)
public class AffairResearchArticleService extends CrudService<AffairResearchArticleDao, AffairResearchArticle> {

	public AffairResearchArticle get(String id) {
		return super.get(id);
	}
	
	public List<AffairResearchArticle> findList(AffairResearchArticle affairResearchArticle) {
		return super.findList(affairResearchArticle);
	}
	
	public Page<AffairResearchArticle> findPage(Page<AffairResearchArticle> page, AffairResearchArticle affairResearchArticle) {
		affairResearchArticle.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairResearchArticle);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairResearchArticle affairResearchArticle) {
		affairResearchArticle.setContent(StringEscapeUtils.unescapeHtml4(affairResearchArticle.getContent()));
		super.save(affairResearchArticle);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairResearchArticle affairResearchArticle) {
		super.delete(affairResearchArticle);
	}
	
}