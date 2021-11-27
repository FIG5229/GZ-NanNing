/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelAwardDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelAward;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 专业技术工作获奖信息集Service
 * @author cecil.li
 * @version 2019-11-11
 */
@Service
@Transactional(readOnly = true)
public class PersonnelAwardService extends CrudService<PersonnelAwardDao, PersonnelAward> {

	public PersonnelAward get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelAward> findList(PersonnelAward personnelAward) {
		return super.findList(personnelAward);
	}
	
	public Page<PersonnelAward> findPage(Page<PersonnelAward> page, PersonnelAward personnelAward) {
		personnelAward.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelAward);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelAward personnelAward) {
		super.save(personnelAward);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelAward personnelAward) {
		super.delete(personnelAward);
	}

	@Transactional(readOnly = false)
	public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
	}
}