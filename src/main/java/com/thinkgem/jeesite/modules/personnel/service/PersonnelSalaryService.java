/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelSalaryDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelSalary;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 工资信息集Service
 * @author cecil.li
 * @version 2019-11-11
 */
@Service
@Transactional(readOnly = true)
public class PersonnelSalaryService extends CrudService<PersonnelSalaryDao, PersonnelSalary> {

	public PersonnelSalary get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelSalary> findList(PersonnelSalary personnelSalary) {
		return super.findList(personnelSalary);
	}
	
	public Page<PersonnelSalary> findPage(Page<PersonnelSalary> page, PersonnelSalary personnelSalary) {
		personnelSalary.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelSalary);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelSalary personnelSalary) {
		super.save(personnelSalary);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelSalary personnelSalary) {
		super.delete(personnelSalary);
	}

	@Transactional(readOnly = false)
	public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
	}
}