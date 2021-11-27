/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairMonthStudy;

/**
 * 月度学习计划DAO接口
 * @author mason.xv
 * @version 2019-11-02
 */
@MyBatisDao
public interface AffairMonthStudyDao extends CrudDao<AffairMonthStudy> {
	
}