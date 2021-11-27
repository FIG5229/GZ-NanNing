/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairPoliceStudyStatistics;

/**
 * 民警学习统计报表DAO接口
 * @author kevin.jia
 * @version 2020-08-11
 */
@MyBatisDao
public interface AffairPoliceStudyStatisticsDao extends CrudDao<AffairPoliceStudyStatistics> {
	
}