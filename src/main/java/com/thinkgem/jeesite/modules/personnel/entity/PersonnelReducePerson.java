/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 减员信息集Entity
 * @author cecil.li
 * @version 2019-11-12
 */
public class PersonnelReducePerson extends DataEntity<PersonnelReducePerson> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 0)
	private String idNumber;		// 身份证号
	private String personnelName;		// 姓名
	@ExcelField(title = "离开本单位类别", type = 0, align = 2, sort = 1)
	private String oldUnitType;		// 离开本单位类别
	@ExcelField(title = "离开本单位日期", type = 0, align = 2, sort = 2)
	private Date oldUnitDate;		// 离开本单位日期
	@ExcelField(title = "调往单位名称", type = 0, align = 2, sort = 3)
	private String toUnitName;		// 调往单位名称
	@ExcelField(title = "调往单位代码", type = 0, align = 2, sort = 4)
	private String toUnitCode;		// 调往单位代码
	@ExcelField(title = "调往单位所在政区", type = 0, align = 2, sort = 5)
	private String toUnitArea;		// 调往单位所在政区
	@ExcelField(title = "调往单位隶属关系", type = 0, align = 2, sort = 6)
	private String toUnitRelationship;		// 调往单位隶属关系
	@ExcelField(title = "调往单位级别", type = 0, align = 2, sort = 7)
	private String toUnitLevel;		// 调往单位级别
	@ExcelField(title = "调往单位性质类别", type = 0, align = 2, sort = 8)
	private String toUnitType;		// 调往单位性质类别
	@ExcelField(title = "调往单位所属行业", type = 0, align = 2, sort = 9)
	private String toUnitIndustry;		// 调往单位所属行业
	@ExcelField(title = "调动原因", type = 0, align = 2, sort = 10)
	private String reason;		// 调动原因
	@ExcelField(title = "文号", type = 0, align = 2, sort = 11)
	private String fileNo;		// 文号
	@ExcelField(title = "调出备注", type = 0, align = 2, sort = 12)
	private String remark;		// 调出备注
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private Date startDate;
	private Date endDate;
	
	public PersonnelReducePerson() {
		super();
	}

	public String getPersonnelName() {
		return personnelName;
	}

	public void setPersonnelName(String personnelName) {
		this.personnelName = personnelName;
	}

	public PersonnelReducePerson(String id){
		super(id);
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getOldUnitDate() {
		return oldUnitDate;
	}

	public void setOldUnitDate(Date oldUnitDate) {
		this.oldUnitDate = oldUnitDate;
	}
	
	public String getToUnitName() {
		return toUnitName;
	}

	public void setToUnitName(String toUnitName) {
		this.toUnitName = toUnitName;
	}
	
	public String getToUnitCode() {
		return toUnitCode;
	}

	public void setToUnitCode(String toUnitCode) {
		this.toUnitCode = toUnitCode;
	}
	
	public String getToUnitArea() {
		return toUnitArea;
	}

	public void setToUnitArea(String toUnitArea) {
		this.toUnitArea = toUnitArea;
	}
	
	public String getToUnitRelationship() {
		return toUnitRelationship;
	}

	public void setToUnitRelationship(String toUnitRelationship) {
		this.toUnitRelationship = toUnitRelationship;
	}
	
	public String getToUnitLevel() {
		return toUnitLevel;
	}

	public void setToUnitLevel(String toUnitLevel) {
		this.toUnitLevel = toUnitLevel;
	}
	
	public String getToUnitType() {
		return toUnitType;
	}

	public void setToUnitType(String toUnitType) {
		this.toUnitType = toUnitType;
	}
	
	public String getToUnitIndustry() {
		return toUnitIndustry;
	}

	public void setToUnitIndustry(String toUnitIndustry) {
		this.toUnitIndustry = toUnitIndustry;
	}
	
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public String getFileNo() {
		return fileNo;
	}

	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
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

	public String getOldUnitType() {
		return oldUnitType;
	}

	public void setOldUnitType(String oldUnitType) {
		this.oldUnitType = oldUnitType;
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