/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairPartyDayActivities;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 党日活动DAO接口
 * @author cecil.li
 * @version 2020-04-12
 */
@MyBatisDao
public interface AffairPartyDayActivitiesDao extends CrudDao<AffairPartyDayActivities> {

    //自动考评
	List<AffairPartyDayActivities> findBaseInfoList(@Param("year")String year, @Param("month")String month, @Param("startYear")String startYear, @Param("endYear")String endYear,  @Param("startTime")String startTime, @Param("endTime")String endTime);
    int isElection(@Param(value = "year")String year,@Param(value = "date") Date date,@Param(value = "partyBranch")String partyBranch,@Param(value = "partyBranchId")String partyBranchId);

    int unitCount(@Param("startTime")String startTime, @Param("endTime")String endTime, @Param("partyOrganizationName")String partyOrganizationName, @Param("partyOrganizationId")String partyOrganizationId);

    Integer selectNumber(@Param("id") String id, @Param("yearL") String yearL, @Param("yearT") String yearT);
}