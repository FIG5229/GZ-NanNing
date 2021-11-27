/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 增员信息集Entity
 * @author cecil.li
 * @version 2019-11-11
 */
public class PersonnelAddPerson extends DataEntity<PersonnelAddPerson> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 0)
	private String idNumber;		// 身份证号
	private String personnelName;		// 姓名
	@ExcelField(title = "进入本单位日期", type = 0, align = 2, sort = 1)
	private Date startDate;		// 进入本单位日期
	@ExcelField(title = "单位增员类别", type = 0, align = 2, sort = 2, dictType="personnel_zytype")
	private String addType;		// 单位增员类别
	@ExcelField(title = "进入本单位原因", type = 0, align = 2, sort = 3)
	private String reason;		// 进入本单位原因
	@ExcelField(title = "本单位所在地区类别", type = 0, align = 2, sort = 4, dictType="personnel_dqtype")
	private String unitAreaType;		// 本单位所在地区类别
	@ExcelField(title = "校正工资档次", type = 0, align = 2, sort = 5)
	private String reviseGrade;		// 校正工资档次
	@ExcelField(title = "校正日期", type = 0, align = 2, sort = 6)
	private Date reviseDate;		// 校正日期
	@ExcelField(title = "起薪日期", type = 0, align = 2, sort = 7)
	private Date startSalaryDate;		// 起薪日期
	@ExcelField(title = "工作单位名称", type = 0, align = 2, sort = 8)
	private String unitName;		// 工作单位名称
	@ExcelField(title = "工作单位代码", type = 0, align = 2, sort = 9)
	private String unitCode;		// 工作单位代码
	@ExcelField(title = "工作单位名称类别", type = 0, align = 2, sort = 10, dictType="personnel_nametype")
	private String unitNameType;		// 工作单位名称类别
	@ExcelField(title = "工作单位所在政区", type = 0, align = 2, sort = 11)
	private String unitArea;		// 工作单位所在政区
	@ExcelField(title = "工作单位隶属关系", type = 0, align = 2, sort = 12)
	private String unitRelationship;		// 工作单位隶属关系
	@ExcelField(title = "工作单位级别", type = 0, align = 2, sort = 13)
	private String unitLevel;		// 工作单位级别
	@ExcelField(title = "工作单位性质类别", type = 0, align = 2, sort = 14)
	private String unitCharacterType;		// 工作单位性质类别
	@ExcelField(title = "工作单位所属行业", type = 0, align = 2, sort = 15)
	private String unitIndustry;		// 工作单位所属行业
	@ExcelField(title = "所在地区类别", type = 0, align = 2, sort = 16)
	private String areaType;		// 所在地区类别
	@ExcelField(title = "个人身份", type = 0, align = 2, sort = 17, dictType="personnel_grsf")
	private String identity;		// 个人身份
	@ExcelField(title = "在原单位职务", type = 0, align = 2, sort = 18)
	private String oldJob;		// 在原单位职务
	@ExcelField(title = "在原单位级别", type = 0, align = 2, sort = 19)
	private String oldLevel;		// 在原单位级别
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private Date beginDate;
	private Date finishDate;
	
	public PersonnelAddPerson() {
		super();
	}

	public String getPersonnelName() {
		return personnelName;
	}

	public void setPersonnelName(String personnelName) {
		this.personnelName = personnelName;
	}

	public PersonnelAddPerson(String id){
		super(id);
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public String getAddType() {
		return addType;
	}

	public void setAddType(String addType) {
		this.addType = addType;
	}
	
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public String getUnitAreaType() {
		return unitAreaType;
	}

	public void setUnitAreaType(String unitAreaType) {
		this.unitAreaType = unitAreaType;
	}
	
	public String getReviseGrade() {
		return reviseGrade;
	}

	public void setReviseGrade(String reviseGrade) {
		this.reviseGrade = reviseGrade;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getReviseDate() {
		return reviseDate;
	}

	public void setReviseDate(Date reviseDate) {
		this.reviseDate = reviseDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getStartSalaryDate() {
		return startSalaryDate;
	}

	public void setStartSalaryDate(Date startSalaryDate) {
		this.startSalaryDate = startSalaryDate;
	}
	
	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	
	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	
	public String getUnitNameType() {
		return unitNameType;
	}

	public void setUnitNameType(String unitNameType) {
		this.unitNameType = unitNameType;
	}
	
	public String getUnitArea() {
		return unitArea;
	}

	public void setUnitArea(String unitArea) {
		this.unitArea = unitArea;
	}
	
	public String getUnitRelationship() {
		return unitRelationship;
	}

	public void setUnitRelationship(String unitRelationship) {
		this.unitRelationship = unitRelationship;
	}
	
	public String getUnitLevel() {
		return unitLevel;
	}

	public void setUnitLevel(String unitLevel) {
		this.unitLevel = unitLevel;
	}
	
	public String getUnitCharacterType() {
		return unitCharacterType;
	}

	public void setUnitCharacterType(String unitCharacterType) {
		this.unitCharacterType = unitCharacterType;
	}
	
	public String getUnitIndustry() {
		return unitIndustry;
	}

	public void setUnitIndustry(String unitIndustry) {
		this.unitIndustry = unitIndustry;
	}
	
	public String getAreaType() {
		return areaType;
	}

	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}
	
	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}
	
	public String getOldJob() {
		return oldJob;
	}

	public void setOldJob(String oldJob) {
		this.oldJob = oldJob;
	}
	
	public String getOldLevel() {
		return oldLevel;
	}

	public void setOldLevel(String oldLevel) {
		this.oldLevel = oldLevel;
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