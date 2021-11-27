/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelPoliceFamily;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelPoliceFamilyTwo;
import com.thinkgem.jeesite.modules.sys.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 民警家庭DAO接口
 * @author tomfu
 * @version 2020-10-26
 */
@MyBatisDao
public interface PersonnelPoliceFamilyTwoDao extends CrudDao<PersonnelPoliceFamilyTwo> {

    PersonnelPoliceFamilyTwo findUserMessage(@Param(value = "loginName")String loginName);

    List<PersonnelPoliceFamilyTwo> findByIds(@Param(value = "ids") List<String> ids);

    String findId(@Param(value = "unitCheckId") String unitCheckId);

    String findUserIsNull(@Param(value = "id") String id);

    String findUnitID();

    void updateCheckType(String checkType);

    String findCheckType(@Param(value = "id") String id);
}