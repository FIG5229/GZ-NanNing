/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairPoliceRank;

/**
 * 警衔管理表DAO接口
 * @author mason.xv
 * @version 2019-11-05
 */
@MyBatisDao
public interface AffairPoliceRankDao extends CrudDao<AffairPoliceRank> {

	List<Map<String, Object>> findInfoByCreateOrgId(@Param(value = "id") String id, @Param(value = "year") Integer year , @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);

	List<Map<String, Object>> findInfoByCreateOrgIds(@Param("ids") List<String> ids, @Param(value = "year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
	
}