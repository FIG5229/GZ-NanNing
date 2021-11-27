/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairSystemConstruction;

/**
 * 制度建设DAO接口
 * @author daniel.liu
 * @version 2020-07-07
 */
@MyBatisDao
public interface AffairSystemConstructionDao extends CrudDao<AffairSystemConstruction> {
	
}