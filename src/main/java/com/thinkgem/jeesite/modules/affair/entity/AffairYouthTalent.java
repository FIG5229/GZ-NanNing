/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 青年人才库Entity
 * @author cecil.li
 * @version 2019-11-19
 */
public class AffairYouthTalent extends DataEntity<AffairYouthTalent> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "警号", type = 0, align = 2, sort = 0)
	private String policeNo;		// 警号
	@ExcelField(title = "单位", type = 0, align = 2, sort = 1)
	private String unit;		// 单位
	private String unitId;		// 单位id
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 2)
	private String name;		// 姓名
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 3)
	private String idNumber;		// 身份证号
	@ExcelField(title = "出生年月", type = 0, align = 2, sort = 4)
	private Date birthday;		// 出生年月
	@ExcelField(title = "毕业院校", type = 0, align = 2, sort = 5)
	private String school;		// 毕业院校
	@ExcelField(title = "何种特长", type = 0, align = 2, sort = 6)
	private String skill;		// 何种特长
	@ExcelField(title = "取得成绩", type = 0, align = 2, sort = 7)
	private String achievement;		// 取得成绩
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private Date create_date;
	private Date startBirthdayDate;
	private Date endBirthdayDate;
	private Date startDate;
	private Date endDate;
	private String userOffice;

	private Boolean flag;	//true:人才青年库新增的人员在人员信息表中没有 false:人才青年库新增的人员在人员信息表中有
	
	public AffairYouthTalent() {
		super();
	}

	public AffairYouthTalent(String id){
		super(id);
	}

	public String getPoliceNo() {
		return policeNo;
	}

	public void setPoliceNo(String policeNo) {
		this.policeNo = policeNo;
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
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}
	
	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}
	
	public String getAchievement() {
		return achievement;
	}

	public void setAchievement(String achievement) {
		this.achievement = achievement;
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
	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public String getUserOffice() {
		return userOffice;
	}

	public void setUserOffice(String userOffice) {
		this.userOffice = userOffice;
	}
}