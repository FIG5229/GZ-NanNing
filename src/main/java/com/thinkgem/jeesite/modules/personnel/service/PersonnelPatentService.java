/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelPatentDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelPatent;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 获取专利信息集Service
 * @author cecil.li
 * @version 2019-11-13
 */
@Service
@Transactional(readOnly = true)
public class PersonnelPatentService extends CrudService<PersonnelPatentDao, PersonnelPatent> {

	public PersonnelPatent get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelPatent> findList(PersonnelPatent personnelPatent) {
		return super.findList(personnelPatent);
	}
	
	public Page<PersonnelPatent> findPage(Page<PersonnelPatent> page, PersonnelPatent personnelPatent) {
		personnelPatent.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelPatent);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelPatent personnelPatent) {
		super.save(personnelPatent);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelPatent personnelPatent) {
		super.delete(personnelPatent);
	}

	@Transactional(readOnly = false)
	public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
	}
}