/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelPoliceFamilyInfo;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelPoliceFamilyInfoDao;

/**
 * 民警家庭Service
 * @author cecil.li
 * @version 2020-11-04
 */
@Service
@Transactional(readOnly = true)
public class PersonnelPoliceFamilyInfoService extends CrudService<PersonnelPoliceFamilyInfoDao, PersonnelPoliceFamilyInfo> {

	public PersonnelPoliceFamilyInfo get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelPoliceFamilyInfo> findList(PersonnelPoliceFamilyInfo personnelPoliceFamilyInfo) {
		return super.findList(personnelPoliceFamilyInfo);
	}
	
	public Page<PersonnelPoliceFamilyInfo> findPage(Page<PersonnelPoliceFamilyInfo> page, PersonnelPoliceFamilyInfo personnelPoliceFamilyInfo) {
		return super.findPage(page, personnelPoliceFamilyInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelPoliceFamilyInfo personnelPoliceFamilyInfo) {
		super.save(personnelPoliceFamilyInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelPoliceFamilyInfo personnelPoliceFamilyInfo) {
		super.delete(personnelPoliceFamilyInfo);
	}

    public List<PersonnelPoliceFamilyInfo> getListByPfId(String pfId) {
		return dao.getListByPfId(pfId);
    }
}