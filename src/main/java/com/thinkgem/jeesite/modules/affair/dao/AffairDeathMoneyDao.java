/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairDeathMoney;

/**
 * 伤亡特殊补助金DAO接口
 * @author cecil.li
 * @version 2020-07-03
 */
@MyBatisDao
public interface AffairDeathMoneyDao extends CrudDao<AffairDeathMoney> {
	
}