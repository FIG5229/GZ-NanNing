/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairCdObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 查(借)阅审批DAO接口
 * @author mason.xv
 * @version 2019-11-30
 */
@MyBatisDao
public interface AffairCdObjectDao extends CrudDao<AffairCdObject> {
     void deleteByApprovelIds(@Param(value = "approvelIds") List<String> approvelIds);

      List<AffairCdObject> findSomeByApprovelId(@Param(value = "approvalId")String approvalId);
}