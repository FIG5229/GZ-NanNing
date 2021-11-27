/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairTalkManagement;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 谈话函询管理DAO接口
 * @author cecil.li
 * @version 2019-11-08
 */
@MyBatisDao
public interface AffairTalkManagementDao extends CrudDao<AffairTalkManagement> {
	List<Map<String, Object>> findStatistic(@Param(value = "affairTalkManagement") AffairTalkManagement affairTalkManagement);
	List<Map<String, Object>> findInfoByUnitId(@Param("id") String id, @Param("year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
	List<Map<String, Object>> findInfoByUnitIds(@Param("ids") List<String> ids, @Param("year") Integer year,@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
	List<Map<String, Object>> findCountInfoByZbUnit(@Param("id") String id, @Param("year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
	List<AffairTalkManagement> findDetailInfoByZbUnit(AffairTalkManagement affairTalkManagement);
	List<Map<String, Object>> findPieInfoByLx(@Param("id") String id, @Param("year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
	List<AffairTalkManagement> findDetailInfoByLx(AffairTalkManagement affairTalkManagement);
	//绩效自动考评根据单位名称获得被考评对象id
	String findCodeByUnit(@Param("unit")String unit);
	String findUserIdByCode(@Param("code")String code);
	List<AffairTalkManagement> allInfo(@Param("year")String year, @Param("month")String month, @Param("startTime")String startTime, @Param("endTime")String endTime);
}