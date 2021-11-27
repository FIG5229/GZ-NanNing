/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairWorkLead;

/**
 * 党建工作领导小组成员DAO接口
 * @author alan
 * @version 2020-06-03
 */
@MyBatisDao
public interface AffairWorkLeadDao extends CrudDao<AffairWorkLead> {

    void shenHeSave(AffairWorkLead affairWorkLead);
}