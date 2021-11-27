/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelResumeDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelResume;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 履历信息集Service
 * @author cecil.li
 * @version 2019-11-09
 */
@Service
@Transactional(readOnly = true)
public class PersonnelResumeService extends CrudService<PersonnelResumeDao, PersonnelResume> {

	public PersonnelResume get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelResume> findList(PersonnelResume personnelResume) {
		return super.findList(personnelResume);
	}
	
	public Page<PersonnelResume> findPage(Page<PersonnelResume> page, PersonnelResume personnelResume) {
		personnelResume.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelResume);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelResume personnelResume) {
		super.save(personnelResume);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelResume personnelResume) {
		super.delete(personnelResume);
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