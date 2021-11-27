/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 党员信息集Entity
 * @author cecil.li
 * @version 2019-11-13
 */
public class PersonnelPartyMember extends DataEntity<PersonnelPartyMember> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 0)
	private String idNumber;		// 身份证号
	private String personnelName;		// 姓名
	@ExcelField(title = "支部大会通过日期", type = 0, align = 2, sort = 1)
	private Date passDate;		// 支部大会通过日期
	@ExcelField(title = "预备党员批准转正日期", type = 0, align = 2, sort = 2)
	private Date formalDate;		// 预备党员批准转正日期
	@ExcelField(title = "入党介绍人", type = 0, align = 2, sort = 3)
	private String introducer;		// 入党介绍人
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

	public PersonnelPartyMember() {
		super();
	}

	public PersonnelPartyMember(String id){
		super(id);
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getPassDate() {
		return passDate;
	}

	public void setPassDate(Date passDate) {
		this.passDate = passDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getFormalDate() {
		return formalDate;
	}

	public void setFormalDate(Date formalDate) {
		this.formalDate = formalDate;
	}
	
	public String getIntroducer() {
		return introducer;
	}

	public void setIntroducer(String introducer) {
		this.introducer = introducer;
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