/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairLedgerEntryTimes;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 台账录入次数DAO接口
 * @author cecil.li
 * @version 2020-12-29
 */
@MyBatisDao
public interface AffairLedgerEntryTimesDao extends CrudDao<AffairLedgerEntryTimes> {
	String monthTimes(@Param("year")String year, @Param("month") String month, @Param("unitId")String unitId);
	List<AffairLedgerEntryTimes> yearTimes(@Param("year")String year, @Param("unitId")String unitId);
	void deleteAllInfo(@Param("year")String year, @Param("unitId")String unitId);
}