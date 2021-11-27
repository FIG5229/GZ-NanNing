/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairWorkPlan;

/**
 * 年度工作安排DAO接口
 * @author wll
 * @version 2020-06-02
 */
@MyBatisDao
public interface AffairWorkPlanDao extends CrudDao<AffairWorkPlan> {

    void shenHeSave(AffairWorkPlan affairWorkPlan);
}