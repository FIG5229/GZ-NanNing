/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.exam.entity.ExamSystemDisplayCheck;
import com.thinkgem.jeesite.modules.exam.dao.ExamSystemDisplayCheckDao;

/**
 * 系统最终审核公示Service
 * @author mason.xv
 * @version 2019-12-13
 */
@Service
@Transactional(readOnly = true)
public class ExamSystemDisplayCheckService extends CrudService<ExamSystemDisplayCheckDao, ExamSystemDisplayCheck> {

	public ExamSystemDisplayCheck get(String id) {
		return super.get(id);
	}
	
	public List<ExamSystemDisplayCheck> findList(ExamSystemDisplayCheck examSystemDisplayCheck) {
		return super.findList(examSystemDisplayCheck);
	}
	
	public Page<ExamSystemDisplayCheck> findPage(Page<ExamSystemDisplayCheck> page, ExamSystemDisplayCheck examSystemDisplayCheck) {
		return super.findPage(page, examSystemDisplayCheck);
	}
	
	@Transactional(readOnly = false)
	public void save(ExamSystemDisplayCheck examSystemDisplayCheck) {
		super.save(examSystemDisplayCheck);
	}
	
	@Transactional(readOnly = false)
	public void delete(ExamSystemDisplayCheck examSystemDisplayCheck) {
		super.delete(examSystemDisplayCheck);
	}
	
}