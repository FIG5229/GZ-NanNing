/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairZxInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 子女信息DAO接口
 * @author cecil.li
 * @version 2019-12-02
 */
@MyBatisDao
public interface AffairZxInfoDao extends CrudDao<AffairZxInfo> {
    Map<String, Object> findInfoByCreateOrgId(@Param("id") String id, @Param("year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);

    Map<String, Object> findInfoByCreateOrgIds(@Param("ids") List<String> ids, @Param("year") Integer year,@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);

    List<Map<String, String>> countstudyAid(@Param("id")String id, @Param("year")Integer year,  @Param("startDate")Date startDate,
                                            @Param("endDate")Date endDate, @Param("month")String month);
    List<AffairZxInfo> findStudyAidList(AffairZxInfo affairZxInfo);
}