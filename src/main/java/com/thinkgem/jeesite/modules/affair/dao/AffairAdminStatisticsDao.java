/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairAdminStatistics;

/**
 * 管理员信息统计DAO接口
 * @author alan.wu
 * @version 2020-07-24
 */
@MyBatisDao
public interface AffairAdminStatisticsDao extends CrudDao<AffairAdminStatistics> {
	
}