/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairTypicalTeamChild;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 典型集体子表DAO接口
 * @author daniel.liu
 * @version 2020-06-19
 */
@MyBatisDao
public interface AffairTypicalTeamChildDao extends CrudDao<AffairTypicalTeamChild> {

    //根据父Id  批量删除
    void deleteByParentIds(@Param(value = "ids")List<String> ids);
    //根据父Id  删除
    void deleteById(String id);
}