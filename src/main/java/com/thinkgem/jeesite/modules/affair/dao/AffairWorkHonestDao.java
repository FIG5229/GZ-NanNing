/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairWorkHonest;

/**
 * 党风廉政建设DAO接口
 * @author Alan
 * @version 2020-06-04
 */
@MyBatisDao
public interface AffairWorkHonestDao extends CrudDao<AffairWorkHonest> {

    void shenHeSave(AffairWorkHonest affairWorkHonest);
}