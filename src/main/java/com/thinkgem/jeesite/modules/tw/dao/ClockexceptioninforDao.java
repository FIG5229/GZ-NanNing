/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.tw.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.tw.entity.Clockexceptioninfor;

/**
 * 自动考评-打卡异常记录DAO接口
 * @author alan.wu
 * @version 2020-11-19
 */
@MyBatisDao
public interface ClockexceptioninforDao extends CrudDao<Clockexceptioninfor> {
	
}