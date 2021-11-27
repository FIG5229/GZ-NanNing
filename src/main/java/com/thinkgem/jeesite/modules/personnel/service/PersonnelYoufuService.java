/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelYoufuDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelYoufu;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 优抚信息集Service
 * @author cecil.li
 * @version 2019-11-13
 */
@Service
@Transactional(readOnly = true)
public class PersonnelYoufuService extends CrudService<PersonnelYoufuDao, PersonnelYoufu> {

	public PersonnelYoufu get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelYoufu> findList(PersonnelYoufu personnelYoufu) {
		return super.findList(personnelYoufu);
	}
	
	public Page<PersonnelYoufu> findPage(Page<PersonnelYoufu> page, PersonnelYoufu personnelYoufu) {
		personnelYoufu.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelYoufu);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelYoufu personnelYoufu) {
		super.save(personnelYoufu);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelYoufu personnelYoufu) {
		super.delete(personnelYoufu);
	}

	@Transactional(readOnly = false)
	public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
	}
}