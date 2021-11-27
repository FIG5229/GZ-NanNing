/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.exam.entity.ExamSystemInitCheack;
import com.thinkgem.jeesite.modules.exam.dao.ExamSystemInitCheackDao;

/**
 * 系统公示Service
 * @author mason.xv
 * @version 2019-12-13
 */
@Service
@Transactional(readOnly = true)
public class ExamSystemInitCheackService extends CrudService<ExamSystemInitCheackDao, ExamSystemInitCheack> {

	public ExamSystemInitCheack get(String id) {
		return super.get(id);
	}
	
	public List<ExamSystemInitCheack> findList(ExamSystemInitCheack examSystemInitCheack) {
		return super.findList(examSystemInitCheack);
	}
	
	public Page<ExamSystemInitCheack> findPage(Page<ExamSystemInitCheack> page, ExamSystemInitCheack examSystemInitCheack) {
		return super.findPage(page, examSystemInitCheack);
	}
	
	@Transactional(readOnly = false)
	public void save(ExamSystemInitCheack examSystemInitCheack) {
		super.save(examSystemInitCheack);
	}
	
	@Transactional(readOnly = false)
	public void delete(ExamSystemInitCheack examSystemInitCheack) {
		super.delete(examSystemInitCheack);
	}
	
}