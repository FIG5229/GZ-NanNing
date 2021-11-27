/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelAddPersonDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelAddPerson;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 增员信息集Service
 * @author cecil.li
 * @version 2019-11-11
 */
@Service
@Transactional(readOnly = true)
public class PersonnelAddPersonService extends CrudService<PersonnelAddPersonDao, PersonnelAddPerson> {

	public PersonnelAddPerson get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelAddPerson> findList(PersonnelAddPerson personnelAddPerson) {
		return super.findList(personnelAddPerson);
	}
	
	public Page<PersonnelAddPerson> findPage(Page<PersonnelAddPerson> page, PersonnelAddPerson personnelAddPerson) {
		personnelAddPerson.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelAddPerson);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelAddPerson personnelAddPerson) {
		super.save(personnelAddPerson);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelAddPerson personnelAddPerson) {
		super.delete(personnelAddPerson);
	}

	@Transactional(readOnly = false)
    public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
    }
}