/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 考评环节Entity
 * @author bradley.zhao
 * @version 2019-12-20
 */
public class ExamWorflowSegments extends DataEntity<ExamWorflowSegments> {

	private static final long serialVersionUID = 1L;
	private String flowId;		// 流程ID
	private String segmentId;		// 流程类型
	private Date endTime;		// 计划结束时间
	private String comment;		// 备注

	public ExamWorflowSegments() {
		super();
	}

	public ExamWorflowSegments(String id){
		super(id);
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public String getSegmentId() {
		return segmentId;
	}

	public void setSegmentId(String segmentId) {
		this.segmentId = segmentId;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}