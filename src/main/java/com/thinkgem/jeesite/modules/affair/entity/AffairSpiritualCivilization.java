/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 精神文明单位管理Entity
 * @author Alan.wu
 * @version 2020-06-12
 */
public class AffairSpiritualCivilization extends DataEntity<AffairSpiritualCivilization> {
	
	private static final long serialVersionUID = 1L;
	private String unit;		// 单位
	private String unitId;		// 单位id
	private String annual;		// 年度
	private String approvalLevels;		// 批准层级
	private Date approvalTime;		// 批准时间
	private String adjunct;		// 附件
	private String description;		// 工作展示
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public AffairSpiritualCivilization() {
		super();
	}

	public AffairSpiritualCivilization(String id){
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

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	public String getAnnual() {
		return annual;
	}

	public void setAnnual(String annual) {
		this.annual = annual;
	}

	public String getApprovalLevels() {
		return approvalLevels;
	}

	public void setApprovalLevels(String approvalLevels) {
		this.approvalLevels = approvalLevels;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm")
	public Date getApprovalTime() {
		return approvalTime;
	}

	public void setApprovalTime(Date approvalTime) {
		this.approvalTime = approvalTime;
	}
	
	public String getAdjunct() {
		return adjunct;
	}

	public void setAdjunct(String adjunct) {
		this.adjunct = adjunct;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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