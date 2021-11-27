/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 纪律规定Entity
 * @author cecil.li
 * @version 2019-11-19
 */
public class AffairDisciplinaryRegulation extends DataEntity<AffairDisciplinaryRegulation> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "文件名称", type = 0, align = 2, sort = 0)
	private String name;		// 文件名称
	@ExcelField(title = "实施时间", type = 0, align = 2, sort = 1)
	private Date impTime;      //实施时间
	@ExcelField(title = "类型", type = 0, align = 2, sort = 2,dictType="affair_jilv")
	private String type;		// 类型
	@ExcelField(title = "上传时间", type = 0, align = 2, sort = 3)
	private Date uploadTime;		// 上传时间
	@ExcelField(title = "状态", type = 0, align = 2, sort = 4)
	private String isType;      //状态
//	@EsAttach
	private String filePath;      //附件
	private String unit;    //上传单位
	private String unitId;    //上传单位id
	private String orderId;    //置顶标志


	private String status;		// 审核状态
	private String reviewer;		// 审核人
	private String auditOpinion;		// 审核意见
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String content;		//内容
	private String receiveUnit;		//接收单位名称
	private String receiveUnitId;		//接收单位id

	private Date startDate;
	private Date endDate;
	private Boolean hasManageAuth;	// 是否有管理权限
	
	public AffairDisciplinaryRegulation() {
		super();
	}

	public AffairDisciplinaryRegulation(String id){
		super(id);
	}

	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getReviewer() {
		return reviewer;
	}

	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}
	
	public String getAuditOpinion() {
		return auditOpinion;
	}

	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
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

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}



	public String getIsType() {
		return isType;
	}

	public void setIsType(String isType) {
		this.isType = isType;
	}

	public Date getImpTime() {
		return impTime;
	}

	public void setImpTime(Date impTime) {
		this.impTime = impTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReceiveUnit() {
		return receiveUnit;
	}

	public void setReceiveUnit(String receiveUnit) {
		this.receiveUnit = receiveUnit;
	}

	public String getReceiveUnitId() {
		return receiveUnitId;
	}

	public void setReceiveUnitId(String receiveUnitId) {
		this.receiveUnitId = receiveUnitId;
	}

	public Boolean getHasManageAuth() {
		return hasManageAuth;
	}

	public void setHasManageAuth(Boolean hasManageAuth) {
		this.hasManageAuth = hasManageAuth;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}