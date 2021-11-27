/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelRewardDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelReward;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 奖励信息集Service
 * @author cecil.li
 * @version 2019-11-09
 */
@Service
@Transactional(readOnly = true)
public class PersonnelRewardService extends CrudService<PersonnelRewardDao, PersonnelReward> {

	public PersonnelReward get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelReward> findList(PersonnelReward personnelReward) {
		return super.findList(personnelReward);
	}
	
	public Page<PersonnelReward> findPage(Page<PersonnelReward> page, PersonnelReward personnelReward) {
		personnelReward.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelReward);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelReward personnelReward) {
		super.save(personnelReward);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelReward personnelReward) {
		super.delete(personnelReward);
	}

	@Transactional(readOnly = false)
	public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
	}
}