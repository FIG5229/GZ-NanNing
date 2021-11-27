/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairDcwtLibrary;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 督察问题管理DAO接口
 * @author cecil.li
 * @version 2019-11-08
 */
@MyBatisDao
public interface AffairDcwtLibraryDao extends CrudDao<AffairDcwtLibrary> {

	/*List<Map<String, Object>> findTotalInfos(@Param("id") String id, @Param("year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);

	List<Map<String, Object>> findCompleteInfos(@Param("id") String id, @Param("year") Integer year,@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);

	List<Map<String, Object>> findMultiTotalInfos(@Param("ids") List<String> ids, @Param("year") Integer year,@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);

	List<Map<String, Object>> findMultiCompleteInfos(@Param("ids") List<String> ids, @Param("year") Integer year,@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);

	List<Map<String, Object>> findPieInfoByCreateOrgId(@Param("id") String id, @Param("year") Integer year,@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);

	List<Map<String, Object>> findPieInfoByCreateOrgIds(@Param("ids") List<String> ids, @Param("year") Integer year,@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);*/




	//按照时间排序
	public List<AffairDcwtLibrary> findListBy(AffairDcwtLibrary affairDcwtLibrary);
	public Page<AffairDcwtLibrary> findListByDate(Page<AffairDcwtLibrary> page, AffairDcwtLibrary affairDcwtLibrary);

