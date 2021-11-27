/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairPoliceThoughtAnalysis;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 民警思想动态分析DAO接口
 * @author daniel.liu
 * @version 2020-05-11
 */
@MyBatisDao
public interface AffairPoliceThoughtAnalysisDao extends CrudDao<AffairPoliceThoughtAnalysis> {

    List<String> selectAllYear();

    List<String> selectAllMonth();

    Integer selectNumber(@Param("yearL") String yearL,@Param("yearT")String yearT,@Param("idNumber") String idNumber);

    List<String> selectAllName();
}