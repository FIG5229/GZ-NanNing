/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelSkillDao;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelSpecialityDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelSkill;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelSpeciality;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelTalents;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 专长信息集Service
 * @author cecil.li
 * @version 2019-11-11
 */
@Service
@Transactional(readOnly = true)
public class PersonnelSpecialityService extends CrudService<PersonnelSpecialityDao, PersonnelSpeciality> {

	@Autowired
	private PersonnelSpecialityDao personnelSpecialityDao;

	public PersonnelSpeciality get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelSpeciality> findList(PersonnelSpeciality personnelSpeciality) {
		return super.findList(personnelSpeciality);
	}
	
	public Page<PersonnelSpeciality> findPage(Page<PersonnelSpeciality> page, PersonnelSpeciality personnelSpeciality) {
		personnelSpeciality.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelSpeciality);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelSpeciality personnelSpeciality) {
		super.save(personnelSpeciality);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelSpeciality personnelSpeciality) {
		super.delete(personnelSpeciality);
	}

	public PersonnelSpeciality selectIdNumber(String id){
		return personnelSpecialityDao.selectIdNumber(id);
	}

	public PersonnelSpeciality selectBean(String id){
		return personnelSpecialityDao.selectBean(id);
	}

	//根据身份证号删除相应信息
	@Transactional(readOnly = false)
	public void deleteByIdNumber(String idNumber) {
		dao.deleteByIdNumber(idNumber);
	}

	//根据身份证号删除相应信息
	@Transactional(readOnly = false)
	public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
	}
}