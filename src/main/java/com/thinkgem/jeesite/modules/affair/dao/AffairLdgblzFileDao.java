/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairLdgblzFile;

/**
 * 领导廉政干部档案表DAO接口
 * @author eav.liu
 * @version 2020-03-04
 */
@MyBatisDao
public interface AffairLdgblzFileDao extends CrudDao<AffairLdgblzFile> {
	
}