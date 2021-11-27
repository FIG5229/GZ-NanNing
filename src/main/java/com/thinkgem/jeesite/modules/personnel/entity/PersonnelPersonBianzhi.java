/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 人员编制信息集Entity
 * @author cecil.li
 * @version 2019-11-12
 */
public class PersonnelPersonBianzhi extends DataEntity<PersonnelPersonBianzhi> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 0)
	private String idNumber;		// 身份证号
	private String personnelName;		// 姓名
	@ExcelField(title = "使用编制种类", type = 0, align = 2, sort = 1, dictType="personnel_bztype")
	private String type;		// 使用编制种类
	@ExcelField(title = "入编日期", type = 0, align = 2, sort = 2)
	private Date intoDate;		// 入编日期
	@ExcelField(title = "入编前身份类别", type = 0, align = 2, sort = 3, dictType="personnel_shenfen")
	private String preIdentityType;		// 入编前身份类别
	@ExcelField(title = "入编前单位", type = 0, align = 2, sort = 4)
	private String preUnit;		// 入编前单位
	@ExcelField(title = "入编方式", type = 0, align = 2, sort = 5, dictType="personnel_rubian")
	private String method;		// 入编方式
	@ExcelField(title = "入编批准单位", type = 0, align = 2, sort = 6)
	private String intoApprovalUnit;		// 入编批准单位
	@ExcelField(title = "出编日期", type = 0, align = 2, sort = 7)
	private Date outDate;		// 出编日期
	@ExcelField(title = "出编原因", type = 0, align = 2, sort = 8)
	private String outReason;		// 出编原因
	@ExcelField(title = "出编批准单位", type = 0, align = 2, sort = 9)
	private String outApprovalUnit;		// 出编批准单位
	@ExcelField(title = "经费来源种类", type = 0, align = 2, sort = 10, dictType="personnel_jingfei")
	private String fundsType;		// 经费来源种类
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private Date startDate;
	private Date endDate;

	public String getPersonnelName() {
		return personnelName;
	}

	public void setPersonnelName(String personnelName) {
		this.personnelName = personnelName;
	}

	public PersonnelPersonBianzhi() {
		super();
	}

	public PersonnelPersonBianzhi(String id){
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
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getIntoDate() {
		return intoDate;
	}

	public void setIntoDate(Date intoDate) {
		this.intoDate = intoDate;
	}
	
	public String getPreIdentityType() {
		return preIdentityType;
	}

	public void setPreIdentityType(String preIdentityType) {
		this.preIdentityType = preIdentityType;
	}
	
	public String getPreUnit() {
		return preUnit;
	}

	public void setPreUnit(String preUnit) {
		this.preUnit = preUnit;
	}
	
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
	public String getIntoApprovalUnit() {
		return intoApprovalUnit;
	}

	public void setIntoApprovalUnit(String intoApprovalUnit) {
		this.intoApprovalUnit = intoApprovalUnit;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getOutDate() {
		return outDate;
	}

	public void setOutDate(Date outDate) {
		this.outDate = outDate;
	}
	
	public String getOutReason() {
		return outReason;
	}

	public void setOutReason(String outReason) {
		this.outReason = outReason;
	}
	
	public String getOutApprovalUnit() {
		return outApprovalUnit;
	}

	public void setOutApprovalUnit(String outApprovalUnit) {
		this.outApprovalUnit = outApprovalUnit;
	}
	
	public String getFundsType() {
		return fundsType;
	}

	public void setFundsType(String fundsType) {
		this.fundsType = fundsType;
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
}