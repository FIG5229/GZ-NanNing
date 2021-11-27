/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelHighLevelDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelHighLevel;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 高层次人才信息集Service
 * @author cecil.li
 * @version 2019-11-13
 */
@Service
@Transactional(readOnly = true)
public class PersonnelHighLevelService extends CrudService<PersonnelHighLevelDao, PersonnelHighLevel> {

	public PersonnelHighLevel get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelHighLevel> findList(PersonnelHighLevel personnelHighLevel) {
		return super.findList(personnelHighLevel);
	}
	
	public Page<PersonnelHighLevel> findPage(Page<PersonnelHighLevel> page, PersonnelHighLevel personnelHighLevel) {
		personnelHighLevel.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelHighLevel);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelHighLevel personnelHighLevel) {
		super.save(personnelHighLevel);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelHighLevel personnelHighLevel) {
		super.delete(personnelHighLevel);
	}

	//根据身份证号删除相应信息
	@Transactional(readOnly = false)
	public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
	}
}