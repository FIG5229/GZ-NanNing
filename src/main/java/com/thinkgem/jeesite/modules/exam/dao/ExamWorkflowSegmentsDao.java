/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.exam.entity.ExamWorflowSegments;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 考评环节DAO接口
 * @author bradley.zhao
 * @version 2019-12-20
 */
@MyBatisDao
public interface ExamWorkflowSegmentsDao extends CrudDao<ExamWorflowSegments> {
   List<Map<String,Object>> findSegmentsList(Map<String, Object> param);

   // 绩效自动考评
   List<Map<String,String>> findInfoList(@Param(value = "id")String id);
   String findDate(@Param(value = "id") String id);
   public void deleteByFlowId(ExamWorflowSegments examWorflowSegments);
   //预警，获取明天结束的workflowId,结束时间
   Map<String, Object> findExamWorkflowIdEndTime(@Param("workFlowId") String workFlowId, @Param("segmentId") String segmentId, @Param("oneDayAfterDate") String oneDayAfterDate);
}