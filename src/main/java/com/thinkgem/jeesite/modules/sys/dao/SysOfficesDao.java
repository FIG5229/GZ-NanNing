/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.SysOffices;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;

/**
 * 组织关系对应关系DAO接口
 * @author bradley.zhao
 * @version 2020-12-13
 */
@MyBatisDao
public interface SysOfficesDao extends CrudDao<SysOffices> {
	String findGroupIdById(@Param("id")String id);
	String findByGroupName(@Param("groupName")String groupName);

	int saveNew(SysOffices sysOffices);

    void deleteById(@Param("id") String id);

	SysOffices selectBeanById(@Param("Id") String Id);

	void saveById(SysOffices sysOffices);
}