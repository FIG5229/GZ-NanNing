/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.warn.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.warn.entity.Warning;

/**
 * 预警信息DAO接口
 * @author eav.liu
 * @version 2019-11-28
 */
@MyBatisDao
public interface WarningDao extends CrudDao<Warning> {

    Warning findKQOldWarnByName(String name);
    Warning findKQOldWarnByAlertContent(String alertContent);

    Warning findDFOldWarnByNewWarning(Warning warning);
}