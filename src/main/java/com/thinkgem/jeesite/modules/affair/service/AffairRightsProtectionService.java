/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairRightsProtectionDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairRightsProtection;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 民警维权Service
 * @author cecil.li
 * @version 2020-06-16
 */
@Service
@Transactional(readOnly = true)
public class AffairRightsProtectionService extends CrudService<AffairRightsProtectionDao, AffairRightsProtection> {

	public AffairRightsProtection get(String id) {
		return super.get(id);
	}
	
	public List<AffairRightsProtection> findList(AffairRightsProtection affairRightsProtection) {
		return super.findList(affairRightsProtection);
	}
	
	public Page<AffairRightsProtection> findPage(Page<AffairRightsProtection> page, AffairRightsProtection affairRightsProtection) {
		affairRightsProtection.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairRightsProtection);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairRightsProtection affairRightsProtection) {
		super.save(affairRightsProtection);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairRightsProtection affairRightsProtection) {
		super.delete(affairRightsProtection);
	}
	
}