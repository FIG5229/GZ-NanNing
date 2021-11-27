/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairBrandManagement;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 品牌创建DAO接口
 * @author cecil.li
 * @version 2019-11-07
 */
@MyBatisDao
public interface AffairBrandManagementDao extends CrudDao<AffairBrandManagement> {

    List<Map<String, Object>> findInfoByUnitId(@Param("id") String id);

    List<Map<String, Object>> findInfoByUnitIds(@Param("ids") List<String> ids);

    List<AffairBrandManagement> findByIds(@Param(value = "ids") List<String> ids);
}