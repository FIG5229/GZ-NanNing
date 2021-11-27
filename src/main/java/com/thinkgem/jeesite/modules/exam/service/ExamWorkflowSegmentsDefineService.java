/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.exam.dao.ExamWorkflowSegmentsDefineDao;
import com.thinkgem.jeesite.modules.exam.entity.ExamWorkflowDefine;
import com.thinkgem.jeesite.modules.exam.entity.ExamWorkflowSegmentsDefine;
import com.thinkgem.jeesite.modules.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 标准考评流程环节定义Service
 * @author eav.liu
 * @version 2019-12-09
 */
@Service
@Transactional(readOnly = true)
public class ExamWorkflowSegmentsDefineService extends CrudService<ExamWorkflowSegmentsDefineDao, ExamWorkflowSegmentsDefine> {

	@Autowired
	private ExamWorkflowSegmentsDefineDao examWorkflowSegmentsDefineDao;

	public ExamWorkflowSegmentsDefine get(String id) {
		return super.get(id);
	}
	
	public List<ExamWorkflowSegmentsDefine> findList(ExamWorkflowSegmentsDefine examWorkflowSegmentsDefine) {
		return super.findList(examWorkflowSegmentsDefine);
	}
	
	public Page<ExamWorkflowSegmentsDefine> findPage(Page<ExamWorkflowSegmentsDefine> page, ExamWorkflowSegmentsDefine examWorkflowSegmentsDefine) {
		return super.findPage(page, examWorkflowSegmentsDefine);
	}
	
	@Transactional(readOnly = false)
	public void save(ExamWorkflowSegmentsDefine examWorkflowSegmentsDefine) {
		super.save(examWorkflowSegmentsDefine);
	}
	
	@Transactional(readOnly = false)
	public void delete(ExamWorkflowSegmentsDefine examWorkflowSegmentsDefine) {
		super.delete(examWorkflowSegmentsDefine);
	}

	/**
	 * 根据流程类型type返回环节集合
	 * @param type
	 * @return
	 */
	public List<ExamWorkflowSegmentsDefine> findByType(String type){
		List<ExamWorkflowSegmentsDefine> list = examWorkflowSegmentsDefineDao.findByType(type);
		return  list;
	}

	public List<User> queryPoliceDatas(ExamWorkflowDefine define) {
		return dao.queryPoliceDatas(define);
	}
}