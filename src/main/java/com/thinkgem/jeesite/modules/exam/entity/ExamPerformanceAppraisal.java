/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.Date;

/**
 * 部门绩效考核情况Entity
 * @author cecil.li
 * @version 2020-01-14
 */
public class ExamPerformanceAppraisal extends DataEntity<ExamPerformanceAppraisal> {
	
	private static final long serialVersionUID = 1L;
	private String dep;		// 部门
	private String lessItem;		// 扣分项目
	private String addItem;		// 加分项目
	private String penalties;		// 扣分
	private String addPoints;		// 加分
	private String sum;		// 总得分
	private Date date;		// 时间
	private String examType;		// 考评周期
	private String workflowId;		// 考评流程id
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private String year;        //年度
	private String month;       //月度
	
	public ExamPerformanceAppraisal() {
		super();
	}

	public ExamPerformanceAppraisal(String id){
		super(id);
	}

	public String getDep() {
		return dep;
	}

	public void setDep(String dep) {
		this.dep = dep;
	}
	
	public String getLessItem() {
		return lessItem;
	}

	public void setLessItem(String lessItem) {
		this.lessItem = lessItem;
	}
	
	public String getAddItem() {
		return addItem;
	}

	public void setAddItem(String addItem) {
		this.addItem = addItem;
	}
	
	public String getPenalties() {
		return penalties;
	}

	public void setPenalties(String penalties) {
		this.penalties = penalties;
	}
	
	public String getAddPoints() {
		return addPoints;
	}

	public void setAddPoints(String addPoints) {
		this.addPoints = addPoints;
	}
	
	public String getSum() {
		return sum;
	}

	public void setSum(String sum) {
		this.sum = sum;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getExamType() {
		return examType;
	}

	public void setExamType(String examType) {
		this.examType = examType;
	}
	
	public String getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}
	
	public String getCreateOrgId() {
		return createOrgId;
	}

	public void setCreateOrgId(String createOrgId) {
		this.createOrgId = createOrgId;
	}
	
	public String getCreateIdNo() {
		return createIdNo;
	}

	public void setCreateIdNo(String createIdNo) {
		this.createIdNo = createIdNo;
	}
	
	public String getUpdateOrgId() {
		return updateOrgId;
	}

	public void setUpdateOrgId(String updateOrgId) {
		this.updateOrgId = updateOrgId;
	}
	
	public String getUpdateIdNo() {
		return updateIdNo;
	}

	public void setUpdateIdNo(String updateIdNo) {
		this.updateIdNo = updateIdNo;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}
}