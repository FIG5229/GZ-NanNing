/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelPoliceFamilyInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 民警家庭DAO接口
 * @author cecil.li
 * @version 2020-11-04
 */
@MyBatisDao
public interface PersonnelPoliceFamilyInfoDao extends CrudDao<PersonnelPoliceFamilyInfo> {
    void deleteByMainId(@Param(value = "pfId") String pfId);
    void deleteByMainIds(@Param(value = "pfIds") List<String> pfIds);

    List<PersonnelPoliceFamilyInfo> getListByPfId(@Param(value = "pfId")String pfId);
}