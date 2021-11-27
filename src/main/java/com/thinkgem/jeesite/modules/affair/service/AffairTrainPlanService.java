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
import com.thinkgem.jeesite.modules.affair.entity.AffairTrainPlan;
import com.thinkgem.jeesite.modules.affair.dao.AffairTrainPlanDao;

/**
 * 培训计划报表Service
 * @author alan.wu
 * @version 2020-07-28
 */
@Service
@Transactional(readOnly = true)
public class AffairTrainPlanService extends CrudService<AffairTrainPlanDao, AffairTrainPlan> {

	public AffairTrainPlan get(String id) {
		return super.get(id);
	}
	
	public List<AffairTrainPlan> findList(AffairTrainPlan affairTrainPlan) {
		return super.findList(affairTrainPlan);
	}
	
	public Page<AffairTrainPlan> findPage(Page<AffairTrainPlan> page, AffairTrainPlan affairTrainPlan) {
		/*if ("105a3740108649a28b22ff633166de0e".equals(UserUtils.getUser().getId()) || "6dd6fe3db8144c17a45b9373a3bd3a6c".equals(UserUtils.getUser().getId()) || "a1fb3139ecfe4f2bb4e61abb18eae828".equals(UserUtils.getUser().getId())){
			affairTrainPlan.setUserId(UserUtils.getUser().getCompany().getId());
			affairTrainPlan.setOfficeId(UserUtils.getUser().getId());
		}else {
			affairTrainPlan.setUserId(UserUtils.getUser().getOffice().getId());
			affairTrainPlan.setOfficeId(UserUtils.getUser().getId());
		}*/
		affairTrainPlan.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairTrainPlan);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairTrainPlan affairTrainPlan) {
		super.save(affairTrainPlan);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairTrainPlan affairTrainPlan) {
		super.delete(affairTrainPlan);
	}
	
}