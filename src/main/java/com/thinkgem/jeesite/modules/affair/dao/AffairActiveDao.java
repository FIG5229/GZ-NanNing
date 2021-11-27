/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairActive;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 活动情况DAO接口
 * @author alan.wu
 * @version 2020-07-27
 */
@MyBatisDao
public interface AffairActiveDao extends CrudDao<AffairActive> {

    List<Map<String,String>> countJuReadActive(@Param("officeId") String officeId, @Param("year")Integer year,
                                               @Param("startDate") Date startDate, @Param("endDate")Date endDate, @Param("month")String month);
    List<Map<String,String>> countChuReadActive(@Param("officeId") String officeId, @Param("year")Integer year,
                                                @Param("startDate") Date startDate, @Param("endDate")Date endDate, @Param("month")String month);
    List<AffairActive> findReadingActivitiesList(AffairActive affairActive);

    List<String> selectAllYear();

    List<String> selectAllMonth();

    Integer selectNumber(@Param("yearL")String yearL,@Param("yearT")String yearT, @Param("idNumber")String idNumber);

    List<String> selectAllName();
}