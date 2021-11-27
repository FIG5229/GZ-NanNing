/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 惩戒信息集Entity
 * @author cecil.li
 * @version 2019-11-11
 */
public class PersonnelPunish extends DataEntity<PersonnelPunish> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "惩戒名称", type = 0, align = 2, sort = 0)
	private String name;		// 惩戒名称
	private String personnelName;		// 姓名
	@ExcelField(title = "惩戒类别", type = 0, align = 2, sort = 1, dictType="personnel_cjtype")
	private String type;		// 惩戒类别
	@ExcelField(title = "惩戒代码", type = 0, align = 2, sort = 2)
	private String code;		// 惩戒代码
	@ExcelField(title = "受惩戒时职务及职级", type = 0, align = 2, sort = 3)
	private String jobLevel;		// 受惩戒时职务及职级
	@ExcelField(title = "惩戒事件序号", type = 0, align = 2, sort = 4)
	private Integer caseNo;		// 惩戒事件序号
	@ExcelField(title = "惩戒原因", type = 0, align = 2, sort = 5)
	private String reason;		// 惩戒原因
	@ExcelField(title = "惩戒事由", type = 0, align = 2, sort = 6)
	private String caseReason;		// 惩戒事由
	@ExcelField(title = "批准日期", type = 0, align = 2, sort = 7)
	private Date approvalDate;		// 批准日期
	@ExcelField(title = "批准机关名称", type = 0, align = 2, sort = 8)
	private String approvalOrg;		// 批准机关名称
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 9)
	private String approvalOrgCode;		// 批准机关代码
	@ExcelField(title = "批准文件名称及文号", type = 0, align = 2, sort = 10)
	private String fileNo;		// 批准文件名称及文号
	@ExcelField(title = "批准机关类别", type = 0, align = 2, sort = 11, dictType="personnel_jgtype")
	private String approvalOfficeType;		// 批准机关类别
	@ExcelField(title = "是否解除/超过有限期", type = 0, align = 2, sort = 12, dictType="yes_no")
	private String isCancelOver;		// 是否解除/超过有限期
	@ExcelField(title = "解除变更惩戒日期", type = 0, align = 2, sort = 13)
	private Date cancelDate;		// 解除变更惩戒日期
	@ExcelField(title = "解除变更惩戒原因", type = 0, align = 2, sort = 14)
	private String cancelReason;		// 解除变更惩戒原因
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 15)
	private String idNumber;		// 身份证号

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

	public PersonnelPunish() {
		super();
	}

	public PersonnelPunish(String id){
		super(id);
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getJobLevel() {
		return jobLevel;
	}

	public void setJobLevel(String jobLevel) {
		this.jobLevel = jobLevel;
	}
	
	public Integer getCaseNo() {
		return caseNo;
	}

	public void setCaseNo(Integer caseNo) {
		this.caseNo = caseNo;
	}
	
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public String getCaseReason() {
		return caseReason;
	}

	public void setCaseReason(String caseReason) {
		this.caseReason = caseReason;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}
	
	public String getApprovalOrg() {
		return approvalOrg;
	}

	public void setApprovalOrg(String approvalOrg) {
		this.approvalOrg = approvalOrg;
	}
	
	public String getApprovalOrgCode() {
		return approvalOrgCode;
	}

	public void setApprovalOrgCode(String approvalOrgCode) {
		this.approvalOrgCode = approvalOrgCode;
	}
	
	public String getFileNo() {
		return fileNo;
	}

	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}
	
	public String getApprovalOfficeType() {
		return approvalOfficeType;
	}

	public void setApprovalOfficeType(String approvalOfficeType) {
		this.approvalOfficeType = approvalOfficeType;
	}
	
	public String getIsCancelOver() {
		return isCancelOver;
	}

	public void setIsCancelOver(String isCancelOver) {
		this.isCancelOver = isCancelOver;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}
	
	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
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