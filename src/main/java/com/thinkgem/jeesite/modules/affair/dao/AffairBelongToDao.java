/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairBelongTo;

/**
 * 党员组织隶属DAO接口
 * @author eav.liu
 * @version 2019-11-11
 */
@MyBatisDao
public interface AffairBelongToDao extends CrudDao<AffairBelongTo> {
	
}