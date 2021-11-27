/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairGhActivityManage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 工会活动管理DAO接口
 * @author mason.xv
 * @version 2020-03-26
 */
@MyBatisDao
public interface AffairGhActivityManageDao extends CrudDao<AffairGhActivityManage> {

    List<AffairGhActivityManage> findByIds(@Param(value = "ids") List<String> ids);
	
}