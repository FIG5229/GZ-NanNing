/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairGroupManagement;

import java.util.List;

/**
 * 团费收缴DAO接口
 * @author cecil.li
 * @version 2019-11-06
 */
@MyBatisDao
public interface AffairGroupManagementDao extends CrudDao<AffairGroupManagement> {

    List<AffairGroupManagement> findFeeDetailList(AffairGroupManagement groupManagement);
}