/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.*;
import com.thinkgem.jeesite.modules.exam.entity.ExamAutoEvaluation;
import com.thinkgem.jeesite.modules.exam.entity.ExamStandardBaseInfo;
import com.thinkgem.jeesite.modules.exam.entity.ExamStandardTemplateData;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelBase;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.SysOffices;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 自动考评DAO接口
 * @author alan.wu
 * @version 2020-08-24
 */
@MyBatisDao
public interface ExamAutoEvaluationDao extends CrudDao<ExamAutoEvaluation> {

    // 查找被考评对象id
    String findEvaluationId(@Param(value = "name")String name,@Param(value = "idNumber")String idNumber);

    //党日活动
    int findCountById(String id);
    int findYearPartyDayCount(@Param(value = "year")String year,@Param(value = "type")String type, @Param(value = "evalType")String evalType,@Param(value = "period")String period,@Param(value = "partyOrganizationName")String partyOrganizationName,@Param(value = "partyOrganizationId")String partyOrganizationId,@Param(value = "modelId")String modelId,@Param(value = "optionId")String optionId);
    //高铁入网 年度
    List<Map<String, Object>> selListByYear(String year);
    //高铁入网 月度
    List<Map<String, Object>> selListByMonth(@Param("year") String year, @Param("month") String month);
    //高铁入网 处考派出所  月度
    List<Map<String, Object>> ckpcsByMonth(@Param("year") String year, @Param("month") String month);

    // 路外伤亡  根据 年份  单位代码 查询事故原因
    List<String> lwswItemByYearCode(@Param("year") String year,@Param("code") String code);
    //路外伤亡 处考处机关  月度
    List<Map<String, Object>> ckcjgLWSWByMonth(@Param("year") String year, @Param("month") String month);

    //撞轧牲畜 处考处机关  年度
    List<Map<String, Object>> ckcjgZYSCCountByNowLastYear(@Param("yearL")String yearL, @Param("yearT")String yearT);
    List<Map<String, Object>> ckcjgZYSCInfoByNowLastYear(@Param("yearL")String yearL, @Param("yearT")String yearT,@Param("userId") String userId);
    //撞轧牲畜 处考处机关  月度
    List<Map<String, Object>> ckcjgZYSCByMonth(@Param("year")String year, @Param("month")String month);
    //撞轧牲畜 处考处机关
    List<Map<String, Object>> ckcjgZYSCByLastNowMonth(@Param("yearL")String yearL, @Param("yearT")String yearT);
    //路外伤亡  处考派出所  年度
    List<Map<String, Object>> ckpcsLWSWCountByYear(String year);


    List<Map<String, Object>> findAllpcs();

    int findpcsPolice(@Param("officeId") String officeId);

    int findGatherSum(@Param("code") String code,@Param("month") String month);

    int findGatherSumYear(@Param("code") String code,@Param("year") String year);

    List<Map<String,Object>> findPolices();

    List<Map<String, Object>> findChuLeads();
    // 处考派出所-月度-路外伤亡
    List<Map<String, Object>> ckpcsLWSWByMonth(String year, String month);

    void save(ExamAutoEvaluation examAutoEvaluation);

    Integer selectZgjb(@Param("yearL") String yearL,@Param("yearT")String yearT, @Param("unitId") String unitId);

    Integer selectNtga(@Param("yearL") String yearL,@Param("yearT")String yearT,@Param("unitId")  String unitId);

    List<AffairPartyRewardPunish> selectParty(@Param("lastYearDate") Date lastYearDate,@Param("nowYearDate") Date nowYearDate,@Param("idNumber") String idNumber);

    List<AffairPartyRewardPunish> selectPartyMonth(@Param("lastMonthDate")Date lastMonthDate,@Param("nowMonthDate")Date nowMonthDate, @Param("idNumber") String idNumber);

    List<AffairPartyRewardPunish> selectAssessPerson(@Param("lastYearDate")Date lastYearDate,@Param("nowYearDate")Date nowYearDate,@Param("idNumber") String idNumber);

    List<AffairPartyRewardPunish> selectAssessPersonMonth(@Param("lastMonthDate")Date lastMonthDate,@Param("nowMonthDate")Date nowMonthDate,@Param("idNumber") String idNumber);

     String selectCode(@Param("officeId") String officeId);

    String findUserId(@Param("code") String code);

    String selectUserId(@Param("id") String id);

    List<String> selectUnitIdById(@Param("idNum") String idNum);

    List<Map<String, String>> selectPoAnIncident(@Param("code") String code,@Param("yearL") String yearL, @Param("yearT") String yearT);
    List<Map<String, String>> selectPoAnIncidentMonth(@Param("code") String code,@Param("yearL") String yearL, @Param("yearT") String yearT);
    List<Map<String, String>> selectPoAnIncident2(@Param("code") String code,@Param("yearL") String yearL, @Param("yearT") String yearT);

    Integer seletDpcj(@Param("code")String code, @Param("checkTime") String checkTime);

    Integer selectZysc(@Param("yearL") String yearL,@Param("yearT") String yearT,@Param("code") String code);

    List<String> selectEvId(@Param("idN") String idN);

