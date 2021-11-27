/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.exam.entity.ExamWorkflowDefine;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 考评流程定义DAO接口
 * @author eav.liu
 * @version 2019-12-09
 */
@MyBatisDao
public interface ExamWorkflowDefineDao extends CrudDao<ExamWorkflowDefine> {

    void updateIsUse(@Param(value = "id") String id, @Param(value = "isUse") String isUse);

    void deleteById(String id);

    // 绩效自动考评
    List<ExamWorkflowDefine> findAllInfo(ExamWorkflowDefine examWorkflowDefine);
}