/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelExpertDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelExpert;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 评审专家经历信息集Service
 * @author cecil.li
 * @version 2019-11-11
 */
@Service
@Transactional(readOnly = true)
public class PersonnelExpertService extends CrudService<PersonnelExpertDao, PersonnelExpert> {

	public PersonnelExpert get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelExpert> findList(PersonnelExpert personnelExpert) {
		return super.findList(personnelExpert);
	}
	
	public Page<PersonnelExpert> findPage(Page<PersonnelExpert> page, PersonnelExpert personnelExpert) {
		personnelExpert.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelExpert);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelExpert personnelExpert) {
		super.save(personnelExpert);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelExpert personnelExpert) {
		super.delete(personnelExpert);
	}

	@Transactional(readOnly = false)
	public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
	}
}