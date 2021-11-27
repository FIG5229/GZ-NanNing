/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.*;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 新闻宣传DAO接口
 * @author cecil.li
 * @version 2019-11-02
 */
@MyBatisDao
public interface AffairNewsDao extends CrudDao<AffairNews> {

	List<Map<String, Object>> findInfoByUnitId(@Param("id") String id, @Param("year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);

	List<Map<String, Object>> findInfoByUnitIds(@Param("ids") List<String> ids, @Param("year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);

     AffairNews selectBeenByUserId(@Param("newId")String newId);

    List<String> selectContributionByUserId(String idNumber);

    public List<AffairCorrespondent> findPerson(AffairCorrespondent param);

    //统计分析新需求
    List<Map<String, Object>> findEchartsInfoByUnit(@Param("id") String id, @Param("year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
    List<Map<String, Object>> findChuEchartsInfoByUnit(@Param("officeId") String officeId, @Param("id") String id, @Param("year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
    List<AffairNews> findEchartsDetailInfo(AffairNews affairNews);

    AffairNews getOne(AffairNews affairNews);

    Integer selectNum(@Param("unitId") String unitId,@Param("time") String time,@Param("typr") String typr);

    List<AffairNews> selectAllKg(@Param("yearL") String yearL,@Param("yearT") String yearT,@Param("unitId") String unitId);

    List<AffairNewsAuthor> findAuthor(@Param("authorId") String authorId);

    List<AffairNewsUnit> findUnit(@Param("authorId") String authorId);

    List<AffairNews> selectAllKgPeople(@Param("yearL")String yearL,@Param("yearT") String yearT,@Param("idN") String idN);

    int selectNumber(@Param("yearL")String yearL,@Param("yearT") String yearT,@Param("idN") String idN,@Param("adopt")String adopt);

    int selectNumberInYear(@Param("yearL")String yearL,@Param("yearT") String yearT,@Param("idN") String idN,@Param("adopt")String adopt,@Param("isYear")String isYear);

    String findUnitId(String unitName);

    List<AffairNews> findListInIds(@Param("newsIdList")List<String> newsIdList);

//    List<AffairNews> findChuEchartsDetailInfo(AffairNews affairNews);


}
