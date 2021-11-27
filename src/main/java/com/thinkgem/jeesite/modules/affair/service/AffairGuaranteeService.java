/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairGuarantee;
import com.thinkgem.jeesite.modules.affair.dao.AffairGuaranteeDao;

/**
 * 医保金Service
 * @author mason.xv
 * @version 2019-11-28
 */
@Service
@Transactional(readOnly = true)
public class AffairGuaranteeService extends CrudService<AffairGuaranteeDao, AffairGuarantee> {

	public AffairGuarantee get(String id) {
		return super.get(id);
	}
	
	public List<AffairGuarantee> findList(AffairGuarantee affairGuarantee) {
		return super.findList(affairGuarantee);
	}
	
	public Page<AffairGuarantee> findPage(Page<AffairGuarantee> page, AffairGuarantee affairGuarantee) {
		return super.findPage(page, affairGuarantee);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairGuarantee affairGuarantee) {
		super.save(affairGuarantee);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairGuarantee affairGuarantee) {
		super.delete(affairGuarantee);
	}
	
}