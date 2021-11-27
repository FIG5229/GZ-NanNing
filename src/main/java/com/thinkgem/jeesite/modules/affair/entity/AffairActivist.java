/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 入党积极分子Entity
 * @author eav.liu
 * @version 2019-10-31
 */
public class AffairActivist extends DataEntity<AffairActivist> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "警号", type = 0, align = 2, sort = 2)
	private String policeNo;		// 警号
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 3)
	private String idNumber;		// 身份证号
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 0)
	private String name;		// 姓名
	@ExcelField(title = "性别", type = 0, align = 2, sort = 1, dictType = "sex")
	private String sex;		// 性别
	@ExcelField(title = "所在党支部", type = 0, align = 2, sort = 4)
	private String partyBranch;		// 所在党支部

	private String partyBranchId;		// 所在党支部机构id
	@ExcelField(title = "培养人", type = 0, align = 2, sort = 7)
	private String fosterPeople;		// 培养人
	@ExcelField(title = "申请入党时间", type = 0, align = 2, sort = 5)
	private Date approvalDate;		// 申请入党时间
	@ExcelField(title = "列入培养对象的时间", type = 0, align = 2, sort = 6)
	private Date enterDate;		// 列入培养对象的时间
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	//供查询时间区间使用
	private Date approvalStartDate;
	//供查询时间区间使用
	private Date approvalEndDate;
	//供查询时间区间使用
	private Date enterStartDate;
	//供查询时间区间使用
	private Date enterEndDate;
	private String treeId;


	//统计分析使用
	private String flag;
	private Integer year;
	private String month;

	private String dateType;
	private Date dateEnd;
	private Date dateStart;

	//推送状态
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public AffairActivist() {
		super();
	}

	public AffairActivist(String id){
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
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEnterEndDate() {
		return enterEndDate;
	}

	public void setEnterEndDate(Date enterEndDate) {
		this.enterEndDate = enterEndDate;
	}

	public String getTreeId() {
		return treeId;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}


	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}



	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}
}