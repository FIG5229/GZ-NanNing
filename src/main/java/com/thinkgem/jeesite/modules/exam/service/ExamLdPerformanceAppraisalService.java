/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.exam.entity.ExamLdPerformanceAppraisal;
import com.thinkgem.jeesite.modules.exam.dao.ExamLdPerformanceAppraisalDao;

/**
 * 绩效考核情况Service
 * @author cecil.li
 * @version 2020-01-14
 */
@Service
@Transactional(readOnly = true)
public class ExamLdPerformanceAppraisalService extends CrudService<ExamLdPerformanceAppraisalDao, ExamLdPerformanceAppraisal> {

	public ExamLdPerformanceAppraisal get(String id) {
		return super.get(id);
	}
	
	public List<ExamLdPerformanceAppraisal> findList(ExamLdPerformanceAppraisal examLdPerformanceAppraisal) {
		return super.findList(examLdPerformanceAppraisal);
	}
	
	public Page<ExamLdPerformanceAppraisal> findPage(Page<ExamLdPerformanceAppraisal> page, ExamLdPerformanceAppraisal examLdPerformanceAppraisal) {
		return super.findPage(page, examLdPerformanceAppraisal);
	}
	
	@Transactional(readOnly = false)
	public void save(ExamLdPerformanceAppraisal examLdPerformanceAppraisal) {
		super.save(examLdPerformanceAppraisal);
	}
	
	@Transactional(readOnly = false)
	public void delete(ExamLdPerformanceAppraisal examLdPerformanceAppraisal) {
		super.delete(examLdPerformanceAppraisal);
	}
	
}