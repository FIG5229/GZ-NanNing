/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelPensionDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelPension;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 抚恤信息集Service
 * @author cecil.li
 * @version 2019-11-13
 */
@Service
@Transactional(readOnly = true)
public class PersonnelPensionService extends CrudService<PersonnelPensionDao, PersonnelPension> {

	public PersonnelPension get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelPension> findList(PersonnelPension personnelPension) {
		return super.findList(personnelPension);
	}
	
	public Page<PersonnelPension> findPage(Page<PersonnelPension> page, PersonnelPension personnelPension) {
		personnelPension.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelPension);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelPension personnelPension) {
		super.save(personnelPension);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelPension personnelPension) {
		super.delete(personnelPension);
	}

	@Transactional(readOnly = false)
	public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
	}
}