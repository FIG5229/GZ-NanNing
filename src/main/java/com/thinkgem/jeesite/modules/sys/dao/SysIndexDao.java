/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.SysIndex;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 索引管理DAO接口
 * @author tom.fu
 * @version 2020-09-16
 */
@MyBatisDao
public interface SysIndexDao extends CrudDao<SysIndex> {

    /**
     * 查询出sys_role表中的id做为下拉框
     * @return
     */
    List<SysIndex> selectIds();

    /**
     * 查询出sys_role表中的name做为下拉框
     * @return
     */
    String selectName(@Param(value = "id") String id);

     List<SysIndex> findListByRoles(@Param(value = "list") List<String> list);

    SysIndex selectBeanByRoles(@Param(value = "idNumber") String idNumber);
}