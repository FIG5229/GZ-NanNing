/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.exam.entity.ExamPushHistory;

/**
 * 绩效考评项推送DAO接口
 * @author daniel.liu
 * @version 2020-11-04
 */
@MyBatisDao
public interface ExamPushHistoryDao extends CrudDao<ExamPushHistory> {
	
}