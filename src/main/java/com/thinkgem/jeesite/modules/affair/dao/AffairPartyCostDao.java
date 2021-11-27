/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairPartyCost;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 党费管理DAO接口
 * @author eav.liu
 * @version 2019-11-11
 */
@MyBatisDao
public interface AffairPartyCostDao extends CrudDao<AffairPartyCost> {

    List<String> findThisYearDatas();

    void deleteThisYearByIdNumber(String IdNumber);

    List<Map<String, String>> findBaseMoney(@Param(value = "year")String year, @Param(value = "month")String month);
    List<Map<String, String>> findBaseSumMoney(@Param(value = "lastYear")String lastYear);
    String findTreeNameByTreeId(@Param(value = "treeId") String treeId);
    String findSumMoney(@Param(value = "treeId") String treeId,@Param(value = "year")String year, @Param(value = "month")String month);
    List<AffairPartyCost> findInfo(AffairPartyCost affairPartyCost);
    int deleteOldInfo(@Param("idNumber") String idNumber, @Param("year") String year, @Param("month") String month);
    int countNumber(@Param("idNumber") String idNumber, @Param("year") String year, @Param("month") String month);

    List<String> findSelYearMonthDatas(@Param("year") String year, @Param("month") String month, @Param("partBranchId") String partBranchId);
    //查询该人员 上一年 工作性津贴 个人所得税 合计
    List<Map<String, String>> findPersonLastYearBaseSumMoney(@Param("lastYear") String lastYear, @Param("idNumber") String idNumber);
    //获取该人员当前年/月工资
    List<Map<String, String>> findPersonBaseMoney(@Param("year")String year, @Param("month")String month, @Param("idNumber")String idNumber);
}