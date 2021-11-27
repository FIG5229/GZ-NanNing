/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairItemReport;
import org.apache.ibatis.annotations.Param;

/**
 * 个人事项报告表DAO接口
 * @author mason.xv
 * @version 2019-11-06
 */
@MyBatisDao
public interface AffairItemReportDao extends CrudDao<AffairItemReport> {

    void shenHe(@Param(value ="affairItemReport" ) AffairItemReport affairItemReport);
	
}