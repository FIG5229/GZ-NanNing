/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairBasicWage;

/**
 * 职务工资标准DAO接口
 * @author cecil.li
 * @version 2020-06-08
 */
@MyBatisDao
public interface AffairBasicWageDao extends CrudDao<AffairBasicWage> {
	
}