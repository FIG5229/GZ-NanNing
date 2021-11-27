/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairTousujubaoguanli;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 投诉举报DAO接口
 * @author cecil.li
 * @version 2019-11-07
 */
@MyBatisDao
public interface AffairTousujubaoguanliDao extends CrudDao<AffairTousujubaoguanli> {

    void shenHe(@Param(value ="affairItemReport" ) AffairTousujubaoguanli affairTousujubaoguanli);
	void zhuanban(@Param(value = "affairTousujubaoguanli") AffairTousujubaoguanli affairTousujubaoguanli, @Param(value = "zbUnitId")String zbUnitId, @Param(value = "zbUnit")String zbUnit);

	/*List<Map<String, Object>> findChashiInfos(@Param("id") String id, @Param("year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);

	List<Map<String, Object>> findChufenInfos(@Param("id") String id, @Param("year") Integer year,@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);

	List<Map<String, Object>> findMultiChashiInfos(@Param("ids") List<String> ids, @Param("year") Integer year,@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);

	List<Map<String, Object>> findMultiChufenInfos(@Param("ids") List<String> ids, @Param("year") Integer year,@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);

	List<Map<String, Object>> findPieInfoByCreateOrgId(@Param("id") String id, @Param("year") Integer year,@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);

	List<Map<String, Object>> findPieInfoByCreateOrgIds(@Param("ids") List<String> ids, @Param("year") Integer year,@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);*/

	void banJie(String id);

	List<AffairTousujubaoguanli> findByIds(@Param(value = "ids") List<String> ids);

	List<Map<String, Object>> findBjInfo(@Param("id") String id, @Param("year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
	List<Map<String, Object>> findExBjInfo(@Param("id") String id, @Param("year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
	List<Map<String, Object>> findChaShiInfo(@Param("sdUnit") String sdUnit, @Param("id") String id, @Param("year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
	List<Map<String, Object>> findChaFouInfo(@Param("sdUnit") String sdUnit, @Param("id") String id, @Param("year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
	List<Map<String, Object>> findBuFenInfo(@Param("sdUnit") String sdUnit, @Param("id") String id, @Param("year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
	List<Map<String, Object>> findZanCunInfo(@Param("sdUnit") String sdUnit, @Param("id") String id, @Param("year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
	List<AffairTousujubaoguanli> findIsCheckInfoDetail(AffairTousujubaoguanli affairTousujubaoguanli);
	List<AffairTousujubaoguanli> findBjTypeInfoDetail(AffairTousujubaoguanli affairTousujubaoguanli);
	List<Map<String, Object>> findZjInfo(@Param("id") String id, @Param("year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
	List<Map<String, Object>> findSfInfo(@Param("id") String id, @Param("year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
	List<Map<String, Object>> findJjInfo(@Param("id") String id, @Param("year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
	List<Map<String, Object>> findZjPieInfo(@Param("sdUnit") String sdUnit, @Param("id") String id, @Param("year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
	List<Map<String, Object>> findSfPieInfo(@Param("sdUnit") String sdUnit, @Param("id") String id, @Param("year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
	List<Map<String, Object>> findJjPieInfo(@Param("sdUnit") String sdUnit, @Param("id") String id, @Param("year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
	List<AffairTousujubaoguanli> findZjInfoDetail(AffairTousujubaoguanli affairTousujubaoguanli);
	List<AffairTousujubaoguanli> findSfInfoDetail(AffairTousujubaoguanli affairTousujubaoguanli);
	List<AffairTousujubaoguanli> findJjInfoDetail(AffairTousujubaoguanli affairTousujubaoguanli);


}