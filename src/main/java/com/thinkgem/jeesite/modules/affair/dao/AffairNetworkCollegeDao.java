/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairNetworkCollege;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 中国干部网络学院DAO接口
 * @author alan.wu
 * @version 2020-08-03
 */
@MyBatisDao
public interface AffairNetworkCollegeDao extends CrudDao<AffairNetworkCollege> {

    Double selectLastYearIntegeralByUserId(@Param("ye") String ye, @Param("userId") String userId);

    Double selectThisYearCodeByUserId(@Param("year") String year,@Param("userId") String userId);

    Double selectLastMonthById(@Param("year")String year, @Param("month")String month, @Param("userId")String userId);

    String selectSumYearById(@Param("year")String year, @Param("userId")String userId);

    Double selectLastScore(@Param("idNumber")String idNumber, @Param("time")String time);

    List<String> selectAllId(@Param("userId")String userId);

    String selectBean(@Param("id")String id, @Param("year")String year);

    String selectUnit(@Param("id")String id);

    String selectName(@Param("id")String id);

    String selectIdNumber(@Param("name") String name);

    List<AffairNetworkCollege> selectAllBean(AffairNetworkCollege affairNetworkCollege);

    List<AffairNetworkCollege> findProblemDataList(AffairNetworkCollege affairNetworkCollege);
}