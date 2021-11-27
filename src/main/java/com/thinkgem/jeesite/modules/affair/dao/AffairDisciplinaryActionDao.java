/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairDisciplinaryAction;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 纪律处分DAO接口
 * @author cecil.li
 * @version 2019-11-08
 */
@MyBatisDao
public interface AffairDisciplinaryActionDao extends CrudDao<AffairDisciplinaryAction> {

    List<Map<String, Object>> findInfoByUnitId(@Param("id") String id, @Param("year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);

    List<Map<String, Object>> findInfoByUnitIds(@Param("ids") List<String> ids, @Param("year") Integer year,@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);

    List<String> selectPunishmentApprovalDocumentById(String userId);

    List<String> selectProblemTypesById(String userId);

    List<String> selectPunishmentClassiryById(String userId);

    List<AffairDisciplinaryAction> selectDisciplinaryActionsById(String userId);

    List<Map<String, Object>> findPeopleCount(@Param("id") String id, @Param("year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
    List<AffairDisciplinaryAction> findJuInfoDetail(AffairDisciplinaryAction affairDisciplinaryAction);
    List<AffairDisciplinaryAction> findNncInfoDetail(AffairDisciplinaryAction affairDisciplinaryAction);
    List<AffairDisciplinaryAction> findLzcInfoDetail(AffairDisciplinaryAction affairDisciplinaryAction);
    List<AffairDisciplinaryAction> findBhcInfoDetail(AffairDisciplinaryAction affairDisciplinaryAction);
    List<Map<String, Object>> findPieInfoByNature(@Param("id") String id, @Param("year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
    List<AffairDisciplinaryAction> findInfoByNatureDetail(AffairDisciplinaryAction affairDisciplinaryAction);
    List<Map<String, Object>> findCountByCfUnit(@Param("id") String id, @Param("year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
    List<AffairDisciplinaryAction> findDetailByCfUnit(AffairDisciplinaryAction affairDisciplinaryAction);
    List<Map<String, Object>> findPeopleCountByChuFen(@Param("id") String id, @Param("year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
    List<AffairDisciplinaryAction> findDetailInfoByChuFen(AffairDisciplinaryAction affairDisciplinaryAction);
    List<Map<String, Object>> findPieInfoByDjChuFen(@Param("id") String id, @Param("year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
    List<AffairDisciplinaryAction> findDetailInfoByDjChuFen(AffairDisciplinaryAction  affairDisciplinaryAction);
    List<Map<String, Object>> findPieInfoByXzChuFen(@Param("id") String id, @Param("year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
    List<AffairDisciplinaryAction> findDetailInfoByXzChuFen(AffairDisciplinaryAction affairDisciplinaryAction);

    List<AffairDisciplinaryAction> allInfo(@Param("year")String year, @Param("month")String month, @Param("startTime")String startTime, @Param("endTime")String endTime);

}