/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairJobSalaryStandard;
import com.thinkgem.jeesite.modules.affair.dao.AffairJobSalaryStandardDao;

/**
 * 职务工资标准Service
 * @author cecil.li
 * @version 2020-07-01
 */
@Service
@Transactional(readOnly = true)
public class AffairJobSalaryStandardService extends CrudService<AffairJobSalaryStandardDao, AffairJobSalaryStandard> {

	public AffairJobSalaryStandard get(String id) {
		return super.get(id);
	}
	
	public List<AffairJobSalaryStandard> findList(AffairJobSalaryStandard affairJobSalaryStandard) {
		return super.findList(affairJobSalaryStandard);
	}
	
	public Page<AffairJobSalaryStandard> findPage(Page<AffairJobSalaryStandard> page, AffairJobSalaryStandard affairJobSalaryStandard) {
		return super.findPage(page, affairJobSalaryStandard);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairJobSalaryStandard affairJobSalaryStandard) {
		super.save(affairJobSalaryStandard);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairJobSalaryStandard affairJobSalaryStandard) {
		super.delete(affairJobSalaryStandard);
	}
	
}