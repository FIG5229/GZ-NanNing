/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairRetire;

/**
 * 离退干部档案登记花名册DAO接口
 * @author mason.xv
 * @version 2019-11-04
 */
@MyBatisDao
public interface AffairRetireDao extends CrudDao<AffairRetire> {
	
}