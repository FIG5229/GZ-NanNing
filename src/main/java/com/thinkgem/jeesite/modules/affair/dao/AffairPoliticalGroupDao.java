/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairPoliticalGroup;

/**
 * 党小组DAO接口
 * @author daniel.liu
 * @version 2020-05-26
 */
@MyBatisDao
public interface AffairPoliticalGroupDao extends CrudDao<AffairPoliticalGroup> {

    AffairPoliticalGroup findInfoByTreeId(String treeId);
}