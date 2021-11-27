/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairLabor;
import com.thinkgem.jeesite.modules.affair.entity.AffairSalaryGather;

/**
 * 个人工资汇总DAO接口
 * @author cecil.li
 * @version 2020-01-19
 */
@MyBatisDao
public interface AffairSalaryGatherDao extends CrudDao<AffairSalaryGather> {
	
}