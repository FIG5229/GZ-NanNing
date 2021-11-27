/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.PersonnelGoAbroadDao;
import com.thinkgem.jeesite.modules.affair.dao.PersonnelGoAbroadTwoDao;
import com.thinkgem.jeesite.modules.affair.entity.PersonnelGoAbroad;
import com.thinkgem.jeesite.modules.affair.entity.PersonnelGoAbroadTwo;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 领导干部出国管理表Service
 * @author mason.xv
 * @version 2019-11-06
 */
@Service
@Transactional(readOnly = true)
public class PersonnelGoAbroadTwoService extends CrudService<PersonnelGoAbroadTwoDao, PersonnelGoAbroadTwo> {

	@Autowired
	private PersonnelGoAbroadTwoDao personnelGoAbroadTwoDao;

	public PersonnelGoAbroadTwo get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelGoAbroadTwo> findList(PersonnelGoAbroadTwo personnelGoAbroadTwo) {
		return super.findList(personnelGoAbroadTwo);
	}
	
	public Page<PersonnelGoAbroadTwo> findPage(Page<PersonnelGoAbroadTwo> page, PersonnelGoAbroadTwo personnelGoAbroadTwo) {
		personnelGoAbroadTwo.setCreateBy(UserUtils.getUser());
		personnelGoAbroadTwo.setUserId(UserUtils.getUser().getOffice().getId());
		personnelGoAbroadTwo.setCardNum(UserUtils.getUser().getId());
		personnelGoAbroadTwo.setUsersId(UserUtils.getUser().getId());
		personnelGoAbroadTwo.setOfficeId(UserUtils.getUser().getOffice().getId());
//		personnelGoAbroad.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelGoAbroadTwo);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelGoAbroadTwo personnelGoAbroadTwo) {
		super.save(personnelGoAbroadTwo);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelGoAbroadTwo personnelGoAbroadTwo) {
		super.delete(personnelGoAbroadTwo);
	}

	public List<PersonnelGoAbroadTwo> findByIds(List<String> ids) {
		return personnelGoAbroadTwoDao.findByIds(ids);
	}
}