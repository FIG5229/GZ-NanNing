/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelWorksDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelWorks;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 论著信息集Service
 * @author cecil.li
 * @version 2019-11-12
 */
@Service
@Transactional(readOnly = true)
public class PersonnelWorksService extends CrudService<PersonnelWorksDao, PersonnelWorks> {

	public PersonnelWorks get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelWorks> findList(PersonnelWorks personnelWorks) {
		return super.findList(personnelWorks);
	}
	
	public Page<PersonnelWorks> findPage(Page<PersonnelWorks> page, PersonnelWorks personnelWorks) {
		personnelWorks.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelWorks);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelWorks personnelWorks) {
		super.save(personnelWorks);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelWorks personnelWorks) {
		super.delete(personnelWorks);
	}

	@Transactional(readOnly = false)
    public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
    }
}