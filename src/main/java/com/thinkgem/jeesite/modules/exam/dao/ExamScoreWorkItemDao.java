/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.exam.entity.ExamScoreWorkItem;
import org.apache.ibatis.annotations.Param;

/**
 * 得分制工作项管理DAO接口
 * @author tom.fu
 * @version 2021-03-04
 */
@MyBatisDao
public interface ExamScoreWorkItemDao extends CrudDao<ExamScoreWorkItem> {

    Integer getInfoCount(@Param("workName") String workName, @Param("examType")String examType);
}