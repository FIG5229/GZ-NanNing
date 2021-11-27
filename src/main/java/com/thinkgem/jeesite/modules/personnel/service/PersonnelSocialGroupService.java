/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelSocialGroupDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelSocialGroup;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 社会团体任职信息集Service
 * @author cecil.li
 * @version 2019-11-11
 */
@Service
@Transactional(readOnly = true)
public class PersonnelSocialGroupService extends CrudService<PersonnelSocialGroupDao, PersonnelSocialGroup> {

	public PersonnelSocialGroup get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelSocialGroup> findList(PersonnelSocialGroup personnelSocialGroup) {
		return super.findList(personnelSocialGroup);
	}
	
	public Page<PersonnelSocialGroup> findPage(Page<PersonnelSocialGroup> page, PersonnelSocialGroup personnelSocialGroup) {
		personnelSocialGroup.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelSocialGroup);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelSocialGroup personnelSocialGroup) {
		super.save(personnelSocialGroup);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelSocialGroup personnelSocialGroup) {
		super.delete(personnelSocialGroup);
	}

	//根据身份证号删除相应信息
	@Transactional(readOnly = false)
	public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
	}
}