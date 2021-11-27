/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelPostChangeDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelPostChange;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 岗位变动信息集Service
 * @author cecil.li
 * @version 2019-11-13
 */
@Service
@Transactional(readOnly = true)
public class PersonnelPostChangeService extends CrudService<PersonnelPostChangeDao, PersonnelPostChange> {

	public PersonnelPostChange get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelPostChange> findList(PersonnelPostChange personnelPostChange) {
		return super.findList(personnelPostChange);
	}
	
	public Page<PersonnelPostChange> findPage(Page<PersonnelPostChange> page, PersonnelPostChange personnelPostChange) {
		personnelPostChange.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelPostChange);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelPostChange personnelPostChange) {
		super.save(personnelPostChange);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelPostChange personnelPostChange) {
		super.delete(personnelPostChange);
	}

	@Transactional(readOnly = false)
	public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
	}
}