	//旧版统计分析
	List<Map<String, Object>> findAllTotalInfos(@Param("id") String id, @Param("year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
	List<Map<String, Object>> findAllCompleteInfos(@Param("id") String id, @Param("year") Integer year,@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
	List<Map<String, Object>> findJuTotalInfos(@Param("id") String id, @Param("year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
	List<Map<String, Object>> findJuCompleteInfos(@Param("id") String id, @Param("year") Integer year,@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
	List<Map<String, Object>> findNncTotalInfos(@Param("id") String id, @Param("year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
	List<Map<String, Object>> findNncCompleteInfos(@Param("id") String id, @Param("year") Integer year,@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
	List<Map<String, Object>> findLzcTotalInfos(@Param("id") String id, @Param("year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
	List<Map<String, Object>> findLzcCompleteInfos(@Param("id") String id, @Param("year") Integer year,@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
	List<Map<String, Object>> findBhcTotalInfos(@Param("id") String id, @Param("year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
	List<Map<String, Object>> findBhcCompleteInfos(@Param("id") String id, @Param("year") Integer year,@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
	List<Map<String, Object>> findAllMultiTotalInfos(@Param("ids") List<String> ids, @Param("year") Integer year,@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
	List<Map<String, Object>> findAllMultiCompleteInfos(@Param("ids") List<String> ids, @Param("year") Integer year,@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
	List<Map<String, Object>> findAllPieInfoByCreateOrgId(@Param("id") String id, @Param("year") Integer year,@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
	List<Map<String, Object>> findAllPieInfoByCreateOrgIds(@Param("ids") List<String> ids, @Param("year") Integer year,@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
	List<Map<String, Object>> findJuMultiTotalInfos(@Param("ids") List<String> ids, @Param("year") Integer year,@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
	List<Map<String, Object>> findJuMultiCompleteInfos(@Param("ids") List<String> ids, @Param("year") Integer year,@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
	List<Map<String, Object>> findJuPieInfoByCreateOrgId(@Param("id") String id, @Param("year") Integer year,@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
	List<Map<String, Object>> findJuPieInfoByCreateOrgIds(@Param("ids") List<String> ids, @Param("year") Integer year,@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
	List<Map<String, Object>> findNncMultiTotalInfos(@Param("ids") List<String> ids, @Param("year") Integer year,@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
	List<Map<String, Object>> findNncMultiCompleteInfos(@Param("ids") List<String> ids, @Param("year") Integer year,@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
	List<Map<String, Object>> findNncPieInfoByCreateOrgId(@Param("id") String id, @Param("year") Integer year,@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
	List<Map<String, Object>> findNncPieInfoByCreateOrgIds(@Param("ids") List<String> ids, @Param("year") Integer year,@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
	List<Map<String, Object>> findLzcMultiTotalInfos(@Param("ids") List<String> ids, @Param("year") Integer year,@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
	List<Map<String, Object>> findLzcMultiCompleteInfos(@Param("ids") List<String> ids, @Param("year") Integer year,@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
	List<Map<String, Object>> findLzcPieInfoByCreateOrgId(@Param("id") String id, @Param("year") Integer year,@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
	List<Map<String, Object>> findLzcPieInfoByCreateOrgIds(@Param("ids") List<String> ids, @Param("year") Integer year,@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
	List<Map<String, Object>> findBhcMultiTotalInfos(@Param("ids") List<String> ids, @Param("year") Integer year,@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
	List<Map<String, Object>> findBhcMultiCompleteInfos(@Param("ids") List<String> ids, @Param("year") Integer year,@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
	List<Map<String, Object>> findBhcPieInfoByCreateOrgId(@Param("id") String id, @Param("year") Integer year,@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
	List<Map<String, Object>> findBhcPieInfoByCreateOrgIds(@Param("ids") List<String> ids, @Param("year") Integer year,@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
	List<Map<String, Object>> findAllSupervisoryUnitByCreateOrgId(@Param("id") String id, @Param("year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
	List<Map<String, Object>> findAllSupervisoryUnitByCreateOrgIds(@Param("ids") List<String> ids, @Param("year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
	List<Map<String, Object>> findSupervisoryUnitByCreateOrgId(@Param("id") String id, @Param("year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
	List<Map<String, Object>> findSupervisoryUnitByCreateOrgIds(@Param("ids") List<String> ids, @Param("year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);
	//督察统计分细功能更新
	List<AffairDcwtLibrary> findAllInfoList(AffairDcwtLibrary affairDcwtLibrary);
	List<AffairDcwtLibrary> findAllPieInfoList(AffairDcwtLibrary affairDcwtLibrary);
	List<AffairDcwtLibrary> findAllDetailPieInfoList(AffairDcwtLibrary affairDcwtLibrary);
	List<AffairDcwtLibrary> findAllDetailInfoList(AffairDcwtLibrary affairDcwtLibrary);
	List<AffairDcwtLibrary> findAllPieDetailInfoList(AffairDcwtLibrary affairDcwtLibrary);
	List<AffairDcwtLibrary> findJuInfoList(AffairDcwtLibrary affairDcwtLibrary);
	List<AffairDcwtLibrary> findJuPieInfoList(AffairDcwtLibrary affairDcwtLibrary);
	List<AffairDcwtLibrary> findJuDetailPieInfoList(AffairDcwtLibrary affairDcwtLibrary);
	List<AffairDcwtLibrary> findJuDetailInfoList(AffairDcwtLibrary affairDcwtLibrary);
	List<AffairDcwtLibrary> findJuPieDetailInfoList(AffairDcwtLibrary affairDcwtLibrary);
	List<AffairDcwtLibrary> findNncInfoList(AffairDcwtLibrary affairDcwtLibrary);
	List<AffairDcwtLibrary> findNncPieInfoList(AffairDcwtLibrary affairDcwtLibrary);
	List<AffairDcwtLibrary> findNncDetailPieInfoList(AffairDcwtLibrary affairDcwtLibrary);
	List<AffairDcwtLibrary> findNncDetailInfoList(AffairDcwtLibrary affairDcwtLibrary);
	List<AffairDcwtLibrary> findNncPieDetailInfoList(AffairDcwtLibrary affairDcwtLibrary);
	List<AffairDcwtLibrary> findLzcInfoList(AffairDcwtLibrary affairDcwtLibrary);
	List<AffairDcwtLibrary> findLzcPieInfoList(AffairDcwtLibrary affairDcwtLibrary);
	List<AffairDcwtLibrary> findLzcDetailPieInfoList(AffairDcwtLibrary affairDcwtLibrary);
	List<AffairDcwtLibrary> findLzcDetailInfoList(AffairDcwtLibrary affairDcwtLibrary);
	List<AffairDcwtLibrary> findLzcPieDetailInfoList(AffairDcwtLibrary affairDcwtLibrary);
	List<AffairDcwtLibrary> findBhcInfoList(AffairDcwtLibrary affairDcwtLibrary);
	List<AffairDcwtLibrary> findBhcPieInfoList(AffairDcwtLibrary affairDcwtLibrary);
	List<AffairDcwtLibrary> findBhcDetailPieInfoList(AffairDcwtLibrary affairDcwtLibrary);
	List<AffairDcwtLibrary> findBhcDetailInfoList(AffairDcwtLibrary affairDcwtLibrary);
	List<AffairDcwtLibrary> findBhcPieDetailInfoList(AffairDcwtLibrary affairDcwtLibrary);
	List<AffairDcwtLibrary> findAllDcLibrary(AffairDcwtLibrary affairDcwtLibrary);
	List<AffairDcwtLibrary> findAllDcLibraryByJdUnit(AffairDcwtLibrary affairDcwtLibrary);

}