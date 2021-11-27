/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelPoliceWork1Dao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelPoliceWork1;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 警务技术(专业技术)职务信息集Service
 * @author cecil.li
 * @version 2019-11-12
 */
@Service
@Transactional(readOnly = true)
public class PersonnelPoliceWork1Service extends CrudService<PersonnelPoliceWork1Dao, PersonnelPoliceWork1> {

	public PersonnelPoliceWork1 get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelPoliceWork1> findList(PersonnelPoliceWork1 personnelPoliceWork1) {
		return super.findList(personnelPoliceWork1);
	}
	
	public Page<PersonnelPoliceWork1> findPage(Page<PersonnelPoliceWork1> page, PersonnelPoliceWork1 personnelPoliceWork1) {
		personnelPoliceWork1.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelPoliceWork1);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelPoliceWork1 personnelPoliceWork1) {
		super.save(personnelPoliceWork1);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelPoliceWork1 personnelPoliceWork1) {
		super.delete(personnelPoliceWork1);
	}

	@Transactional(readOnly = false)
	public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
	}
}