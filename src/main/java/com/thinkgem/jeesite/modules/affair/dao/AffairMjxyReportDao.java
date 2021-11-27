/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairMjxyReport;
import com.thinkgem.jeesite.modules.affair.entity.AffairMjxyReportStatistic;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 民警休养申报DAO接口
 * @author cecil.li
 * @version 2019-11-05
 */
@MyBatisDao
public interface AffairMjxyReportDao extends CrudDao<AffairMjxyReport> {
	AffairMjxyReportStatistic findNumsByParentId(@Param(value = "parentId") String parentId, @Param(value = "affairMjxyReport") AffairMjxyReport affairMjxyReport);

	AffairMjxyReportStatistic findByUnitId(@Param(value = "unitId") String unitId, @Param(value = "affairMjxyReport") AffairMjxyReport affairMjxyReport);

	List<AffairMjxyReport> findByIds(@Param(value = "ids") List<String> ids);


	List<Map<String, Object>> findInfoByCreateOrgId(@Param("id") String id);

	List<Map<String, Object>> findInfoByCreateOrgIds(@Param("ids") List<String> ids);

	List<AffairMjxyReport> tongJiMingXi(AffairMjxyReport affairMjxyReport);

    List<AffairMjxyReport> getList(AffairMjxyReport affairMjxyReport);

    void deleteByType(@Param("type") String type, @Param("unit") String unit, @Param("startDate") Date startDate, @Param("endDate") Date endDate,@Param("place")String place);

	void deleteByIdNumbers(@Param("idNumbers") List<String> idNumbers);

}