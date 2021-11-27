/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.Date;

/**
 * 系统内组织关系移交转接Entity
 * @author eav.liu
 * @version 2019-11-11
 */
public class AffairRelationshipTransfer extends DataEntity<AffairRelationshipTransfer> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 姓名
	private String policeNo;		// 警号
	private String idNumber;		// 身份证号
	private String oldOrganization;		// 原组织
	private String nowOrganization;		// 申请转入组织
	private String oldOrganizationId;		// 原组织部id
	private String nowOrganizationId;		// 申请转入组织id
	private Date applyDate;		// 申请时间
	private String transferType;		// 转移类型（1:处内转接 2:跨处转接 3：局到处转接  4：局内转接  5：局外转接 ）
	private Date handleTime;		// 处理时间
	private String status;		// 审核状态
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String statusFlag;		//1：通过 2：未通过 3：未审核

	private Date handleStartTime;

	private Date handleEndTime;
	//审批权限
	private boolean hasManageAuth;
	//分为局级别和处级别
	private String role;

	private String treeId;	//接受前端传来的左侧树的id

	private AffairTransferSh affairTransferSh;


	public AffairRelationshipTransfer() {
		super();
	}

	public AffairRelationshipTransfer(String id){
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getPoliceNo() {
		return policeNo;
	}

	public void setPoliceNo(String policeNo) {
		this.policeNo = policeNo;
	}
	
	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public String getOldOrganization() {
		return oldOrganization;
	}

	public void setOldOrganization(String oldOrganization) {
		this.oldOrganization = oldOrganization;
	}
	
	public String getNowOrganization() {
		return nowOrganization;
	}

	public void setNowOrganization(String nowOrganization) {
		this.nowOrganization = nowOrganization;
	}
	
	public String getOldOrganizationId() {
		return oldOrganizationId;
	}

	public void setOldOrganizationId(String oldOrganizationId) {
		this.oldOrganizationId = oldOrganizationId;
	}
	
	public String getNowOrganizationId() {
		return nowOrganizationId;
	}

	public void setNowOrganizationId(String nowOrganizationId) {
		this.nowOrganizationId = nowOrganizationId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	
	public String getTransferType() {
		return transferType;
	}

	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getHandleTime() {
		return handleTime;
	}

	public void setHandleTime(Date handleTime) {
		this.handleTime = handleTime;
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

	public Date getHandleStartTime() {
		return handleStartTime;
	}

	public void setHandleStartTime(Date handleStartTime) {
		this.handleStartTime = handleStartTime;
	}

	public Date getHandleEndTime() {
		return handleEndTime;
	}

	public void setHandleEndTime(Date handleEndTime) {
		this.handleEndTime = handleEndTime;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isHasManageAuth() {
		return hasManageAuth;
	}

	public void setHasManageAuth(boolean hasManageAuth) {
		this.hasManageAuth = hasManageAuth;
	}

	public AffairTransferSh getAffairTransferSh() {
		return affairTransferSh;
	}

	public void setAffairTransferSh(AffairTransferSh affairTransferSh) {
		this.affairTransferSh = affairTransferSh;
	}

	public String getTreeId() {
		return treeId;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}

	public String getStatusFlag() {
		return statusFlag;
	}

	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}
}