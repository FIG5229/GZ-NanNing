/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairIdeaAnalysis;
import org.apache.ibatis.annotations.Param;

/**
 * 党员队伍思想状况分析DAO接口
 * @author eav.liu
 * @version 2019-11-09
 */
@MyBatisDao
public interface AffairIdeaAnalysisDao extends CrudDao<AffairIdeaAnalysis> {
    // 审核
    void shenHeSave(@Param(value = "affairIdeaAnalysis") AffairIdeaAnalysis affairIdeaAnalysis);

    //自动考评
    int findCountByMonth(@Param(value = "year")String year,@Param(value = "quarterly")String quarterly,@Param(value = "org")String org, @Param(value = "orgId")String orgId);

    Integer selectNumber(@Param(value = "yearL")String yearL,@Param("yearT")String yearT,@Param(value = "idNumber") String idNumber);

    Integer selectLeadNumber(@Param(value = "time")String time, @Param(value = "idNumber")String idNumber);
}