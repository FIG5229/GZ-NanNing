/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairLzxxjyActivities;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 廉政学习教育活动DAO接口
 * @author cecil.li
 * @version 2019-11-08
 */
@MyBatisDao
public interface AffairLzxxjyActivitiesDao extends CrudDao<AffairLzxxjyActivities> {
    Map<String, Object> findInfoByCreateOrgId(@Param("id") String id, @Param("year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);

    Map<String, Object> findInfoByCreateOrgIds(@Param("ids") List<String> ids, @Param("year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);

    int findSignNum(@Param(value = "affairLzxxjyActivities") AffairLzxxjyActivities affairLzxxjyActivities);

    int findSumNum(@Param(value = "affairLzxxjyActivities") AffairLzxxjyActivities affairLzxxjyActivities);

    List<AffairLzxxjyActivities> findByIds(@Param(value = "ids") List<String> ids);

    List<AffairLzxxjyActivities> indexNoticeList();

    List<String> selectAllYear();

    Integer selectNumber(@Param(value = "yearL")String yearL,@Param(value = "yearT")String yearT, @Param(value = "idNumber")String idNumber);

    List<String> selectAllName();

    List<String> selectAllMonth();
}