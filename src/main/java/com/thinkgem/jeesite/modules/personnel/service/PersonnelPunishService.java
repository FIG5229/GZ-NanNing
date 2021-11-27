/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelPunishDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelPunish;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 惩戒信息集Service
 * @author cecil.li
 * @version 2019-11-11
 */
@Service
@Transactional(readOnly = true)
public class PersonnelPunishService extends CrudService<PersonnelPunishDao, PersonnelPunish> {

	public PersonnelPunish get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelPunish> findList(PersonnelPunish personnelPunish) {
		return super.findList(personnelPunish);
	}
	
	public Page<PersonnelPunish> findPage(Page<PersonnelPunish> page, PersonnelPunish personnelPunish) {
		personnelPunish.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelPunish);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelPunish personnelPunish) {
		super.save(personnelPunish);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelPunish personnelPunish) {
		super.delete(personnelPunish);
	}

	//根据身份证号删除相应信息
	@Transactional(readOnly = false)
	public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
	}
}