/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairInstructorLibrary;

/**
 * 教官库DAO接口
 * @author alan.wu
 * @version 2020-07-22
 */
@MyBatisDao
public interface AffairInstructorLibraryDao extends CrudDao<AffairInstructorLibrary> {
	
}