/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 公务员登记信息集Entity
 * @author cecil.li
 * @version 2019-11-13
 */
public class PersonnelCivilServant extends DataEntity<PersonnelCivilServant> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 0)
	private String idNumber;		// 身份证号
	private String personnelName;		// 姓名
	@ExcelField(title = "登记标识", type = 0, align = 2, sort = 1)
	private String identification;		// 登记标识
	@ExcelField(title = "录用日期", type = 0, align = 2, sort = 2)
	private Date employDate;		// 录用日期
	@ExcelField(title = "登记日期", type = 0, align = 2, sort = 3)
	private Date registerDate;		// 登记日期
	@ExcelField(title = "登记时所在单位", type = 0, align = 2, sort = 4)
	private String unit;		// 登记时所在单位
	@ExcelField(title = "登记时所任职务", type = 0, align = 2, sort = 5)
	private String job;		// 登记时所任职务
	@ExcelField(title = "登记批准单位", type = 0, align = 2, sort = 6)
	private String approvalUnit;		// 登记批准单位
	@ExcelField(title = "登记编号", type = 0, align = 2, sort = 7)
	private String registerNo;		// 登记编号
	@ExcelField(title = "登记文号", type = 0, align = 2, sort = 8)
	private String fileNo;		// 登记文号
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private Date startDate;
	private Date endDate;
	
	public PersonnelCivilServant() {
		super();
	}

	public PersonnelCivilServant(String id){
		super(id);
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEmployDate() {
		return employDate;
	}

	public void setEmployDate(Date employDate) {
		this.employDate = employDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}
	
	public String getApprovalUnit() {
		return approvalUnit;
	}

	public void setApprovalUnit(String approvalUnit) {
		this.approvalUnit = approvalUnit;
	}
	
	public String getRegisterNo() {
		return registerNo;
	}

	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}
	
	public String getFileNo() {
		return fileNo;
	}

	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
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

	public String getPersonnelName() {
		return personnelName;
	}

	public void setPersonnelName(String personnelName) {
		this.personnelName = personnelName;
	}
}