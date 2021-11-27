/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelAchievementDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelAchievement;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 专业技术工作及成果信息集Service
 * @author cecil.li
 * @version 2019-11-11
 */
@Service
@Transactional(readOnly = true)
public class PersonnelAchievementService extends CrudService<PersonnelAchievementDao, PersonnelAchievement> {

	public PersonnelAchievement get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelAchievement> findList(PersonnelAchievement personnelAchievement) {
		return super.findList(personnelAchievement);
	}
	
	public Page<PersonnelAchievement> findPage(Page<PersonnelAchievement> page, PersonnelAchievement personnelAchievement) {
		personnelAchievement.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelAchievement);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelAchievement personnelAchievement) {
		super.save(personnelAchievement);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelAchievement personnelAchievement) {
		super.delete(personnelAchievement);
	}

	@Transactional(readOnly = false)
    public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
    }
}