/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairGroupStudy;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 党委中心组学习DAO接口
 * @author cecil.li
 * @version 2019-11-04
 */
@MyBatisDao
public interface AffairGroupStudyDao extends CrudDao<AffairGroupStudy> {

    Map<String, Object> findInfoByCreateOrgId(@Param("id") String id, @Param("year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);

    Map<String, Object> findInfoByCreateOrgIds(@Param("ids") List<String> ids, @Param("year") Integer year,@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);

    List<Map<String, String>> countJuPartyStudy(@Param("companyId") String companyId, @Param("year")Integer year,
                                                @Param("startDate") Date startDate, @Param("endDate")Date endDate, @Param("month")String month);

    List<Map<String, String>> countChuPartyStudy(@Param("companyId") String companyId, @Param("year")Integer year,
                                                 @Param("startDate") Date startDate, @Param("endDate")Date endDate, @Param("month")String month);

    List<AffairGroupStudy> findPartyStudyList(AffairGroupStudy affairGroupStudy);

    List<String> selectAllYear();

    List<String> selectAllMonth();

    Integer selectTime(@Param("yearL") String yearL,@Param("yearT") String yearT,@Param("idNumber")  String idNumber);
}