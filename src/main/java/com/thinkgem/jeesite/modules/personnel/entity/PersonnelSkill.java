/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 专长信息集Entity
 * @author cecil.li
 * @version 2019-11-11
 */
public class PersonnelSkill extends DataEntity<PersonnelSkill> {
	/*
	@ExcelField(title = "等级程度", type = 0, align = 2, sort = 2, dictType="personnel_djcd")
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 6)
	* */
	private static final long serialVersionUID = 1L;
	private String type;		// 专长类别
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 1)
	private String personnelName;		// 姓名
	private String computerDegree;		// 计算机使用程度
	private String gradeDegree;		// 等级程度
	private String unitName;		// 专长认定单位名称
	private String supplement;		// 专长类别补充
	private String describe;		// 专长描述
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 2)
	private String idNumber;		// 身份证号
	@ExcelField(title = "性别", type = 0, align = 2, sort = 3,dictType = "sex")
	private String sex;		// 性别
	@ExcelField(title = "出生年月", type = 0, align = 2, sort = 4)
	private Date birthday;		// 出生年月
	@ExcelField(title = "特长", type = 0, align = 2, sort = 5)
	private String speciality;		// 特长
	@ExcelField(title = "单位", type = 0, align = 2, sort = 6)
	private String unit;		// 单位
	private String unitId;		// 单位id

	@ExcelField(title = "人才类别", type = 0, align = 2, sort = 7,dictType = "talents_category")
	private String category;		// 高层次人才类别
	@ExcelField(title = "人才名称", type = 0, align = 2, sort = 8)
	private String talentsName;		// 人才名称
	@ExcelField(title = "享受待遇", type = 0, align = 2, sort = 9)
	private String talentsWelfare;		// 享受待遇
	@ExcelField(title = "人才用久居留权地名称", type = 0, align = 2, sort = 10)
	private String resideAddress;		// 居留地
	@ExcelField(title = "人才用久居留权地代码", type = 0, align = 2, sort = 11)
	private String resideAddressCode;		// 居留地代码
	@ExcelField(title = "专业类别描述", type = 0, align = 2, sort = 12)
	private String specialtyCategoryDescribe;		// 专业类别描述
	@ExcelField(title = "称号批准日期", type = 0, align = 2, sort = 13)
	private Date titleRatifyDate;		// 称号批准日期
	@ExcelField(title = "称号批准单位", type = 0, align = 2, sort = 14)
	private String titleRatifyUnit;		// 称号批准单位
	@ExcelField(title = "称号批准单位级别", type = 0, align = 2, sort = 15,dictType = "unit_rank")
	private String titleRatifyUnitGrade;		// 称号批准单位级别
	@ExcelField(title = "称号批准单位隶属关系", type = 0, align = 2, sort = 16)
	private String titleRatifyUnitRelation;		// 称号批准单位隶属关系





	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号


	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
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

	public String getPersonnelName() {
		return personnelName;
	}

	public void setPersonnelName(String personnelName) {
		this.personnelName = personnelName;
	}

	public PersonnelSkill() {
		super();
	}

	public PersonnelSkill(String id){
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
	
	public String getComputerDegree() {
		return computerDegree;
	}

	public void setComputerDegree(String computerDegree) {
		this.computerDegree = computerDegree;
	}
	
	public String getGradeDegree() {
		return gradeDegree;
	}

	public void setGradeDegree(String gradeDegree) {
		this.gradeDegree = gradeDegree;
	}
	
	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	
	public String getSupplement() {
		return supplement;
	}

	public void setSupplement(String supplement) {
		this.supplement = supplement;
	}
	
	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTalentsName() {
		return talentsName;
	}

	public void setTalentsName(String talentsName) {
		this.talentsName = talentsName;
	}

	public String getTalentsWelfare() {
		return talentsWelfare;
	}

	public void setTalentsWelfare(String talentsWelfare) {
		this.talentsWelfare = talentsWelfare;
	}

	public String getResideAddress() {
		return resideAddress;
	}

	public void setResideAddress(String resideAddress) {
		this.resideAddress = resideAddress;
	}

	public String getResideAddressCode() {
		return resideAddressCode;
	}

	public void setResideAddressCode(String resideAddressCode) {
		this.resideAddressCode = resideAddressCode;
	}

	public String getSpecialtyCategoryDescribe() {
		return specialtyCategoryDescribe;
	}

	public void setSpecialtyCategoryDescribe(String specialtyCategoryDescribe) {
		this.specialtyCategoryDescribe = specialtyCategoryDescribe;
	}
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getTitleRatifyDate() {
		return titleRatifyDate;
	}

	public void setTitleRatifyDate(Date titleRatifyDate) {
		this.titleRatifyDate = titleRatifyDate;
	}

	public String getTitleRatifyUnit() {
		return titleRatifyUnit;
	}

	public void setTitleRatifyUnit(String titleRatifyUnit) {
		this.titleRatifyUnit = titleRatifyUnit;
	}

	public String getTitleRatifyUnitGrade() {
		return titleRatifyUnitGrade;
	}

	public void setTitleRatifyUnitGrade(String titleRatifyUnitGrade) {
		this.titleRatifyUnitGrade = titleRatifyUnitGrade;
	}

	public String getTitleRatifyUnitRelation() {
		return titleRatifyUnitRelation;
	}

	public void setTitleRatifyUnitRelation(String titleRatifyUnitRelation) {
		this.titleRatifyUnitRelation = titleRatifyUnitRelation;
	}
}