/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelAddressDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelAddress;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 住址通信信息集Service
 * @author cecil.li
 * @version 2019-11-11
 */
@Service
@Transactional(readOnly = true)
public class PersonnelAddressService extends CrudService<PersonnelAddressDao, PersonnelAddress> {

	public PersonnelAddress get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelAddress> findList(PersonnelAddress personnelAddress) {
		return super.findList(personnelAddress);
	}
	
	public Page<PersonnelAddress> findPage(Page<PersonnelAddress> page, PersonnelAddress personnelAddress) {
		personnelAddress.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelAddress);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelAddress personnelAddress) {
		super.save(personnelAddress);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelAddress personnelAddress) {
		super.delete(personnelAddress);
	}

	@Transactional(readOnly = false)
    public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
    }
}