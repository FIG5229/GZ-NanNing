/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import com.thinkgem.jeesite.modules.affair.entity.AffairDocManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairCourseDocument;
import com.thinkgem.jeesite.modules.affair.dao.AffairCourseDocumentDao;

/**
 * 参考文档Service
 * @author alan.wu
 * @version 2020-08-05
 */
@Service
@Transactional(readOnly = true)
public class AffairCourseDocumentService extends CrudService<AffairCourseDocumentDao, AffairCourseDocument> {

	@Autowired
	private AffairCourseDocumentDao affairCourseDocumentDao;

	public AffairCourseDocument get(String id) {
		return super.get(id);
	}
	
	public List<AffairCourseDocument> findList(AffairCourseDocument affairCourseDocument) {
		return super.findList(affairCourseDocument);
	}
	
	public Page<AffairCourseDocument> findPage(Page<AffairCourseDocument> page, AffairCourseDocument affairCourseDocument) {
		return super.findPage(page, affairCourseDocument);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairCourseDocument affairCourseDocument) {
		super.save(affairCourseDocument);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairCourseDocument affairCourseDocument) {
		super.delete(affairCourseDocument);
	}

	@Transactional(readOnly = false)
	public  List<AffairCourseDocument> selectByClassId(String id) {
		return affairCourseDocumentDao.selectByClassId(id);
	}
}