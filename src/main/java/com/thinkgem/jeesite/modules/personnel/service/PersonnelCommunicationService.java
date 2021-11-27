/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelCommunicationDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelCommunication;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 交流信息集Service
 * @author cecil.li
 * @version 2019-11-12
 */
@Service
@Transactional(readOnly = true)
public class PersonnelCommunicationService extends CrudService<PersonnelCommunicationDao, PersonnelCommunication> {

	public PersonnelCommunication get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelCommunication> findList(PersonnelCommunication personnelCommunication) {
		return super.findList(personnelCommunication);
	}
	
	public Page<PersonnelCommunication> findPage(Page<PersonnelCommunication> page, PersonnelCommunication personnelCommunication) {
		personnelCommunication.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelCommunication);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelCommunication personnelCommunication) {
		super.save(personnelCommunication);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelCommunication personnelCommunication) {
		super.delete(personnelCommunication);
	}

	@Transactional(readOnly = false)
    public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
    }
}