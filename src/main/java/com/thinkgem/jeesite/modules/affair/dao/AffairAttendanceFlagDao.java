/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairAttendanceFlag;
import org.apache.ibatis.annotations.Param;

/**
 * 节假日DAO接口
 * @author cecil.li
 * @version 2020-06-30
 */
@MyBatisDao
public interface AffairAttendanceFlagDao extends CrudDao<AffairAttendanceFlag> {
    int findAllDay(@Param("ye") Integer ye, @Param("mo") Integer mo);
	int countXiuDays(@Param("startDate") String startDate, @Param("endDate") String endDate);
}