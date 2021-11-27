/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairBorrow;

/**
 * 档案台账查阅表DAO接口
 * @author mason.xv
 * @version 2019-11-04
 */
@MyBatisDao
public interface AffairBorrowDao extends CrudDao<AffairBorrow> {

	List<Map<String, Object>> findInfoByCreateOrgId(@Param("id") String id);

	List<Map<String, Object>> findInfoByCreateOrgIds(@Param("ids") List<String> ids);
	
}