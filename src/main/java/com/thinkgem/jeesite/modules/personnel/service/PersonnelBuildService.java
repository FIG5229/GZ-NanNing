/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelBuildDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelBuild;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 体格检查信息集Service
 * @author cecil.li
 * @version 2019-11-12
 */
@Service
@Transactional(readOnly = true)
public class PersonnelBuildService extends CrudService<PersonnelBuildDao, PersonnelBuild> {

	public PersonnelBuild get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelBuild> findList(PersonnelBuild personnelBuild) {
		return super.findList(personnelBuild);
	}
	
	public Page<PersonnelBuild> findPage(Page<PersonnelBuild> page, PersonnelBuild personnelBuild) {
		personnelBuild.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelBuild);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelBuild personnelBuild) {
		super.save(personnelBuild);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelBuild personnelBuild) {
		super.delete(personnelBuild);
	}

	//根据身份证号删除相应信息
	@Transactional(readOnly = false)
	public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
	}
}