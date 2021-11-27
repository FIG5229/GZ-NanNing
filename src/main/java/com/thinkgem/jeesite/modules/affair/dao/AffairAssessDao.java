/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairAssess;
import com.thinkgem.jeesite.modules.affair.entity.AffairGeneralSituation;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 党委书记述职测评DAO接口
 * @author eav.liu
 * @version 2019-11-11
 */
@MyBatisDao
public interface AffairAssessDao extends CrudDao<AffairAssess> {

    Map<String, Long> findFinishSumByPId(@Param(value = "id") String id, @Param(value = "year")Integer year, @Param(value = "startDate")Date startDate, @Param(value = "endDate")Date endDate, @Param(value = "month")String month);

    Integer findFinishSumByPIds(@Param(value = "ids")List<String> ids, @Param(value = "year")Integer year, @Param(value = "startDate")Date startDate, @Param(value = "endDate")Date endDate, @Param(value = "month")String month);

    /*修改参数类型 便于添加不存在的label*/
    List<Map<String, String>> findPieInfoByPBId(@Param(value = "id") String id, @Param(value = "year")Integer year, @Param(value = "startDate")Date startDate, @Param(value = "endDate")Date endDate, @Param(value = "month")String month);

    /*修改参数类型 便于添加不存在的label*/
    List<Map<String, String>> findPieInfoByPBIds(@Param(value = "ids")List<String> ids, @Param(value = "year")Integer year, @Param(value = "startDate")Date startDate, @Param(value = "endDate")Date endDate, @Param(value = "month")String month);

    List<AffairAssess> findAssessLevelList(AffairAssess affairAssess);

    // 审核
    void shenHeSave(@Param(value = "affairAssess") AffairAssess affairAssess);

    /**
     *查询所有的党组织
     */
    List<AffairGeneralSituation> findAllPartyOrg(AffairGeneralSituation affairGeneralSituation);

    /**
     * 查询每一个党组织是否测评
     * @param year
     * @param partyOrganization
     * @param partyOrganizationId
     * @return
     */
    int findCount(@Param(value = "startYear")String year, @Param("endYear")String endYear, @Param(value = "partyOrganization")String partyOrganization, @Param(value = "partyOrganizationId")String partyOrganizationId);
    List<Map<String, String>> findPeopleInfo(@Param(value = "org")String org, @Param(value = "orgId")String orgId);
    String peopleJob(@Param(value = "name")String name,@Param(value = "idNumber") String idNumber);

}