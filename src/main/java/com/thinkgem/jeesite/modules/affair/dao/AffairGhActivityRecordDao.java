/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairGhActivityRecord;
import com.thinkgem.jeesite.modules.affair.entity.AffairTnActivityRecord;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 工会活动记录DAO接口
 * @author cecil.li
 * @version 2019-11-29
 */
@MyBatisDao
public interface AffairGhActivityRecordDao extends CrudDao<AffairGhActivityRecord> {

    Map<String, Object> findInfoByCreateOrgId(@Param("id") String id);

    Map<String, Object> findInfoByCreateOrgIds(@Param("ids") List<String> ids);

    List<AffairGhActivityRecord> findByIds(@Param(value = "ids") List<String> ids);

    List<Map<String, String>> countActivity(@Param("year") Integer year, @Param("dateStart") Date dateStart, @Param("dateEnd") Date dateEnd,
                                            @Param("month") String month);

    List<AffairGhActivityRecord> findActivityDetailList(AffairGhActivityRecord record);

    int selectGhhd(@Param("lastYearDate") String lastYearDate,@Param("nowYearDate") String nowYearDate, @Param("unitId") String unitId);
}