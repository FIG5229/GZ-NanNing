/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.tw.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.tw.entity.TFeedback;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 微信警情-自动考评DAO接口
 * @author alan.wu
 * @version 2020-10-16
 */
@MyBatisDao
public interface TFeedbackDao extends CrudDao<TFeedback> {

    List<TFeedback> selectAll(@Param("time") String time,@Param("name")String name);
}