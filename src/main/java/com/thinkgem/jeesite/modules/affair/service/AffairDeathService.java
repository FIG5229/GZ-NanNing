/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairDeathDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairDeath;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 死亡干部档案登记花名册Service
 * @author mason.xv
 * @version 2019-11-04
 */
@Service
@Transactional(readOnly = true)
public class AffairDeathService extends CrudService<AffairDeathDao, AffairDeath> {

	public AffairDeath get(String id) {
		return super.get(id);
	}
	
	public List<AffairDeath> findList(AffairDeath affairDeath) {
		return super.findList(affairDeath);
	}
	
	public Page<AffairDeath> findPage(Page<AffairDeath> page, AffairDeath affairDeath) {
		affairDeath.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairDeath);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairDeath affairDeath) {
		super.save(affairDeath);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairDeath affairDeath) {
		super.delete(affairDeath);
	}
	
}