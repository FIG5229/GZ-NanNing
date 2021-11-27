/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelUseGunDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelUseGun;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 公务用枪持枪证信息集Service
 * @author cecil.li
 * @version 2019-11-13
 */
@Service
@Transactional(readOnly = true)
public class PersonnelUseGunService extends CrudService<PersonnelUseGunDao, PersonnelUseGun> {

	public PersonnelUseGun get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelUseGun> findList(PersonnelUseGun personnelUseGun) {
		return super.findList(personnelUseGun);
	}
	
	public Page<PersonnelUseGun> findPage(Page<PersonnelUseGun> page, PersonnelUseGun personnelUseGun) {
		personnelUseGun.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelUseGun);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelUseGun personnelUseGun) {
		super.save(personnelUseGun);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelUseGun personnelUseGun) {
		super.delete(personnelUseGun);
	}

	@Transactional(readOnly = false)
	public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
	}
}