/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.exam.dao.ExamStandardBaseInfoDao;
import com.thinkgem.jeesite.modules.exam.dao.ExamWorkflowSegmentsTaskDao;
import com.thinkgem.jeesite.modules.exam.entity.ExamWorkflowSegmentsTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 考评流程任务分配Service
 * @author eav.liu
 * @version 2019-12-10
 *









 *
 */
@Service
@Transactional(readOnly = true)
public class ExamWorkflowSegmentsTaskService extends CrudService<ExamWorkflowSegmentsTaskDao, ExamWorkflowSegmentsTask> {

    @Autowired
    private ExamWorkflowSegmentsTaskDao examWorkflowSegmentsTaskDao;

    @Autowired
    private ExamStandardBaseInfoDao examStandardBaseInfoDao;

	public ExamWorkflowSegmentsTask get(String id) {
		return super.get(id);
	}
	
	public List<ExamWorkflowSegmentsTask> findList(ExamWorkflowSegmentsTask examWorkflowSegmentsTask) {
		return super.findList(examWorkflowSegmentsTask);
	}
	
	public Page<ExamWorkflowSegmentsTask> findPage(Page<ExamWorkflowSegmentsTask> page, ExamWorkflowSegmentsTask examWorkflowSegmentsTask) {
		return super.findPage(page, examWorkflowSegmentsTask);
	}
	
	@Transactional(readOnly = false)
	public void save(ExamWorkflowSegmentsTask examWorkflowSegmentsTask) {
		super.save(examWorkflowSegmentsTask);
	}
	
	@Transactional(readOnly = false)
	public void delete(ExamWorkflowSegmentsTask examWorkflowSegmentsTask) {
		super.delete(examWorkflowSegmentsTask);
	}

    /**
     * 根据流程id查总条数
     * @param workflowId
     * @return
     */
    public Integer findSumByWdIdAndSegId(String workflowId,String segmentId){
        return examWorkflowSegmentsTaskDao.findSumByWdIdAndSegId(workflowId,segmentId);
    }

    /**
     * 根据流程id和人员ids不为空的条数
     * @param workflowId
     * @return
     */
    public Integer findNumByWdIdAndSegIdAndIds(String workflowId,String segmentId){
        return examWorkflowSegmentsTaskDao.findNumByWdIdAndSegIdAndIds(workflowId,segmentId);
    }

	/**
	 *查询tab标签的名字 即考评标准的简称
	 * @param list
	 * @return
	 */
	public List<Map<String, String>> findTabList(List<String> list){
		return examStandardBaseInfoDao.findTabList(list);
	}

    /*public void taskSave(ExamWorkflowSegmentsTask examWorkflowSegmentsTask, HttpServletRequest request) {
        String[] pIds = request.getParameterValues("personIds");
        if(pIds != null && pIds.length > 0){
            for (String id: pIds) {
                String personNames = request.getParameter("personNames"+id);
                String personIds = request.getParameter("ids"+id);
                String workType = request.getParameter("workType" + id);
                String segmentId = request.getParameter("segmentId" + id);
                ExamWorkflowSegmentsTask task = new ExamWorkflowSegmentsTask();
                task.setId(id);
                task.setSegmentId(segmentId);
                task.setWorkflowId(examWorkflowSegmentsTask.getWorkflowId());
                task.setPersonNames(personNames);
                task.setIds(personIds);
                task.setWorkType(workType);
                task.setTagType(examWorkflowSegmentsTask.getTagType());
                this.save(task);
            }
            //更新exam_workflow_segment_guanlian表的status字段
            Integer sum = this.findSumByWdIdAndSegId(examWorkflowSegmentsTask.getWorkflowId(),request.getParameter("segmentId" + pIds[0]));
            Integer num = this.findNumByWdIdAndSegIdAndIds(examWorkflowSegmentsTask.getWorkflowId(),request.getParameter("segmentId" + pIds[0]));
            if(sum.intValue() == num.intValue() ){
                ExamWorkflowSegmentGuanlian examWorkflowSegmentGuanlian = new ExamWorkflowSegmentGuanlian();
                examWorkflowSegmentGuanlian.setId(request.getParameter("segmentId" + pIds[0]));

            }
        }
    }*/

    public String findWorkflowId(String tagType,String segmentId){
		String findWorkflowId = examWorkflowSegmentsTaskDao.findWorkflowId(tagType, segmentId);
    	return findWorkflowId;
	}

	public  List<Map<String, Object>> getAllTaskSegmentList(){
		List<Map<String, Object>> list=(List<Map<String, Object>>)CacheUtils.get("allTaskSegmentList");
		if(null == list){
			Map<String, Object> param = new HashMap<String, Object>();
			list=examWorkflowSegmentsTaskDao.getAllTaskSegmentList(param);
			CacheUtils.put("allTaskSegmentList", list);
		}
    	return list;
	}

	public  void freshAllTaskSegmentList(){
    	if(null != CacheUtils.get("allTaskSegmentList")){
			CacheUtils.remove("allTaskSegmentList");
		}
		Map<String, Object> param = new HashMap<String, Object>();
		List<Map<String, Object>> list=examWorkflowSegmentsTaskDao.getAllTaskSegmentList(param);
		CacheUtils.put("allTaskSegmentList", list);
	}

	public String findListNumber(ExamWorkflowSegmentsTask task){
		return examWorkflowSegmentsTaskDao.findListNumber(task);
	}

	public List<Map<String, Object>>  findAssignListNumber(ExamWorkflowSegmentsTask task){
		return examWorkflowSegmentsTaskDao.findAssignListNumber(task);
	}

	@Transactional(readOnly = false)
	public 	void updateAssignData(List<ExamWorkflowSegmentsTask> list){
		examWorkflowSegmentsTaskDao.updateAssignData(list);
	}

	@Transactional(readOnly = false)
	public void deleteByStandardId(String standardId) {
    	dao.deleteByStandardId(standardId);
	}

	/**
	 * 保存时更新任务分配
	 * @param templateIds
	 * @param workflowId
	 */
	@Transactional(readOnly = false)
	public void updateTaskByWorkflowId(String[] templateIds, String workflowId) {
		dao.updateTaskByWorkflowId(templateIds,workflowId);
	}


	/*根据标准和行号删除任务分配*/
	/*行号和标准Id不能为空		rowNum standardId*/
	@Transactional(readOnly = false)
	public void deleteByStandardIdAndNum(ExamWorkflowSegmentsTask segmentsTask) {
		if (StringUtils.isNotBlank(segmentsTask.getStandardId()) && segmentsTask.getRowNum() != null){
			examWorkflowSegmentsTaskDao.deleteByStandardIdAndNum(segmentsTask);
		}else {
			throw new RuntimeException("rowNum ：不能为空，standardId：不能为空");
		}
	}
}