/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairActivityMien;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelPoliceFamilyDao;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelPoliceFamilyTwoDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelPoliceFamily;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelPoliceFamilyTwo;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 民警家庭Service
 * @author tomfu
 * @version 2020-10-26
 */
@Service
@Transactional(readOnly = true)
public class PersonnelPoliceFamilyTwoService extends CrudService<PersonnelPoliceFamilyTwoDao, PersonnelPoliceFamilyTwo> {
	@Autowired
	private PersonnelPoliceFamilyTwoDao personnelPoliceFamilyTwoDao;

	public PersonnelPoliceFamilyTwo get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelPoliceFamilyTwo> findList(PersonnelPoliceFamilyTwo personnelPoliceFamilyTwo) {
		return super.findList(personnelPoliceFamilyTwo);
	}
	
	public Page<PersonnelPoliceFamilyTwo> findPage(Page<PersonnelPoliceFamilyTwo> page, PersonnelPoliceFamilyTwo personnelPoliceFamilyTwo) {
		personnelPoliceFamilyTwo.setUserId(UserUtils.getUser().getId());
		personnelPoliceFamilyTwo.setOfficeId(UserUtils.getUser().getOffice().getId());
		return super.findPage(page, personnelPoliceFamilyTwo);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelPoliceFamilyTwo personnelPoliceFamilyTwo) {
		super.save(personnelPoliceFamilyTwo);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelPoliceFamilyTwo personnelPoliceFamilyTwo) {
		super.delete(personnelPoliceFamilyTwo);
	}

	public PersonnelPoliceFamilyTwo findUserMessage(String loginName){
		return personnelPoliceFamilyTwoDao.findUserMessage(loginName);
	}
	/**
	 * 批量提交
	 * @param ids
	 */
	public List<PersonnelPoliceFamilyTwo> findByIds(List<String> ids){
		List<PersonnelPoliceFamilyTwo> list = personnelPoliceFamilyTwoDao.findByIds(ids);
		return list;
	}
	public String findId(String unitCheckId){
	 return personnelPoliceFamilyTwoDao.findId(unitCheckId);
	}

	public String findUserIsNull(String id){
	 return personnelPoliceFamilyTwoDao.findUserIsNull(id);
	}

	public String findUnitID(){
	 return personnelPoliceFamilyTwoDao.findUnitID();
	}

	public String  findCheckType(String id){
	   return personnelPoliceFamilyTwoDao.findCheckType(id);
    }


}