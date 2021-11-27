/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.exam.entity.ExamWorkflowSegmentGuanlian;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 考评流程环节关联DAO接口
 * @author eav.liu
 * @version 2019-12-10
 */
@MyBatisDao
public interface ExamWorkflowSegmentGuanlianDao extends CrudDao<ExamWorkflowSegmentGuanlian> {

    List<ExamWorkflowSegmentGuanlian> findByWdIdAndType(@Param(value = "wdId") String wdId, @Param(value = "flowType") String flowType);

    void deleteAllByWdId(String wdId);

    ExamWorkflowSegmentGuanlian findByWdIdAndSegDefId(@Param(value = "wdId") String workflowDefineId, @Param(value = "segDefId") String segDefId);
}