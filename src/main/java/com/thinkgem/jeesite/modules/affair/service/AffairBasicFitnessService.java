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
import com.thinkgem.jeesite.modules.affair.entity.AffairBasicFitness;
import com.thinkgem.jeesite.modules.affair.dao.AffairBasicFitnessDao;

/**
 * 基本体能成绩Service
 * @author cecil.li
 * @version 2020-12-28
 */
@Service
@Transactional(readOnly = true)
public class AffairBasicFitnessService extends CrudService<AffairBasicFitnessDao, AffairBasicFitness> {

	public AffairBasicFitness get(String id) {
		return super.get(id);
	}
	
	public List<AffairBasicFitness> findList(AffairBasicFitness affairBasicFitness) {
		return super.findList(affairBasicFitness);
	}
	
	public Page<AffairBasicFitness> findPage(Page<AffairBasicFitness> page, AffairBasicFitness affairBasicFitness) {
		affairBasicFitness.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairBasicFitness);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairBasicFitness affairBasicFitness) {
		super.save(affairBasicFitness);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairBasicFitness affairBasicFitness) {
		super.delete(affairBasicFitness);
	}
	
}