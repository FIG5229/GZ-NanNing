/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairInstructorSpecStatistics;

/**
 * 教官特长统计报表DAO接口
 * @author kevin.jia
 * @version 2020-08-10
 */
@MyBatisDao
public interface AffairInstructorSpecStatisticsDao extends CrudDao<AffairInstructorSpecStatistics> {
	
}