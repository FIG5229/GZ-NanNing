/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairPoliceHomeChild;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 民警小家关联表DAO接口
 * @author daniel.liu
 * @version 2020-07-15
 */
@MyBatisDao
public interface AffairPoliceHomeChildDao extends CrudDao<AffairPoliceHomeChild> {

    List<AffairPoliceHomeChild> findByParentId(@Param(value = "id")String id);
}