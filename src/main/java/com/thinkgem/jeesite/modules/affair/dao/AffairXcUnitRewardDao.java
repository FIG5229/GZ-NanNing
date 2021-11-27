/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairXcUnitReward;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 单位集体奖励表DAO接口
 * @author cecil.li
 * @version 2019-11-02
 */
@MyBatisDao
public interface AffairXcUnitRewardDao extends CrudDao<AffairXcUnitReward> {
    List<Map<String, Object>> findInfoByRewardName(@Param("id") String id, @Param("year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
    List<AffairXcUnitReward> findDetailInfoByRewardName(AffairXcUnitReward affairXcUnitReward);
    List<AffairXcUnitReward> findOtherDetailInfoByRewardName(AffairXcUnitReward affairXcUnitReward);
    List<Map<String, Object>> findChuInfoByRewardName(@Param("officeId") String officeId, @Param("id") String id, @Param("year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);

    List<String> findCodeName(@Param(value = "nameCode") String nameCode);

    List<AffairXcUnitReward> selectUnitReward(@Param(value = "unitId") String unitId);

    AffairXcUnitReward selectBean(@Param("id") String id);

    Integer selectNumber(@Param("checkTime") String checkTime,@Param("unitId") String unitId,@Param("level") String level);

    List<AffairXcUnitReward> selectAllReward(@Param("idN") String idN, @Param("yearL") String yearL,@Param("yearT")String yearT);

    List<AffairXcUnitReward> selectAllRewardYear(@Param("idN")String idN, @Param("year")String year);
}