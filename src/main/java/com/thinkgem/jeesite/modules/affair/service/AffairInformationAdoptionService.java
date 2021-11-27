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
import com.thinkgem.jeesite.modules.affair.entity.AffairInformationAdoption;
import com.thinkgem.jeesite.modules.affair.dao.AffairInformationAdoptionDao;

/**
 * 信息采用Service
 * @author cecil.li
 * @version 2020-12-29
 */
@Service
@Transactional(readOnly = true)
public class AffairInformationAdoptionService extends CrudService<AffairInformationAdoptionDao, AffairInformationAdoption> {

	public AffairInformationAdoption get(String id) {
		return super.get(id);
	}
	
	public List<AffairInformationAdoption> findList(AffairInformationAdoption affairInformationAdoption) {
		return super.findList(affairInformationAdoption);
	}
	
	public Page<AffairInformationAdoption> findPage(Page<AffairInformationAdoption> page, AffairInformationAdoption affairInformationAdoption) {
		affairInformationAdoption.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairInformationAdoption);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairInformationAdoption affairInformationAdoption) {
		super.save(affairInformationAdoption);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairInformationAdoption affairInformationAdoption) {
		super.delete(affairInformationAdoption);
	}
	
}