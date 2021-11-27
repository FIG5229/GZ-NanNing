/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 协管干部信息集Entity
 * @author cecil.li
 * @version 2019-11-13
 */
public class PersonnelXieguanCadre extends DataEntity<PersonnelXieguanCadre> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 0)
	private String idNumber;		// 身份证号
	private String personnelName;		// 姓名
	@ExcelField(title = "协管层级", type = 0, align = 2, sort = 1)
	private String level;		// 协管层级
	@ExcelField(title = "兼任类型", type = 0, align = 2, sort = 2, dictType="personnel_jrlx")
	private String type;		// 兼任类型
	@ExcelField(title = "纳入协管时职务", type = 0, align = 2, sort = 3)
	private String job;		// 纳入协管时职务
	@ExcelField(title = "纳入协管时单位", type = 0, align = 2, sort = 4)
	private String unit;		// 纳入协管时单位
	@ExcelField(title = "纳入协管日期", type = 0, align = 2, sort = 5)
	private Date startDate;		// 纳入协管日期
	@ExcelField(title = "退出协管日期", type = 0, align = 2, sort = 6)
	private Date endDate;		// 退出协管日期
	@ExcelField(title = "任现职前职务及时间", type = 0, align = 2, sort = 7)
	private String nowJobTime;		// 任现职前职务及时间
	@ExcelField(title = "任同级党政领导职务情况及时间", type = 0, align = 2, sort = 8)
	private String situationTime1;		// 任同级党政领导职务情况及时间
	@ExcelField(title = "任公安机关主要领导职务情况及时间", type = 0, align = 2, sort = 9)
	private String situationTime2;		// 任公安机关主要领导职务情况及时间
	@ExcelField(title = "异地任职", type = 0, align = 2, sort = 10)
	private String differentPlace;		// 异地任职
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private Date beginDate;
	private Date finishDate;

	public String getPersonnelName() {
		return personnelName;
	}

	public void setPersonnelName(String personnelName) {
		this.personnelName = personnelName;
	}

	public PersonnelXieguanCadre() {
		super();
	}

	public PersonnelXieguanCadre(String id){
		super(id);
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}
	
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
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
	
	public String getNowJobTime() {
		return nowJobTime;
	}

	public void setNowJobTime(String nowJobTime) {
		this.nowJobTime = nowJobTime;
	}
	
	public String getSituationTime1() {
		return situationTime1;
	}

	public void setSituationTime1(String situationTime1) {
		this.situationTime1 = situationTime1;
	}
	
	public String getSituationTime2() {
		return situationTime2;
	}

	public void setSituationTime2(String situationTime2) {
		this.situationTime2 = situationTime2;
	}
	
	public String getDifferentPlace() {
		return differentPlace;
	}

	public void setDifferentPlace(String differentPlace) {
		this.differentPlace = differentPlace;
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
	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}
}