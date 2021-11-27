/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 组织考核考察（审查）信息集Entity
 * @author cecil.li
 * @version 2019-11-11
 */
public class PersonnelOrgCheck extends DataEntity<PersonnelOrgCheck> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "考核类别", type = 0, align = 2, sort = 1, dictType="personnel_khtype")
	private String type;		// 考核类别
	@ExcelField(title = "考察日期", type = 0, align = 2, sort = 2)
	private Date date;		// 考察日期
	@ExcelField(title = "考察组织部门名称", type = 0, align = 2, sort = 3)
	private String department;		// 考察组织部门名称
	@ExcelField(title = "考察意见", type = 0, align = 2, sort = 4)
	private String opinion;		// 考察意见
	@ExcelField(title = "考察人姓名", type = 0, align = 2, sort = 5)
	private String name;		// 考察人姓名
	@ExcelField(title = "考察方式标识", type = 0, align = 2, sort = 6)
	private String identification;		// 考察方式标识
	@ExcelField(title = "考察结论类别", type = 0, align = 2, sort = 7, dictType="personnel_kcjllb")
	private String conclusionType;		// 考察结论类别


	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 8)
	private String idNumber;		// 身份证号
	private String personName;  // 姓名


	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private Date startDate;
	private Date endDate;
	
	public PersonnelOrgCheck() {
		super();
	}

	public PersonnelOrgCheck(String id){
		super(id);
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}
	
	public String getConclusionType() {
		return conclusionType;
	}

	public void setConclusionType(String conclusionType) {
		this.conclusionType = conclusionType;
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

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}
}