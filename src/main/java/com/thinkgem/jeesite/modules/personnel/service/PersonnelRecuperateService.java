/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelRecuperateDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelRecuperate;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 疗（休）养信息集Service
 * @author cecil.li
 * @version 2019-11-09
 */
@Service
@Transactional(readOnly = true)
public class PersonnelRecuperateService extends CrudService<PersonnelRecuperateDao, PersonnelRecuperate> {

	public PersonnelRecuperate get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelRecuperate> findList(PersonnelRecuperate personnelRecuperate) {
		return super.findList(personnelRecuperate);
	}
	
	public Page<PersonnelRecuperate> findPage(Page<PersonnelRecuperate> page, PersonnelRecuperate personnelRecuperate) {
		personnelRecuperate.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelRecuperate);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelRecuperate personnelRecuperate) {
		super.save(personnelRecuperate);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelRecuperate personnelRecuperate) {
		super.delete(personnelRecuperate);
	}
	
}