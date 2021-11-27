/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.ExamAutoConfig;

/**
 * 自动考评配置DAO接口
 * @author danil.liu
 * @version 2020-12-15
 */
@MyBatisDao
public interface ExamAutoConfigDao extends CrudDao<ExamAutoConfig> {
	
}