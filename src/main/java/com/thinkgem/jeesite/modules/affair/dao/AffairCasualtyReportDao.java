/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairCasualtyReport;
import com.thinkgem.jeesite.modules.affair.entity.AffairCasualtyReportStatistic;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 抚恤申报DAO接口
 * @author mason.xv
 * @version 2019-11-15
 */
@MyBatisDao
public interface AffairCasualtyReportDao extends CrudDao<AffairCasualtyReport> {

    List<AffairCasualtyReport> findByIds(@Param(value = "ids") List<String> ids);

    void shenHe(@Param(value ="affairCasualtyReport" ) AffairCasualtyReport affairCasualtyReport);

    AffairCasualtyReportStatistic findNumsByParentId(@Param(value = "parentId") String parentId, @Param(value = "affairCasualtyReport") AffairCasualtyReport affairCasualtyReport);

    AffairCasualtyReportStatistic findByUnitId(@Param(value = "unitId") String unitId,@Param(value = "affairCasualtyReport") AffairCasualtyReport affairCasualtyReport);

    AffairCasualtyReportStatistic findOneTypeByUnitId(@Param(value = "unitId") String unitId,@Param(value = "affairCasualtyReport") AffairCasualtyReport affairCasualtyReport);
}