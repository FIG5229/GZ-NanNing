/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairSalaryLevel;
import com.thinkgem.jeesite.modules.affair.dao.AffairSalaryLevelDao;

/**
 * 级别工资标准Service
 * @author cecil.li
 * @version 2020-06-08
 */
@Service
@Transactional(readOnly = true)
public class AffairSalaryLevelService extends CrudService<AffairSalaryLevelDao, AffairSalaryLevel> {

	public AffairSalaryLevel get(String id) {
		return super.get(id);
	}
	
	public List<AffairSalaryLevel> findList(AffairSalaryLevel affairSalaryLevel) {
		return super.findList(affairSalaryLevel);
	}
	
	public Page<AffairSalaryLevel> findPage(Page<AffairSalaryLevel> page, AffairSalaryLevel affairSalaryLevel) {
		return super.findPage(page, affairSalaryLevel);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairSalaryLevel affairSalaryLevel) {
		super.save(affairSalaryLevel);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairSalaryLevel affairSalaryLevel) {
		super.delete(affairSalaryLevel);
	}
	
}