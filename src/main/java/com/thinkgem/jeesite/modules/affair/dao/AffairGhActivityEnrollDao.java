/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairGhActivityEnroll;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 工会活动报名DAO接口
 * @author cecil.li
 * @version 2019-11-06
 */
@MyBatisDao
public interface AffairGhActivityEnrollDao extends CrudDao<AffairGhActivityEnroll> {

    List<AffairGhActivityEnroll> findByIds(@Param(value = "ids") List<String> ids);

/*
    void shenHe(@Param(value = "affairGhActivityEnroll") AffairGhActivityEnroll affairGhActivityEnroll);
*/

    Map<String, Object> findInfoByCreateOrgId(@Param("id") String id);

    Map<String, Object> findInfoByCreateOrgIds(@Param("ids") List<String> ids);
}