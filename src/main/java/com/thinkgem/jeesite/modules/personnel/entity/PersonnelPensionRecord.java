/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 抚恤金发放记录信息集Entity
 * @author cecil.li
 * @version 2019-11-13
 */
public class PersonnelPensionRecord extends DataEntity<PersonnelPensionRecord> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 0)
	private String idNumber;		// 身份证号
	@ExcelField(title = "抚恤金发放日期", type = 0, align = 2, sort = 2)
	private Date provideDate;		// 抚恤金发放日期
	@ExcelField(title = "抚恤金审批日期", type = 0, align = 2, sort = 3)
	private Date approvalDate;		// 抚恤金审批日期
	@ExcelField(title = "抚恤金发放对象", type = 0, align = 2, sort = 4)
	private String providePerson;		// 抚恤金发放对象
	@ExcelField(title = "终止发放原因", type = 0, align = 2, sort = 5)
	private String endReason;		// 终止发放原因
	@ExcelField(title = "终止日期", type = 0, align = 2, sort = 6)
	private Date endDate;		// 终止日期
	@ExcelField(title = "抚恤金性质", type = 0, align = 2, sort = 7, dictType="pension_nature")
	private String character;		// 抚恤金性质
	@ExcelField(title = "抚恤金说明", type = 0, align = 2, sort = 8)
	private String explain;		// 抚恤金说明
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	@ExcelField(title = "单位", type = 0, align = 2, sort = 1)
	private String unit;		//单位名称
	private String unitId;		//单位id

	private Date beginDate1;
	private Date finishDate1;
	private Date beginDate;
	private Date finishDate;

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getBeginDate1() {
		return beginDate1;
	}

	public void setBeginDate1(Date beginDate1) {
		this.beginDate1 = beginDate1;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getFinishDate1() {
		return finishDate1;
	}

	public void setFinishDate1(Date finishDate1) {
		this.finishDate1 = finishDate1;
	}


	
	public PersonnelPensionRecord() {
		super();
	}

	public PersonnelPensionRecord(String id){
		super(id);
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getProvideDate() {
		return provideDate;
	}

	public void setProvideDate(Date provideDate) {
		this.provideDate = provideDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}
	
	public String getProvidePerson() {
		return providePerson;
	}

	public void setProvidePerson(String providePerson) {
		this.providePerson = providePerson;
	}
	
	public String getEndReason() {
		return endReason;
	}

	public void setEndReason(String endReason) {
		this.endReason = endReason;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public String getCharacter() {
		return character;
	}

	public void setCharacter(String character) {
		this.character = character;
	}
	
	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
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
}