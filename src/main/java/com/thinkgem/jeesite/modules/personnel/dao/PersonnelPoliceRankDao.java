/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelPoliceCertificate;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelPoliceRank;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 警衔信息管理DAO接口
 * @author cecil.li
 * @version 2019-11-09
 */
@MyBatisDao
public interface PersonnelPoliceRankDao extends CrudDao<PersonnelPoliceRank> {

    List<PersonnelPoliceRank> selectHistoryByIdNumber(String idNumber);

    List<PersonnelPoliceRank> findInfoByIdNumber(@Param("idNumber") String idNumber);

    PersonnelPoliceRank findLastOneByIdNumber(PersonnelPoliceRank personnelPoliceRank);
    //获取当前记录
    PersonnelPoliceRank findNowPoliceRankByIdNumber(PersonnelPoliceRank personnelPoliceRank);

    List<Map<String, String>> countYearReport(PersonnelPoliceRank personnelPoliceRank);

    List<Map<String, String>> countRankChange(PersonnelPoliceRank personnelPoliceRank);

    List<Map<String, String>> findPromotionList(@Param("param") Map<String, Date> param,@Param("years") Integer integer);

    List<PersonnelPoliceRank> findLastList(PersonnelPoliceRank personnelPoliceRank);

    //统计分析
    List<Map<String, Object>> findInfoByCreateOrgId(@Param(value = "id") String id, @Param(value = "year") Integer year , @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);

    List<Map<String, Object>> findInfoByCreateOrgIds(@Param("ids") List<String> ids, @Param(value = "year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);

    List<Map<String, String>> countPoliceRank(@Param("id") String id, @Param("year") Integer year, @Param("startDate") Date startDate,
                                              @Param("endDate") Date endDate, @Param("month") String month);

    List<PersonnelPoliceRank> findChartsPoliceRankList(PersonnelPoliceRank personnelPoliceRank);

    void deleteByIdNumbers(@Param("idNumbers")List<String> idNumbers);
}