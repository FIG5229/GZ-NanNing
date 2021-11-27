/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 考评初审人员关系表Entity
 * @author kevin.jia
 * @version 2020-11-18
 */
public class ExamKpPersonRelation extends DataEntity<ExamKpPersonRelation> {
	
	private static final long serialVersionUID = 1L;
	private String kpType;		// 考评类别
	private String unitId;		// 考评对象单位id
	private String unitName;		// 考评对象单位名称
	private String kpUserId;		// 考评对象userId
	private String csUnitId;		// 初审对象单位id
	private String csUnitName;		// 初审对象单位名称
	private String csUserId;		// 初审对象userId
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String kpUserName;      //被考评人员/单位
	private String csUserName;		//初审人员/单位

	public ExamKpPersonRelation() {
		super();
	}

	public ExamKpPersonRelation(String id){
		super(id);
	}

	public String getKpType() {
		return kpType;
	}

	public void setKpType(String kpType) {
		this.kpType = kpType;
	}
	
	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	
	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	
	public String getKpUserId() {
		return kpUserId;
	}

	public void setKpUserId(String kpUserId) {
		this.kpUserId = kpUserId;
	}
	
	public String getCsUnitId() {
		return csUnitId;
	}

	public void setCsUnitId(String csUnitId) {
		this.csUnitId = csUnitId;
	}
	
	public String getCsUnitName() {
		return csUnitName;
	}

	public void setCsUnitName(String csUnitName) {
		this.csUnitName = csUnitName;
	}
	
	public String getCsUserId() {
		return csUserId;
	}

	public void setCsUserId(String csUserId) {
		this.csUserId = csUserId;
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

	public String getKpUserName() {
		return kpUserName;
	}

	public void setKpUserName(String kpUserName) {
		this.kpUserName = kpUserName;
	}

	public String getCsUserName() {
		return csUserName;
	}

	public void setCsUserName(String csUserName) {
		this.csUserName = csUserName;
	}
}