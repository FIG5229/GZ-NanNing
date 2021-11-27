/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 考评流程环节关联Entity
 * @author eav.liu
 * @version 2019-12-10
 */
public class ExamWorkflowSegmentGuanlian extends DataEntity<ExamWorkflowSegmentGuanlian> {
	
	private static final long serialVersionUID = 1L;
	private String workflowDefineId;		// 流程id
	private String type;		// 流程类型
	private String name;		// 环节名称
	private Integer sort;		// 顺序号
	private String status;		// 人员任务分配状态
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String segDefId;
	
	public ExamWorkflowSegmentGuanlian() {
		super();
	}

	public ExamWorkflowSegmentGuanlian(String id){
		super(id);
	}

	public String getWorkflowDefineId() {
		return workflowDefineId;
	}

	public void setWorkflowDefineId(String workflowDefineId) {
		this.workflowDefineId = workflowDefineId;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getSegDefId() {
		return segDefId;
	}

	public void setSegDefId(String segDefId) {
		this.segDefId = segDefId;
	}
}