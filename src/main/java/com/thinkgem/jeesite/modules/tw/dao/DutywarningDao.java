/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.tw.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.tw.entity.Dutywarning;

/**
 * 自动考评-打卡预警DAO接口
 * @author alan.wu
 * @version 2020-11-19
 */
@MyBatisDao
public interface DutywarningDao extends CrudDao<Dutywarning> {
	
}