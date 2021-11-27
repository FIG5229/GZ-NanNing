/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairQualification;
import com.thinkgem.jeesite.modules.affair.dao.AffairQualificationDao;

/**
 * 警务技术任职资格管理Service
 * @author mason.xv
 * @version 2019-11-06
 */
@Service
@Transactional(readOnly = true)
public class AffairQualificationService extends CrudService<AffairQualificationDao, AffairQualification> {

	public AffairQualification get(String id) {
		return super.get(id);
	}
	
	public List<AffairQualification> findList(AffairQualification affairQualification) {
		return super.findList(affairQualification);
	}
	
	public Page<AffairQualification> findPage(Page<AffairQualification> page, AffairQualification affairQualification) {
		return super.findPage(page, affairQualification);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairQualification affairQualification) {
		super.save(affairQualification);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairQualification affairQualification) {
		super.delete(affairQualification);
	}
	
}