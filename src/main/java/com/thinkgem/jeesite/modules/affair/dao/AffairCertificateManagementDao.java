/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairCertificateManagement;
import com.thinkgem.jeesite.modules.affair.entity.AffairSendTeacher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 证书模板管理DAO接口
 * @author jack.xu
 * @version 2020-07-23
 */
@MyBatisDao
public interface AffairCertificateManagementDao extends CrudDao<AffairCertificateManagement> {
    List<AffairCertificateManagement> findByIds(@Param(value = "ids") List<String> ids);
}