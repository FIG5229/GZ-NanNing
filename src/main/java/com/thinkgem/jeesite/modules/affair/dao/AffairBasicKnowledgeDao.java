/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairBasicKnowledge;
import com.thinkgem.jeesite.modules.affair.entity.AffairHealthCheckup;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 基本知识成绩DAO接口
 * @author cecil.li
 * @version 2020-12-28
 */
@MyBatisDao
public interface AffairBasicKnowledgeDao extends CrudDao<AffairBasicKnowledge> {
	int unitCount(@Param("year")String year, @Param("month")String month, @Param("unitId")String unitId, @Param("idNumber")String idNumber);
	List<AffairBasicKnowledge> noPassInfo(@Param("year")String year, @Param("month")String month, @Param("unitId")String unitId);
	List<AffairBasicKnowledge> allInfo(@Param("year")String year, @Param("month")String month);
	// 统计分析
	List<AffairBasicKnowledge> basicInfo(AffairBasicKnowledge affairBasicKnowledge);
	List<AffairBasicKnowledge> findBasicKnoledgeList(AffairBasicKnowledge affairBasicKnowledge);
}