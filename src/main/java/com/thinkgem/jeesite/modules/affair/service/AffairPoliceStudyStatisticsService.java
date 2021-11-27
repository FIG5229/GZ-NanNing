/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairPoliceStudyStatistics;
import com.thinkgem.jeesite.modules.affair.dao.AffairPoliceStudyStatisticsDao;

/**
 * 民警学习统计报表Service
 * @author kevin.jia
 * @version 2020-08-11
 */
@Service
@Transactional(readOnly = true)
public class AffairPoliceStudyStatisticsService extends CrudService<AffairPoliceStudyStatisticsDao, AffairPoliceStudyStatistics> {

	public AffairPoliceStudyStatistics get(String id) {
		return super.get(id);
	}
	
	public List<AffairPoliceStudyStatistics> findList(AffairPoliceStudyStatistics affairPoliceStudyStatistics) {
		return super.findList(affairPoliceStudyStatistics);
	}
	
	public Page<AffairPoliceStudyStatistics> findPage(Page<AffairPoliceStudyStatistics> page, AffairPoliceStudyStatistics affairPoliceStudyStatistics) {
		return super.findPage(page, affairPoliceStudyStatistics);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairPoliceStudyStatistics affairPoliceStudyStatistics) {
		super.save(affairPoliceStudyStatistics);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairPoliceStudyStatistics affairPoliceStudyStatistics) {
		super.delete(affairPoliceStudyStatistics);
	}
	
}