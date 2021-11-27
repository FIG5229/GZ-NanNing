/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 公务用枪持枪证信息集Entity
 * @author cecil.li
 * @version 2019-11-13
 */
public class PersonnelUseGun extends DataEntity<PersonnelUseGun> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 0)
	private String idNumber;		// 身份证号
	private String personnelName;		// 姓名
	@ExcelField(title = "有效期限", type = 0, align = 2, sort = 2)
	private String validPeriod;		// 有效期限
	@ExcelField(title = "证件有效状态", type = 0, align = 2, sort = 3, dictType="personnel_zjyxzt")
	private String status;		// 证件有效状态
	@ExcelField(title = "发证机关", type = 0, align = 2, sort = 4)
	private String issueOrg;		// 发证机关
	@ExcelField(title = "发证日期", type = 0, align = 2, sort = 5)
	private Date issueDate;		// 发证日期
	@ExcelField(title = "换证日期", type = 0, align = 2, sort = 6)
	private Date replaceDate;		// 换证日期
	@ExcelField(title = "记录序号", type = 0, align = 2, sort = 7)
	private String sequenceNo;		// 记录序号
	@ExcelField(title = "使用枪支情况", type = 0, align = 2, sort = 8)
	private String situation;		// 使用枪支情况
	@ExcelField(title = "持枪证说明", type = 0, align = 2, sort = 9)
	private String explain;		// 持枪证说明
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	@ExcelField(title = "证件号码", type = 0, align = 2, sort = 1)
	private String zjNumber;

	private Date startDate;
	private Date endDate;

	public String getPersonnelName() {
		return personnelName;
	}

	public void setPersonnelName(String personnelName) {
		this.personnelName = personnelName;
	}

	public PersonnelUseGun() {
		super();
	}

	public PersonnelUseGun(String id){
		super(id);
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public String getValidPeriod() {
		return validPeriod;
	}

	public void setValidPeriod(String validPeriod) {
		this.validPeriod = validPeriod;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getReplaceDate() {
		return replaceDate;
	}

	public void setReplaceDate(Date replaceDate) {
		this.replaceDate = replaceDate;
	}
	
	public String getSequenceNo() {
		return sequenceNo;
	}

	public void setSequenceNo(String sequenceNo) {
		this.sequenceNo = sequenceNo;
	}
	
	public String getSituation() {
		return situation;
	}

	public void setSituation(String situation) {
		this.situation = situation;
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

	public String getIssueOrg() {
		return issueOrg;
	}

	public void setIssueOrg(String issueOrg) {
		this.issueOrg = issueOrg;
	}

	public String getZjNumber() {
		return zjNumber;
	}

	public void setZjNumber(String zjNumber) {
		this.zjNumber = zjNumber;
	}
}