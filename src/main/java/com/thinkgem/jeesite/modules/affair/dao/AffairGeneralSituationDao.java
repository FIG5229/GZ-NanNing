/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairGeneralSituation;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 党组织概况DAO接口
 * @author eav.liu
 * @version 2019-10-31
 */
@MyBatisDao
public interface AffairGeneralSituationDao extends CrudDao<AffairGeneralSituation> {

    String findByName(@Param(value = "name") String name);

	List<Map<String, Object>> findInfoById(@Param(value = "id") String id, @Param(value = "year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);

	List<Map<String, Object>> findInfoByIds(@Param(value = "ids") List<String> ids, @Param(value = "year") Integer year, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("month") String month);

    List<AffairGeneralSituation> findTreeData(AffairGeneralSituation affairGeneralSituation);
    //过滤党小组
    List<AffairGeneralSituation> findTreeData2(AffairGeneralSituation affairGeneralSituation);
    List<AffairGeneralSituation> findTreeData();
    List<AffairGeneralSituation> findByPartyOrganization(String partyOrganization);

    void deleteByPartyOrganization(String partyOrganization);
    Map getOrgInfo(@Param(value = "id") String id);

    List<String> findAllChildIdById(String id);

    Integer findALLPartyBranch();

    List<AffairGeneralSituation> findJLChFtreeData();

    List<AffairGeneralSituation> findTransfertreeData();

    List<AffairGeneralSituation> findLifeMeetList(AffairGeneralSituation affairGeneralSituation);

    List<AffairGeneralSituation> findAssessDetailList(AffairGeneralSituation affairGeneralSituation);
 
    String selectUnitId(@Param(value = "unitName")String unitName);

    //党日活动中基层领导自动考评
    String findNameByOrg(String org);

    String selectNameById(@Param(value = "id")String id);

    String selectSJName(@Param(value = "name")String name);

    List<String> selectAllName();

    void shenHeSave(AffairGeneralSituation affairGeneralSituation);

    String selectPartyOrganizationById(@Param("id")String id);

    List<AffairGeneralSituation> selectChuPartyBranch();
    //查询局机关党支部
    List<AffairGeneralSituation> getJJGPartyBranch();

    /**
     * @param chuId 处单位id
     * @param type  类型 1 ：处机关    2 ：派出所
     */
    List<AffairGeneralSituation> getChuPartyBranch(@Param("chuId") String chuId, @Param("type") String type);

    List<AffairGeneralSituation> selectdzzName();

    List<AffairGeneralSituation> selectdzbName();

    List<AffairGeneralSituation> selectdzbzzName();

    String selectUnitName(@Param("gzname") String gzname);
    //获取各处党委奖励信息
    List<AffairGeneralSituation> getChuPartyCommittee();

    List<AffairGeneralSituation> selectAllPartyBranch();

    List<AffairGeneralSituation> selectAlldzz();

    List<AffairGeneralSituation> selectAllParty();

    List<AffairGeneralSituation> selectAllPartydxz();

    List<AffairGeneralSituation> selectChuParty(@Param("chuId") String chuId);

    AffairGeneralSituation selectBeanByChuId(@Param("chuId") String chuId);

    String findNameByPartyName(@Param("name") String name);
}