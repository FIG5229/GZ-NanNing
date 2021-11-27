/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairClassMemberDao;
import com.thinkgem.jeesite.modules.affair.dao.AffairWorkPlanDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairClassMember;
import com.thinkgem.jeesite.modules.affair.entity.AffairWorkPlan;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 年度工作安排Service
 * @author wll
 * @version 2020-06-02
 */
@Service
@Transactional(readOnly = true)
public class AffairWorkPlanService extends CrudService<AffairWorkPlanDao, AffairWorkPlan> {

	@Autowired
	private AffairWorkPlanDao affairWorkPlanDao;

	public AffairWorkPlan get(String id) {
		return super.get(id);
	}
	
	public List<AffairWorkPlan> findList(AffairWorkPlan affairWorkPlan) {
		return super.findList(affairWorkPlan);
	}
	
	public Page<AffairWorkPlan> findPage(Page<AffairWorkPlan> page, AffairWorkPlan affairWorkPlan) {
		affairWorkPlan.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairWorkPlan);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairWorkPlan affairWorkPlan) {
		super.save(affairWorkPlan);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairWorkPlan affairWorkPlan) {
		super.delete(affairWorkPlan);
	}

	@Transactional(readOnly = false)
	public void shenHeSave(AffairWorkPlan affairWorkPlan) {
		affairWorkPlanDao.shenHeSave(affairWorkPlan);
	}
}