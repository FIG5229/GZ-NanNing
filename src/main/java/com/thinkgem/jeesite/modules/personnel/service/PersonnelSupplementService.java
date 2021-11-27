/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelSupplementDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelSupplement;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 补充信息集Service
 * @author cecil.li
 * @version 2019-11-13
 */
@Service
@Transactional(readOnly = true)
public class PersonnelSupplementService extends CrudService<PersonnelSupplementDao, PersonnelSupplement> {

	public PersonnelSupplement get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelSupplement> findList(PersonnelSupplement personnelSupplement) {
		return super.findList(personnelSupplement);
	}
	
	public Page<PersonnelSupplement> findPage(Page<PersonnelSupplement> page, PersonnelSupplement personnelSupplement) {
		personnelSupplement.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelSupplement);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelSupplement personnelSupplement) {
		super.save(personnelSupplement);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelSupplement personnelSupplement) {
		super.delete(personnelSupplement);
	}

	//根据身份证号删除相应信息
	@Transactional(readOnly = false)
	public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
	}
}