/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelScoreDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelScore;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 业绩信息集Service
 * @author cecil.li
 * @version 2019-11-12
 */
@Service
@Transactional(readOnly = true)
public class PersonnelScoreService extends CrudService<PersonnelScoreDao, PersonnelScore> {

	public PersonnelScore get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelScore> findList(PersonnelScore personnelScore) {
		return super.findList(personnelScore);
	}
	
	public Page<PersonnelScore> findPage(Page<PersonnelScore> page, PersonnelScore personnelScore) {
		personnelScore.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelScore);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelScore personnelScore) {
		super.save(personnelScore);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelScore personnelScore) {
		super.delete(personnelScore);
	}
	@Transactional(readOnly = false)
	public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
	}
}