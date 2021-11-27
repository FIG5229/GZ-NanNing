/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelHrRecord;

/**
 * 人事档案管理信息集DAO接口
 * @author cecil.li
 * @version 2019-11-12
 */
@MyBatisDao
public interface PersonnelHrRecordDao extends CrudDao<PersonnelHrRecord> {

	List<Map<String, Object>> findZhuanruInfo(@Param(value = "id") String id, @Param(value = "year") Integer year , @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);

	List<Map<String, Object>> findZhuanchuInfo(@Param("id") String id, @Param(value = "year") Integer year ,@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);

	List<Map<String, Object>> findZhuanruInfos(@Param(value = "ids") List<String> ids, @Param(value = "year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);

	List<Map<String, Object>> findZhuanchuInfos(@Param(value = "ids") List<String> ids, @Param(value = "year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);

    void deleteByIdNumbers(@Param("idNumbers") List<String> idNumbers);
}