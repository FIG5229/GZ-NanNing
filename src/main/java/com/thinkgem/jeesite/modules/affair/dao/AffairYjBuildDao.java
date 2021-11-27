/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairYjBuild;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 硬件建设DAO接口
 * @author kevin.jia
 * @version 2020-08-04
 */
@MyBatisDao
public interface AffairYjBuildDao extends CrudDao<AffairYjBuild> {

    List<AffairYjBuild> findByIds(@Param("ids") List<String> ids);
}