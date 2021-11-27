/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.exam.entity.ExamWorkflowSegmentsTask;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 考评流程任务分配DAO接口
 * @author eav.liu
 * @version 2019-12-10
 */
@MyBatisDao
public interface ExamWorkflowSegmentsTaskDao extends CrudDao<ExamWorkflowSegmentsTask> {
	Integer findSumByWdIdAndSegId(@Param(value = "wdId") String workflowId, @Param(value = "segId") String segmentId);

	Integer findNumByWdIdAndSegIdAndIds(@Param(value = "wdId") String workflowId, @Param(value = "segId") String segmentId);

	List<Map<String,String>> findAllByStardardId(@Param(value = "stardardId") String stardardId);

	String findWorkflowId(@Param(value = "tagType") String tagType,@Param(value = "segmentId") String segmentId);

	void deleteTask(@Param(value = "workflowId")String workflowId,@Param(value = "tagTypeList")List<String> tagTypeList);

	void deleteAllByWorkflowId(@Param(value = "workflowId")String workflowId);

	List<Map<String, Object>> getAllTaskSegmentList(Map<String,Object> param);

	String findListNumber(ExamWorkflowSegmentsTask task);
	List<Map<String, Object>>  findAssignListNumber(ExamWorkflowSegmentsTask task);
	void updateAssignData(List<ExamWorkflowSegmentsTask> list);

    void deleteByStandardId(@Param("standardId") String standardId);

    void updateTaskByWorkflowId(@Param("templateIds") String[] templateIds,@Param("workflowId") String workflowId);

    void deleteByStandardIdAndNum(ExamWorkflowSegmentsTask segmentsTask);
}