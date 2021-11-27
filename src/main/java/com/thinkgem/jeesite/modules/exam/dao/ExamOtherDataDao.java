/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.exam.entity.ExamOtherData;

/**
 * 其他数据DAO接口
 * @author kevin.jia
 * @version 2020-11-11
 */
@MyBatisDao
public interface ExamOtherDataDao extends CrudDao<ExamOtherData> {
	
}