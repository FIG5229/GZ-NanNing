/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 查(借)阅审批查档人Entity
 * @author mason.xv
 * @version 2019-11-30
 */
public class AffairCdPerson extends DataEntity<AffairCdPerson> {
	
	private static final long serialVersionUID = 1L;
	private String approvalId;		// 审批表id 父类
	private String idNumber;		// 查档人员身份证号
	private String name;		// 查档人员姓名
	private String unit;		// 查档人员单位
	private String job;		// 查档人员职务
	private String face;		// 查档人员政治面貌
	private String unitId;		// 查档对象单位id
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	
	public AffairCdPerson() {
		super();
	}

	public AffairCdPerson(String id){
		super(id);
	}



	public void setApprovalId(String approvalId) {
		this.approvalId = approvalId;
	}

	public String getApprovalId() {
		return approvalId;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}
	
	public String getFace() {
		return face;
	}

	public void setFace(String face) {
		this.face = face;
	}
	
	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
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