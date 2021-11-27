/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairLearnPower;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;

import java.util.List;

/**
 * 学习强国DAO接口
 * @author Alan
 * @version 2020-06-04
 */
@MyBatisDao
public interface AffairLearnPowerDao extends CrudDao<AffairLearnPower> {

    Double selectLastYearIntegeralByUserId(@Param("ye") String ye,@Param("userId") String userId);

    Double selectThisYearCodeByUserId(@Param("year") String year,@Param("userId") String userId);

    Double selectLastMonthById(@Param("year")String year, @Param("month")String month, @Param("userId")String userId);

    Double selectSumYearById(@Param("year")String year, @Param("userId")String userId, @Param("unitId") String unitId);

    Double selectLastScore(@Param("idNumber")String idNumber, @Param("time")String time);

    List<String> selectAllId(@Param("idN") String idN);

    String selectBean(@Param("id")String id, @Param("year")String year);

    String selectUnit(@Param("id")String id);

    String selectName(@Param("id")String id);

    List<String> selectAllTime();

    Integer selectNumber(@Param("yearL")String yearL,@Param("yearT")String yearT,@Param("idNumber") String idNumber);

    String selectUnitId(@Param("unit")String unit);

    List<String> selectAllName();

    String selectIdNumber(@Param("name") String name);

    List<AffairLearnPower> selectAllBean(AffairLearnPower affairLearnPower);

    List<AffairLearnPower> findProblemDataList(AffairLearnPower affairLearnPower);

    Integer selectPeoNumber(@Param("yearL") String yearL,@Param("yearT") String yearT,@Param("idNumber") String idNumber,@Param("score")Integer score);

    Integer selectMjNumber(@Param("time") String time,@Param("idNumber") String idNumber,@Param("score")Integer score);

    Integer selectPeoNumberYear(@Param("time") String time,@Param("idNumber") String idNumber,@Param("score") int score);

    Integer selectPeopleNumber(@Param("time") String time, @Param("idNumber")String idNumber,@Param("score") int score);

    Integer selectPNumber(@Param("time")String time, @Param("idNumber1")String idNumber1);

    AffairLearnPower findByIdNumberTime(@Param("idNumber") String idNumber, @Param("time")String time);
}