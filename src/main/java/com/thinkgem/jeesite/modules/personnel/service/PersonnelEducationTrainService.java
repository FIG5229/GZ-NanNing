/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelEducationTrainDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelEducationTrain;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 教育培训（进修）信息管理Service
 * @author cecil.li
 * @version 2019-11-09
 */
@Service
@Transactional(readOnly = true)
public class PersonnelEducationTrainService extends CrudService<PersonnelEducationTrainDao, PersonnelEducationTrain> {

	public PersonnelEducationTrain get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelEducationTrain> findList(PersonnelEducationTrain personnelEducationTrain) {
		return super.findList(personnelEducationTrain);
	}
	
	public Page<PersonnelEducationTrain> findPage(Page<PersonnelEducationTrain> page, PersonnelEducationTrain personnelEducationTrain) {
		personnelEducationTrain.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelEducationTrain);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelEducationTrain personnelEducationTrain) {
		super.save(personnelEducationTrain);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelEducationTrain personnelEducationTrain) {
		super.delete(personnelEducationTrain);
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