/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairGonghuiPersonnel;
import com.thinkgem.jeesite.modules.affair.entity.AffairTwUnitAward;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 团委集体表彰DAO接口
 * @author cecil.li
 * @version 2019-11-06
 */
@MyBatisDao
public interface AffairTwUnitAwardDao extends CrudDao<AffairTwUnitAward> {
/*    void shenHe(@Param(value = "affairTwUnitAward") AffairTwUnitAward affairTwUnitAward);*/

    Map<String, Object> findInfoByCreateOrgId(@Param("id") String id);

    Map<String, Object> findInfoByCreateOrgIds(@Param("ids") List<String> ids);

    List<AffairTwUnitAward> findByIds(@Param(value = "ids") List<String> ids);

    List<AffairTwUnitAward> selectList(@Param(value = "orgId") String orgId);

    AffairTwUnitAward selectBean(@Param("id") String id);

}