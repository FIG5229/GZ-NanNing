/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairTnActivityRecord;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 团内活动记录DAO接口
 * @author cecil.li
 * @version 2019-11-29
 */
@MyBatisDao
public interface AffairTnActivityRecordDao extends CrudDao<AffairTnActivityRecord> {

    Map<String, Object> findInfoByCreateOrgId(@Param("id") String id);

    Map<String, Object> findInfoByCreateOrgIds(@Param("ids") List<String> ids);

    List<AffairTnActivityRecord> findByIds(@Param(value = "ids") List<String> ids);

    /*查看本公司及以下*/
    List<Map<String, String>> countActivity(@Param("officeId") String officeId, @Param("year")Integer year,
                                            @Param("startDate") Date startDate, @Param("endDate")Date endDate, @Param("month")String month);

    /*查看三处和局*/
    List<Map<String, String>> countTopActivity( @Param("year")Integer year,
                                            @Param("startDate") Date startDate, @Param("endDate")Date endDate, @Param("month")String month);

    List<AffairTnActivityRecord> findActivityList(AffairTnActivityRecord affairTnActivityRecord);

    List<AffairTnActivityRecord> findTopActivityList(AffairTnActivityRecord affairTnActivityRecord);

    List<String> selectAllYear();

    List<String> selectAllMonth();

    Integer selectNumber( @Param("time")String time,  @Param("unitId")String unitId);

    Integer selectNumberPass(@Param("year") String year,@Param("unitId") String unitId);

    int selectTpNumber(@Param("yearL") String yearL,@Param("yearT") String yearT,@Param("id") String id);

    int selectTpsbNumber(@Param("yearL") String yearL,@Param("yearT") String yearT,@Param("id") String id);

    int selectTpJkcNumber(@Param("yearL") String yearL,@Param("yearT") String yearT,@Param("id") String id);

    int selectTpJkcNumberYear(@Param("yearL") String yearL,@Param("yearT") String yearT,@Param("id") String id);

    int selectJkcNum(@Param("s")String s, @Param("yearL")String yearL, @Param("yearT")String yearT);

    int selectJkcNumYear(@Param("chuId")String chuId, @Param("yearL")String yearL, @Param("yearT")String yearT);

    int selectJkcIsPushNumYear(@Param("chuId")String chuId, @Param("yearL")String yearL, @Param("yearT")String yearT);
}