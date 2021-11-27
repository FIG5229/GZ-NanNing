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
import com.thinkgem.jeesite.modules.affair.entity.AffairBaseGunSafety;
import com.thinkgem.jeesite.modules.affair.dao.AffairBaseGunSafetyDao;

/**
 * 基本枪支安全操作Service
 * @author cecil.li
 * @version 2020-12-28
 */
@Service
@Transactional(readOnly = true)
public class AffairBaseGunSafetyService extends CrudService<AffairBaseGunSafetyDao, AffairBaseGunSafety> {

	public AffairBaseGunSafety get(String id) {
		return super.get(id);
	}
	
	public List<AffairBaseGunSafety> findList(AffairBaseGunSafety affairBaseGunSafety) {
		return super.findList(affairBaseGunSafety);
	}
	
	public Page<AffairBaseGunSafety> findPage(Page<AffairBaseGunSafety> page, AffairBaseGunSafety affairBaseGunSafety) {
		affairBaseGunSafety.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairBaseGunSafety);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairBaseGunSafety affairBaseGunSafety) {
		super.save(affairBaseGunSafety);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairBaseGunSafety affairBaseGunSafety) {
		super.delete(affairBaseGunSafety);
	}
	
}