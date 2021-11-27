/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.exam.entity.ExamWorkflow;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 考评管理DAO接口
 * @author bradley.zhao
 * @version 2019-12-20
 */
@MyBatisDao
public interface ExamWorkflowDao extends CrudDao<ExamWorkflow> {
	List<Map<String,String>> selectFlowList(Map<String, String> param);
	List<Map<String,String>> selectByPerson(Map<String, String> param);
	void updateStatus(Map<String, Object> param);

	// 绩效自动考评
	List<Map<String, String>> findAllInfo(@Param(value = "id")String id);

	//根据ids查询相应数据
    List<ExamWorkflow> findInfosByIds(@Param("workflowIds") List<String> workflowIds);

	//根据年度查询相应考评信息
	List<ExamWorkflow> findListByYear(@Param("yearNum") int yearNum);

	void saveExamBeta(ExamWorkflow examWorkflow);

	List<ExamWorkflow> findYearInfosByIds(@Param("workflowIds")List<String> workflowIds, @Param("yearNum") String yearNum);
	// 预警 获取未结束考评
    List<ExamWorkflow> getNoEndExamList();
}