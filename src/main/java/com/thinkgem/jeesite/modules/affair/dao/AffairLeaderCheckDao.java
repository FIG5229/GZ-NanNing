/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairLeaderCheck;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 领导年度考核表DAO接口
 * @author mason.xv
 * @version 2019-11-05
 */
@MyBatisDao
public interface AffairLeaderCheckDao extends CrudDao<AffairLeaderCheck> {

    void shenHe(@Param(value ="affairLeaderCheck" ) AffairLeaderCheck affairLeaderCheck);

    List<AffairLeaderCheck> findByIds(@Param(value = "ids") List<String> ids);
}