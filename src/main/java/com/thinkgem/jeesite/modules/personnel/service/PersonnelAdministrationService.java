/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelAdministrationDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelAdministration;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 行政职务信息集Service
 * @author cecil.li
 * @version 2019-11-13
 */
@Service
@Transactional(readOnly = true)
public class PersonnelAdministrationService extends CrudService<PersonnelAdministrationDao, PersonnelAdministration> {

	public PersonnelAdministration get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelAdministration> findList(PersonnelAdministration personnelAdministration) {
		return super.findList(personnelAdministration);
	}
	
	public Page<PersonnelAdministration> findPage(Page<PersonnelAdministration> page, PersonnelAdministration personnelAdministration) {
		personnelAdministration.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelAdministration);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelAdministration personnelAdministration) {
		super.save(personnelAdministration);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelAdministration personnelAdministration) {
		super.delete(personnelAdministration);
	}

	@Transactional(readOnly = false)
    public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
    }
}