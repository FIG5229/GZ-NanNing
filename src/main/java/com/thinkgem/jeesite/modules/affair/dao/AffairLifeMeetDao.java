/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairLifeMeet;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 民主（组织）生活会DAO接口
 * @author eav.liu
 * @version 2019-11-09
 */
@MyBatisDao
public interface AffairLifeMeetDao extends CrudDao<AffairLifeMeet> {

    void shenHeSave(@Param(value = "affairLifeMeet") AffairLifeMeet affairLifeMeet);

    Map<String, Long> findFinishSumByPId(@Param(value = "id") String id, @Param(value = "year")Integer year, @Param(value = "startDate")Date startDate, @Param(value = "endDate")Date endDate, @Param(value = "month")String month);

    Integer findFinishSumByPIds(@Param(value = "idList") List<String> idList, @Param(value = "year")Integer year, @Param(value = "startDate")Date startDate, @Param(value = "endDate")Date endDate, @Param(value = "month")String month);

    Integer selectNumber(@Param("id") String id,@Param("yearL") String yearL,@Param("yearT") String yearT);
}