    List<Map<String, Object>> findFireList(Map<String, Object> param);

    String selectNoUserid(@Param("idNumber") String idNumber);

    List<ExamStandardBaseInfo> findModelByOptionList(ExamAutoEvaluation examAutoEvaluation);

    List<AffairPoliticalGroup> selectAllName();

    List<PersonnelBase> selectAllPeopleMessage(@Param("idN") String idN);

    ExamAutoEvaluation selectKGLastBean(@Param("idNumber") String idNumber,@Param("year") String year, @Param("month") String month);

    Integer selectNumber(@Param("idNumber")String idNumber, @Param("year")String year, @Param("month")String month,@Param("period")String period);

    void deleteBean(@Param("idNumber")String idNumber,@Param("year") String year, @Param("month")String month, @Param("period")String period);
    //删除根据 年度、月度、人、考评模板、考评项
    void deleteInfoByMore(ExamAutoEvaluation examAutoEvaluation);

    void deleteBeanYear(@Param("idNumber")String idNumber,@Param("year") String year, @Param("period")String period);

    /*查询加扣分历史*/
    List<ExamAutoEvaluation> findScoreHistoryList(ExamAutoEvaluation examAutoEvaluation);

    List<Map<String, Object>> selListByNowLastTime(@Param("nowYearDate") Date nowYearDate, @Param("lastYearDate") Date lastYearDate);

    List<Map<String, Object>> ckpcsByNowLastTime(@Param("nowMonthDate") Date nowMonthDate, @Param("lastMonthDate") Date lastMonthDate);
    //路外伤亡，局考处
    List<Map<String, Object>> jkcLWSWByNowLastMonth(@Param("nowMonthDate") Date nowMonthDate, @Param("lastMonthDate") Date lastMonthDate);

    List<Map<String, Object>> ckcjgLWSWByNowLastTime(@Param("nowMonthDate")Date nowMonthDate, @Param("lastMonthDate")Date lastMonthDate);
    //路外伤亡，处机关-年度
    List<Map<String, Object>> ckcjgLWSWCountByNowLast(@Param("nowYearDate") Date nowYearDate, @Param("lastYearDate") Date lastYearDate);
    //路外伤亡，派出所-年度
    List<Map<String, Object>> ckpcsLWSWCountByNowLastYear(@Param("nowYearDate")Date nowYearDate, @Param("lastYearDate") Date lastYearDate);
    // 路外伤亡  根据 年份  单位代码 查询事故原因
    List<String> lwswItemByNowLastYearCode(@Param("nowYearDate") Date nowYearDate, @Param("lastYearDate") Date lastYearDate, @Param("unitCode") String unitCode);
    //路外伤亡，派出所-月度
    List<Map<String, Object>> ckpcsLWSWByNowLastMonth(@Param("nowMonthDate") Date nowMonthDate, @Param("lastMonthDate") Date lastMonthDate);

    List<ExamAutoEvaluation> findProblemList(ExamAutoEvaluation examAutoEvaluation);

    List<AffairTwBase> findTuanZuZhi(@Param("id") String id);

    List<Office> selectJjgUnit();

    String selectEvalId(@Param("code")String code);

    ExamAutoEvaluation getInfoByMore(ExamAutoEvaluation examAutoEvaluation);

    String selectSJName(@Param("id") String id);

    List<String> selectTwEvId(@Param("id") String id);

    Integer selectZyscGt(@Param("yearL") String yearL,@Param("yearT") String yearT,@Param("code") String code);
    //百名民警查缉案件数未完成（每月）局考处，月度
    List<Map<String, Object>> selCountjkcBMMJByTime(@Param("chuId")String chuId ,@Param("yearL") String yearL,@Param("yearT") String yearT);

    List<Map<String, Object>> ckpcsZYSCByLastNowDate(@Param("lastMonthDate") Date lastMonthDate, @Param("nowMonthDate") Date nowMonthDate);

    Integer selectWxxsaj(@Param("yearL") String yearL, @Param("yearT") String yearT,@Param("code") String code);

    Integer selectWxxsajYear(@Param("yearL") String yearL,@Param("yearT") String yearT,@Param("code") String code);
    //处考派出所-撞轧牲畜-年度查询-事故发生案件数
    List<Map<String, Object>> ckpcsZYSCCountByNowLastYear(@Param("lastYearDate") Date lastYearDate, @Param("nowYearDate") Date nowYearDate);
    //处考派出所-撞轧牲畜-年度查询-超过基数的单位具体信息
    List<Map<String, Object>> ckpcsZYSCInfoByNowLastYear(@Param("lastYearDate") Date lastYearDate, @Param("nowYearDate") Date nowYearDate, @Param("userId")String userId);

    List<Map<String, Object>> selectAllpcs();


    List<String> selectDetails(@Param("yearL") String yearL,@Param("yearT") String yearT,@Param("code") String code);

    List<String> selectChuUnit(String unit);

    Integer selectWxxsajListYear(@Param("yearL") String yearL,@Param("yearT") String yearT,@Param("listCode") List<String> listCode);

    List<String> selectDetailsList(@Param("yearL") String yearL,@Param("yearT") String yearT,@Param("listCode") List<String> listCode);
}