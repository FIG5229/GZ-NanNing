/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairPolicewomanWorkReceive;
import com.thinkgem.jeesite.modules.affair.dao.AffairPolicewomanWorkReceiveDao;

/**
 * 女警工作管理关联表Service
 * @author eav.liu
 * @version 2020-03-26
 */
@Service
@Transactional(readOnly = true)
public class AffairPolicewomanWorkReceiveService extends CrudService<AffairPolicewomanWorkReceiveDao, AffairPolicewomanWorkReceive> {

	public AffairPolicewomanWorkReceive get(String id) {
		return super.get(id);
	}
	
	public List<AffairPolicewomanWorkReceive> findList(AffairPolicewomanWorkReceive affairPolicewomanWorkReceive) {
		return super.findList(affairPolicewomanWorkReceive);
	}
	
	public Page<AffairPolicewomanWorkReceive> findPage(Page<AffairPolicewomanWorkReceive> page, AffairPolicewomanWorkReceive affairPolicewomanWorkReceive) {
		return super.findPage(page, affairPolicewomanWorkReceive);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairPolicewomanWorkReceive affairPolicewomanWorkReceive) {
		super.save(affairPolicewomanWorkReceive);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairPolicewomanWorkReceive affairPolicewomanWorkReceive) {
		super.delete(affairPolicewomanWorkReceive);
	}
	
}