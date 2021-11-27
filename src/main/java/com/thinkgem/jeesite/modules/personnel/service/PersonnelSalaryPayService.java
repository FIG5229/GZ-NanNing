/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelSalaryPayDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelSalaryPay;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 工资发放信息集Service
 * @author cecil.li
 * @version 2019-11-11
 */
@Service
@Transactional(readOnly = true)
public class PersonnelSalaryPayService extends CrudService<PersonnelSalaryPayDao, PersonnelSalaryPay> {

	public PersonnelSalaryPay get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelSalaryPay> findList(PersonnelSalaryPay personnelSalaryPay) {
		return super.findList(personnelSalaryPay);
	}
	
	public Page<PersonnelSalaryPay> findPage(Page<PersonnelSalaryPay> page, PersonnelSalaryPay personnelSalaryPay) {
		personnelSalaryPay.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelSalaryPay);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelSalaryPay personnelSalaryPay) {
		super.save(personnelSalaryPay);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelSalaryPay personnelSalaryPay) {
		super.delete(personnelSalaryPay);
	}

	//根据身份证号删除相应信息
	@Transactional(readOnly = false)
	public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
	}
}