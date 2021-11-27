/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 高层次人才信息集Entity
 * @author cecil.li
 * @version 2019-11-13
 */
public class PersonnelHighLevel extends DataEntity<PersonnelHighLevel> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 0)
	private String idNumber;		// 身份证号
	@ExcelField(title = "高层次人才类别", type = 0, align = 2, sort = 1, dictType="personnel_gccrclb")
	private String type;		// 高层次人才类别
	@ExcelField(title = "高层次人才名称", type = 0, align = 2, sort = 2)
	private String name;		// 高层次人才名称
	@ExcelField(title = "高层次人才享受待遇", type = 0, align = 2, sort = 3)
	private String treatment;		// 高层次人才享受待遇
	@ExcelField(title = "高层次人才永久居留权地名称", type = 0, align = 2, sort = 4)
	private String placeName;		// 高层次人才永久居留权地名称
	@ExcelField(title = "高层次人才永久居留权地代码", type = 0, align = 2, sort = 5)
	private String placeCode;		// 高层次人才永久居留权地代码
	@ExcelField(title = "专业类别描述", type = 0, align = 2, sort = 6)
	private String describe;		// 专业类别描述
	@ExcelField(title = "高层次人才称号批准日期", type = 0, align = 2, sort = 7)
	private Date approvalDate;		// 高层次人才称号批准日期
	@ExcelField(title = "高层次人才称号批准单位名称", type = 0, align = 2, sort = 8)
	private String approvalUnit;		// 高层次人才称号批准单位名称
	@ExcelField(title = "高层次人才称号批准单位隶属关系", type = 0, align = 2, sort = 9)
	private String approvalUnitRelationship;		// 高层次人才称号批准单位隶属关系
	@ExcelField(title = "高层次人才称号批准单位级别", type = 0, align = 2, sort = 10, dictType="personnel_gccdwjb")
	private String approvalUnitLevel;		// 高层次人才称号批准单位级别
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String personName;  // 姓名

	private Date startDate;
	private Date endDate;
	
	public PersonnelHighLevel() {
		super();
	}

	public PersonnelHighLevel(String id){
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
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getTreatment() {
		return treatment;
	}

	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}
	
	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
	
	public String getPlaceCode() {
		return placeCode;
	}

	public void setPlaceCode(String placeCode) {
		this.placeCode = placeCode;
	}
	
	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
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
	
	public String getApprovalUnitRelationship() {
		return approvalUnitRelationship;
	}

	public void setApprovalUnitRelationship(String approvalUnitRelationship) {
		this.approvalUnitRelationship = approvalUnitRelationship;
	}
	
	public String getApprovalUnitLevel() {
		return approvalUnitLevel;
	}

	public void setApprovalUnitLevel(String approvalUnitLevel) {
		this.approvalUnitLevel = approvalUnitLevel;
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