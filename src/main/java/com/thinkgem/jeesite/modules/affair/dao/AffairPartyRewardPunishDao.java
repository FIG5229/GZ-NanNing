/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairPartyRewardPunish;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 党员奖惩信息DAO接口
 * @author eav.liu
 * @version 2019-11-12
 */
@MyBatisDao
public interface AffairPartyRewardPunishDao extends CrudDao<AffairPartyRewardPunish> {

    Integer selectNumber(@Param("lastYearDate")Date lastYearDate,@Param("nowYearDate")Date nowYearDate,@Param("idNumber") String idNumber,@Param("level") String level);
    Integer selectNumberTwo(@Param("lastYearDate")Date lastYearDate,@Param("nowYearDate")Date nowYearDate,@Param("idNumber") String idNumber,@Param("level") String level,@Param("levelTwo") String levelTwo);
    Integer selectNumberMonth(@Param("lastMonthDate")Date lastMonthDate,@Param("nowMonthDate")Date nowMonthDate,@Param("idNumber") String idNumber,@Param("level") String level);
    Integer selectNumberMonthTwo(@Param("lastMonthDate")Date lastMonthDate,@Param("nowMonthDate")Date nowMonthDate,@Param("idNumber") String idNumber,@Param("level") String level,@Param("levelTwo")String levelTwo);

    List<AffairPartyRewardPunish> selectPerson(@Param("lastYearDate") Date lastYearDate,@Param("nowYearDate") Date nowYearDate, @Param("unitId") String unitId);

    List<AffairPartyRewardPunish> selectPersonMonth(@Param("lastMonthDate") Date lastMonthDate,@Param("nowMonthDate") Date nowMonthDate,@Param("unitId") String unitId);

    List<AffairPartyRewardPunish> selectAssessPersonAward(@Param("lastYearDate")Date lastYearDate,@Param("nowYearDate")Date nowYearDate,@Param("idNumber") String idNumber);

    List<AffairPartyRewardPunish> selectAssessPersonAwardMonth(@Param("lastMonthDate")Date lastMonthDate,@Param("nowMonthDate")Date nowMonthDate,@Param("idNumber") String idNumber);
}