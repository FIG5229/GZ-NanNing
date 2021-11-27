/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairDisciplinaryRegulationReceive;
import com.thinkgem.jeesite.modules.affair.dao.AffairDisciplinaryRegulationReceiveDao;

/**
 * 纪律规定接收单位表Service
 * @author eav.liu
 * @version 2020-04-09
 */
@Service
@Transactional(readOnly = true)
public class AffairDisciplinaryRegulationReceiveService extends CrudService<AffairDisciplinaryRegulationReceiveDao, AffairDisciplinaryRegulationReceive> {

	public AffairDisciplinaryRegulationReceive get(String id) {
		return super.get(id);
	}
	
	public List<AffairDisciplinaryRegulationReceive> findList(AffairDisciplinaryRegulationReceive affairDisciplinaryRegulationReceive) {
		return super.findList(affairDisciplinaryRegulationReceive);
	}
	
	public Page<AffairDisciplinaryRegulationReceive> findPage(Page<AffairDisciplinaryRegulationReceive> page, AffairDisciplinaryRegulationReceive affairDisciplinaryRegulationReceive) {
		return super.findPage(page, affairDisciplinaryRegulationReceive);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairDisciplinaryRegulationReceive affairDisciplinaryRegulationReceive) {
		super.save(affairDisciplinaryRegulationReceive);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairDisciplinaryRegulationReceive affairDisciplinaryRegulationReceive) {
		super.delete(affairDisciplinaryRegulationReceive);
	}
	
}