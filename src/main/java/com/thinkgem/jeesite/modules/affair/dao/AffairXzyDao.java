/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairXzy;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 小种养DAO接口
 * @author cecil.li
 * @version 2019-11-29
 */
@MyBatisDao
public interface AffairXzyDao extends CrudDao<AffairXzy> {

    Map<String, Object> findInfoByCreateOrgId(@Param("id") String id, @Param("year") Integer year, @Param("mStartDate") Date mStartDate, @Param("mEndDate") Date mEndDate);

    Map<String, Object> findInfoByCreateOrgIds(@Param("ids") List<String> ids, @Param("year") Integer year, @Param("mStartDate") Date mStartDate, @Param("mEndDate") Date mEndDate);
}