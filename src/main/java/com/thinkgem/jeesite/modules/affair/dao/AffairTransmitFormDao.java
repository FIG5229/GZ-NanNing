/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairTransmitForm;

/**
 * 传递单DAO接口
 * @author mason.xv
 * @version 2019-11-29
 */
@MyBatisDao
public interface AffairTransmitFormDao extends CrudDao<AffairTransmitForm> {
	
}