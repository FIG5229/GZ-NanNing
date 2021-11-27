/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairWorkDoneManage;
import org.apache.ibatis.annotations.Param;

/**
 * 工作总结管理DAO接口
 * @author Alan.wu
 * @version 2020-06-12
 */
@MyBatisDao
public interface AffairWorkDoneManageDao extends CrudDao<AffairWorkDoneManage> {
    // 审核
    void shenHeSave(@Param(value = "affairWorkDoneManage") AffairWorkDoneManage affairWorkDoneManage);
}