/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelPoliceWork2Dao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelPoliceWork2;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 警务技术(专业技术)任职资格信息集Service
 * @author cecil.li
 * @version 2019-11-12
 */
@Service
@Transactional(readOnly = true)
public class PersonnelPoliceWork2Service extends CrudService<PersonnelPoliceWork2Dao, PersonnelPoliceWork2> {

	public PersonnelPoliceWork2 get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelPoliceWork2> findList(PersonnelPoliceWork2 personnelPoliceWork2) {
		return super.findList(personnelPoliceWork2);
	}
	
	public Page<PersonnelPoliceWork2> findPage(Page<PersonnelPoliceWork2> page, PersonnelPoliceWork2 personnelPoliceWork2) {
		personnelPoliceWork2.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelPoliceWork2);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelPoliceWork2 personnelPoliceWork2) {
		super.save(personnelPoliceWork2);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelPoliceWork2 personnelPoliceWork2) {
		super.delete(personnelPoliceWork2);
	}

	@Transactional(readOnly = false)
	public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
	}
}