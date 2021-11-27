/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.exam.entity.ExamCheck;

/**
 * 检查法相情况录入DAO接口
 * @author mason.xv
 * @version 2019-12-09
 */
@MyBatisDao
public interface ExamCheckDao extends CrudDao<ExamCheck> {
	
}