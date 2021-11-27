/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelInvolveSecretDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelInvolveSecret;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 涉密信息集Service
 * @author cecil.li
 * @version 2019-11-12
 */
@Service
@Transactional(readOnly = true)
public class PersonnelInvolveSecretService extends CrudService<PersonnelInvolveSecretDao, PersonnelInvolveSecret> {

	public PersonnelInvolveSecret get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelInvolveSecret> findList(PersonnelInvolveSecret personnelInvolveSecret) {
		return super.findList(personnelInvolveSecret);
	}
	
	public Page<PersonnelInvolveSecret> findPage(Page<PersonnelInvolveSecret> page, PersonnelInvolveSecret personnelInvolveSecret) {
		personnelInvolveSecret.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelInvolveSecret);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelInvolveSecret personnelInvolveSecret) {
		super.save(personnelInvolveSecret);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelInvolveSecret personnelInvolveSecret) {
		super.delete(personnelInvolveSecret);
	}

	//根据身份证号删除相应信息
	@Transactional(readOnly = false)
	public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
	}
}