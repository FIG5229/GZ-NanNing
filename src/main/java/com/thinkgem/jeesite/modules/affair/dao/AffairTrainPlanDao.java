/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairTrainPlan;

/**
 * 培训计划报表DAO接口
 * @author alan.wu
 * @version 2020-07-28
 */
@MyBatisDao
public interface AffairTrainPlanDao extends CrudDao<AffairTrainPlan> {
	
}