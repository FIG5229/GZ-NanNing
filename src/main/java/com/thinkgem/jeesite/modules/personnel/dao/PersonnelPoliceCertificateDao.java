/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelPoliceCertificate;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 人民警察证信息集DAO接口
 * @author cecil.li
 * @version 2019-11-11
 */
@MyBatisDao
public interface PersonnelPoliceCertificateDao extends CrudDao<PersonnelPoliceCertificate> {

    void updateByIdNumbers(@Param(value = "idNumbers") List<String> idNumbers, @Param(value = "backFillDate")Date backFillDate);

    List<PersonnelPoliceCertificate> selectHistoryByIdNumber(@Param(value = "idNumber") String idNumber);

    String selectBeanByIdNumber(@Param(value = "idNumber")String idNumber);

    PersonnelPoliceCertificate findNewBean(PersonnelPoliceCertificate personnelPoliceCertificate);

    List<PersonnelPoliceCertificate> selTreeMonthPoliceCertificateInfo(String date);

    void deleteByIdNumbers(@Param("idNumbers") List<String> idNumbers);
}