/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.exam.entity.ExamPersonsAssignRuleChild;
import com.thinkgem.jeesite.modules.exam.dao.ExamPersonsAssignRuleChildDao;

/**
 * 考评人员分配规则子表管理Service
 * @author bradley.zhao
 * @version 2020-03-21
 */
@Service
@Transactional(readOnly = true)
public class ExamPersonsAssignRuleChildService extends CrudService<ExamPersonsAssignRuleChildDao, ExamPersonsAssignRuleChild> {

	public ExamPersonsAssignRuleChild get(String id) {
		return super.get(id);
	}
	
	public List<ExamPersonsAssignRuleChild> findList(ExamPersonsAssignRuleChild examPersonsAssignRuleChild) {
		return super.findList(examPersonsAssignRuleChild);
	}
	
	public Page<ExamPersonsAssignRuleChild> findPage(Page<ExamPersonsAssignRuleChild> page, ExamPersonsAssignRuleChild examPersonsAssignRuleChild) {
		return super.findPage(page, examPersonsAssignRuleChild);
	}
	
	@Transactional(readOnly = false)
	public void save(ExamPersonsAssignRuleChild examPersonsAssignRuleChild) {
		super.save(examPersonsAssignRuleChild);
	}
	
	@Transactional(readOnly = false)
	public void delete(ExamPersonsAssignRuleChild examPersonsAssignRuleChild) {
		super.delete(examPersonsAssignRuleChild);
	}
	
}