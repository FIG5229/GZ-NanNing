/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.warn.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.warn.entity.WarnHistory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 预警历史记录DAO接口
 * @author kevin.jia
 * @version 2020-12-28
 */
@MyBatisDao
public interface WarnHistoryDao extends CrudDao<WarnHistory> {

    WarnHistory selByWarnIdReceiveId(@Param("warnId") String warnId, @Param("receivePerId") String receivePerId);
}