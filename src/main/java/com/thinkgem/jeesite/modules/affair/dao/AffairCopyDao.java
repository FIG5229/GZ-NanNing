/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairCopy;

/**
 * 复印档案登记DAO接口
 * @author mason.xv
 * @version 2019-11-05
 */
@MyBatisDao
public interface AffairCopyDao extends CrudDao<AffairCopy> {
	
}