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
import com.thinkgem.jeesite.modules.affair.entity.AffairCourseTeacher;
import com.thinkgem.jeesite.modules.affair.dao.AffairCourseTeacherDao;

/**
 * 辅导教官Service
 * @author alan.wu
 * @version 2020-08-05
 */
@Service
@Transactional(readOnly = true)
public class AffairCourseTeacherService extends CrudService<AffairCourseTeacherDao, AffairCourseTeacher> {

	@Autowired
	private AffairCourseTeacherDao affairCourseTeacherDao;

	public AffairCourseTeacher get(String id) {
		return super.get(id);
	}
	
	public List<AffairCourseTeacher> findList(AffairCourseTeacher affairCourseTeacher) {
		return super.findList(affairCourseTeacher);
	}
	
	public Page<AffairCourseTeacher> findPage(Page<AffairCourseTeacher> page, AffairCourseTeacher affairCourseTeacher) {
		return super.findPage(page, affairCourseTeacher);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairCourseTeacher affairCourseTeacher) {
		super.save(affairCourseTeacher);
	}

	@Transactional(readOnly = false)
	public void delete(AffairCourseTeacher affairCourseTeacher) {
		super.delete(affairCourseTeacher);
	}


	@Transactional(readOnly = false)
	public List<AffairCourseTeacher> selectBean(String id){
		List<AffairCourseTeacher> affairCourseTeacher = affairCourseTeacherDao.selectBean(id);
		return affairCourseTeacher;

	}

}