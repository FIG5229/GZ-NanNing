/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairCourseClass;
import com.thinkgem.jeesite.modules.affair.dao.AffairCourseClassDao;

/**
 * 课程研发Service
 * @author alan.wu
 * @version 2020-08-06
 */
@Service
@Transactional(readOnly = true)
public class AffairCourseClassService extends CrudService<AffairCourseClassDao, AffairCourseClass> {

	public AffairCourseClass get(String id) {
		return super.get(id);
	}
	
	public List<AffairCourseClass> findList(AffairCourseClass affairCourseClass) {
		return super.findList(affairCourseClass);
	}
	
	public Page<AffairCourseClass> findPage(Page<AffairCourseClass> page, AffairCourseClass affairCourseClass) {
		return super.findPage(page, affairCourseClass);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairCourseClass affairCourseClass) {
		super.save(affairCourseClass);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairCourseClass affairCourseClass) {
		super.delete(affairCourseClass);
	}
	
}