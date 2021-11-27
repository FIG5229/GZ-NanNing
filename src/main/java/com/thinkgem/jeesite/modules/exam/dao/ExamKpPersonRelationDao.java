/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.exam.entity.ExamKpPersonRelation;

/**
 * 考评初审人员关系表DAO接口
 * @author kevin.jia
 * @version 2020-11-18
 */
@MyBatisDao
public interface ExamKpPersonRelationDao extends CrudDao<ExamKpPersonRelation> {
	
}