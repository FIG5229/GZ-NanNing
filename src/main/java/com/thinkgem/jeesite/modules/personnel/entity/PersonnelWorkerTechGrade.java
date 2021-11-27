/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 工人技术等级信息集Entity
 * @author cecil.li
 * @version 2019-11-13
 */
public class PersonnelWorkerTechGrade extends DataEntity<PersonnelWorkerTechGrade> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 0)
	private String idNumber;		// 身份证号
	@ExcelField(title = "工人技术等级", type = 0, align = 2, sort = 1, dictType="personnel_grjsdj")
	private String grade;		// 工人技术等级
	@ExcelField(title = "评定日期", type = 0, align = 2, sort = 2)
	private Date date;		// 评定日期
	@ExcelField(title = "当前状态", type = 0, align = 2, sort = 3, dictType="personnel_grdqzt")
	private String status;		// 当前状态
	@ExcelField(title = "评定单位", type = 0, align = 2, sort = 4)
	private String unit;		// 评定单位
	@ExcelField(title = "评定单位名称", type = 0, align = 2, sort = 5)
	private String unitName;		// 评定单位名称
	@ExcelField(title = "评定单位代码", type = 0, align = 2, sort = 6)
	private String unitCode;		// 评定单位代码
	@ExcelField(title = "评定依据", type = 0, align = 2, sort = 7)
	private String basis;		// 评定依据
	@ExcelField(title = "技术等级截止日期", type = 0, align = 2, sort = 8)
	private Date endDate;		// 技术等级截止日期
	@ExcelField(title = "技术等级变动类别", type = 0, align = 2, sort = 9, dictType="personnel_jsdjbdlb")
	private String changeType;		// 技术等级变动类别
	@ExcelField(title = "证书编号", type = 0, align = 2, sort = 10)
	private String certificateNo;		// 证书编号
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String personName;  // 姓名

	private Date beginDate;
	private Date finishDate;
	
	public PersonnelWorkerTechGrade() {
		super();
	}

	public PersonnelWorkerTechGrade(String id){
		super(id);
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
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
	
	public String getBasis() {
		return basis;
	}

	public void setBasis(String basis) {
		this.basis = basis;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}
	
	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
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

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}
}