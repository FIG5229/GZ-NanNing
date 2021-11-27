/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairGz;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 固资管理DAO接口
 * @author cecil.li
 * @version 2019-11-05
 */
@MyBatisDao
public interface AffairGzDao extends CrudDao<AffairGz> {

    void shenHe(@Param(value = "affairGz") AffairGz affairGz);

    List<Map<String, Object>> findInfoByCreateOrgId(@Param("id") String id, @Param("year") Integer year, @Param("mStartDate") Date mStartDate, @Param("mEndDate") Date mEndDate);

    List<Map<String, Object>> findInfoByCreateOrgIds(@Param("ids") List<String> ids, @Param("year") Integer year, @Param("mStartDate") Date mStartDate, @Param("mEndDate") Date mEndDate);
}