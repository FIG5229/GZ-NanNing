/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairCertificate;
import com.thinkgem.jeesite.modules.affair.entity.AffairCertificateIssue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 证书DAO接口
 * @author jack.xu
 * @version 2020-07-22
 */
@MyBatisDao
public interface AffairCertificateDao extends CrudDao<AffairCertificate> {
    List<AffairCertificate> findByIds(@Param(value = "ids") List<String> ids);
}