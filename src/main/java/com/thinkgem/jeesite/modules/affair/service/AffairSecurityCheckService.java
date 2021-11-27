/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairSecurityCheckDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairSecurityCheck;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service
 * @author mason.xv
 * @version 2019-11-05
 */
@Service
@Transactional(readOnly = true)
public class AffairSecurityCheckService extends CrudService<AffairSecurityCheckDao, AffairSecurityCheck> {

	public AffairSecurityCheck get(String id) {
		return super.get(id);
	}
	
	public List<AffairSecurityCheck> findList(AffairSecurityCheck affairSecurityCheck) {
		return super.findList(affairSecurityCheck);
	}
	
	public Page<AffairSecurityCheck> findPage(Page<AffairSecurityCheck> page, AffairSecurityCheck affairSecurityCheck) {
		affairSecurityCheck.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairSecurityCheck);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairSecurityCheck affairSecurityCheck) {
		super.save(affairSecurityCheck);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairSecurityCheck affairSecurityCheck) {
		super.delete(affairSecurityCheck);
	}
	
}