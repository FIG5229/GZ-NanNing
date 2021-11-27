/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairCertificateAdministrator;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 证书管理员DAO接口
 * @author jack.xu
 * @version 2020-07-27
 */
@MyBatisDao
public interface AffairCertificateAdministratorDao extends CrudDao<AffairCertificateAdministrator> {
	List<AffairCertificateAdministrator> findByIds(@Param(value = "ids") List<String> ids);
}