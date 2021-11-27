/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairEvaluationCriteria;
import com.thinkgem.jeesite.modules.affair.dao.AffairEvaluationCriteriaDao;

/**
 * 测评标准Service
 * @author daniel.liu
 * @version 2020-07-02
 */
@Service
@Transactional(readOnly = true)
public class AffairEvaluationCriteriaService extends CrudService<AffairEvaluationCriteriaDao, AffairEvaluationCriteria> {

	public AffairEvaluationCriteria get(String id) {
		return super.get(id);
	}
	
	public List<AffairEvaluationCriteria> findList(AffairEvaluationCriteria affairEvaluationCriteria) {
		return super.findList(affairEvaluationCriteria);
	}
	
	public Page<AffairEvaluationCriteria> findPage(Page<AffairEvaluationCriteria> page, AffairEvaluationCriteria affairEvaluationCriteria) {
		affairEvaluationCriteria.getSqlMap().put("dsf",dataScopeFilter(UserUtils.getUser(),"o","a"));
		return super.findPage(page, affairEvaluationCriteria);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairEvaluationCriteria affairEvaluationCriteria) {
		super.save(affairEvaluationCriteria);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairEvaluationCriteria affairEvaluationCriteria) {
		super.delete(affairEvaluationCriteria);
	}
	
}