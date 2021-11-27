/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelVacationDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelVacation;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 休假信息集Service
 * @author cecil.li
 * @version 2019-11-11
 */
@Service
@Transactional(readOnly = true)
public class PersonnelVacationService extends CrudService<PersonnelVacationDao, PersonnelVacation> {

	public PersonnelVacation get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelVacation> findList(PersonnelVacation personnelVacation) {
		return super.findList(personnelVacation);
	}
	
	public Page<PersonnelVacation> findPage(Page<PersonnelVacation> page, PersonnelVacation personnelVacation) {
		personnelVacation.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelVacation);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelVacation personnelVacation) {
		super.save(personnelVacation);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelVacation personnelVacation) {
		super.delete(personnelVacation);
	}

	@Transactional(readOnly = false)
	public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
	}
}