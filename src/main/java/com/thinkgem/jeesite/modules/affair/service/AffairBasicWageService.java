/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairBasicWage;
import com.thinkgem.jeesite.modules.affair.dao.AffairBasicWageDao;

/**
 * 职务工资标准Service
 * @author cecil.li
 * @version 2020-06-08
 */
@Service
@Transactional(readOnly = true)
public class AffairBasicWageService extends CrudService<AffairBasicWageDao, AffairBasicWage> {

	public AffairBasicWage get(String id) {
		return super.get(id);
	}
	
	public List<AffairBasicWage> findList(AffairBasicWage affairBasicWage) {
		return super.findList(affairBasicWage);
	}
	
	public Page<AffairBasicWage> findPage(Page<AffairBasicWage> page, AffairBasicWage affairBasicWage) {
		return super.findPage(page, affairBasicWage);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairBasicWage affairBasicWage) {
		super.save(affairBasicWage);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairBasicWage affairBasicWage) {
		super.delete(affairBasicWage);
	}
	
}