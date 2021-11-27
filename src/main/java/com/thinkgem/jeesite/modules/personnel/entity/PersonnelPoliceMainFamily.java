/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.List;

/**
 * 民警家庭Entity
 * @author cecil.li
 * @version 2020-11-04
 */
public class PersonnelPoliceMainFamily extends DataEntity<PersonnelPoliceMainFamily> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 姓名
	private String unit;		// 单位
	private String unitId;		// 单位id
	private String idNumber;		// 身份证号
	private String policeNum;		// 警号
	private String maritalStatus;		// 婚姻状况
	private String brother;		// 是否有兄弟姐妹
	private String child;		// 是否有子女
	private String childMaritalStatus;		// 子女是否已婚
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String status;  // 环节状态
	private String isAdd;
	private String isComplete;

	private String hasMarried;		//是否已婚
	private String hasBrother;		//是否有兄弟姐妹
	private String hasChild;		//是否有孩子
	private String hasChildInLow;		//孩子是否有配偶


	private String juChuCheckMan;		// 局审核人
	private String unitCheckMan;		// 处审核人
	private String submitMan;		// 提交人
	private String checkType;		// 审核状态
	private String juChuCheckId;		// 局审核id
	private String unitCheckId;		// 处审核id
	private String submitId;		// 提交人id
	private String opinion;         // 审核意见

	private String officeId;
	private String userId;


	public String getJuChuCheckMan() {
		return juChuCheckMan;
	}

	public void setJuChuCheckMan(String juChuCheckMan) {
		this.juChuCheckMan = juChuCheckMan;
	}

	public String getUnitCheckMan() {
		return unitCheckMan;
	}

	public void setUnitCheckMan(String unitCheckMan) {
		this.unitCheckMan = unitCheckMan;
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

	public String getJuChuCheckId() {
		return juChuCheckId;
	}

	public void setJuChuCheckId(String juChuCheckId) {
		this.juChuCheckId = juChuCheckId;
	}

	public String getUnitCheckId() {
		return unitCheckId;
	}

	public void setUnitCheckId(String unitCheckId) {
		this.unitCheckId = unitCheckId;
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

	private List<PersonnelPoliceFamilyInfo> personnelPoliceFamilyInfoList;
	
	public PersonnelPoliceMainFamily() {
		super();
	}

	public PersonnelPoliceMainFamily(String id){
		super(id);
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
	
	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	
	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public String getPoliceNum() {
		return policeNum;
	}

	public void setPoliceNum(String policeNum) {
		this.policeNum = policeNum;
	}
	
	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	
	public String getBrother() {
		return brother;
	}

	public void setBrother(String brother) {
		this.brother = brother;
	}
	
	public String getChild() {
		return child;
	}

	public void setChild(String child) {
		this.child = child;
	}
	
	public String getChildMaritalStatus() {
		return childMaritalStatus;
	}

	public void setChildMaritalStatus(String childMaritalStatus) {
		this.childMaritalStatus = childMaritalStatus;
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

	public List<PersonnelPoliceFamilyInfo> getPersonnelPoliceFamilyInfoList() {
		return personnelPoliceFamilyInfoList;
	}

	public void setPersonnelPoliceFamilyInfoList(List<PersonnelPoliceFamilyInfo> personnelPoliceFamilyInfoList) {
		this.personnelPoliceFamilyInfoList = personnelPoliceFamilyInfoList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIsAdd() {
		return isAdd;
	}

	public void setIsAdd(String isAdd) {
		this.isAdd = isAdd;
	}

	public String getIsComplete() {
		return isComplete;
	}

	public void setIsComplete(String isComplete) {
		this.isComplete = isComplete;
	}


	public String getHasMarried() {
		return hasMarried;
	}

	public void setHasMarried(String hasMarried) {
		this.hasMarried = hasMarried;
	}

	public String getHasBrother() {
		return hasBrother;
	}

	public void setHasBrother(String hasBrother) {
		this.hasBrother = hasBrother;
	}

	public String getHasChild() {
		return hasChild;
	}

	public void setHasChild(String hasChild) {
		this.hasChild = hasChild;
	}

	public String getHasChildInLow() {
		return hasChildInLow;
	}

	public void setHasChildInLow(String hasChildInLow) {
		this.hasChildInLow = hasChildInLow;
	}
}