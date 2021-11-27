/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairCourseResource;
import com.thinkgem.jeesite.modules.affair.dao.AffairCourseResourceDao;

/**
 * 课程资源Service
 * @author alan.wu
 * @version 2020-07-31
 */
@Service
@Transactional(readOnly = true)
public class AffairCourseResourceService extends CrudService<AffairCourseResourceDao, AffairCourseResource> {

	@Autowired
	AffairCourseResourceDao affairCourseResourceDao;


	public AffairCourseResource get(String id) {
		return super.get(id);
	}
	
	public List<AffairCourseResource> findList(AffairCourseResource affairCourseResource) {
		return super.findList(affairCourseResource);
	}
	
	public Page<AffairCourseResource> findPage(Page<AffairCourseResource> page, AffairCourseResource affairCourseResource) {
		affairCourseResource.getSqlMap().put("dsf",dataScopeFilter(UserUtils.getUser(),"o","a"));
		return super.findPage(page, affairCourseResource);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairCourseResource affairCourseResource) {
		super.save(affairCourseResource);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairCourseResource affairCourseResource) {
		super.delete(affairCourseResource);
	}
	
	@Transactional(readOnly = false)
	public String findType(String id) {
		String type = affairCourseResourceDao.findType(id);
		return type;
	}

	@Transactional(readOnly = false)
	public String findClassId(String id) {
		String classId = affairCourseResourceDao.findClassId(id);
		return classId;
	}



}