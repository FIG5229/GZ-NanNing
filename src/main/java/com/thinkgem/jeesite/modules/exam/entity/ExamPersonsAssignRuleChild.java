/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 考评人员分配规则子表管理Entity
 * @author bradley.zhao
 * @version 2020-03-21
 */
public class ExamPersonsAssignRuleChild extends DataEntity<ExamPersonsAssignRuleChild> {
	
	private static final long serialVersionUID = 1L;
	private String ruleId;		// 规则ID
	private String examDepart;		// 考核部门
	private String selfPersonIds;		// 自评人IDS
	private String selfPersonNames;		// 自评人Names
	private String examPersonIds;		// 考评人IDS
	private String examPersonNames;		// 考评人Names
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	
	public ExamPersonsAssignRuleChild() {
		super();
	}

	public ExamPersonsAssignRuleChild(String id){
		super(id);
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	
	public String getExamDepart() {
		return examDepart;
	}

	public void setExamDepart(String examDepart) {
		this.examDepart = examDepart;
	}
	
	public String getSelfPersonIds() {
		return selfPersonIds;
	}

	public void setSelfPersonIds(String selfPersonIds) {
		this.selfPersonIds = selfPersonIds;
	}
	
	public String getSelfPersonNames() {
		return selfPersonNames;
	}

	public void setSelfPersonNames(String selfPersonNames) {
		this.selfPersonNames = selfPersonNames;
	}
	
	public String getExamPersonIds() {
		return examPersonIds;
	}

	public void setExamPersonIds(String examPersonIds) {
		this.examPersonIds = examPersonIds;
	}
	
	public String getExamPersonNames() {
		return examPersonNames;
	}

	public void setExamPersonNames(String examPersonNames) {
		this.examPersonNames = examPersonNames;
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
	
}