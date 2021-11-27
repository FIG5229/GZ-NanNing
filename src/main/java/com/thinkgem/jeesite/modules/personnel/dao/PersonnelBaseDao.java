/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelBase;
import com.thinkgem.jeesite.modules.personnel.entity.PoliceCertificate;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 人员基本情况信息DAO接口
 * @author cecil.li
 * @version 2019-11-09
 */
@MyBatisDao
public interface PersonnelBaseDao extends CrudDao<PersonnelBase> {
	List<PersonnelBase> findAllWorkInfo(PersonnelBase personnelBase);
    List<Map<String,String>> findNavList();
    List<Map<String,String>> findTotalNum(Map<String, String> param);
	List<Map<String, Object>> findInfosByCode(@Param("id") String id);
	List<Map<String, Object>> findInfosByCodes(@Param("ids") List<String> ids);
	List<Map<String, Object>> findJobInfosByCode(@Param("id") String id);
	List<Map<String, Object>> findJobInfosByCodes(@Param("ids") List<String> ids);

	PersonnelBase findInfoByIdNumber(@Param(value = "idNumber") String idNumber);

	void updateWorkUnitByIds(@Param(value = "ids") List<String> ids, @Param(value = "workUnitId") String workUnitId, @Param(value = "workUnitName") String workUnitName);

	void updateActualWorkUnitByIds(@Param(value = "ids") List<String> ids, @Param(value = "actualUnitId") String actualUnitId, @Param(value = "actualUnitName") String actualUnitName);

	List<String> findByUnitName(@Param(value = "name") String name,@Param(value = "unit") String unit);

    Integer findPersonNumByWorkunitId(@Param(value = "id") String id);

	Integer findPersonNumByWorkunitIds(@Param(value = "ids") List<String> ids);

	PersonnelBase findInfoByIdName(PersonnelBase personnelBase);

	List<PersonnelBase> findPerson(PersonnelBase param);

    List<Map<String, String>> findNavTree();
	//复合查询中，选择信息集下拉框
	List<Map<String, String>> findTableName();

	//人员名册基本信息
	List<Map<String, String>> findRegisterBase(PersonnelBase personnelBase);
	List<Map<String, String>> findNoRegisterBase(PersonnelBase personnelBase);

	//全日制最高学历信息
	List<Map<String, String>> findQrzXueLiInfo(PersonnelBase personnelBase);

	//在职最高学历信息
	List<Map<String, String>> findZzXueLiInfo(PersonnelBase personnelBase);

	//全日制最高学位信息
	List<Map<String, String>> findQrzXueWeiInfo(PersonnelBase personnelBase);

	//在职最高学位信息
	List<Map<String, String>> findZzXueWeiInfo(PersonnelBase personnelBase);

	//年度考核信息
	List<Map<String, String>> findCheckInfo(PersonnelBase personnelBase);

	//履历信息
	List<Map<String, String>> findLvLiInfo(PersonnelBase personnelBase);

	//奖励信息
	List<Map<String, String>> findJiangLiInfo(PersonnelBase personnelBase);

	//惩戒信息
	List<Map<String, String>> findChengJieInfo(PersonnelBase personnelBase);

	//家庭成员信息
	List<Map<String, String>> findFamilyInfo(PersonnelBase personnelBase);

    List<PoliceCertificate> findByIdNumbers(@Param(value = "idNumbers") List<String> idNumbers);

    List<PersonnelBase> findHealthReferenceList(PersonnelBase personnelBase);

	List<PersonnelBase> findPoliticsFaceList(PersonnelBase personnelBase);

	//查询距离退休年龄相差一个月的人员信息
	List<PersonnelBase> findOneMonthTX();

	List<PersonnelBase> findInfosByIds(@Param("ids") List<String> ids);

	List<PersonnelBase> findInfoByIdUnitId(String officeId);

    PersonnelBase selectBean(@Param("idNumber")String idNumber);


	List<PersonnelBase> findListCopy(PersonnelBase personnelBase);

	//查询男士已达到59 60 的年龄 即将达到退休年龄的人员信息
    List<PersonnelBase> findManTXList();

    //查询出年龄大于等于54 的女士人员信息
	List<PersonnelBase> findWomanList();

	//通过身份证集合获取人员详细信息
    List<PersonnelBase> findInfoByList(List<PersonnelBase> list);

    PersonnelBase findUserMessage(@Param( value = "loginName")String loginName);

	List<Map<String, String>>  findUserUnit(@Param( value = "loginName")String loginName);

	String findUserUnitId(@Param( value = "code")String code);

	List<PoliceCertificate> selectByIdNumbers(@Param(value = "idNumbers") List<String> idNumbers,@Param("status") String status);

	List<Map<String, String>> findLastRegisterBase(PersonnelBase personnelBase);

    String selectUnitName(@Param( value = "idN")String idN);

	String selectUnitId(@Param( value = "idNumber")String idNumber);
	//绩效考评-统计分析-警种
    List<String> findBmhjzByUnitId(String unitId);
	//绩效考评-统计分析-警种
	List<String> findjobAbbreviationByUnitId(String unitId);

	String selectUnit(@Param("name") String name);

	//根据身份证号查询单位和id
	Map<String, String> findUnitByIdNumber(@Param("idNumber")String idNumber);
	// 临时抽调民警修改单位
	void updateWorkUnitId(@Param("actualUnit")String actualUnit, @Param("actualUnitId")String actualUnitId, @Param("idNumber")String idNumber);

	//根据单位id获取单位下面的人员
	List<PersonnelBase> findAllUserByUnitId(@Param("unitId")String unitId);

    List<Map<String, String>> countPersonnelAge(@Param("id") String id, @Param("year") Integer year, @Param("startDate") Date startDate,
												@Param("endDate") Date endDate, @Param("month") String month);

    List<Map<String, String>> countNation(@Param("id") String id, @Param("year") Integer year, @Param("startDate") Date startDate,
										  @Param("endDate") Date endDate, @Param("month") String month);

	List<Map<String, Integer>> countWorkYear(@Param("id") String id, @Param("year") Integer year, @Param("startDate") Date startDate,
											@Param("endDate") Date endDate, @Param("month") String month);

	List<Map<String, String>> countPersonnelCategory(@Param("id") String id, @Param("year") Integer year, @Param("startDate") Date startDate,
													 @Param("endDate") Date endDate, @Param("month") String month);

    List<PersonnelBase> findAgeList(PersonnelBase personnelBase);

    List<PersonnelBase> findChartsNationList(PersonnelBase personnelBase);

	List<PersonnelBase> findChartsCategoryList(PersonnelBase personnelBase);

	List<PersonnelBase> findChartsWorkYearList(PersonnelBase personnelBase);

	String getIdNumberById(String id);

    int selCountByUnirId(@Param("chuId") String chuId);

	List<PersonnelBase> findPageOfWorkUnit(PersonnelBase personnelBase);
}
