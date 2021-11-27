/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairTypicalPersonNews;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 典型培树事迹DAO接口
 * @author daniel.liu
 * @version 2020-06-18
 */
@MyBatisDao
public interface AffairTypicalPersonNewsDao extends CrudDao<AffairTypicalPersonNews> {

    void deleteByParentId(String typicalPersonId);

    void deleteByParentIds(@Param(value = "ids")List<String> ids);
}