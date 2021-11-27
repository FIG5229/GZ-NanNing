/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 发展党员Entity
 * @author eav.liu
 * @version 2019-11-01
 */
public class AffairDevelopObject extends DataEntity<AffairDevelopObject> {

	private static final long serialVersionUID = 1L;
	@ExcelField(title = "警号", type = 0, align = 2, sort = 3)
	private String policeNo;		// 警号
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 4)
	private String idNumber;		// 身份证号
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 0)
	private String name;		// 姓名
	@ExcelField(title = "性别", type = 0, align = 2, sort = 1, dictType = "sex")
	private String sex;		// 性别（1：男 2：女）
	@ExcelField(title = "所属党支部", type = 0, align = 2, sort = 5)
	private String partyBranch;		// 所在党支部

	private String partyBranchId;		// 所在党支部机构id
	@ExcelField(title = "培养人", type = 0, align = 2, sort = 9)
	private String fosterPeople;		// 培养人
	@ExcelField(title = "申请入党时间", type = 0, align = 2, sort = 6)
	private Date approvalDate;		// 申请入党时间
	@ExcelField(title = "列入培养对象的时间", type = 0, align = 2, sort = 7)
	private Date enterDate;		// 列入培养对象的时间
	@ExcelField(title = "列为入党发展对象时间", type = 0, align = 2, sort = 8)
	private Date enterPartDate;		// 列为入党发展对象时间


	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private Date approvalStartDate;
	private Date approvalEndDate;

	private Date enterStartDate;
	private Date enterEndDate;

	private Date enterPartStartDate;
	private Date enterPartEndDate;
	private String treeId;

	//统计分析使用
	private String dateType;
	private Integer year;
	private Date dateStart;
	private Date dateEnd;
	private String month;
	//推送状态
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public AffairDevelopObject() {
		super();
	}

	public AffairDevelopObject(String id){
		super(id);
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPartyBranch() {
		return partyBranch;
	}

	public void setPartyBranch(String partyBranch) {
		this.partyBranch = partyBranch;
	}

	public String getPartyBranchId() {
		return partyBranchId;
	}

	public void setPartyBranchId(String partyBranchId) {
		this.partyBranchId = partyBranchId;
	}

	public String getFosterPeople() {
		return fosterPeople;
	}

	public void setFosterPeople(String fosterPeople) {
		this.fosterPeople = fosterPeople;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEnterDate() {
		return enterDate;
	}

	public void setEnterDate(Date enterDate) {
		this.enterDate = enterDate;
	}

	public Date getEnterPartDate() {
		return enterPartDate;
	}

	public void setEnterPartDate(Date enterPartDate) {
		this.enterPartDate = enterPartDate;
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
	public Date getApprovalStartDate() {
		return approvalStartDate;
	}

	public void setApprovalStartDate(Date approvalStartDate) {
		this.approvalStartDate = approvalStartDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getApprovalEndDate() {
		return approvalEndDate;
	}

	public void setApprovalEndDate(Date approvalEndDate) {
		this.approvalEndDate = approvalEndDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEnterStartDate() {
		return enterStartDate;
	}

	public void setEnterStartDate(Date enterStartDate) {
		this.enterStartDate = enterStartDate;
	}

	public Date getEnterPartStartDate() {
		return enterPartStartDate;
	}

	public void setEnterPartStartDate(Date enterPartStartDate) {
		this.enterPartStartDate = enterPartStartDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEnterEndDate() {
		return enterEndDate;
	}

	public void setEnterEndDate(Date enterEndDate) {
		this.enterEndDate = enterEndDate;
	}

	public Date getEnterPartEndDate() {
		return enterPartEndDate;
	}

	public void setEnterPartEndDate(Date enterPartEndDate) {
		this.enterPartEndDate = enterPartEndDate;
	}

	public String getTreeId() {
		return treeId;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}
}