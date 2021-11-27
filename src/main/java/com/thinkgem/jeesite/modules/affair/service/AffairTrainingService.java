/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairTraining;
import com.thinkgem.jeesite.modules.affair.dao.AffairTrainingDao;

/**
 * 练兵比武Service
 * @author alan.wu
 * @version 2020-07-15
 */
@Service
@Transactional(readOnly = true)
public class AffairTrainingService extends CrudService<AffairTrainingDao, AffairTraining> {

	@Autowired
	private AffairTrainingDao affairTrainingDao;

	public AffairTraining get(String id) {
		return super.get(id);
	}
	
	public List<AffairTraining> findList(AffairTraining affairTraining) {
		return super.findList(affairTraining);
	}
	
	public Page<AffairTraining> findPage(Page<AffairTraining> page, AffairTraining affairTraining) {
		affairTraining.getSqlMap().put("dsf",dataScopeFilter(UserUtils.getUser(),"o","a"));
		return super.findPage(page, affairTraining);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairTraining affairTraining) {
		String unitId = affairTraining.getUnitId();
		String unit = affairTraining.getUnit();
		if("".equals(unitId) || null == unitId && (!"".equals(unit) && null != unit)){
			String Id = affairTrainingDao.findofficeId(unit);
			affairTraining.setUnitId(Id);
		}
		super.save(affairTraining);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairTraining affairTraining) {
		super.delete(affairTraining);
	}
	
}