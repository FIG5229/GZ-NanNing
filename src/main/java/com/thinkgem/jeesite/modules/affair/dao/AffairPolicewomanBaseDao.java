/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairPolicewomanBase;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 女警基本情况DAO接口
 * @author cecil.li
 * @version 2019-11-05
 */
@MyBatisDao
public interface AffairPolicewomanBaseDao extends CrudDao<AffairPolicewomanBase> {

    List<Map<String, Object>> findInfoByCreateOrgId(@Param("id") String id);

    List<Map<String, Object>> findInfoByCreateOrgIds(@Param("ids") List<String> ids);
	
}