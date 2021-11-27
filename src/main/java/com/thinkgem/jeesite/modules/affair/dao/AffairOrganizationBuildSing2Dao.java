/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairOrganizationBuildSing2;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 组织建设关联表2DAO接口
 * @author cecil.li
 * @version 2019-12-16
 */
@MyBatisDao
public interface AffairOrganizationBuildSing2Dao extends CrudDao<AffairOrganizationBuildSing2> {

    public void deleteByMainId(String obId);
    public void deleteByMainIds(@Param(value = "obIds") List<String> obIds);
}