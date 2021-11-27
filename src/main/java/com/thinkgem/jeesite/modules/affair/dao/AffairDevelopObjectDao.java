/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairDevelopObject;

import java.util.List;

/**
 * 发展党员DAO接口
 * @author eav.liu
 * @version 2019-11-01
 */
@MyBatisDao
public interface AffairDevelopObjectDao extends CrudDao<AffairDevelopObject> {

    List<String> findListByIdNo(String idNo);

    void deleteByIdNo(String idNo);

    List<AffairDevelopObject> findDevelopList(AffairDevelopObject affairDevelopObject);
}