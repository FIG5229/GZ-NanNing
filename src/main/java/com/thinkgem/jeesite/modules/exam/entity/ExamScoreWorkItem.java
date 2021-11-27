/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 得分制工作项管理Entity
 * @author tom.fu
 * @version 2021-03-04
 */
public class ExamScoreWorkItem extends DataEntity<ExamScoreWorkItem> {
	
	private static final long serialVersionUID = 1L;
	private String examType;		// 类型
	private String workName;		// 工作项
	private String beiyongOne;		// 预留字段1
	private String beiyongTwo;		// 预留字段2
	private String beiyongThree;		// 预留字段3
	private String beiyongFour;		// 预留字段4
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	
	public ExamScoreWorkItem() {
		super();
	}

	public ExamScoreWorkItem(String id){
		super(id);
	}

	public String getExamType() {
		return examType;
	}

	public void setExamType(String examType) {
		this.examType = examType;
	}
	
	public String getWorkName() {
		return workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}
	
	public String getBeiyongOne() {
		return beiyongOne;
	}

	public void setBeiyongOne(String beiyongOne) {
		this.beiyongOne = beiyongOne;
	}
	
	public String getBeiyongTwo() {
		return beiyongTwo;
	}

	public void setBeiyongTwo(String beiyongTwo) {
		this.beiyongTwo = beiyongTwo;
	}
	
	public String getBeiyongThree() {
		return beiyongThree;
	}

	public void setBeiyongThree(String beiyongThree) {
		this.beiyongThree = beiyongThree;
	}
	
	public String getBeiyongFour() {
		return beiyongFour;
	}

	public void setBeiyongFour(String beiyongFour) {
		this.beiyongFour = beiyongFour;
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