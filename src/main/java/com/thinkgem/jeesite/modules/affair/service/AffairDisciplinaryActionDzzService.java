/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairDisciplinaryActionDzzDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairDisciplinaryActionDzz;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 党组织纪律处分Service
 * @author cecil.li
 * @version 2020-04-09
 */
@Service
@Transactional(readOnly = true)
public class AffairDisciplinaryActionDzzService extends CrudService<AffairDisciplinaryActionDzzDao, AffairDisciplinaryActionDzz> {

	public AffairDisciplinaryActionDzz get(String id) {
		return super.get(id);
	}
	
	public List<AffairDisciplinaryActionDzz> findList(AffairDisciplinaryActionDzz affairDisciplinaryActionDzz) {
		return super.findList(affairDisciplinaryActionDzz);
	}
	
	public Page<AffairDisciplinaryActionDzz> findPage(Page<AffairDisciplinaryActionDzz> page, AffairDisciplinaryActionDzz affairDisciplinaryActionDzz) {
		affairDisciplinaryActionDzz.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairDisciplinaryActionDzz);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairDisciplinaryActionDzz affairDisciplinaryActionDzz) {
		super.save(affairDisciplinaryActionDzz);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairDisciplinaryActionDzz affairDisciplinaryActionDzz) {
		super.delete(affairDisciplinaryActionDzz);
	}
	
}