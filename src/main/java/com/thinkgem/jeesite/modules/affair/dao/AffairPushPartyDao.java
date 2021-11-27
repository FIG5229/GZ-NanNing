/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairPushParty;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 推优入党DAO接口
 * @author cecil.li
 * @version 2019-11-06
 */
@MyBatisDao
public interface AffairPushPartyDao extends CrudDao<AffairPushParty> {

    List<AffairPushParty> findByIds(@Param(value = "ids") List<String> ids);

    List<String> selectAllName();

    Integer selectNumber(@Param("idNumber") String idNumber);
}