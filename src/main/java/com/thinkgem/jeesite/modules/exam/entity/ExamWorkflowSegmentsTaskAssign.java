/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.List;

/**
 * 考评流程任务分配
 */
public class ExamWorkflowSegmentsTaskAssign extends DataEntity<ExamWorkflowSegmentsTaskAssign> {

	private static final long serialVersionUID = 1L;
	private List<ExamWorkflowSegmentsTask> datas; //考评项任务数据

	public List<ExamWorkflowSegmentsTask> getDatas() {
		return datas;
	}

	public void setDatas(List<ExamWorkflowSegmentsTask> datas) {
		this.datas = datas;
	}
}