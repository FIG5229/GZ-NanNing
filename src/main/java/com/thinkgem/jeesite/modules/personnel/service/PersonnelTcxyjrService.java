/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelTcxyjrDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelTcxyjr;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 退出现役军人（武警）信息集Service
 * @author cecil.li
 * @version 2019-11-12
 */
@Service
@Transactional(readOnly = true)
public class PersonnelTcxyjrService extends CrudService<PersonnelTcxyjrDao, PersonnelTcxyjr> {

	public PersonnelTcxyjr get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelTcxyjr> findList(PersonnelTcxyjr personnelTcxyjr) {
		return super.findList(personnelTcxyjr);
	}
	
	public Page<PersonnelTcxyjr> findPage(Page<PersonnelTcxyjr> page, PersonnelTcxyjr personnelTcxyjr) {
		personnelTcxyjr.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelTcxyjr);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelTcxyjr personnelTcxyjr) {
		super.save(personnelTcxyjr);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelTcxyjr personnelTcxyjr) {
		super.delete(personnelTcxyjr);
	}

	//根据身份证号删除相应信息
	@Transactional(readOnly = false)
	public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
	}
}