/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelEmployDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelEmploy;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 聘用信息集Service
 * @author cecil.li
 * @version 2019-11-11
 */
@Service
@Transactional(readOnly = true)
public class PersonnelEmployService extends CrudService<PersonnelEmployDao, PersonnelEmploy> {

	public PersonnelEmploy get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelEmploy> findList(PersonnelEmploy personnelEmploy) {
		return super.findList(personnelEmploy);
	}
	
	public Page<PersonnelEmploy> findPage(Page<PersonnelEmploy> page, PersonnelEmploy personnelEmploy) {
		personnelEmploy.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelEmploy);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelEmploy personnelEmploy) {
		super.save(personnelEmploy);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelEmploy personnelEmploy) {
		super.delete(personnelEmploy);
	}

	@Transactional(readOnly = false)
	public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
	}
}