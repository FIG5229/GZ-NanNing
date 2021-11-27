/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairSevenKnowledge;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 七知档案DAO接口
 * @author daniel.liu
 * @version 2020-07-03
 */
@MyBatisDao
public interface AffairSevenKnowledgeDao extends CrudDao<AffairSevenKnowledge> {

    int selByIdNumber(String idNumber);

    void deleteByIdNumber(String idNumber);
    //七知档案统计分析
    List<Map<String, Object>> sevenKnowledgeStatistics(@Param("parentId") String parentId, @Param("year")Integer year,
                                                       @Param("startDate") Date startDate, @Param("endDate")Date endDate, @Param("month")String month);
    //七知档案统计分析-详情
    List<AffairSevenKnowledge> findSevenKnowledgeChartList(AffairSevenKnowledge affairSevenKnowledge);
    //处账号-七知档案统计分析-详情
    List<Map<String, Object>> sevenChuKnowledgeStatistics(@Param("parentId") String parentId, @Param("year")Integer year,
                                                          @Param("startDate") Date startDate, @Param("endDate")Date endDate, @Param("month")String month);
}