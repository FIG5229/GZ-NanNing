/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelYearCheckDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelYearCheck;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 年度考核信息集Service
 * @author cecil.li
 * @version 2019-11-12
 */
@Service
@Transactional(readOnly = true)
public class PersonnelYearCheckService extends CrudService<PersonnelYearCheckDao, PersonnelYearCheck> {

	public PersonnelYearCheck get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelYearCheck> findList(PersonnelYearCheck personnelYearCheck) {
		return super.findList(personnelYearCheck);
	}
	
	public Page<PersonnelYearCheck> findPage(Page<PersonnelYearCheck> page, PersonnelYearCheck personnelYearCheck) {
		personnelYearCheck.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelYearCheck);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelYearCheck personnelYearCheck) {
		super.save(personnelYearCheck);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelYearCheck personnelYearCheck) {
		super.delete(personnelYearCheck);
	}

	@Transactional(readOnly = false)
    public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
    }
}