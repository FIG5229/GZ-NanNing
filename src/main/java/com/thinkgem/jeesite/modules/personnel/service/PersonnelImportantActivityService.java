/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelImportantActivityDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelImportantActivity;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 参与重要活动信息集Service
 * @author cecil.li
 * @version 2019-11-13
 */
@Service
@Transactional(readOnly = true)
public class PersonnelImportantActivityService extends CrudService<PersonnelImportantActivityDao, PersonnelImportantActivity> {

	public PersonnelImportantActivity get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelImportantActivity> findList(PersonnelImportantActivity personnelImportantActivity) {
		return super.findList(personnelImportantActivity);
	}
	
	public Page<PersonnelImportantActivity> findPage(Page<PersonnelImportantActivity> page, PersonnelImportantActivity personnelImportantActivity) {
		personnelImportantActivity.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelImportantActivity);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelImportantActivity personnelImportantActivity) {
		super.save(personnelImportantActivity);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelImportantActivity personnelImportantActivity) {
		super.delete(personnelImportantActivity);
	}

	//根据身份证号删除相应信息
	@Transactional(readOnly = false)
	public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
	}
}