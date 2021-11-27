/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.org.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.org.entity.OrgBianzhi;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 单位编制DAO接口
 * @author eav.liu
 * @version 2019-11-22
 */
@MyBatisDao
public interface OrgBianzhiDao extends CrudDao<OrgBianzhi> {

	Map<String, Object> findInfoByOrgId(@Param("id") String id, @Param("year") Integer year);

	Map<String, Object> findInfoByOrgIds(@Param("ids") List<String> ids, @Param("year") Integer year);

    void deleteByOrgId(String orgId);
}