/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairWorkAttendance;

/**
 * 考勤信息DAO接口
 * @author mason.xv
 * @version 2019-11-15
 */
@MyBatisDao
public interface AffairWorkAttendanceDao extends CrudDao<AffairWorkAttendance> {
	
}