/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairRelationshipTransfer;
import org.apache.ibatis.annotations.Param;

/**
 * 系统内组织关系移交转接DAO接口
 * @author eav.liu
 * @version 2019-11-11
 */
@MyBatisDao
public interface AffairRelationshipTransferDao extends CrudDao<AffairRelationshipTransfer> {

    String findLastByIdNumber(String idNumber);

    int updateInfo(@Param(value = "affairRelationshipTransfer") AffairRelationshipTransfer affairRelationshipTransfer);
}