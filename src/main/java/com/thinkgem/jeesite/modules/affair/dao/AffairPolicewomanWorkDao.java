/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairPolicewomanWork;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 女警工作管理DAO接口
 * @author eav.liu
 * @version 2020-03-26
 */
@MyBatisDao
public interface AffairPolicewomanWorkDao extends CrudDao<AffairPolicewomanWork> {

    List<AffairPolicewomanWork> findByIds(@Param(value = "ids") List<String> ids);
}