/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairTwPersonalAward;
import com.thinkgem.jeesite.modules.affair.entity.AffairTwUnitAward;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 团委个人表彰DAO接口
 * @author cecil.li
 * @version 2019-11-06
 */
@MyBatisDao
public interface AffairTwPersonalAwardDao extends CrudDao<AffairTwPersonalAward> {

    void shenHe(@Param(value = "affairTwPersonalAward") AffairTwPersonalAward affairTwPersonalAward);

    Map<String, Object> findInfoByCreateOrgId(@Param("id") String id);

    Map<String, Object> findInfoByCreateOrgIds(@Param("ids") List<String> ids);

    List<AffairTwPersonalAward> findByIds(@Param(value = "ids") List<String> ids);
}