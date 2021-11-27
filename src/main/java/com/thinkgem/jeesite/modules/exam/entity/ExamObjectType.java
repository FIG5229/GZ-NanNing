/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 被考评对象类别关系表Entity
 * @author kevin.jia
 * @version 2021-02-22
 */
public class ExamObjectType extends DataEntity<ExamObjectType> {
	
	private static final long serialVersionUID = 1L;
	private String typeName;		// 类别名称
	private String examType;		// 考评类别
	private String juChuId;		// 局处单位id
	private String objectUserName;		// 被考评对象用户名
	private String objectUserId;		// 被考评对象用户id
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String isGt;		// 是否为高铁1：高铁 0：普铁
	private String officeId;    //单位id

	private String userId;
	public ExamObjectType() {
		super();
	}

	public ExamObjectType(String id){
		super(id);
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	public String getExamType() {
		return examType;
	}

	public void setExamType(String examType) {
		this.examType = examType;
	}
	
	public String getJuChuId() {
		return juChuId;
	}

	public void setJuChuId(String juChuId) {
		this.juChuId = juChuId;
	}
	
	public String getObjectUserName() {
		return objectUserName;
	}

	public void setObjectUserName(String objectUserName) {
		this.objectUserName = objectUserName;
	}
	
	public String getObjectUserId() {
		return objectUserId;
	}

	public void setObjectUserId(String objectUserId) {
		this.objectUserId = objectUserId;
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
	
	public String getIsGt() {
		return isGt;
	}

	public void setIsGt(String isGt) {
		this.isGt = isGt;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}