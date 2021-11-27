/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.exam.entity.ExamPerformanceAppraisal;
import com.thinkgem.jeesite.modules.exam.dao.ExamPerformanceAppraisalDao;

/**
 * 部门绩效考核情况Service
 * @author cecil.li
 * @version 2020-01-14
 */
@Service
@Transactional(readOnly = true)
public class ExamPerformanceAppraisalService extends CrudService<ExamPerformanceAppraisalDao, ExamPerformanceAppraisal> {

	public ExamPerformanceAppraisal get(String id) {
		return super.get(id);
	}
	
	public List<ExamPerformanceAppraisal> findList(ExamPerformanceAppraisal examPerformanceAppraisal) {
		return super.findList(examPerformanceAppraisal);
	}
	
	public Page<ExamPerformanceAppraisal> findPage(Page<ExamPerformanceAppraisal> page, ExamPerformanceAppraisal examPerformanceAppraisal) {
		return super.findPage(page, examPerformanceAppraisal);
	}
	
	@Transactional(readOnly = false)
	public void save(ExamPerformanceAppraisal examPerformanceAppraisal) {
		super.save(examPerformanceAppraisal);
	}
	
	@Transactional(readOnly = false)
	public void delete(ExamPerformanceAppraisal examPerformanceAppraisal) {
		super.delete(examPerformanceAppraisal);
	}
	
}