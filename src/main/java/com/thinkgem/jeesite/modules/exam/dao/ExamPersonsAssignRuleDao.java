/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.exam.entity.ExamPersonsAssignRule;

/**
 * 考评人员分配规则管理DAO接口
 * @author bradley.zhao
 * @version 2020-03-21
 */
@MyBatisDao
public interface ExamPersonsAssignRuleDao extends CrudDao<ExamPersonsAssignRule> {
	
}