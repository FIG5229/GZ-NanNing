/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 录（入）警信息集Entity
 * @author cecil.li
 * @version 2019-11-12
 */
public class PersonnelEnterPolice extends DataEntity<PersonnelEnterPolice> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 0)
	private String idNumber;		// 身份证号
	private String personnelName;		// 姓名
	@ExcelField(title = "呈报日期", type = 0, align = 2, sort = 1)
	private Date reportDate;		// 呈报日期
	@ExcelField(title = "呈报单位名称", type = 0, align = 2, sort = 2)
	private String reportUnit;		// 呈报单位名称
	@ExcelField(title = "呈报单位代码", type = 0, align = 2, sort = 3)
	private String reportUnitCode;		// 呈报单位代码
	@ExcelField(title = "进入公安方式", type = 0, align = 2, sort = 4)
	private String method;		// 进入公安方式
	@ExcelField(title = "呈报请示名称", type = 0, align = 2, sort = 5)
	private String fileNo;		// 呈报请示名称（文号)
	@ExcelField(title = "批准日期", type = 0, align = 2, sort = 6)
	private Date approvalDate;		// 批准日期
	@ExcelField(title = "批准单位名称", type = 0, align = 2, sort = 7)
	private String approvalUnit;		// 批准单位名称
	@ExcelField(title = "批准单位代码", type = 0, align = 2, sort = 8)
	private String approvalUnitCode;		// 批准单位代码
	@ExcelField(title = "批准文号", type = 0, align = 2, sort = 9)
	private String approvalFileNo;		// 批准文号
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private Date startDate;
	private Date endDate;

	public String getPersonnelName() {
		return personnelName;
	}

	public void setPersonnelName(String personnelName) {
		this.personnelName = personnelName;
	}

	public PersonnelEnterPolice() {
		super();
	}

	public PersonnelEnterPolice(String id){
		super(id);
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
	
	public String getReportUnit() {
		return reportUnit;
	}

	public void setReportUnit(String reportUnit) {
		this.reportUnit = reportUnit;
	}
	
	public String getReportUnitCode() {
		return reportUnitCode;
	}

	public void setReportUnitCode(String reportUnitCode) {
		this.reportUnitCode = reportUnitCode;
	}
	
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
	public String getFileNo() {
		return fileNo;
	}

	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}
	
	public String getApprovalUnit() {
		return approvalUnit;
	}

	public void setApprovalUnit(String approvalUnit) {
		this.approvalUnit = approvalUnit;
	}
	
	public String getApprovalUnitCode() {
		return approvalUnitCode;
	}

	public void setApprovalUnitCode(String approvalUnitCode) {
		this.approvalUnitCode = approvalUnitCode;
	}
	
	public String getApprovalFileNo() {
		return approvalFileNo;
	}

	public void setApprovalFileNo(String approvalFileNo) {
		this.approvalFileNo = approvalFileNo;
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
}