/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairTypicalPersonVisit;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 典型集体走访记录DAO接口
 * @author daniel.liu
 * @version 2020-07-31
 */
@MyBatisDao
public interface AffairTypicalPersonVisitDao extends CrudDao<AffairTypicalPersonVisit> {
    void deleteByParentId(String typicalPersonId);

    void deleteByParentIds(@Param(value = "ids") List<String> ids);
}