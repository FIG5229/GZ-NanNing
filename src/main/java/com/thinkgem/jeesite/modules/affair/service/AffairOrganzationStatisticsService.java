/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairOrganzationStatistics;
import com.thinkgem.jeesite.modules.affair.dao.AffairOrganzationStatisticsDao;

/**
 * 机构部门学习统计Service
 * @author alan.wu
 * @version 2020-07-28
 */
@Service
@Transactional(readOnly = true)
public class AffairOrganzationStatisticsService extends CrudService<AffairOrganzationStatisticsDao, AffairOrganzationStatistics> {

	public AffairOrganzationStatistics get(String id) {
		return super.get(id);
	}
	
	public List<AffairOrganzationStatistics> findList(AffairOrganzationStatistics affairOrganzationStatistics) {
		return super.findList(affairOrganzationStatistics);
	}
	
	public Page<AffairOrganzationStatistics> findPage(Page<AffairOrganzationStatistics> page, AffairOrganzationStatistics affairOrganzationStatistics) {
		return super.findPage(page, affairOrganzationStatistics);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairOrganzationStatistics affairOrganzationStatistics) {
		super.save(affairOrganzationStatistics);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairOrganzationStatistics affairOrganzationStatistics) {
		super.delete(affairOrganzationStatistics);
	}
	
}