/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelCivilServantDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelCivilServant;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 公务员登记信息集Service
 * @author cecil.li
 * @version 2019-11-13
 */
@Service
@Transactional(readOnly = true)
public class PersonnelCivilServantService extends CrudService<PersonnelCivilServantDao, PersonnelCivilServant> {

	public PersonnelCivilServant get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelCivilServant> findList(PersonnelCivilServant personnelCivilServant) {
		return super.findList(personnelCivilServant);
	}
	
	public Page<PersonnelCivilServant> findPage(Page<PersonnelCivilServant> page, PersonnelCivilServant personnelCivilServant) {
		personnelCivilServant.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelCivilServant);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelCivilServant personnelCivilServant) {
		super.save(personnelCivilServant);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelCivilServant personnelCivilServant) {
		super.delete(personnelCivilServant);
	}

	@Transactional(readOnly = false)
    public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
    }
}