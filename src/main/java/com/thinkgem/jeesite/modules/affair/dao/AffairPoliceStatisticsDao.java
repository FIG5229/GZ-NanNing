/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairPoliceStatistics;
import com.thinkgem.jeesite.modules.affair.entity.AffairPoliceStudyStatistics;

import java.util.List;

/**
 * 民警课程学习统计DAO接口
 * @author alan.wu
 * @version 2020-07-17
 */
@MyBatisDao
public interface AffairPoliceStatisticsDao extends CrudDao<AffairPoliceStatistics> {
    //民警学习统计报表-->根据警号查询课程详情
    List<AffairPoliceStatistics> findByPoliceIdNumber(AffairPoliceStudyStatistics affairPoliceStudyStatistics);
}