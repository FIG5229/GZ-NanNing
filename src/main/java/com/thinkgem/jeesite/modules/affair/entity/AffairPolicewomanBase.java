/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 女警基本情况Entity
 * @author cecil.li
 * @version 2019-11-05
 */
public class AffairPolicewomanBase extends DataEntity<AffairPolicewomanBase> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 0)
	private String name;		// 姓名
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 1)
	private String idNumber;     //身份证号
	@ExcelField(title = "出生年月日", type = 0, align = 2, sort = 2)
	private Date birthday;		// 出生年月日
	@ExcelField(title = "单位", type = 0, align = 2, sort = 3)
	private String unit;		// 单位
	private String unitId;		// 单位id
	@ExcelField(title = "是否是党员", type = 0, align = 2, sort = 4, dictType="yes_no")
	private String isPartyMember;		// 是否是党员
	@ExcelField(title = "学历", type = 0, align = 2, sort = 5, dictType="affair_xueli")
	private String xl;		// 学历
	@ExcelField(title = "技术职称", type = 0, align = 2, sort = 6, dictType="affair_jishu")
	private String jszc;		// 技术职称
	@ExcelField(title = "毕业院校", type = 0, align = 2, sort = 7)
	private String school;		// 毕业院校
	@ExcelField(title = "所学专业", type = 0, align = 2, sort = 8)
	private String major;		// 所学专业
	@ExcelField(title = "获得荣誉情况", type = 0, align = 2, sort = 9)
	private String situation;		// 获得荣誉情况


	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号


	private Date startDate;
	private Date endDate;
	private Date startBirthdayDate;
	private Date endBirthdayDate;
	
	public AffairPolicewomanBase() {
		super();
	}

	public AffairPolicewomanBase(String id){
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
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
	
	public String getIsPartyMember() {
		return isPartyMember;
	}

	public void setIsPartyMember(String isPartyMember) {
		this.isPartyMember = isPartyMember;
	}
	
	public String getXl() {
		return xl;
	}

	public void setXl(String xl) {
		this.xl = xl;
	}
	
	public String getJszc() {
		return jszc;
	}

	public void setJszc(String jszc) {
		this.jszc = jszc;
	}
	
	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}
	
	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}
	
	public String getSituation() {
		return situation;
	}

	public void setSituation(String situation) {
		this.situation = situation;
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

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getStartBirthdayDate() {
		return startBirthdayDate;
	}

	public void setStartBirthdayDate(Date startBirthdayDate) {
		this.startBirthdayDate = startBirthdayDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEndBirthdayDate() {
		return endBirthdayDate;
	}

	public void setEndBirthdayDate(Date endBirthdayDate) {
		this.endBirthdayDate = endBirthdayDate;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
}