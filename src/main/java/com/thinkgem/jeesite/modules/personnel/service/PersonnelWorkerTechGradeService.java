/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelWorkerTechGradeDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelWorkerTechGrade;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 工人技术等级信息集Service
 * @author cecil.li
 * @version 2019-11-13
 */
@Service
@Transactional(readOnly = true)
public class PersonnelWorkerTechGradeService extends CrudService<PersonnelWorkerTechGradeDao, PersonnelWorkerTechGrade> {

	public PersonnelWorkerTechGrade get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelWorkerTechGrade> findList(PersonnelWorkerTechGrade personnelWorkerTechGrade) {
		return super.findList(personnelWorkerTechGrade);
	}
	
	public Page<PersonnelWorkerTechGrade> findPage(Page<PersonnelWorkerTechGrade> page, PersonnelWorkerTechGrade personnelWorkerTechGrade) {
		personnelWorkerTechGrade.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelWorkerTechGrade);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelWorkerTechGrade personnelWorkerTechGrade) {
		super.save(personnelWorkerTechGrade);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelWorkerTechGrade personnelWorkerTechGrade) {
		super.delete(personnelWorkerTechGrade);
	}

	@Transactional(readOnly = false)
	public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
	}
}