/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairOneHelpOne;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * 一帮一DAO接口
 * @author mason.xv
 * @version 2020-04-15
 */
@MyBatisDao
public interface AffairOneHelpOneDao extends CrudDao<AffairOneHelpOne> {
    AffairOneHelpOne getIdByTitle(AffairOneHelpOne oneHelpOne);

    int selectOneHelpOne(@Param("lastYearDate") String lastYearDate,@Param("nowYearDate") String nowYearDate, @Param("unitId") String unitId);
}