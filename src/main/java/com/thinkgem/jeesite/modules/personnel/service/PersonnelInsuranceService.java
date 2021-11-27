/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelInsuranceDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelInsurance;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 社会保险信息集Service
 * @author cecil.li
 * @version 2019-11-12
 */
@Service
@Transactional(readOnly = true)
public class PersonnelInsuranceService extends CrudService<PersonnelInsuranceDao, PersonnelInsurance> {

	public PersonnelInsurance get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelInsurance> findList(PersonnelInsurance personnelInsurance) {
		return super.findList(personnelInsurance);
	}
	
	public Page<PersonnelInsurance> findPage(Page<PersonnelInsurance> page, PersonnelInsurance personnelInsurance) {
		personnelInsurance.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelInsurance);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelInsurance personnelInsurance) {
		super.save(personnelInsurance);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelInsurance personnelInsurance) {
		super.delete(personnelInsurance);
	}

	@Transactional(readOnly = false)
	public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
	}
}