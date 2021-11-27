/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairMedicareChange;

/**
 * 医保关系变动DAO接口
 * @author cecil.li
 * @version 2020-07-08
 */
@MyBatisDao
public interface AffairMedicareChangeDao extends CrudDao<AffairMedicareChange> {
	
}