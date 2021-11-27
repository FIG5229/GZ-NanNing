/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.exam.entity.ExamSystemDisplay;
import com.thinkgem.jeesite.modules.exam.dao.ExamSystemDisplayDao;

/**
 * 系统公示Service
 * @author mason.xv
 * @version 2019-12-13
 */
@Service
@Transactional(readOnly = true)
public class ExamSystemDisplayService extends CrudService<ExamSystemDisplayDao, ExamSystemDisplay> {

	public ExamSystemDisplay get(String id) {
		return super.get(id);
	}
	
	public List<ExamSystemDisplay> findList(ExamSystemDisplay examSystemDisplay) {
		return super.findList(examSystemDisplay);
	}
	
	public Page<ExamSystemDisplay> findPage(Page<ExamSystemDisplay> page, ExamSystemDisplay examSystemDisplay) {
		return super.findPage(page, examSystemDisplay);
	}
	
	@Transactional(readOnly = false)
	public void save(ExamSystemDisplay examSystemDisplay) {
		super.save(examSystemDisplay);
	}
	
	@Transactional(readOnly = false)
	public void delete(ExamSystemDisplay examSystemDisplay) {
		super.delete(examSystemDisplay);
	}
	
}