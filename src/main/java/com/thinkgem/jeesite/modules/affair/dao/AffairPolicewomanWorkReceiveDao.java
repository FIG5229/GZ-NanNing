/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairPolicewomanWork;
import com.thinkgem.jeesite.modules.affair.entity.AffairPolicewomanWorkReceive;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 女警工作管理关联表DAO接口
 * @author eav.liu
 * @version 2020-03-26
 */
@MyBatisDao
public interface AffairPolicewomanWorkReceiveDao extends CrudDao<AffairPolicewomanWorkReceive> {

    void deleteByNoticeId(@Param(value = "pwWorkId") String pwWorkId);
}