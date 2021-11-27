/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 奖励信息集Entity
 * @author cecil.li
 * @version 2019-11-09
 */
public class PersonnelReward extends DataEntity<PersonnelReward> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "奖励名称代码", type = 0, align = 2, sort = 0)
	private String nameCode;		// 奖励名称代码
	@ExcelField(title = "批准文件文号", type = 0, align = 2, sort = 1)
	private String fileNo;		// 批准文件文号
	@ExcelField(title = "批准文件名称", type = 0, align = 2, sort = 2)
	private String fileName;		// 批准文件名称
	@ExcelField(title = "奖励名称", type = 0, align = 2, sort = 3)
	private String name;		// 奖励名称
	@ExcelField(title = "受奖励时职务", type = 0, align = 2, sort = 4)
	private String job;		// 受奖励时职务
	@ExcelField(title = "受奖励时职务层次", type = 0, align = 2, sort = 5)
	private String jobLevel;		// 受奖励时职务层次
	@ExcelField(title = "荣誉称号级别", type = 0, align = 2, sort = 6, dictType="personnel_rychjb")
	private String designationLevel;		// 荣誉称号级别
	@ExcelField(title = "追授标志", type = 0, align = 2, sort = 7)
	private String sign;		// 追授标志
	@ExcelField(title = "享受待遇类别", type = 0, align = 2, sort = 8, dictType="personnel_dytype")
	private String treatmentType;		// 享受待遇类别
	@ExcelField(title = "奖励原因", type = 0, align = 2, sort = 9)
	private String rewardReason;		// 奖励原因
	@ExcelField(title = "批准机关名称", type = 0, align = 2, sort = 10)
	private String approvalOrgName;		// 批准机关名称
	@ExcelField(title = "批准机关代码", type = 0, align = 2, sort = 11)
	private String approvalOrgCode;		// 批准机关代码
	@ExcelField(title = "批准机关类别", type = 0, align = 2, sort = 12, dictType="personnel_jgtype")
	private String approcalOrgType;		// 批准机关类别
	@ExcelField(title = "批准机关层次", type = 0, align = 2, sort = 13)
	private String approcalOrgLevel;		// 批准机关层次
	@ExcelField(title = "批准日期", type = 0, align = 2, sort = 14)
	private Date approcalDate;		// 批准日期
	@ExcelField(title = "事迹材料(500字以内)", type = 0, align = 2, sort = 15)
	private String achievement;		// 事迹材料(500字以内)
	@ExcelField(title = "撤销标识", type = 0, align = 2, sort = 16)
	private String cancelIdentification;		// 撤销标识
	@ExcelField(title = "撤销日期", type = 0, align = 2, sort = 17)
	private Date cancelDate;		// 撤销日期
	@ExcelField(title = "撤销原因", type = 0, align = 2, sort = 18)
	private String cancelReason;		// 撤销原因
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 19)
	private String idNumber;		// 身份证号
	private String personName;  // 姓名


	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	
	public PersonnelReward() {
		super();
	}

	public PersonnelReward(String id){
		super(id);
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public String getNameCode() {
		return nameCode;
	}

	public void setNameCode(String nameCode) {
		this.nameCode = nameCode;
	}
	
	public String getFileNo() {
		return fileNo;
	}

	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}
	
	public String getJobLevel() {
		return jobLevel;
	}

	public void setJobLevel(String jobLevel) {
		this.jobLevel = jobLevel;
	}
	
	public String getDesignationLevel() {
		return designationLevel;
	}

	public void setDesignationLevel(String designationLevel) {
		this.designationLevel = designationLevel;
	}
	
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	
	public String getTreatmentType() {
		return treatmentType;
	}

	public void setTreatmentType(String treatmentType) {
		this.treatmentType = treatmentType;
	}
	
	public String getRewardReason() {
		return rewardReason;
	}

	public void setRewardReason(String rewardReason) {
		this.rewardReason = rewardReason;
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
	
	public String getApprocalOrgType() {
		return approcalOrgType;
	}

	public void setApprocalOrgType(String approcalOrgType) {
		this.approcalOrgType = approcalOrgType;
	}
	
	public String getApprocalOrgLevel() {
		return approcalOrgLevel;
	}

	public void setApprocalOrgLevel(String approcalOrgLevel) {
		this.approcalOrgLevel = approcalOrgLevel;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getApprocalDate() {
		return approcalDate;
	}

	public void setApprocalDate(Date approcalDate) {
		this.approcalDate = approcalDate;
	}
	
	public String getAchievement() {
		return achievement;
	}

	public void setAchievement(String achievement) {
		this.achievement = achievement;
	}
	
	public String getCancelIdentification() {
		return cancelIdentification;
	}

	public void setCancelIdentification(String cancelIdentification) {
		this.cancelIdentification = cancelIdentification;
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

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}
}