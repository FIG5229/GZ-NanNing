/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import java.util.List;

import com.thinkgem.jeesite.modules.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelPoliceFamily;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelPoliceFamilyDao;

/**
 * 民警家庭Service
 * @author daniel.liu
 * @version 2020-07-09
 */
@Service
@Transactional(readOnly = true)
public class PersonnelPoliceFamilyService extends CrudService<PersonnelPoliceFamilyDao, PersonnelPoliceFamily> {
	@Autowired
	private PersonnelPoliceFamilyDao personnelPoliceFamilyDao;

	public PersonnelPoliceFamily get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelPoliceFamily> findList(PersonnelPoliceFamily personnelPoliceFamily) {
		return super.findList(personnelPoliceFamily);
	}
	
	public Page<PersonnelPoliceFamily> findPage(Page<PersonnelPoliceFamily> page, PersonnelPoliceFamily personnelPoliceFamily) {
		return super.findPage(page, personnelPoliceFamily);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelPoliceFamily personnelPoliceFamily) {
		super.save(personnelPoliceFamily);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelPoliceFamily personnelPoliceFamily) {
		super.delete(personnelPoliceFamily);
	}

	public PersonnelPoliceFamily findUserMessage(String loginName){
		return personnelPoliceFamilyDao.findUserMessage(loginName);
	}
	
}