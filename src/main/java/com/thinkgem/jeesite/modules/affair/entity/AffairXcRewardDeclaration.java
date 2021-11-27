/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 单位集体奖励申报Entity
 * @author cecil.li
 * @version 2019-12-21
 */
public class AffairXcRewardDeclaration extends DataEntity<AffairXcRewardDeclaration> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "申报时间", type = 0, align = 2, sort = 2)
	private Date date;		// 申报时间
	@ExcelField(title = "申报单位", type = 0, align = 2, sort = 3)
	private String unit;		// 申报单位
	private String unitId;		// 申报单位id
	@ExcelField(title = "审核单位", type = 0, align = 2, sort = 4)
	private String approvalUnit;		// 审核通过单位
	private String approvalUnitId;		// 审核通过单位id
	@ExcelField(title = "奖励名称", type = 0, align = 2, sort = 0)
	private String name;		// 奖励名称
	private String fileNo;		// 奖励文号
	@ExcelField(title = "简要事迹", type = 0, align = 2, sort = 6)
	private String remark;		// 简要事迹
	private String filePath;		// 附件
	@ExcelField(title = "主要负责人", type = 0, align = 2, sort = 5)
	private String adminPeople;     //主要负责人
	private	String tsStatus;        //推送状态
	@ExcelField(title = "荣誉级别", type = 0, align = 2, sort = 1, dictType = "affair_approval_unitLevel")
	private String type;          //奖励级别
	private String status;		// 审核状态
	private String opinion;		// 审核意见
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private Date beginDate;		// 开始 奖励时间
	private Date endDate;		// 结束 奖励时间
	private String shPerson;
	private String juCheckMan;
	private String chuCheckMan;
	private String submitMan;
	private String sbType;
	private String juCheckId;
	private String chuCheckId;
	private String submitId;
	private String officeId;
	private String userId;
	
	public AffairXcRewardDeclaration() {
		super();
	}

	public AffairXcRewardDeclaration(String id){
		super(id);
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
	
	public String getApprovalUnit() {
		return approvalUnit;
	}

	public void setApprovalUnit(String approvalUnit) {
		this.approvalUnit = approvalUnit;
	}
	
	public String getApprovalUnitId() {
		return approvalUnitId;
	}

	public void setApprovalUnitId(String approvalUnitId) {
		this.approvalUnitId = approvalUnitId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getFileNo() {
		return fileNo;
	}

	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getShPerson() {
		return shPerson;
	}

	public void setShPerson(String shPerson) {
		this.shPerson = shPerson;
	}

	public String getAdminPeople() {
		return adminPeople;
	}

	public void setAdminPeople(String adminPeople) {
		this.adminPeople = adminPeople;
	}

	public String getTsStatus() {
		return tsStatus;
	}

	public void setTsStatus(String tsStatus) {
		this.tsStatus = tsStatus;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getSbType() {
		return sbType;
	}

	public void setSbType(String sbType) {
		this.sbType = sbType;
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