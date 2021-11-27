/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairHealthCheckup;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 健康体检DAO接口
 * @author mason.xv
 * @version 2020-03-23
 */
@MyBatisDao
public interface AffairHealthCheckupDao extends CrudDao<AffairHealthCheckup> {

    Integer findInfoByCreateOrgId(@Param(value = "id") String id, @Param(value = "year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);

    Integer findInfoByCreateOrgIds(@Param(value = "ids") List<String> ids, @Param(value = "year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);

    List<Map<String, Object>> findTypeInfoByCreateOrgId(@Param(value = "id") String id, @Param(value = "year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);

    List<Map<String, Object>> findTypeInfoByCreateOrgIds(@Param(value = "ids") List<String> ids, @Param(value = "year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);

    List<AffairHealthCheckup> findHealthCheckupList(AffairHealthCheckup affairHealthCheckup);

    List<AffairHealthCheckup> findHealthReferenceList(AffairHealthCheckup affairHealthCheckup);

    int selectIsTiJian(@Param("lastYearDate")String lastYearDate,@Param("nowYearDate")String nowYearDate,@Param("unitId") String unitId);
}