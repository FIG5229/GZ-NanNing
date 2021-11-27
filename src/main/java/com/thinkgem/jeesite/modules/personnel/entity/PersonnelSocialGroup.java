/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 社会团体任职信息集Entity
 * @author cecil.li
 * @version 2019-11-11
 */
public class PersonnelSocialGroup extends DataEntity<PersonnelSocialGroup> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 0)
	private String name;		// 名称
	@ExcelField(title = "挂靠单位名称", type = 0, align = 2, sort = 1)
	private String unitName;		// 挂靠单位名称
	@ExcelField(title = "挂靠单位代码", type = 0, align = 2, sort = 2)
	private String unitCode;		// 挂靠单位代码
	@ExcelField(title = "代码", type = 0, align = 2, sort = 3)
	private String code;		// 代码
	@ExcelField(title = "所在政区", type = 0, align = 2, sort = 4)
	private String area;		// 所在政区
	@ExcelField(title = "层次", type = 0, align = 2, sort = 5)
	private String level;		// 层次
	@ExcelField(title = "性质类别", type = 0, align = 2, sort = 6, dictType="personnel_xzlb")
	private String type;		// 性质类别
	@ExcelField(title = "所属行业", type = 0, align = 2, sort = 7, dictType="personnel_sshy")
	private String industry;		// 所属行业
	@ExcelField(title = "统计标识", type = 0, align = 2, sort = 8)
	private String identification;		// 统计标识
	@ExcelField(title = "社会团体职务名称", type = 0, align = 2, sort = 9)
	private String jobName;		// 社会团体职务名称
	@ExcelField(title = "任职日期", type = 0, align = 2, sort = 10)
	private Date workDate;		// 任职日期
	@ExcelField(title = "任职状态", type = 0, align = 2, sort = 11, dictType="personnel_rztype")
	private String status;		// 任职状态
	@ExcelField(title = "多职务主次序号", type = 0, align = 2, sort = 12)
	private Integer sequenceNo;		// 多职务主次序号
	@ExcelField(title = "集体内排列顺序", type = 0, align = 2, sort = 13)
	private String sort;		// 集体内排列顺序
	@ExcelField(title = "主管工作", type = 0, align = 2, sort = 14)
	private String majorJob;		// 主管工作
	@ExcelField(title = "批准机关名称", type = 0, align = 2, sort = 15)
	private String approvalOrgName;		// 批准机关名称
	@ExcelField(title = "批准机关代码", type = 0, align = 2, sort = 16)
	private String approvalOrgCode;		// 批准机关代码
	@ExcelField(title = "连续任该职起始日期", type = 0, align = 2, sort = 17)
	private Date startDate;		// 连续任该职起始日期
	@ExcelField(title = "免职日期", type = 0, align = 2, sort = 18)
	private Date cancelDate;		// 免职日期
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 19)
	private String idNumber;		// 身份证号
	private String personName;  // 姓名


	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private Date beginDate;
	private Date finishDate;
	
	public PersonnelSocialGroup() {
		super();
	}

	public PersonnelSocialGroup(String id){
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
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}
	
	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}
	
	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getWorkDate() {
		return workDate;
	}

	public void setWorkDate(Date workDate) {
		this.workDate = workDate;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public Integer getSequenceNo() {
		return sequenceNo;
	}

	public void setSequenceNo(Integer sequenceNo) {
		this.sequenceNo = sequenceNo;
	}
	
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
	public String getMajorJob() {
		return majorJob;
	}

	public void setMajorJob(String majorJob) {
		this.majorJob = majorJob;
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
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
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