/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairTjRegister;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 团籍注册DAO接口
 * @author cecil.li
 * @version 2019-11-20
 */
@MyBatisDao
public interface AffairTjRegisterDao extends CrudDao<AffairTjRegister> {
	public AffairTjRegister getGroup(String idNumber);

	List<AffairTjRegister> findByIds(@Param(value = "ids") List<String> ids);

	AffairTjRegister findInfoByIdNumber(@Param(value = "idNumber")String idNumber);

    List<String> findListByIdNo(String idNumber);

	void deleteById(String idNumber);

    List<AffairTjRegister> findListByPartyBranchId(String partyBranchId);

    List<String> getPartyBranchIdByIdNo(String idNumber);

    List<String> getPartyBranchIdByIds(@Param(value = "ids") List<String> ids);

    //根据年龄统计相应的人数
    Map<String,String> findTuanQingAge(@Param("officeId")String officeId,@Param("year") Integer year,@Param("startDate") Date startDate, @Param("endDate") Date endDate,
                    @Param("month") String month, @Param(value = "smailAge") int smailAge, @Param(value = "bigAge") int bigAge);

    List<Map<String, String>> findTourFee(@Param(value = "officeId") String officeId, @Param(value = "year") Integer year,
                                          @Param(value = "month") String month, @Param("startDate")Date startDate,@Param("endDate") Date endDate);

    List<Map<String, String>> findEducation(@Param(value = "officeId") String officeId,@Param("year") Integer year,
                                            @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month")String month);

    List<AffairTjRegister> findEducationPage(AffairTjRegister affairTjRegister);

    List<Map<String, String>> countPolitical(@Param("officeId") String officeId, @Param("year")Integer year,
                                             @Param("startDate")Date startDate, @Param("endDate")Date endDate,  @Param("month")String month);

    List<AffairTjRegister> findPoliticalPage(AffairTjRegister affairTjRegister);


    List<AffairTjRegister> findAgeList(AffairTjRegister affairTjRegister);

    List<Map<String, String>> countCadres(@Param("officeId") String officeId, @Param("year")Integer year,
                                          @Param("startDate")Date startDate, @Param("endDate")Date endDate,  @Param("month")String month);

    List<AffairTjRegister> findCadresList(AffairTjRegister affairTjRegister);


    List<AffairTjRegister> findPersonNotInList(@Param("idNumbers") String[] attendIdArray);
}