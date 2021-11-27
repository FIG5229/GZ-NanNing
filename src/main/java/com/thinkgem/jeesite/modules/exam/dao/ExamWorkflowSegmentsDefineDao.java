/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.exam.entity.ExamWorkflowDefine;
import com.thinkgem.jeesite.modules.exam.entity.ExamWorkflowSegmentsDefine;
import com.thinkgem.jeesite.modules.sys.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 标准考评流程环节定义DAO接口
 * @author eav.liu
 * @version 2019-12-09
 */
@MyBatisDao
public interface ExamWorkflowSegmentsDefineDao extends CrudDao<ExamWorkflowSegmentsDefine> {

    List<ExamWorkflowSegmentsDefine> findByType(String type);

    String findIdByTypeAndName(@Param(value = "type") String type, @Param(value = "name") String name);

    List<User> queryPoliceDatas(ExamWorkflowDefine define);
}