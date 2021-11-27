/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairEducationTrain;
import com.thinkgem.jeesite.modules.affair.entity.AffairTwUnitAward;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 教育训练培训计划DAO接口
 * @author jack.xu
 * @version 2020-07-01
 */
@MyBatisDao
public interface AffairEducationTrainDao extends CrudDao<AffairEducationTrain> {
//    List<Map<String, Object>> findInfoByCreateOrgId(@Param("id") String id, @Param("year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
//    List<Map<String, Object>> findInfoByCreateOrgIds(@Param("ids") List<String> ids, @Param("year") Integer year,@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
    List<AffairEducationTrain> findByIds(@Param(value = "ids") List<String> ids);
}