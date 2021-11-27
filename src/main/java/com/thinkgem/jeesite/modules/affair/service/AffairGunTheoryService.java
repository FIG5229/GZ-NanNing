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
import com.thinkgem.jeesite.modules.affair.entity.AffairGunTheory;
import com.thinkgem.jeesite.modules.affair.dao.AffairGunTheoryDao;

/**
 * 枪支理论Service
 * @author cecil.li
 * @version 2021-01-12
 */
@Service
@Transactional(readOnly = true)
public class AffairGunTheoryService extends CrudService<AffairGunTheoryDao, AffairGunTheory> {

	public AffairGunTheory get(String id) {
		return super.get(id);
	}
	
	public List<AffairGunTheory> findList(AffairGunTheory affairGunTheory) {
		return super.findList(affairGunTheory);
	}
	
	public Page<AffairGunTheory> findPage(Page<AffairGunTheory> page, AffairGunTheory affairGunTheory) {
		affairGunTheory.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairGunTheory);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairGunTheory affairGunTheory) {
		super.save(affairGunTheory);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairGunTheory affairGunTheory) {
		super.delete(affairGunTheory);
	}
	
}