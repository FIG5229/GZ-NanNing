/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairTixieActivityStyle;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 体协工作活动风采DAO接口
 * @author cecil.li
 * @version 2019-11-07
 */
@MyBatisDao
public interface AffairTixieActivityStyleDao extends CrudDao<AffairTixieActivityStyle> {

    Map<String, Object> findInfoByCreateOrgId(@Param("id") String id);

    Map<String, Object> findInfoByCreateOrgIds(@Param("ids") List<String> ids);
}