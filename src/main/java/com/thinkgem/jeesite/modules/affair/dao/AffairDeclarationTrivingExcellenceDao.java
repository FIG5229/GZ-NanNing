/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairDeclarationTrivingExcellence;
import com.thinkgem.jeesite.modules.affair.entity.AffairDifficultHelp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 创先争优申报DAO接口
 * @author daniel.liu
 * @version 2020-06-05
 */
@MyBatisDao
public interface AffairDeclarationTrivingExcellenceDao extends CrudDao<AffairDeclarationTrivingExcellence> {
    List<AffairDeclarationTrivingExcellence> findByIds(@Param(value = "ids") List<String> ids);
}