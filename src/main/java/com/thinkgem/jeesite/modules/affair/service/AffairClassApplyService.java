/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairClassApply;
import com.thinkgem.jeesite.modules.affair.dao.AffairClassApplyDao;

/**
 * 班级报名Service
 * @author kevin.jia
 * @version 2020-07-29
 */
@Service
@Transactional(readOnly = true)
public class AffairClassApplyService extends CrudService<AffairClassApplyDao, AffairClassApply> {

	public AffairClassApply get(String id) {
		return super.get(id);
	}
	
	public List<AffairClassApply> findList(AffairClassApply affairClassApply) {
		return super.findList(affairClassApply);
	}
	
	public Page<AffairClassApply> findPage(Page<AffairClassApply> page, AffairClassApply affairClassApply) {
		return super.findPage(page, affairClassApply);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairClassApply affairClassApply) {
		super.save(affairClassApply);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairClassApply affairClassApply) {
		super.delete(affairClassApply);
	}
	
}