/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairBaseSkill;
import com.thinkgem.jeesite.modules.affair.dao.AffairBaseSkillDao;

/**
 * 基本技能成绩Service
 * @author cecil.li
 * @version 2020-12-28
 */
@Service
@Transactional(readOnly = true)
public class AffairBaseSkillService extends CrudService<AffairBaseSkillDao, AffairBaseSkill> {

	public AffairBaseSkill get(String id) {
		return super.get(id);
	}
	
	public List<AffairBaseSkill> findList(AffairBaseSkill affairBaseSkill) {
		return super.findList(affairBaseSkill);
	}
	
	public Page<AffairBaseSkill> findPage(Page<AffairBaseSkill> page, AffairBaseSkill affairBaseSkill) {
		affairBaseSkill.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairBaseSkill);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairBaseSkill affairBaseSkill) {
		super.save(affairBaseSkill);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairBaseSkill affairBaseSkill) {
		super.delete(affairBaseSkill);
	}
	
}