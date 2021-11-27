/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 民警因私外出报备Entity
 * @author kevin.jia
 * @version 2020-11-10
 */
public class AffairYjGoOutReport extends DataEntity<AffairYjGoOutReport> {
	
	private static final long serialVersionUID = 1L;
	private String unit;		// 部门名称
	private String unitId;		// 部门id
	private String goPlace;		// 前往地区
	private Date leaveTime;		// 离开时间
	private Date backTime;		// 返回时间
	private String thing;		// 事由
	private String bmzzldOpinion;		// 部门正职领导意见
	private String fgldOpinion;		// 分管领导审批意见
	private String mainldOpinion;		// 主要领导审批意见
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String juCheckMan;		// 局审核人
	private String chuCheckMan;		// 处审核人
	private String submitMan;		// 提交人
	private String checkType;		// 审核状态
	private String juCheckId;		// 局审核id
	private String chuCheckId;		// 处审核id
	private String submitId;		// 提交人id
	private String opinion;		// 审核意见
	private String name;		// 姓名
	private String idNumber;		// 身份证号
	private String personType;		// 人员类别(1-非领导,2-领导)
	private String bmzzldName;		// 部门正职领导
	private String fgldName;		// 分管领导
	private String mainldName;		// 主要领导
	private String bmzzldId;		// 部门正职领导id
	private String fgldId;		// 分管领导id
	private String mainldId;		// 主要领导id

	private String userId;   //当前用户id
	private boolean isPerson;   //当前用户是否为民警
	private String officeId;//当前用户单位id

	private String size;
	private Double lastSize;		//统计分析详情使用
	private Double newSize;		//统计分析详情使用


	public Double getLastSize() {
		return lastSize;
	}

	public void setLastSize(Double lastSize) {
		this.lastSize = lastSize;
	}

	public Double getNewSize() {
		return newSize;
	}

	public void setNewSize(Double newSize) {
		this.newSize = newSize;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public AffairYjGoOutReport() {
		super();
	}

	public AffairYjGoOutReport(String id){
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
	
	public String getGoPlace() {
		return goPlace;
	}

	public void setGoPlace(String goPlace) {
		this.goPlace = goPlace;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLeaveTime() {
		return leaveTime;
	}

	public void setLeaveTime(Date leaveTime) {
		this.leaveTime = leaveTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBackTime() {
		return backTime;
	}

	public void setBackTime(Date backTime) {
		this.backTime = backTime;
	}
	
	public String getThing() {
		return thing;
	}

	public void setThing(String thing) {
		this.thing = thing;
	}
	
	public String getBmzzldOpinion() {
		return bmzzldOpinion;
	}

	public void setBmzzldOpinion(String bmzzldOpinion) {
		this.bmzzldOpinion = bmzzldOpinion;
	}
	
	public String getFgldOpinion() {
		return fgldOpinion;
	}

	public void setFgldOpinion(String fgldOpinion) {
		this.fgldOpinion = fgldOpinion;
	}
	
	public String getMainldOpinion() {
		return mainldOpinion;
	}

	public void setMainldOpinion(String mainldOpinion) {
		this.mainldOpinion = mainldOpinion;
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
	
	public String getJuCheckMan() {
		return juCheckMan;
	}

	public void setJuCheckMan(String juCheckMan) {
		this.juCheckMan = juCheckMan;
	}
	
	public String getChuCheckMan() {
		return chuCheckMan;
	}

	public void setChuCheckMan(String chuCheckMan) {
		this.chuCheckMan = chuCheckMan;
	}
	
	public String getSubmitMan() {
		return submitMan;
	}

	public void setSubmitMan(String submitMan) {
		this.submitMan = submitMan;
	}
	
	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}
	
	public String getJuCheckId() {
		return juCheckId;
	}

	public void setJuCheckId(String juCheckId) {
		this.juCheckId = juCheckId;
	}
	
	public String getChuCheckId() {
		return chuCheckId;
	}

	public void setChuCheckId(String chuCheckId) {
		this.chuCheckId = chuCheckId;
	}
	
	public String getSubmitId() {
		return submitId;
	}

	public void setSubmitId(String submitId) {
		this.submitId = submitId;
	}
	
	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public String getPersonType() {
		return personType;
	}

	public void setPersonType(String personType) {
		this.personType = personType;
	}

	public String getBmzzldName() {
		return bmzzldName;
	}

	public void setBmzzldName(String bmzzldName) {
		this.bmzzldName = bmzzldName;
	}

	public String getFgldName() {
		return fgldName;
	}

	public void setFgldName(String fgldName) {
		this.fgldName = fgldName;
	}

	public String getMainldName() {
		return mainldName;
	}

	public void setMainldName(String mainldName) {
		this.mainldName = mainldName;
	}

	public String getBmzzldId() {
		return bmzzldId;
	}

	public void setBmzzldId(String bmzzldId) {
		this.bmzzldId = bmzzldId;
	}

	public String getFgldId() {
		return fgldId;
	}

	public void setFgldId(String fgldId) {
		this.fgldId = fgldId;
	}

	public String getMainldId() {
		return mainldId;
	}

	public void setMainldId(String mainldId) {
		this.mainldId = mainldId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean getIsPerson() {
		return isPerson;
	}

	public void setIsPerson(boolean person) {
		isPerson = person;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}
}