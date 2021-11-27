/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import com.thinkgem.jeesite.modules.affair.entity.AffairDifficultHelp;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairDeclarationTrivingExcellence;
import com.thinkgem.jeesite.modules.affair.dao.AffairDeclarationTrivingExcellenceDao;

/**
 * 创先争优申报Service
 * @author daniel.liu
 * @version 2020-06-05
 */
@Service
@Transactional(readOnly = true)
public class AffairDeclarationTrivingExcellenceService extends CrudService<AffairDeclarationTrivingExcellenceDao, AffairDeclarationTrivingExcellence> {

	@Autowired
	private AffairDeclarationTrivingExcellenceDao affairDeclarationTrivingExcellenceDao;

	public AffairDeclarationTrivingExcellence get(String id) {
		return super.get(id);
	}
	
	public List<AffairDeclarationTrivingExcellence> findList(AffairDeclarationTrivingExcellence affairDeclarationTrivingExcellence) {
		return super.findList(affairDeclarationTrivingExcellence);
	}
	
	public Page<AffairDeclarationTrivingExcellence> findPage(Page<AffairDeclarationTrivingExcellence> page, AffairDeclarationTrivingExcellence affairDeclarationTrivingExcellence) {
		affairDeclarationTrivingExcellence.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairDeclarationTrivingExcellence);
	}

	public List<AffairDeclarationTrivingExcellence> findByIds(List<String> ids){
		List<AffairDeclarationTrivingExcellence> list = affairDeclarationTrivingExcellenceDao.findByIds(ids);
		return list;
	}

	@Transactional(readOnly = false)
	public void save(AffairDeclarationTrivingExcellence affairDeclarationTrivingExcellence) {
		super.save(affairDeclarationTrivingExcellence);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairDeclarationTrivingExcellence affairDeclarationTrivingExcellence) {
		super.delete(affairDeclarationTrivingExcellence);
	}
	
}