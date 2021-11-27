/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairCertificateIssue;
import com.thinkgem.jeesite.modules.affair.entity.AffairSendTeacher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 颁发证书DAO接口
 * @author jack.xu
 * @version 2020-07-22
 */
@MyBatisDao
public interface AffairCertificateIssueDao extends CrudDao<AffairCertificateIssue> {
    List<AffairCertificateIssue> findByIds(@Param(value = "ids") List<String> ids);
}