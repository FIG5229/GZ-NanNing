/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.exam.entity.ExamOffice;

/**
 * 绩效组织树表DAO接口
 * @author daniel.liu
 * @version 2020-11-04
 */
@MyBatisDao
public interface ExamOfficeDao extends CrudDao<ExamOffice> {
	/*此表谨慎修改 添加*/
}