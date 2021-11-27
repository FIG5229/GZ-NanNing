/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairWelfareCondolences;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 福利慰问DAO接口
 * @author daniel.liu
 * @version 2020-05-12
 */
@MyBatisDao
public interface AffairWelfareCondolencesDao extends CrudDao<AffairWelfareCondolences> {
	List<AffairWelfareCondolences> allList(@Param("year")String year, @Param("month")String month, @Param("startTime")String startTime, @Param("endTime")String endTime);
	// 自动考评每个单位的扣分项次数
	int unitCount( @Param("startTime")String startTime, @Param("endTime")String endTime, @Param("unitId")String unitId);
}