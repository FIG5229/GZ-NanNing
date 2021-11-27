/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelOrgCheckDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelOrgCheck;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 组织考核考察（审查）信息集Service
 * @author cecil.li
 * @version 2019-11-11
 */
@Service
@Transactional(readOnly = true)
public class PersonnelOrgCheckService extends CrudService<PersonnelOrgCheckDao, PersonnelOrgCheck> {

	public PersonnelOrgCheck get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelOrgCheck> findList(PersonnelOrgCheck personnelOrgCheck) {
		return super.findList(personnelOrgCheck);
	}
	
	public Page<PersonnelOrgCheck> findPage(Page<PersonnelOrgCheck> page, PersonnelOrgCheck personnelOrgCheck) {
		personnelOrgCheck.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelOrgCheck);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelOrgCheck personnelOrgCheck) {
		super.save(personnelOrgCheck);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelOrgCheck personnelOrgCheck) {
		super.delete(personnelOrgCheck);
	}

	//根据身份证号删除相应信息
	@Transactional(readOnly = false)
	public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
	}
}