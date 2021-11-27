/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairInformationAdoption;

/**
 * 信息采用DAO接口
 * @author cecil.li
 * @version 2020-12-29
 */
@MyBatisDao
public interface AffairInformationAdoptionDao extends CrudDao<AffairInformationAdoption> {
	
}