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
import com.thinkgem.jeesite.modules.affair.entity.AffairTeacherClass;
import com.thinkgem.jeesite.modules.affair.dao.AffairTeacherClassDao;

/**
 * 班主任培训班管理Service
 * @author alan.wu
 * @version 2020-07-08
 */
@Service
@Transactional(readOnly = true)
public class AffairTeacherClassService extends CrudService<AffairTeacherClassDao, AffairTeacherClass> {

	public AffairTeacherClass get(String id) {
		return super.get(id);
	}
	
	public List<AffairTeacherClass> findList(AffairTeacherClass affairTeacherClass) {
		return super.findList(affairTeacherClass);
	}
	
	public Page<AffairTeacherClass> findPage(Page<AffairTeacherClass> page, AffairTeacherClass affairTeacherClass) {
		affairTeacherClass.getSqlMap().put("dsf",dataScopeFilter(UserUtils.getUser(),"o","a"));
		return super.findPage(page, affairTeacherClass);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairTeacherClass affairTeacherClass) {
		super.save(affairTeacherClass);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairTeacherClass affairTeacherClass) {
		super.delete(affairTeacherClass);
	}
	
}