/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairGeneralSituation;
import com.thinkgem.jeesite.modules.affair.entity.AffairOrganizationBulid;
import com.thinkgem.jeesite.modules.affair.entity.AffairTjRegisterFromP;
import com.thinkgem.jeesite.modules.affair.entity.AffairTwBase;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 团委（支部）基本信息DAO接口
 * @author cecil.li
 * @version 2019-12-04
 */
@MyBatisDao
public interface AffairTwBaseDao extends CrudDao<AffairTwBase> {

    List<AffairOrganizationBulid> findTreeData();
    List<AffairOrganizationBulid> findTreeData(AffairTwBase affairTwBase);
    String findByName(@Param(value = "name") String name);

    List<AffairTwBase> getSum(AffairTwBase affairTwBase);
    Map getOrgInfo(@Param(value = "id") String id);

    List<AffairTjRegisterFromP> getPersonnelUnder28(@Param(value = "id")String id);

    String findListByName(String name);

    List<String> selectAllTw();

    String selectId(@Param(value = "twName")String twName);

    List<AffairTwBase> findAllTwUnit();
    //根据id获得所属上级id
    String findOrgNameById(String id);

    List<AffairTwBase> getChuPartyBranch(@Param("chuId") String chuId, @Param("type")String type);

    List<AffairTwBase> selectChuTw(@Param("twId") String twId);

    List<AffairTwBase> selectBeanTwById(@Param("id") String id);

    AffairTwBase selectTwBeanById(@Param("id") String id);
}