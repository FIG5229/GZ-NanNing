/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelSocialGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 社会团体任职信息集DAO接口
 * @author cecil.li
 * @version 2019-11-11
 */
@MyBatisDao
public interface PersonnelSocialGroupDao extends CrudDao<PersonnelSocialGroup> {

    void deleteByIdNumbers(@Param("idNumbers") List<String> idNumbers);

}