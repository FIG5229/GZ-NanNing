/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.exam.dao.ExamWorkflowSegmentsDao;
import com.thinkgem.jeesite.modules.exam.entity.ExamWorflowSegments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 考评环节Service
 * @author bradley.zhao
 * @version 2019-12-20
 */
@Service
@Transactional(readOnly = true)
public class ExamWorkflowSegmentsService extends CrudService<ExamWorkflowSegmentsDao, ExamWorflowSegments> {
	@Autowired
	ExamWorkflowSegmentsDao examWorkflowSegmentsDao;

	public ExamWorflowSegments get(String id) {
		return super.get(id);
	}
	
	public List<ExamWorflowSegments> findList(ExamWorflowSegments examWorflowSegments) {
		return super.findList(examWorflowSegments);
	}
	/*查询考评流程标准*/
	public List<Map<String,Object>> findSegmentsList(Map<String,Object> param){
		return examWorkflowSegmentsDao.findSegmentsList(param);
	}

	public Page<ExamWorflowSegments> findPage(Page<ExamWorflowSegments> page, ExamWorflowSegments examWorflowSegments) {
		return super.findPage(page, examWorflowSegments);
	}
	
	@Transactional(readOnly = false)
	public void save(ExamWorflowSegments examWorflowSegments) {
		super.save(examWorflowSegments);
	}
	
	@Transactional(readOnly = false)
	public void delete(ExamWorflowSegments examWorflowSegments) {
		super.delete(examWorflowSegments);
	}


	// 自动考评
	public List<Map<String,String>> findInfoList(String id){
		return examWorkflowSegmentsDao.findInfoList(id);
	}
	public String findDate(String id){
		return examWorkflowSegmentsDao.findDate(id);
	}

	//预警，获取明天结束的workflowId,结束时间
	public Map<String, Object> findExamWorkflowIdEndTime(String workFlowId, String segmentId, String oneDayAfterDate) {
		return examWorkflowSegmentsDao.findExamWorkflowIdEndTime(workFlowId,segmentId,oneDayAfterDate);
	}
}