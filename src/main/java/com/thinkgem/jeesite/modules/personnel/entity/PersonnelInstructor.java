/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 教官信息集Entity
 * @author cecil.li
 * @version 2019-11-11
 */
public class PersonnelInstructor extends DataEntity<PersonnelInstructor> {
	
	private static final long serialVersionUID = 1L;
	private String personnelName;		// 姓名
	@ExcelField(title = "教官类别", type = 0, align = 2, sort = 0, dictType="personnel_instype")
	private String type;		// 教官类别
	@ExcelField(title = "聘用级别", type = 0, align = 2, sort = 1)
	private String level;		// 聘用级别
	@ExcelField(title = "聘用日期", type = 0, align = 2, sort = 2)
	private Date employDate;		// 聘用日期
	@ExcelField(title = "评审日期", type = 0, align = 2, sort = 3)
	private Date reviewDate;		// 评审日期
	@ExcelField(title = "评审人员", type = 0, align = 2, sort = 4)
	private String reviewPerson;		// 评审人员
	@ExcelField(title = "聘用单位", type = 0, align = 2, sort = 5)
	private String employUnit;		// 聘用单位
	@ExcelField(title = "教官特长", type = 0, align = 2, sort = 6)
	private String speciality;		// 教官特长
	@ExcelField(title = "拟解聘日期", type = 0, align = 2, sort = 7)
	private Date dismissDate;		// 拟解聘日期
	@ExcelField(title = "是否为终身制", type = 0, align = 2, sort = 8, dictType="yes_no")
	private String isLife;		// 是否为终身制
	@ExcelField(title = "评审理由", type = 0, align = 2, sort = 9)
	private String reason;		// 评审理由
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 10)
	private String idNumber;		// 身份证号


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

	public PersonnelInstructor() {
		super();
	}

	public PersonnelInstructor(String id){
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
	
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEmployDate() {
		return employDate;
	}

	public void setEmployDate(Date employDate) {
		this.employDate = employDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}
	
	public String getReviewPerson() {
		return reviewPerson;
	}

	public void setReviewPerson(String reviewPerson) {
		this.reviewPerson = reviewPerson;
	}
	
	public String getEmployUnit() {
		return employUnit;
	}

	public void setEmployUnit(String employUnit) {
		this.employUnit = employUnit;
	}
	
	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDismissDate() {
		return dismissDate;
	}

	public void setDismissDate(Date dismissDate) {
		this.dismissDate = dismissDate;
	}
	
	public String getIsLife() {
		return isLife;
	}

	public void setIsLife(String isLife) {
		this.isLife = isLife;
	}
	
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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