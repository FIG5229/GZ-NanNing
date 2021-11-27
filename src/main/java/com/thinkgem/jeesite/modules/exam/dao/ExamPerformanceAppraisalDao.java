/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.exam.entity.ExamPerformanceAppraisal;

/**
 * 部门绩效考核情况DAO接口
 * @author cecil.li
 * @version 2020-01-14
 */
@MyBatisDao
public interface ExamPerformanceAppraisalDao extends CrudDao<ExamPerformanceAppraisal> {
	
}