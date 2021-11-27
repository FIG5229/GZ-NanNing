/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelEnterPoliceDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelEnterPolice;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 录（入）警信息集Service
 * @author cecil.li
 * @version 2019-11-12
 */
@Service
@Transactional(readOnly = true)
public class PersonnelEnterPoliceService extends CrudService<PersonnelEnterPoliceDao, PersonnelEnterPolice> {

	public PersonnelEnterPolice get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelEnterPolice> findList(PersonnelEnterPolice personnelEnterPolice) {
		return super.findList(personnelEnterPolice);
	}
	
	public Page<PersonnelEnterPolice> findPage(Page<PersonnelEnterPolice> page, PersonnelEnterPolice personnelEnterPolice) {
		personnelEnterPolice.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelEnterPolice);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelEnterPolice personnelEnterPolice) {
		super.save(personnelEnterPolice);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelEnterPolice personnelEnterPolice) {
		super.delete(personnelEnterPolice);
	}

	//根据身份证号删除相应信息
	@Transactional(readOnly = false)
	public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
	}
}