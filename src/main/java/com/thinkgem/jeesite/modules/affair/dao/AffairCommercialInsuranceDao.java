/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairCommercialInsurance;

/**
 * 商业保险DAO接口
 * @author alan.wu
 * @version 2020-07-03
 */
@MyBatisDao
public interface AffairCommercialInsuranceDao extends CrudDao<AffairCommercialInsurance> {
	
}