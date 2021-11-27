/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairProbationaryParty;
import com.thinkgem.jeesite.modules.affair.dao.AffairProbationaryPartyDao;

/**
 * 预备党员转正Service
 * @author freeman
 * @version 2020-06-02
 */
@Service
@Transactional(readOnly = true)
public class AffairProbationaryPartyService extends CrudService<AffairProbationaryPartyDao, AffairProbationaryParty> {

	public AffairProbationaryParty get(String id) {
		return super.get(id);
	}
	
	public List<AffairProbationaryParty> findList(AffairProbationaryParty affairProbationaryParty) {
		return super.findList(affairProbationaryParty);
	}
	
	public Page<AffairProbationaryParty> findPage(Page<AffairProbationaryParty> page, AffairProbationaryParty affairProbationaryParty) {
		return super.findPage(page, affairProbationaryParty);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairProbationaryParty affairProbationaryParty) {
		super.save(affairProbationaryParty);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairProbationaryParty affairProbationaryParty) {
		super.delete(affairProbationaryParty);
	}
	
}