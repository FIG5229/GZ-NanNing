/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelReducePersonDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelReducePerson;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 减员信息集Service
 * @author cecil.li
 * @version 2019-11-12
 */
@Service
@Transactional(readOnly = true)
public class PersonnelReducePersonService extends CrudService<PersonnelReducePersonDao, PersonnelReducePerson> {

	public PersonnelReducePerson get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelReducePerson> findList(PersonnelReducePerson personnelReducePerson) {
		return super.findList(personnelReducePerson);
	}
	
	public Page<PersonnelReducePerson> findPage(Page<PersonnelReducePerson> page, PersonnelReducePerson personnelReducePerson) {
		personnelReducePerson.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelReducePerson);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelReducePerson personnelReducePerson) {
		super.save(personnelReducePerson);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelReducePerson personnelReducePerson) {
		super.delete(personnelReducePerson);
	}

	//根据身份证号删除相应信息
	@Transactional(readOnly = false)
	public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
	}
}