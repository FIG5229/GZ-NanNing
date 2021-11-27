/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairInstructorSpecStatistics;
import com.thinkgem.jeesite.modules.affair.dao.AffairInstructorSpecStatisticsDao;

/**
 * 教官特长统计报表Service
 * @author kevin.jia
 * @version 2020-08-10
 */
@Service
@Transactional(readOnly = true)
public class AffairInstructorSpecStatisticsService extends CrudService<AffairInstructorSpecStatisticsDao, AffairInstructorSpecStatistics> {

	public AffairInstructorSpecStatistics get(String id) {
		return super.get(id);
	}
	
	public List<AffairInstructorSpecStatistics> findList(AffairInstructorSpecStatistics affairInstructorSpecStatistics) {
		return super.findList(affairInstructorSpecStatistics);
	}
	
	public Page<AffairInstructorSpecStatistics> findPage(Page<AffairInstructorSpecStatistics> page, AffairInstructorSpecStatistics affairInstructorSpecStatistics) {
		affairInstructorSpecStatistics.getSqlMap().put("dsf",dataScopeFilter(UserUtils.getUser(),"o","a"));
		return super.findPage(page, affairInstructorSpecStatistics);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairInstructorSpecStatistics affairInstructorSpecStatistics) {
		super.save(affairInstructorSpecStatistics);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairInstructorSpecStatistics affairInstructorSpecStatistics) {
		super.delete(affairInstructorSpecStatistics);
	}
	
}