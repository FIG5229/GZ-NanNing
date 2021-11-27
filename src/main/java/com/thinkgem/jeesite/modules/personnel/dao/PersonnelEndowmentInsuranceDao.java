/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelEndowmentInsurance;
import org.apache.ibatis.annotations.Param;

/**
 * 养老保险DAO接口
 * @author alan.wu
 * @version 2020-06-29
 */
@MyBatisDao
public interface PersonnelEndowmentInsuranceDao extends CrudDao<PersonnelEndowmentInsurance> {


    PersonnelEndowmentInsurance selectByIy(@Param("idn") String idn, @Param("year") String year);
}