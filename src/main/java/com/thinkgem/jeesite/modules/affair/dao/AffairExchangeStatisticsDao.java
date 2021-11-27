/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairExchangeStatistics;

/**
 * 交流锻炼统计表DAO接口
 * @author alan.wu
 * @version 2020-07-29
 */
@MyBatisDao
public interface AffairExchangeStatisticsDao extends CrudDao<AffairExchangeStatistics> {
	
}