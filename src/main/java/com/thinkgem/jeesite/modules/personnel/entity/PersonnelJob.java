/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 职务层次信息集Entity
 * @author cecil.li
 * @version 2019-11-11
 */
public class PersonnelJob extends DataEntity<PersonnelJob> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "职务层次", type = 0, align = 2, sort = 0, dictType="personnel_zwcc")
	private String jobLevel;		// 职务层次
	@ExcelField(title = "状态", type = 0, align = 2, sort = 1, dictType="personnel_zwtype")
	private String status;		// 状态
	@ExcelField(title = "起算日期", type = 0, align = 2, sort = 2)
	private Date startDate;		// 起算日期
	@ExcelField(title = "是否高配", type = 0, align = 2, sort = 3, dictType="yes_no")
	private String isHigh;		// 是否高配
	@ExcelField(title = "变动原因", type = 0, align = 2, sort = 4)
	private String reason;		// 变动原因
	@ExcelField(title = "批准文号", type = 0, align = 2, sort = 5)
	private String fileNo;		// 批准文号
	@ExcelField(title = "批准机关名称", type = 0, align = 2, sort = 6)
	private String approvalOrgName;		// 批准机关名称
	@ExcelField(title = "批准机关代码", type = 0, align = 2, sort = 7)
	private String approvalOrgCode;		// 批准机关代码
	@ExcelField(title = "批准日期", type = 0, align = 2, sort = 8)
	private Date approvalDate;		// 批准日期
	@ExcelField(title = "终止日期", type = 0, align = 2, sort = 9)
	private Date endDate;		// 终止日期
	@ExcelField(title = "职务层次备注", type = 0, align = 2, sort = 10)
	private String remark;		// 职务层次备注
	@ExcelField(title = "享受县以下机关职级", type = 0, align = 2, sort = 11, dictType="yes_no")
	private String enjoyJob;		// 享受县以下机关职级
	@ExcelField(title = "享受县以下机关职级批准日期", type = 0, align = 2, sort = 12)
	private Date enjoyDate;		// 享受县以下机关职级批准日期
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 13)
	private String idNumber;		// 身份证号

	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String approvalOrgNameId;		// 批准机关id

	private String personName;  // 姓名

	private Date beginDate;
	private Date finishDate;

	/*统计分析使用*/
	private String label;
	private String unitId;
	
	public PersonnelJob() {
		super();
	}

	public PersonnelJob(String id){
		super(id);
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public String getJobLevel() {
		return jobLevel;
	}

	public void setJobLevel(String jobLevel) {
		this.jobLevel = jobLevel;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public String getIsHigh() {
		return isHigh;
	}

	public void setIsHigh(String isHigh) {
		this.isHigh = isHigh;
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
	
	public String getApprovalOrgName() {
		return approvalOrgName;
	}

	public void setApprovalOrgName(String approvalOrgName) {
		this.approvalOrgName = approvalOrgName;
	}
	
	public String getApprovalOrgCode() {
		return approvalOrgCode;
	}

	public void setApprovalOrgCode(String approvalOrgCode) {
		this.approvalOrgCode = approvalOrgCode;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getEnjoyJob() {
		return enjoyJob;
	}

	public void setEnjoyJob(String enjoyJob) {
		this.enjoyJob = enjoyJob;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEnjoyDate() {
		return enjoyDate;
	}

	public void setEnjoyDate(Date enjoyDate) {
		this.enjoyDate = enjoyDate;
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
	
	public String getApprovalOrgNameId() {
		return approvalOrgNameId;
	}

	public void setApprovalOrgNameId(String approvalOrgNameId) {
		this.approvalOrgNameId = approvalOrgNameId;
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

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
}