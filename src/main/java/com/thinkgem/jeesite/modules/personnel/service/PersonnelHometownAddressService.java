/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelHometownAddressDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelHometownAddress;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 籍贯地址Service
 * @author cecil.li
 * @version 2020-07-09
 */
@Service
@Transactional(readOnly = true)
public class PersonnelHometownAddressService extends CrudService<PersonnelHometownAddressDao, PersonnelHometownAddress> {

	public PersonnelHometownAddress get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelHometownAddress> findList(PersonnelHometownAddress personnelHometownAddress) {
		return super.findList(personnelHometownAddress);
	}
	
	public Page<PersonnelHometownAddress> findPage(Page<PersonnelHometownAddress> page, PersonnelHometownAddress personnelHometownAddress) {
		personnelHometownAddress.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelHometownAddress);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelHometownAddress personnelHometownAddress) {
		super.save(personnelHometownAddress);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelHometownAddress personnelHometownAddress) {
		super.delete(personnelHometownAddress);
	}
	
}