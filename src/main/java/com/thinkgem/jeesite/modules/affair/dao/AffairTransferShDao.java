/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairTransferSh;

/**
 * 系统内组织关系移交转接关联表DAO接口
 * @author eav.liu
 * @version 2019-11-12
 */
@MyBatisDao
public interface AffairTransferShDao extends CrudDao<AffairTransferSh> {

    Integer findNumByTransferId(String transferId);

    AffairTransferSh findInfoByTranSferId(String tranSferId);
}