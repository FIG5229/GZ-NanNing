/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairWorkSummaryDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairWorkSummary;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 工委工作总结Service
 * @author cecil.li
 * @version 2019-11-07
 */
@Service
@Transactional(readOnly = true)
public class AffairWorkSummaryService extends CrudService<AffairWorkSummaryDao, AffairWorkSummary> {

	public AffairWorkSummary get(String id) {
		return super.get(id);
	}
	
	public List<AffairWorkSummary> findList(AffairWorkSummary affairWorkSummary) {
		return super.findList(affairWorkSummary);
	}
	
	public Page<AffairWorkSummary> findPage(Page<AffairWorkSummary> page, AffairWorkSummary affairWorkSummary) {
		affairWorkSummary.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairWorkSummary);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairWorkSummary affairWorkSummary) {
		affairWorkSummary.setMainContent(StringEscapeUtils.unescapeHtml4(affairWorkSummary.getMainContent()));
		super.save(affairWorkSummary);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairWorkSummary affairWorkSummary) {
		super.delete(affairWorkSummary);
	}

	public Map<String, Object> findInfoByCreateOrgId(String id) {
		return dao.findInfoByCreateOrgId(id);
	}

	public Map<String, Object> findInfoByCreateOrgIds(List<String> ids) {
		return dao.findInfoByCreateOrgIds(ids);
	}

	public int getCount(String id, String startTime, String endTime, String year){
		return dao.getCount(id, startTime, endTime, year);
	}
}