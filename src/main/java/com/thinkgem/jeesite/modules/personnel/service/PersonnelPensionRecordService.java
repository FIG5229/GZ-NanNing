/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelPensionRecordDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelPensionRecord;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 抚恤金发放记录信息集Service
 * @author cecil.li
 * @version 2019-11-13
 */
@Service
@Transactional(readOnly = true)
public class PersonnelPensionRecordService extends CrudService<PersonnelPensionRecordDao, PersonnelPensionRecord> {

	public PersonnelPensionRecord get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelPensionRecord> findList(PersonnelPensionRecord personnelPensionRecord) {
		return super.findList(personnelPensionRecord);
	}
	
	public Page<PersonnelPensionRecord> findPage(Page<PersonnelPensionRecord> page, PersonnelPensionRecord personnelPensionRecord) {
		personnelPensionRecord.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelPensionRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelPensionRecord personnelPensionRecord) {
		super.save(personnelPensionRecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelPensionRecord personnelPensionRecord) {
		super.delete(personnelPensionRecord);
	}

	@Transactional(readOnly = false)
	public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
	}

}