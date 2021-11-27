/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.exam.entity.ExamSystemDisplayCheck;

/**
 * 系统最终审核公示DAO接口
 * @author mason.xv
 * @version 2019-12-13
 */
@MyBatisDao
public interface ExamSystemDisplayCheckDao extends CrudDao<ExamSystemDisplayCheck> {
	
}