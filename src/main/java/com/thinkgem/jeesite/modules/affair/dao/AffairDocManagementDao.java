/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairDocManagement;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文档管理DAO接口
 * @author kevin.jia
 * @version 2020-07-30
 */
@MyBatisDao
public interface AffairDocManagementDao extends CrudDao<AffairDocManagement> {

    List<AffairDocManagement> selectAll();

    AffairDocManagement selectBeanById(@Param("id") String id);


    List<AffairDocManagement> findByIds(@Param("ids") List<String> ids);
}