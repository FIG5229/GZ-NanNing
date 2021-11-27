/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairPartyMember;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 党员花名册DAO接口
 * @author eav.liu
 * @version 2019-11-05
 */
@MyBatisDao
public interface AffairPartyMemberDao extends CrudDao<AffairPartyMember> {

	List<Map<String, Object>> findInfoByPartyBranchId(@Param(value = "id") String id , @Param(value="year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);

	List<Map<String, Object>> findInfoByPartyBranchIds(@Param(value ="ids") List<String> ids, @Param(value ="year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);

    List<String> findListByIdNo(String idNo);

    AffairPartyMember getGroup(String idNumber);

	void deleteByIdNo(String idNo);

    List<AffairPartyMember> findListByPartyBranchId(String partyBranchId);

    List<String> getPartyBranchIdByIdNo(String idNo);

    List<Map<String, Object>> findCategoryInfoById(@Param(value = "id") String id, @Param(value = "year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);

    List<Map<String, Object>> findCategoryInfoByIds(@Param(value = "ids") List<String> ids, @Param(value = "year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);

    List<Map<String, Object>> findSexInfoById(@Param(value = "id") String id, @Param(value = "year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);

    List<Map<String, Object>> findSexInfoByIds(@Param(value = "ids") List<String> ids, @Param(value = "year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);

    List<Map<String, Object>> findNationInfoById(@Param(value = "id") String id, @Param(value = "year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);

    List<Map<String, Object>> findNationInfoByIds(@Param(value = "ids") List<String> ids, @Param(value = "year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);

    List<AffairPartyMember> findListByIdNumber(String idNumber);

    List<Map<String, String>> getAllInfo();

    List<Map<String, String>> getPMDatasByCardNum(@Param(value = "cardNums") List<String> cardNums,@Param(value = "partBranchId")String partBranchId);//增加党组织id参数 10月16日 JIA

    List<Map<String, String>> getPMDatas(String partBranchId);//增加党组织id条件  10月16日 JIA

    List<Map<String, String>> getPartyBranchIdByIds(@Param(value = "ids") List<String> ids);

    List<Map<String, Object>> findWorkPlaceInfoById(@Param(value = "id") String id, @Param(value = "year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);

    List<Map<String, Object>> findWorkPlaceInfoByIds(@Param(value = "ids")List<String> ids, @Param(value = "year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);

    List<AffairPartyMember> echartWorkPlaceFindPageByPbId(AffairPartyMember affairPartyMember);

    List<AffairPartyMember> echartWorkPlaceFindPageByPbIds(AffairPartyMember affairPartyMember);

    List<AffairPartyMember> echartCategoryFindPageByPbId(AffairPartyMember affairPartyMember);

    List<AffairPartyMember> echartCategoryFindPageByPbIds(AffairPartyMember affairPartyMember);

    List<AffairPartyMember> echartNationFindPageByPbId(AffairPartyMember affairPartyMember);

    List<AffairPartyMember> echartNationFindPageByPbIds(AffairPartyMember affairPartyMember);

    List<AffairPartyMember> echartSexFindPageByPbId(AffairPartyMember affairPartyMember);

    List<AffairPartyMember> echartSexFindPageByPbIds(AffairPartyMember affairPartyMember);
    AffairPartyMember byIdNumber(AffairPartyMember affairPartyMember);

    //预备党员转正
    List<AffairPartyMember> zhuanzheng(AffairPartyMember affairPartyMember);
    //预备党员审核
    List<AffairPartyMember> findByIds(@Param(value = "ids") List<String> ids);
    void shenHe(@Param(value = "affairPartyMember") AffairPartyMember affairPartyMember);

    List<Map<String,String>> countPartyMember(@Param(value = "year") Integer year, @Param(value = "startDate") Date startDate,
                                              @Param(value = "endDate") Date endDate, @Param(value = "month") String month);

    /*更换成findPartyMemberList*/
    List<AffairPartyMember> findPartyMemberPage(AffairPartyMember affairPartyMember);

    List<AffairPartyMember> findPartyNationList(AffairPartyMember partyMember);

    List<AffairPartyMember> findPartyMemberList(AffairPartyMember partyMember);


    /**
     * 党日活动自动考评
     * 查找党支部下的人元
     */
    List<Map<String,String>> peopleList(@Param(value = "org")String oeg);

    List<String> selectNoSJNameList(@Param(value = "name")String name, @Param(value = "unitId")String unitId);

    String selectUnit(@Param(value = "id")String id);

    String selectUnitId(@Param(value = "unit")String unit);

    List<AffairPartyMember> selectAllPeople(@Param("yearL") String yearL,@Param("yearT") String yearT,@Param("unitId") String unitId);
    //查询几天后过政治生日人员
    List<AffairPartyMember> selThreeDayAfterBirthday(String threeDayAfter);
    //根据党组织id查询该党组织人数
    int getNumByPartyId(@Param(value = "partyId")String partyId);
}