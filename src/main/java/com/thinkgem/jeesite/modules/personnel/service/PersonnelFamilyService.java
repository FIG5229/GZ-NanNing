/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelFamilyDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelFamily;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 家庭成员及社会关系信息集Service
 * @author cecil.li
 * @version 2019-11-09
 */
@Service
@Transactional(readOnly = true)
public class PersonnelFamilyService extends CrudService<PersonnelFamilyDao, PersonnelFamily> {

	@Autowired
	private PersonnelFamilyDao personnelFamilyDao;

	public PersonnelFamily get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelFamily> findList(PersonnelFamily personnelFamily) {
		return super.findList(personnelFamily);
	}
	
	public Page<PersonnelFamily> findPage(Page<PersonnelFamily> page, PersonnelFamily personnelFamily) {
		personnelFamily.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelFamily);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelFamily personnelFamily) {
		super.save(personnelFamily);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelFamily personnelFamily) {
		super.delete(personnelFamily);
	}

	public List<PersonnelFamily> findFamilyByIdNumer(PersonnelFamily param) {
		return super.dao.getFamilyByIdNumber(param);
	}
	@Transactional(readOnly = false)
	public void updateDelFlag(PersonnelFamily personnelFamily) {
		personnelFamilyDao.updateDelFlag(personnelFamily);
	}

    public List<PersonnelFamily> findListByIdNumber(String idNumber) {
		return dao.findListByIdNumber(idNumber);
    }

	@Transactional(readOnly = false)
	public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
	}
}