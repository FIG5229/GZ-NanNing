/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelDailyContactDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelDailyContact;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 日常联系情况信息集Service
 * @author cecil.li
 * @version 2019-11-12
 */
@Service
@Transactional(readOnly = true)
public class PersonnelDailyContactService extends CrudService<PersonnelDailyContactDao, PersonnelDailyContact> {

	public PersonnelDailyContact get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelDailyContact> findList(PersonnelDailyContact personnelDailyContact) {
		return super.findList(personnelDailyContact);
	}
	
	public Page<PersonnelDailyContact> findPage(Page<PersonnelDailyContact> page, PersonnelDailyContact personnelDailyContact) {
		personnelDailyContact.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelDailyContact);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelDailyContact personnelDailyContact) {
		super.save(personnelDailyContact);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelDailyContact personnelDailyContact) {
		super.delete(personnelDailyContact);
	}

	@Transactional(readOnly = false)
    public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
    }
}