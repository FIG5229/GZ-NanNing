/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.exam.entity.ExamPersonsAssignRule;
import com.thinkgem.jeesite.modules.exam.dao.ExamPersonsAssignRuleDao;
import com.thinkgem.jeesite.modules.exam.entity.ExamPersonsAssignRuleChild;
import com.thinkgem.jeesite.modules.exam.dao.ExamPersonsAssignRuleChildDao;

/**
 * 考评人员分配规则管理Service
 * @author bradley.zhao
 * @version 2020-03-21
 */
@Service
@Transactional(readOnly = true)
public class ExamPersonsAssignRuleService extends CrudService<ExamPersonsAssignRuleDao, ExamPersonsAssignRule> {

	@Autowired
	private ExamPersonsAssignRuleChildDao examPersonsAssignRuleChildDao;
	
	public ExamPersonsAssignRule get(String id) {
		ExamPersonsAssignRule examPersonsAssignRule = super.get(id);
		ExamPersonsAssignRuleChild examPersonsAssignRuleChild = new ExamPersonsAssignRuleChild();
		examPersonsAssignRuleChild.setRuleId(examPersonsAssignRule.getId());
		examPersonsAssignRule.setExamPersonsAssignRuleChildList(examPersonsAssignRuleChildDao.findList(examPersonsAssignRuleChild));
		return examPersonsAssignRule;
	}
	
	public List<ExamPersonsAssignRule> findList(ExamPersonsAssignRule examPersonsAssignRule) {
		return super.findList(examPersonsAssignRule);
	}
	
	public Page<ExamPersonsAssignRule> findPage(Page<ExamPersonsAssignRule> page, ExamPersonsAssignRule examPersonsAssignRule) {
		return super.findPage(page, examPersonsAssignRule);
	}
	
	@Transactional(readOnly = false)
	public void save(ExamPersonsAssignRule examPersonsAssignRule) {
		super.save(examPersonsAssignRule);
		for (ExamPersonsAssignRuleChild examPersonsAssignRuleChild : examPersonsAssignRule.getExamPersonsAssignRuleChildList()){
			if (examPersonsAssignRuleChild.getId() == null){
				continue;
			}
			if (ExamPersonsAssignRuleChild.DEL_FLAG_NORMAL.equals(examPersonsAssignRuleChild.getDelFlag())){
				if (StringUtils.isBlank(examPersonsAssignRuleChild.getId())){
					examPersonsAssignRuleChild.setRuleId(examPersonsAssignRule.getId());
					examPersonsAssignRuleChild.preInsert();
					examPersonsAssignRuleChildDao.insert(examPersonsAssignRuleChild);
				}else{
					examPersonsAssignRuleChild.preUpdate();
					examPersonsAssignRuleChildDao.update(examPersonsAssignRuleChild);
				}
			}else{
				examPersonsAssignRuleChildDao.delete(examPersonsAssignRuleChild);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(ExamPersonsAssignRule examPersonsAssignRule) {
		super.delete(examPersonsAssignRule);
		examPersonsAssignRuleChildDao.delete(new ExamPersonsAssignRuleChild(examPersonsAssignRule.getId()));
	}
	
}