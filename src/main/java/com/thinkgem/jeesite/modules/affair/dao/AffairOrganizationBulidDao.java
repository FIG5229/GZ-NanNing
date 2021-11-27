/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairOrganizationBulid;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 组织建设DAO接口
 * @author cecil.li
 * @version 2019-12-16
 */
@MyBatisDao
public interface AffairOrganizationBulidDao extends CrudDao<AffairOrganizationBulid> {

    void shenHe(@Param(value = "affairOrganizationBulid") AffairOrganizationBulid affairOrganizationBulid);

    List<AffairOrganizationBulid> findTreeData();
    List<AffairOrganizationBulid> findTreeData(AffairOrganizationBulid affairOrganizationBulid);
    List<Map<String, Object>> findInfoByUnitId(@Param("id") String id);

    List<Map<String, Object>> findInfoByUnitIds(@Param("ids") List<String> ids);

    String findByName(@Param(value = "name") String name);
    Map getOrgInfo(@Param(value = "id") String id);
}