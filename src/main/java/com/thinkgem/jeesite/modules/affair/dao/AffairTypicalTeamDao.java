/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairTypicalTeam;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 典型集体DAO接口
 * @author daniel.liu
 * @version 2020-06-16
 */
@MyBatisDao
public interface AffairTypicalTeamDao extends CrudDao<AffairTypicalTeam> {

    List<Map<String,String>> countTypicalTeam(@Param("officeId")String officeId, @Param("year") Integer year, @Param("startDate") Date startDate,
                                                @Param("endDate") Date endDate, @Param("month") String month);

    List<AffairTypicalTeam> findTypicalList(AffairTypicalTeam affairTypicalPerson);
}