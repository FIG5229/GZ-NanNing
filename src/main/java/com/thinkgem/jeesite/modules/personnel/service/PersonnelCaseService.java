/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelCaseDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelCase;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 案件技术支持信息集Service
 * @author cecil.li
 * @version 2019-11-13
 */
@Service
@Transactional(readOnly = true)
public class PersonnelCaseService extends CrudService<PersonnelCaseDao, PersonnelCase> {

	public PersonnelCase get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelCase> findList(PersonnelCase personnelCase) {
		return super.findList(personnelCase);
	}
	
	public Page<PersonnelCase> findPage(Page<PersonnelCase> page, PersonnelCase personnelCase) {
		personnelCase.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelCase);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelCase personnelCase) {
		super.save(personnelCase);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelCase personnelCase) {
		super.delete(personnelCase);
	}

	@Transactional(readOnly = false)
    public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
    }
}