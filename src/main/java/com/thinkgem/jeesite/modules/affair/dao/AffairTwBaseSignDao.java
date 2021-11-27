/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairTwBaseSign;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 团委（支部）基本信息DAO接口
 * @author cecil.li
 * @version 2019-12-04
 */
@MyBatisDao
public interface AffairTwBaseSignDao extends CrudDao<AffairTwBaseSign> {

    public void deleteByMainId(String tbId);

    public void deleteByMainIds(@Param(value = "tbIds") List<String> tbIds);


    Integer selectNumber(@Param(value = "id") String id);

    List<Map<String, String>> countCares(@Param("officeId") String officeId, @Param("year")Integer year,
                                        @Param("startDate")Date startDate, @Param("endDate")Date endDate,  @Param("month")String month);

    List<AffairTwBaseSign> findCadresList(AffairTwBaseSign affairTwBaseSign);
}