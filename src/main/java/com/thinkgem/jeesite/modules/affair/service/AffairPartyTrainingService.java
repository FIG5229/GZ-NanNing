/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairPartyTraining;
import com.thinkgem.jeesite.modules.affair.dao.AffairPartyTrainingDao;

/**
 * 党内培训Service
 * @author freeman
 * @version 2020-06-02
 */
@Service
@Transactional(readOnly = true)
public class AffairPartyTrainingService extends CrudService<AffairPartyTrainingDao, AffairPartyTraining> {

	public AffairPartyTraining get(String id) {
		return super.get(id);
	}
	
	public List<AffairPartyTraining> findList(AffairPartyTraining affairPartyTraining) {
		return super.findList(affairPartyTraining);
	}
	
	public Page<AffairPartyTraining> findPage(Page<AffairPartyTraining> page, AffairPartyTraining affairPartyTraining) {
		return super.findPage(page, affairPartyTraining);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairPartyTraining affairPartyTraining) {
		super.save(affairPartyTraining);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairPartyTraining affairPartyTraining) {
		super.delete(affairPartyTraining);
	}

}