/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairCourseBuildStatistical;
import com.thinkgem.jeesite.modules.affair.dao.AffairCourseBuildStatisticalDao;

/**
 * 铁路公安机关课程建设情况统计Service
 * @author kevin.jia
 * @version 2020-08-13
 */
@Service
@Transactional(readOnly = true)
public class AffairCourseBuildStatisticalService extends CrudService<AffairCourseBuildStatisticalDao, AffairCourseBuildStatistical> {
	@Autowired
	private AffairCourseBuildStatisticalDao affairCourseBuildStatisticalDao;

	public AffairCourseBuildStatistical get(String id) {
		return super.get(id);
	}
	
	public List<AffairCourseBuildStatistical> findList(AffairCourseBuildStatistical affairCourseBuildStatistical) {
		return super.findList(affairCourseBuildStatistical);
	}
	
	public Page<AffairCourseBuildStatistical> findPage(Page<AffairCourseBuildStatistical> page, AffairCourseBuildStatistical affairCourseBuildStatistical) {
		return super.findPage(page, affairCourseBuildStatistical);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairCourseBuildStatistical affairCourseBuildStatistical) {
		super.save(affairCourseBuildStatistical);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairCourseBuildStatistical affairCourseBuildStatistical) {
		super.delete(affairCourseBuildStatistical);
	}
	
}