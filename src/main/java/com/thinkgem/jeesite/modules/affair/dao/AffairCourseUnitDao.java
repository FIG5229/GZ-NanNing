/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairCourseResource;
import com.thinkgem.jeesite.modules.affair.entity.AffairCourseUnit;

/**
 * 课程资源共享单位DAO接口
 * @author alan.wu
 * @version 2020-07-31
 */
@MyBatisDao
public interface AffairCourseUnitDao extends CrudDao<AffairCourseUnit> {
	
}