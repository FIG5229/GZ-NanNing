/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 奖励查询Entity
 * @author cecil.li
 * @version 2019-12-21
 */
public class AffairRewardInquire extends DataEntity<AffairRewardInquire> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 0)
	private String name;		// 姓名
	@ExcelField(title = "单位", type = 0, align = 2, sort = 1)
	private String unit;		// 批准单位
	private String unitId;		// 批准单位id
	@ExcelField(title = "奖励名称", type = 0, align = 2, sort = 2)
	private String title;		// 奖惩名称
	@ExcelField(title = "文号", type = 0, align = 2, sort = 3)
	private String fileNo;		// 奖惩文号
	@ExcelField(title = "批准单位", type = 0, align = 2, sort = 4)
	private String approvalOrg;
	private String approvalOrgId;
	@ExcelField(title = "奖励类别", type = 0, align = 2, sort = 5,dictType = "affair_approval_unitLevel")
	private String type;		// 奖惩类别
	private Date date;		// 批准时间
	private String idNumber;		// 身份证号
	private String policeNumber;		// 警号
	private String opinion;		// 奖惩说明
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String range;		// 范围
	private String userId;
	private String typeFlag;

	private Date startDate;
	private Date endDate;
	
	public AffairRewardInquire() {
		super();
	}

	public AffairRewardInquire(String id){
		super(id);
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public String getPoliceNumber() {
		return policeNumber;
	}

	public void setPoliceNumber(String policeNumber) {
		this.policeNumber = policeNumber;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getFileNo() {
		return fileNo;
	}

	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
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
	
	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getApprovalOrg() {
		return approvalOrg;
	}

	public void setApprovalOrg(String approvalOrg) {
		this.approvalOrg = approvalOrg;
	}

	public String getApprovalOrgId() {
		return approvalOrgId;
	}

	public void setApprovalOrgId(String approvalOrgId) {
		this.approvalOrgId = approvalOrgId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTypeFlag() {
		return typeFlag;
	}

	public void setTypeFlag(String typeFlag) {
		this.typeFlag = typeFlag;
	}
}