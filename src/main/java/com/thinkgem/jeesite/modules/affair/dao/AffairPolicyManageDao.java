/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairPolicyManage;

/**
 * 政策管理DAO接口
 * @author cecil.li
 * @version 2020-05-29
 */
@MyBatisDao
public interface AffairPolicyManageDao extends CrudDao<AffairPolicyManage> {
	
}