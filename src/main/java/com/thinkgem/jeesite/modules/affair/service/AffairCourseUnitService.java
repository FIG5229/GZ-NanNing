/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairCourseResourceDao;
import com.thinkgem.jeesite.modules.affair.dao.AffairCourseUnitDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairCourseResource;
import com.thinkgem.jeesite.modules.affair.entity.AffairCourseUnit;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 课程资源Service
 * @author alan.wu
 * @version 2020-07-31
 */
@Service
@Transactional(readOnly = true)
public class AffairCourseUnitService extends CrudService<AffairCourseUnitDao, AffairCourseUnit> {

	public AffairCourseUnit get(String id) {
		return super.get(id);
	}
	
	public List<AffairCourseUnit> findList(AffairCourseUnit affairCourseUnit) {
		return super.findList(affairCourseUnit);
	}
	
	public Page<AffairCourseUnit> findPage(Page<AffairCourseUnit> page, AffairCourseUnit affairCourseUnit) {
		return super.findPage(page, affairCourseUnit);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairCourseUnit affairCourseUnit) {
		super.save(affairCourseUnit);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairCourseUnit affairCourseUnit) {
		super.delete(affairCourseUnit);
	}
	
}