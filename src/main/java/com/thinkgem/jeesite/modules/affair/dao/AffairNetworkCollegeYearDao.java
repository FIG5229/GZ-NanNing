/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairLearnPowerYear;
import com.thinkgem.jeesite.modules.affair.entity.AffairNetworkCollegeYear;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 中国干部网络学院--年度DAO接口
 * @author alan.wu
 * @version 2020-08-03
 */
@MyBatisDao
public interface AffairNetworkCollegeYearDao extends CrudDao<AffairNetworkCollegeYear> {

    int findUserById(@Param("yea") String yea, @Param("userId") String userId);

    void updateById(@Param("yea") String yea, @Param("userId") String userId, @Param("sumCode") Double sumCode, @Param("thisYearCode") Double thisYearCode,@Param("lastYearIntegral")Double lastYearIntegral);

    void creat(@Param("affairNetworkCollegeYear") AffairNetworkCollegeYear affairNetworkCollegeYear);

    String selectSumYearById(@Param("year")String year, @Param("userId")String userId);

    String selectLastScore(@Param("idNumber")String idNumber, @Param("time")String time);

    List<String> selectAllId();

    String selectBean(@Param("id")String id, @Param("year")String year);

    String selectUnit(@Param("id")String id);

    String selectName(@Param("id")String id);

    List<AffairNetworkCollegeYear> findListByYear(AffairNetworkCollegeYear affairNetworkCollegeYear);

    Double findlastYearIntegral(AffairNetworkCollegeYear affairNetworkCollegeYear);
}