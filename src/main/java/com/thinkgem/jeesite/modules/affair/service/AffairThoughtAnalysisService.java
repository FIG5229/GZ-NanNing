/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairThoughtAnalysisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairThoughtAnalysis;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 思想状况分析Service
 * @author cecil.li
 * @version 2019-11-07
 */
@Service
@Transactional(readOnly = true)
public class AffairThoughtAnalysisService extends CrudService<AffairThoughtAnalysisDao, AffairThoughtAnalysis> {

	public AffairThoughtAnalysis get(String id) {
		return super.get(id);
	}
	
	public List<AffairThoughtAnalysis> findList(AffairThoughtAnalysis affairThoughtAnalysis) {
		return super.findList(affairThoughtAnalysis);
	}
	
	public Page<AffairThoughtAnalysis> findPage(Page<AffairThoughtAnalysis> page, AffairThoughtAnalysis affairThoughtAnalysis) {
		affairThoughtAnalysis.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairThoughtAnalysis);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairThoughtAnalysis affairThoughtAnalysis) {
		affairThoughtAnalysis.setMainContent(StringEscapeUtils.unescapeHtml4(affairThoughtAnalysis.getMainContent()));
		super.save(affairThoughtAnalysis);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairThoughtAnalysis affairThoughtAnalysis) {
		super.delete(affairThoughtAnalysis);
	}

	public Map<String, Object> findInfoByCreateOrgId(String id) {
		return dao.findInfoByCreateOrgId(id);
	}

	public Map<String, Object> findInfoByCreateOrgIds(List<String> ids) {
		return dao.findInfoByCreateOrgIds(ids);
	}

	public int getCount(String id, String unit, String startTime,String endTime,String year){
		return dao.getCount(id,unit,startTime,endTime,year);
	}

	public List<String> findUnitId(String id){
		return dao.findUnitId(id);
	}
}