/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairWorkDone;

/**
 * 主体责任落实中的工作总结DAO接口
 * @author Alan.wu
 * @version 2020-05-29
 */
@MyBatisDao
public interface AffairWorkDoneDao extends CrudDao<AffairWorkDone> {

    void shenHeSave(AffairWorkDone affairWorkDone);
}