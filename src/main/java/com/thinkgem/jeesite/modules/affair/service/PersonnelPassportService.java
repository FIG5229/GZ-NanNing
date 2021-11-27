/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.PersonnelPassport;
import com.thinkgem.jeesite.modules.affair.dao.PersonnelPassportDao;

/**
 * 领导干部护照(通行证)管理表Service
 * @author mason.xv
 * @version 2019-11-06
 */
@Service
@Transactional(readOnly = true)
public class PersonnelPassportService extends CrudService<PersonnelPassportDao, PersonnelPassport> {

	public PersonnelPassport get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelPassport> findList(PersonnelPassport personnelPassport) {
		return super.findList(personnelPassport);
	}
	
	public Page<PersonnelPassport> findPage(Page<PersonnelPassport> page, PersonnelPassport personnelPassport) {
		return super.findPage(page, personnelPassport);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelPassport personnelPassport) {
		super.save(personnelPassport);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelPassport personnelPassport) {
		super.delete(personnelPassport);
	}

	@Transactional(readOnly = false)
    public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
    }
}