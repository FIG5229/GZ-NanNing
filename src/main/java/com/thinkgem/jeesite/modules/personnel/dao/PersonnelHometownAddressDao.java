/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelHometownAddress;

/**
 * 籍贯地址DAO接口
 * @author cecil.li
 * @version 2020-07-09
 */
@MyBatisDao
public interface PersonnelHometownAddressDao extends CrudDao<PersonnelHometownAddress> {
	
}