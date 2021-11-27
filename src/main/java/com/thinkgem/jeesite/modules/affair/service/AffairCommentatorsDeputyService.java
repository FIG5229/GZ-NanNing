/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairCommentatorsDeputy;
import com.thinkgem.jeesite.modules.affair.dao.AffairCommentatorsDeputyDao;

/**
 * 网评员管理-副表Service
 * @author alan.wu
 * @version 2020-06-22
 */
@Service
@Transactional(readOnly = true)
public class AffairCommentatorsDeputyService extends CrudService<AffairCommentatorsDeputyDao, AffairCommentatorsDeputy> {

	public AffairCommentatorsDeputy get(String id) {
		return super.get(id);
	}
	
	public List<AffairCommentatorsDeputy> findList(AffairCommentatorsDeputy affairCommentatorsDeputy) {
		return super.findList(affairCommentatorsDeputy);
	}
	
	public Page<AffairCommentatorsDeputy> findPage(Page<AffairCommentatorsDeputy> page, AffairCommentatorsDeputy affairCommentatorsDeputy) {
		return super.findPage(page, affairCommentatorsDeputy);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairCommentatorsDeputy affairCommentatorsDeputy) {
		super.save(affairCommentatorsDeputy);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairCommentatorsDeputy affairCommentatorsDeputy) {
		super.delete(affairCommentatorsDeputy);
	}
	
}