/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.exam.entity.ExamPushHistory;
import com.thinkgem.jeesite.modules.exam.dao.ExamPushHistoryDao;

/**
 * 绩效考评项推送Service
 * @author daniel.liu
 * @version 2020-11-04
 */
@Service
@Transactional(readOnly = true)
public class ExamPushHistoryService extends CrudService<ExamPushHistoryDao, ExamPushHistory> {

	public ExamPushHistory get(String id) {
		return super.get(id);
	}
	
	public List<ExamPushHistory> findList(ExamPushHistory examPushHistory) {
		return super.findList(examPushHistory);
	}
	
	public Page<ExamPushHistory> findPage(Page<ExamPushHistory> page, ExamPushHistory examPushHistory) {
		return super.findPage(page, examPushHistory);
	}
	
	@Transactional(readOnly = false)
	public void save(ExamPushHistory examPushHistory) {
		super.save(examPushHistory);
	}
	
	@Transactional(readOnly = false)
	public void delete(ExamPushHistory examPushHistory) {
		super.delete(examPushHistory);
	}
	
}