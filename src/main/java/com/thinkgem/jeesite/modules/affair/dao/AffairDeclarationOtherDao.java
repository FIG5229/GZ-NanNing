/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairDeclarationOther;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 其他申报DAO接口
 * @author daniel.liu
 * @version 2020-06-05
 */
@MyBatisDao
public interface AffairDeclarationOtherDao extends CrudDao<AffairDeclarationOther> {
    List<AffairDeclarationOther> findByIds(@Param(value = "ids") List<String> ids);
}