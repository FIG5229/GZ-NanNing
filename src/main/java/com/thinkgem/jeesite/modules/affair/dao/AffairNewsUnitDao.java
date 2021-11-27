/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairNewsUnit;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 刊搞搞件子所属单位DAO接口
 * @author daniel.liu
 * @version 2020-09-27
 */
@MyBatisDao
public interface AffairNewsUnitDao extends CrudDao<AffairNewsUnit> {

    @Override
    void deleteByIds(@Param("ids") List<String> ids);
}