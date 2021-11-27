/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairYearThreeOnePlan;
import org.apache.ibatis.annotations.Param;

/**
 * 年度&ldquo;三会一课&rdquo;计划DAO接口
 * @author eav.liu
 * @version 2019-11-07
 */
@MyBatisDao
public interface AffairYearThreeOnePlanDao extends CrudDao<AffairYearThreeOnePlan> {

    void shenHeSave(@Param(value = "affairYearThreeOnePlan") AffairYearThreeOnePlan affairYearThreeOnePlan);
}