/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.exam.entity.ExamScoreWorkItem;
import com.thinkgem.jeesite.modules.exam.dao.ExamScoreWorkItemDao;

/**
 * 得分制工作项管理Service
 * @author tom.fu
 * @version 2021-03-04
 */
@Service
@Transactional(readOnly = true)
public class ExamScoreWorkItemService extends CrudService<ExamScoreWorkItemDao, ExamScoreWorkItem> {

	public ExamScoreWorkItem get(String id) {
		return super.get(id);
	}
	
	public List<ExamScoreWorkItem> findList(ExamScoreWorkItem examScoreWorkItem) {
		return super.findList(examScoreWorkItem);
	}
	
	public Page<ExamScoreWorkItem> findPage(Page<ExamScoreWorkItem> page, ExamScoreWorkItem examScoreWorkItem) {
		return super.findPage(page, examScoreWorkItem);
	}
	
	@Transactional(readOnly = false)
	public void save(ExamScoreWorkItem examScoreWorkItem) {
		super.save(examScoreWorkItem);
	}
	
	@Transactional(readOnly = false)
	public void delete(ExamScoreWorkItem examScoreWorkItem) {
		super.delete(examScoreWorkItem);
	}
	
}