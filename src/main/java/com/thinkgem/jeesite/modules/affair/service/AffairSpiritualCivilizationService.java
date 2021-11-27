/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairSpiritualCivilizationDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairSpiritualCivilization;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 精神文明单位管理Service
 * @author Alan.wu
 * @version 2020-06-12
 */
@Service
@Transactional(readOnly = true)
public class AffairSpiritualCivilizationService extends CrudService<AffairSpiritualCivilizationDao, AffairSpiritualCivilization> {

	public AffairSpiritualCivilization get(String id) {
		return super.get(id);
	}
	
	public List<AffairSpiritualCivilization> findList(AffairSpiritualCivilization affairSpiritualCivilization) {
		return super.findList(affairSpiritualCivilization);
	}
	
	public Page<AffairSpiritualCivilization> findPage(Page<AffairSpiritualCivilization> page, AffairSpiritualCivilization affairSpiritualCivilization) {
/*
		affairSpiritualCivilization.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
*/
		affairSpiritualCivilization.setUnitId(UserUtils.getUser().getOffice().getId());
		affairSpiritualCivilization.setUserId(UserUtils.getUser().getId());
		return super.findPage(page, affairSpiritualCivilization);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairSpiritualCivilization affairSpiritualCivilization) {
		super.save(affairSpiritualCivilization);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairSpiritualCivilization affairSpiritualCivilization) {
		super.delete(affairSpiritualCivilization);
	}
	
}