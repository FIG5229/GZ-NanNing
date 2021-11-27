/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.exam.dao.ExamWorkflowSegmentGuanlianDao;
import com.thinkgem.jeesite.modules.exam.entity.ExamWorkflowDefine;
import com.thinkgem.jeesite.modules.exam.entity.ExamWorkflowSegmentGuanlian;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 考评流程环节关联Service
 * @author eav.liu
 * @version 2019-12-10
 */
@Service
@Transactional(readOnly = true)
public class ExamWorkflowSegmentGuanlianService extends CrudService<ExamWorkflowSegmentGuanlianDao, ExamWorkflowSegmentGuanlian> {

	@Autowired
	private ExamWorkflowSegmentGuanlianDao examWorkflowSegmentGuanlianDao;

	public ExamWorkflowSegmentGuanlian get(String id) {
		return super.get(id);
	}
	
	public List<ExamWorkflowSegmentGuanlian> findList(ExamWorkflowSegmentGuanlian examWorkflowSegmentGuanlian) {
		return super.findList(examWorkflowSegmentGuanlian);
	}
	
	public Page<ExamWorkflowSegmentGuanlian> findPage(Page<ExamWorkflowSegmentGuanlian> page, ExamWorkflowSegmentGuanlian examWorkflowSegmentGuanlian) {
		return super.findPage(page, examWorkflowSegmentGuanlian);
	}
	
	@Transactional(readOnly = false)
	public void save(ExamWorkflowSegmentGuanlian examWorkflowSegmentGuanlian) {
		super.save(examWorkflowSegmentGuanlian);
	}
	
	@Transactional(readOnly = false)
	public void delete(ExamWorkflowSegmentGuanlian examWorkflowSegmentGuanlian) {
		super.delete(examWorkflowSegmentGuanlian);
	}

	public List<ExamWorkflowSegmentGuanlian> findByWdIdAndType(ExamWorkflowDefine examWorkflowDefine){
		List<ExamWorkflowSegmentGuanlian> list = examWorkflowSegmentGuanlianDao.findByWdIdAndType(examWorkflowDefine.getId(), examWorkflowDefine.getFlowType());
		return list;
	}

	public ExamWorkflowSegmentGuanlian findByWdIdAndSegDefId(String workflowDefineId, String segDefId){
		return examWorkflowSegmentGuanlianDao.findByWdIdAndSegDefId(workflowDefineId, segDefId);
	}
}