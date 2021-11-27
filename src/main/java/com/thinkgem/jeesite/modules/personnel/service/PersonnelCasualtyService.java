/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelCasualtyDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelCasualty;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 伤亡信息集Service
 * @author cecil.li
 * @version 2019-11-09
 */
@Service
@Transactional(readOnly = true)
public class PersonnelCasualtyService extends CrudService<PersonnelCasualtyDao, PersonnelCasualty> {

	public PersonnelCasualty get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelCasualty> findList(PersonnelCasualty personnelCasualty) {
		return super.findList(personnelCasualty);
	}
	
	public Page<PersonnelCasualty> findPage(Page<PersonnelCasualty> page, PersonnelCasualty personnelCasualty) {
		personnelCasualty.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelCasualty);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelCasualty personnelCasualty) {
		super.save(personnelCasualty);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelCasualty personnelCasualty) {
		super.delete(personnelCasualty);
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