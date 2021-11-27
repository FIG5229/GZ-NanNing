/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairDeclarationQiyi;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 工作申报DAO接口
 * @author daniel.liu
 * @version 2020-07-01
 */
@MyBatisDao
public interface AffairDeclarationQiyiDao extends CrudDao<AffairDeclarationQiyi> {

    List<AffairDeclarationQiyi> findByIds(@Param(value = "ids") List<String> ids);
}