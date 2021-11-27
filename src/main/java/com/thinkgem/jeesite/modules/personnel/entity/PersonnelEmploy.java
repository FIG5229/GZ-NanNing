/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 聘用信息集Entity
 * @author cecil.li
 * @version 2019-11-11
 */
public class PersonnelEmploy extends DataEntity<PersonnelEmploy> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "聘用单位", type = 0, align = 2, sort = 0)
	private String unit;		// 聘用单位
	private String personnelName;		// 姓名
	@ExcelField(title = "聘用日期", type = 0, align = 2, sort = 1)
	private Date date;		// 聘用日期
	@ExcelField(title = "聘用岗位", type = 0, align = 2, sort = 2)
	private String job;		// 聘用岗位
	@ExcelField(title = "聘用年限", type = 0, align = 2, sort = 3)
	private String year;		// 聘用年限
	@ExcelField(title = "聘用状态", type = 0, align = 2, sort = 4, dictType="personnel_pytype")
	private String status;		// 聘用状态
	@ExcelField(title = "聘用批准单位", type = 0, align = 2, sort = 5)
	private String approvalUnit;		// 聘用批准单位
	@ExcelField(title = "聘用文件名称（文号）", type = 0, align = 2, sort = 6)
	private String fileName;		// 聘用文件名称（文号）
	@ExcelField(title = "备注", type = 0, align = 2, sort = 7)
	private String remark;		// 备注
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 8)
	private String idNumber;		// 身份证号


	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String unitId;		// 聘用单位id


	private Date startDate;
	private Date endDate;

	public String getPersonnelName() {
		return personnelName;
	}

	public void setPersonnelName(String personnelName) {
		this.personnelName = personnelName;
	}

	public PersonnelEmploy() {
		super();
	}

	public PersonnelEmploy(String id){
		super(id);
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}
	
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getApprovalUnit() {
		return approvalUnit;
	}

	public void setApprovalUnit(String approvalUnit) {
		this.approvalUnit = approvalUnit;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
	
	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
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