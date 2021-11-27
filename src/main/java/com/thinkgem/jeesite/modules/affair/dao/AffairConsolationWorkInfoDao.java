/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairConsolationWorkInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 慰问工作管理DAO接口
 * @author cecil.li
 * @version 2019-11-05
 */
@MyBatisDao
public interface AffairConsolationWorkInfoDao extends CrudDao<AffairConsolationWorkInfo> {

	List<Map<String, String>> findInfoByCreateOrgId(@Param("id") String id, @Param("year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);

	List<Map<String, String>> findInfoByCreateOrgIds(@Param("ids") List<String> ids, @Param("year") Integer year,@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);

    List<AffairConsolationWorkInfo> findSympathyDetailList(AffairConsolationWorkInfo consolationWorkInfo);

    List<AffairConsolationWorkInfo> allInfo(@Param("year") String year, @Param("month") String month, @Param("startTime")String startTime, @Param("endTime")String endTime);
    //自动考评单位次数
    int unitCount(@Param("startTime")String startTime, @Param("endTime")String endTime, @Param("unitId")String unitId);
}