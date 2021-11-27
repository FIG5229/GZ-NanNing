/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairPersonalReward;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 个人奖励DAO接口
 * @author cecil.li
 * @version 2019-11-02
 */
@MyBatisDao
public interface AffairPersonalRewardDao extends CrudDao<AffairPersonalReward> {

    List<Map<String,Object>> selectRewardNameById(String userId);

    List<Map<String,Object>> selectRewardApprovalDocumentById(String userId);

    List<Map<String,Object>> selectRewardClassify(String userId);

    List<AffairPersonalReward> selectRewardByUserId(String userId);

    //统计分析
    List<Map<String, Object>> findInfoByRewardName(@Param("id") String id, @Param("year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
    List<AffairPersonalReward> findDetailInfoByRewardName(AffairPersonalReward affairPersonalReward);
    List<AffairPersonalReward> findOtherDetailInfoByRewardName(AffairPersonalReward affairPersonalReward);
    List<Map<String, Object>> findChuInfoByRewardName(@Param("officeId") String officeId, @Param("id") String id, @Param("year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);

    List<AffairPersonalReward> selectBean(@Param("idNumber") String idNumber);

    List<AffairPersonalReward> selectAllReward(@Param("idNumber") String idNumber,@Param("yearL")String yearL, @Param("yearT") String yearT);

    List<AffairPersonalReward> selectAllRewardYear(@Param("idNumber") String idNumber, @Param("yearL") String yearL,@Param("yearT")String yearT);
}