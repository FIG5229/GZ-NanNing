/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairArchiveApproval;

/**
 * 查(借)阅审批DAO接口
 * @author mason.xv
 * @version 2019-11-30
 */
@MyBatisDao
public interface AffairArchiveApprovalDao extends CrudDao<AffairArchiveApproval> {
	
}