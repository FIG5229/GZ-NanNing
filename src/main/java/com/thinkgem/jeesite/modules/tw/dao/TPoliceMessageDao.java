/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.tw.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.tw.entity.TPoliceMessage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 警情预警-自动考评DAO接口
 * @author alan.wu
 * @version 2020-10-16
 */
@MyBatisDao
public interface TPoliceMessageDao extends CrudDao<TPoliceMessage> {

    List<TPoliceMessage> selectAllList(@Param("yearL") String yearL,@Param("yearT") String yearT);


}