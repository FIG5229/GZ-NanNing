/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairOrgRewardPunish;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 组织奖惩信息DAO接口
 * @author cecil.li
 * @version 2019-11-01
 */
@MyBatisDao
public interface AffairOrgRewardPunishDao extends CrudDao<AffairOrgRewardPunish> {
    List<Map<String, Object>> findInfoByCreateOrgId(@Param(value = "id") String id,@Param(value = "year") Integer year, @Param(value = "month") String month);
    List<Map<String, Object>> findInfoByCreateOrgIds(@Param(value = "ids") List<String> ids,@Param(value = "year") Integer year, @Param(value = "month") String month);

    List<String> findListByFileNo(String fileNo);

    void deleteByFileNo(String fileNo);

    Integer selectNumber(@Param("time") String time, @Param("idNumber") String idNumber);

    Integer selectTjNumber(@Param("checkTime") String checkTime,@Param("unitId") String unitId,@Param("tj") String tj);

    List<AffairOrgRewardPunish> getJLXXByMonth(@Param("partyId")String partyId, @Param("checkTime")String checkTime);

    List<AffairOrgRewardPunish> getJLXXByNowLastTime(@Param("partyId")String partyId, @Param("nowCheckTime")Date nowCheckTime, @Param("lastCheckTime") Date lastCheckTime);

    List<AffairOrgRewardPunish> getJLXXByTime(@Param("officeId") String officeId, @Param("checkTime") String checkTime);

    List<AffairOrgRewardPunish> getJLXXZJCByNowLastTime(@Param("officeId")String officeId,@Param("nowCheckTime")Date nowCheckTime, @Param("lastCheckTime") Date lastCheckTime);
}