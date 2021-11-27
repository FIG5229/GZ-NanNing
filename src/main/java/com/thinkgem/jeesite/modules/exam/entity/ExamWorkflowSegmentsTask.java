/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 考评流程任务分配Entity
 * @author eav.liu
 * @version 2019-12-10
 */
public class ExamWorkflowSegmentsTask extends DataEntity<ExamWorkflowSegmentsTask> {

	private static final long serialVersionUID = 1L;
	private String workflowId;	//流程id
	private String segmentId;		// 环节id
	private String tagType;		// 部门业务/部门公共加分/部门公共扣分
	private String workType;		// 工作分类
	private String roles;		// 角色
	private String personNames;		// 被分配任务的人员的姓名
	private String ids;		// 被分配任务的人员id
	private String status;		// 状态
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private Integer rowNum;	//行号
	private String standardId; //考评标准ID

	private String[] templatesIds;//接受前端的考核标准模版
	
	public ExamWorkflowSegmentsTask() {
		super();
	}

	public ExamWorkflowSegmentsTask(String id){
		super(id);
	}

	public String getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}

	public String getSegmentId() {
		return segmentId;
	}

	public void setSegmentId(String segmentId) {
		this.segmentId = segmentId;
	}
	
	public String getTagType() {
		return tagType;
	}

	public void setTagType(String tagType) {
		this.tagType = tagType;
	}
	
	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}
	
	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public String getPersonNames() {
		return personNames;
	}

	public void setPersonNames(String personNames) {
		this.personNames = personNames;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
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

	public String[] getTemplatesIds() {
		return templatesIds;
	}

	public void setTemplatesIds(String[] templatesIds) {
		this.templatesIds = templatesIds;
	}

	public Integer getRowNum() {
		return rowNum;
	}

	public void setRowNum(Integer rowNum) {
		this.rowNum = rowNum;
	}

	public String getStandardId() {
		return standardId;
	}

	public void setStandardId(String standardId) {
		this.standardId = standardId;
	}
}