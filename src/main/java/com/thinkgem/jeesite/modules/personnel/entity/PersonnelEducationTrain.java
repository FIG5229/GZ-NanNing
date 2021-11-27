/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 教育培训（进修）信息管理Entity
 * @author cecil.li
 * @version 2019-11-09
 */
public class PersonnelEducationTrain extends DataEntity<PersonnelEducationTrain> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "培训班名称", type = 0, align = 2, sort = 0)
	private String className;		// 培训班名称
	@ExcelField(title = "培训类别", type = 0, align = 2, sort = 1, dictType="personnel_pxtype")
	private String type;		// 培训类别
	@ExcelField(title = "培训完成情况", type = 0, align = 2, sort = 2)
	private String situation;		// 培训完成情况
	@ExcelField(title = "培训时所在单位及职务", type = 0, align = 2, sort = 3)
	private String unitJob;		// 培训时所在单位及职务
	@ExcelField(title = "培训机构名称", type = 0, align = 2, sort = 4)
	private String organizationName;		// 培训机构名称
	@ExcelField(title = "培训地点", type = 0, align = 2, sort = 5)
	private String place;		// 培训地点
	@ExcelField(title = "主办单位名称", type = 0, align = 2, sort = 6)
	private String sponsorName;		// 主办单位名称
	@ExcelField(title = "主办单位代码", type = 0, align = 2, sort = 7)
	private String sponsorCode;		// 主办单位代码
	@ExcelField(title = "主办单位级别", type = 0, align = 2, sort = 8, dictType="personnel_level")
	private String sponsorLevel;		// 主办单位级别
	@ExcelField(title = "起始日期", type = 0, align = 2, sort = 9)
	private Date startDate;		// 起始日期
	@ExcelField(title = "结束日期", type = 0, align = 2, sort = 10)
	private Date endDate;		// 结束日期
	@ExcelField(title = "培训离岗状态", type = 0, align = 2, sort = 11, dictType="personnel_pxlgtype")
	private String leaveStatus;		// 培训离岗状态
	@ExcelField(title = "培训证书编号", type = 0, align = 2, sort = 12)
	private String certificateNumber;		// 培训证书编号
	@ExcelField(title = "免训原因", type = 0, align = 2, sort = 13)
	private String freeReason;		// 免训原因
	@ExcelField(title = "免训标识", type = 0, align = 2, sort = 14)
	private String freeIdentification;		// 免训标识
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 15)
	private String idNumber;		// 身份证号
	private String personName;  // 姓名


	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号



	private Date beginDate;
	private Date finishDate;
	
	public PersonnelEducationTrain() {
		super();
	}

	public PersonnelEducationTrain(String id){
		super(id);
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getSituation() {
		return situation;
	}

	public void setSituation(String situation) {
		this.situation = situation;
	}
	
	public String getUnitJob() {
		return unitJob;
	}

	public void setUnitJob(String unitJob) {
		this.unitJob = unitJob;
	}
	
	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	
	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}
	
	public String getSponsorName() {
		return sponsorName;
	}

	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
	}
	
	public String getSponsorCode() {
		return sponsorCode;
	}

	public void setSponsorCode(String sponsorCode) {
		this.sponsorCode = sponsorCode;
	}
	
	public String getSponsorLevel() {
		return sponsorLevel;
	}

	public void setSponsorLevel(String sponsorLevel) {
		this.sponsorLevel = sponsorLevel;
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
	
	public String getLeaveStatus() {
		return leaveStatus;
	}

	public void setLeaveStatus(String leaveStatus) {
		this.leaveStatus = leaveStatus;
	}
	
	public String getCertificateNumber() {
		return certificateNumber;
	}

	public void setCertificateNumber(String certificateNumber) {
		this.certificateNumber = certificateNumber;
	}
	
	public String getFreeReason() {
		return freeReason;
	}

	public void setFreeReason(String freeReason) {
		this.freeReason = freeReason;
	}
	
	public String getFreeIdentification() {
		return freeIdentification;
	}

	public void setFreeIdentification(String freeIdentification) {
		this.freeIdentification = freeIdentification;
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