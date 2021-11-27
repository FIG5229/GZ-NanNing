/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairCreateWork;

/**
 * 创建工作DAO接口
 * @author daniel.liu
 * @version 2020-07-06
 */
@MyBatisDao
public interface AffairCreateWorkDao extends CrudDao<AffairCreateWork> {
	
}