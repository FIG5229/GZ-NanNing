/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 系统公示Entity
 * @author mason.xv
 * @version 2019-12-13
 */
public class ExamSystemInitCheack extends DataEntity<ExamSystemInitCheack> {
	
	private static final long serialVersionUID = 1L;
	private String unit;		// 单位名称
	private String unitId;		// 单位ID
	private String selfExamType;		// 自评状态
	private String systemExamType;		// 系统初核状态
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String name;		// 姓名
	
	public ExamSystemInitCheack() {
		super();
	}

	public ExamSystemInitCheack(String id){
		super(id);
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	
	public String getSelfExamType() {
		return selfExamType;
	}

	public void setSelfExamType(String selfExamType) {
		this.selfExamType = selfExamType;
	}
	
	public String getSystemExamType() {
		return systemExamType;
	}

	public void setSystemExamType(String systemExamType) {
		this.systemExamType = systemExamType;
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
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}