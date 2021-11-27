/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairCorrespondent;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 通讯员管理DAO接口
 * @author Alan.wu
 * @version 2020-06-18
 */
@MyBatisDao
public interface AffairCorrespondentDao extends CrudDao<AffairCorrespondent> {

    List<AffairCorrespondent> findPerson(@Param("name") String name);
}