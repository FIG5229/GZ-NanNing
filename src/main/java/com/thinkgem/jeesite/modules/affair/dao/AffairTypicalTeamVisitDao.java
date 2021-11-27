/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairTypicalTeamVisit;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 典型集体走访记录DAO接口
 * @author daniel.liu
 * @version 2020-07-31
 */
@MyBatisDao
public interface AffairTypicalTeamVisitDao extends CrudDao<AffairTypicalTeamVisit> {

    //根据父Id  批量删除
    void deleteByParentIds(@Param(value = "ids") List<String> ids);
    //根据父Id  删除
    void deleteById(String id);
	
}