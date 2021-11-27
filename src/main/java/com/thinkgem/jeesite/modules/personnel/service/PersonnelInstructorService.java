/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelInstructorDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelInstructor;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 教官信息集Service
 * @author cecil.li
 * @version 2019-11-11
 */
@Service
@Transactional(readOnly = true)
public class PersonnelInstructorService extends CrudService<PersonnelInstructorDao, PersonnelInstructor> {

	public PersonnelInstructor get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelInstructor> findList(PersonnelInstructor personnelInstructor) {
		return super.findList(personnelInstructor);
	}
	
	public Page<PersonnelInstructor> findPage(Page<PersonnelInstructor> page, PersonnelInstructor personnelInstructor) {
		personnelInstructor.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelInstructor);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelInstructor personnelInstructor) {
		super.save(personnelInstructor);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelInstructor personnelInstructor) {
		super.delete(personnelInstructor);
	}
	@Transactional(readOnly = false)
	public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
	}
}