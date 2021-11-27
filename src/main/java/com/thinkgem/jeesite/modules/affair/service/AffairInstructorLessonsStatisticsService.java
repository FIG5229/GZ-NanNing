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
import com.thinkgem.jeesite.modules.affair.entity.AffairInstructorLessonsStatistics;
import com.thinkgem.jeesite.modules.affair.dao.AffairInstructorLessonsStatisticsDao;

/**
 * 教官授课统计Service
 * @author kevin.jia
 * @version 2020-08-11
 */
@Service
@Transactional(readOnly = true)
public class AffairInstructorLessonsStatisticsService extends CrudService<AffairInstructorLessonsStatisticsDao, AffairInstructorLessonsStatistics> {

	public AffairInstructorLessonsStatistics get(String id) {
		return super.get(id);
	}
	
	public List<AffairInstructorLessonsStatistics> findList(AffairInstructorLessonsStatistics affairInstructorLessonsStatistics) {
		return super.findList(affairInstructorLessonsStatistics);
	}
	
	public Page<AffairInstructorLessonsStatistics> findPage(Page<AffairInstructorLessonsStatistics> page, AffairInstructorLessonsStatistics affairInstructorLessonsStatistics) {
		affairInstructorLessonsStatistics.getSqlMap().put("dsf",dataScopeFilter(UserUtils.getUser(),"o","a"));
		return super.findPage(page, affairInstructorLessonsStatistics);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairInstructorLessonsStatistics affairInstructorLessonsStatistics) {
		super.save(affairInstructorLessonsStatistics);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairInstructorLessonsStatistics affairInstructorLessonsStatistics) {
		super.delete(affairInstructorLessonsStatistics);
	}
	
}