/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairZyInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 助医管理DAO接口
 * @author cecil.li
 * @version 2019-11-05
 */
@MyBatisDao
public interface AffairZyInfoDao extends CrudDao<AffairZyInfo> {
	Float getSumMoney(AffairZyInfo affairZyInfo);

	List<Map<String, Object>> findInfoByCreateOrgId(@Param(value = "id") String id , @Param(value="year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);

	List<Map<String, Object>> findInfoByCreateOrgIds(@Param("ids") List<String> ids, @Param(value="year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);

    List<AffairZyInfo> findMedicalAidList(AffairZyInfo affairZyInfo);
}