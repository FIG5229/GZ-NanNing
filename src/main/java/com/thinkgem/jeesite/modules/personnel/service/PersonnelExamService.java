/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelExamDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelExam;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 执法资格等级考试情况信息集Service
 * @author cecil.li
 * @version 2019-11-11
 */
@Service
@Transactional(readOnly = true)
public class PersonnelExamService extends CrudService<PersonnelExamDao, PersonnelExam> {

	public PersonnelExam get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelExam> findList(PersonnelExam personnelExam) {
		return super.findList(personnelExam);
	}
	
	public Page<PersonnelExam> findPage(Page<PersonnelExam> page, PersonnelExam personnelExam) {
		personnelExam.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelExam);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelExam personnelExam) {
		super.save(personnelExam);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelExam personnelExam) {
		super.delete(personnelExam);
	}

	//根据身份证号删除相应信息
	@Transactional(readOnly = false)
	public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
	}
}