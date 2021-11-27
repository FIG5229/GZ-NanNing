/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairIdeaAnalysisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairIdeaAnalysis;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 党员队伍思想状况分析Service
 * @author eav.liu
 * @version 2019-11-09
 */
@Service
@Transactional(readOnly = true)
public class AffairIdeaAnalysisService extends CrudService<AffairIdeaAnalysisDao, AffairIdeaAnalysis> {

	@Autowired
	private AffairIdeaAnalysisDao affairIdeaAnalysisDao;

	public AffairIdeaAnalysis get(String id) {
		return super.get(id);
	}
	
	public List<AffairIdeaAnalysis> findList(AffairIdeaAnalysis affairIdeaAnalysis) {
		return super.findList(affairIdeaAnalysis);
	}
	
	public Page<AffairIdeaAnalysis> findPage(Page<AffairIdeaAnalysis> page, AffairIdeaAnalysis affairIdeaAnalysis) {
		affairIdeaAnalysis.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairIdeaAnalysis);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairIdeaAnalysis affairIdeaAnalysis) {
		affairIdeaAnalysis.setContent(StringEscapeUtils.unescapeHtml4(affairIdeaAnalysis.getContent()));
		super.save(affairIdeaAnalysis);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairIdeaAnalysis affairIdeaAnalysis) {
		super.delete(affairIdeaAnalysis);
	}

	@Transactional(readOnly = false)
	public void shenHeSave(AffairIdeaAnalysis affairIdeaAnalysis) {
		affairIdeaAnalysisDao.shenHeSave(affairIdeaAnalysis);
	}

	public int findCountByMonth(String year,String quarterly, String org, String orgId){
		return affairIdeaAnalysisDao.findCountByMonth(year,quarterly,org,orgId);
	}

	public Integer selectNumber(String yearL,String yearT,String idNumber){
		return affairIdeaAnalysisDao.selectNumber(yearL,yearT,idNumber);
	}
	public Integer selectLeadNumber(String time,String idNumber){
		return affairIdeaAnalysisDao.selectLeadNumber(time,idNumber);
	}


}