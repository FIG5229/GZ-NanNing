/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelFamily;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelPoliceFamilyInfo;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelPoliceFamilyTwo;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelPoliceMainFamily;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 民警家庭DAO接口
 * @author cecil.li
 * @version 2020-11-04
 */
@MyBatisDao
public interface PersonnelPoliceMainFamilyDao extends CrudDao<PersonnelPoliceMainFamily> {
    int saveInfo(PersonnelPoliceMainFamily personnelPoliceMainFamily);

    String findUserIsNull(@Param("id") String id);

    List<PersonnelPoliceMainFamily> findByIds(@Param(value = "ids") List<String> ids);

    String findCheckType(@Param("id") String id);

    List<PersonnelPoliceFamilyInfo> getSonTable(@Param("id") String id);

    List<PersonnelFamily> findIsPersonel(@Param("idNumber") String idNumber);

    void revocation(@Param("id") String id,@Param("checkType") String checkType);
}