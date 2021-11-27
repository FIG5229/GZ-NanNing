/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairOneHelpOne;
import com.thinkgem.jeesite.modules.affair.entity.AffairOneHelpOneMain;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 一帮一明细DAO接口
 * @author daniel.liu
 * @version 2020-06-23
 */
@MyBatisDao
public interface AffairOneHelpOneMainDao extends CrudDao<AffairOneHelpOneMain> {


    void deleteByParentIds(@Param(value = "ids") List<String> ids);
}