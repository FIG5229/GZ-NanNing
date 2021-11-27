/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairExamEntering;
import com.thinkgem.jeesite.modules.affair.dao.AffairExamEnteringDao;

/**
 * 考试成绩录入Service
 * @author alan.wu
 * @version 2020-07-15
 */
@Service
@Transactional(readOnly = true)
public class AffairExamEnteringService extends CrudService<AffairExamEnteringDao, AffairExamEntering> {

	public AffairExamEntering get(String id) {
		return super.get(id);
	}
	
	public List<AffairExamEntering> findList(AffairExamEntering affairExamEntering) {
		return super.findList(affairExamEntering);
	}
	
	public Page<AffairExamEntering> findPage(Page<AffairExamEntering> page, AffairExamEntering affairExamEntering) {
		return super.findPage(page, affairExamEntering);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairExamEntering affairExamEntering) {
		super.save(affairExamEntering);
	}

	@Transactional(readOnly = false)
	public void delete(AffairExamEntering affairExamEntering) {
		super.delete(affairExamEntering);
	}
	
}