/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairMediaManagement;
import com.thinkgem.jeesite.modules.affair.entity.AffairTwUnitAward;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 刊用情况DAO接口
 * @author cecil.li
 * @version 2019-11-07
 */
@MyBatisDao
public interface AffairMediaManagementDao extends CrudDao<AffairMediaManagement> {

    List<AffairMediaManagement> findByIds(@Param(value = "ids") List<String> ids);

}