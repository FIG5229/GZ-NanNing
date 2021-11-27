/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelPersonBianzhiDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelPersonBianzhi;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 人员编制信息集Service
 * @author cecil.li
 * @version 2019-11-12
 */
@Service
@Transactional(readOnly = true)
public class PersonnelPersonBianzhiService extends CrudService<PersonnelPersonBianzhiDao, PersonnelPersonBianzhi> {

	public PersonnelPersonBianzhi get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelPersonBianzhi> findList(PersonnelPersonBianzhi personnelPersonBianzhi) {
		return super.findList(personnelPersonBianzhi);
	}
	
	public Page<PersonnelPersonBianzhi> findPage(Page<PersonnelPersonBianzhi> page, PersonnelPersonBianzhi personnelPersonBianzhi) {
		personnelPersonBianzhi.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelPersonBianzhi);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelPersonBianzhi personnelPersonBianzhi) {
		super.save(personnelPersonBianzhi);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelPersonBianzhi personnelPersonBianzhi) {
		super.delete(personnelPersonBianzhi);
	}

	@Transactional(readOnly = false)
	public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
	}
}