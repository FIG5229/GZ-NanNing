/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 警衔信息管理Entity
 * @author cecil.li
 * @version 2019-11-09
 */
public class PersonnelPoliceRank extends DataEntity<PersonnelPoliceRank> {
	
	private static final long serialVersionUID = 1L;
	/*停止使用，使用policeRankLevel代替*/
//	@ExcelField(title = "警衔名称", type = 0, align = 2, sort = 0)
	private String name;		// 警衔名称
	@ExcelField(title = "警衔类型", type = 0, align = 2, sort = 1, dictType="personnel_jxtype")
	private String type;		// 警衔类型
	@ExcelField(title = "衔称变动原因", type = 0, align = 2, sort = 2, dictType = "dict_change_reason")
	private String changeReason;		// 衔称变动原因
	@ExcelField(title = "起算日期", type = 0, align = 2, sort = 3)
	private Date startDate;		// 起算日期
	@ExcelField(title = "终止日期", type = 0, align = 2, sort = 4)
	private Date endDate;		// 终止日期
	@ExcelField(title = "授衔日期", type = 0, align = 2, sort = 5)
	private Date awardTitleDate;		// 授衔日期
	@ExcelField(title = "授衔批准单位名称", type = 0, align = 2, sort = 6)
	private String approvalUnitName;		// 授衔批准单位名称
	@ExcelField(title = "授衔令号", type = 0, align = 2, sort = 7)
	private String approvalNumber;		// 授衔令号
	@ExcelField(title = "授衔批准单位代码", type = 0, align = 2, sort = 8)
	private String approvalUnitCode;		// 授衔批准单位代码
	@ExcelField(title = "衔称状态", type = 0, align = 2, sort = 9, dictType="personnel_xctype")
	private String status;		// 衔称状态
	@ExcelField(title = "授衔来源", type = 0, align = 2, sort = 10, dictType = "dict_rank_source")
	private String source;		// 授衔来源
	@ExcelField(title = "警衔取消原因", type = 0, align = 2, sort = 11, dictType = "dict_cancel_reason")
	private String cancelReason;		// 警衔取消原因
	@ExcelField(title = "警衔保留原因", type = 0, align = 2, sort = 12, dictType = "dict_retain_reason")
	private String retainReason;		// 警衔保留原因
	@ExcelField(title = "警衔不保留原因", type = 0, align = 2, sort = 13, dictType = "dict_noretain_reason")
	private String noretainReason;		// 警衔不保留原因
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 14)
	private String idNumber;  // 身份证号
	private String personName;  // 姓名

	/*警衔测算使用 关联人员表字段*/
	private Date publicSecurityDate;

	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号


	private Date beginDate;
	private Date finishDate;
	private Date endBeginDate;
	private Date endFinishDate;
	private Date sxStartDate;
	private Date sxEndDate;

	//新加字段
	@ExcelField(title = "警衔名称", type = 0, align = 2, sort = 0,dictType = "police_rank_level")
	private String policeRankLevel;	//警衔级别

	//警衔测算类型
	private String calculateType;
	/*警衔统计类型*/
	private String analysisType;


	private String unit;		//单位
	private String unitId;		//单位id
	private String peopleName;		//姓名
	private String sex;				//性别
	private Date birthdayTime;		//出生时间
	private Date workTime;			//参加工作时间
	private String xuezhi;			//学制
	private String xuezhiYear;		//学制年限
	private String nowRank;			//现任警衔

	private String jobAbbreviation; //职务
	private String jobLevel;//职务层次
	private Date jobStartDate;//任职日期

	/*人员基本信息表状态*/
	private String baseStatus;

	/*统计添加字段 前端使用*/
	private Integer years;

	/*统计分析使用*/
	private String label;

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

	public String getPeopleName() {
		return peopleName;
	}

	public void setPeopleName(String peopleName) {
		this.peopleName = peopleName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getBirthdayTime() {
		return birthdayTime;
	}

	public void setBirthdayTime(Date birthdayTime) {
		this.birthdayTime = birthdayTime;
	}
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getWorkTime() {
		return workTime;
	}

	public void setWorkTime(Date workTime) {
		this.workTime = workTime;
	}

	public String getXuezhi() {
		return xuezhi;
	}

	public void setXuezhi(String xuezhi) {
		this.xuezhi = xuezhi;
	}

	public String getXuezhiYear() {
		return xuezhiYear;
	}

	public void setXuezhiYear(String xuezhiYear) {
		this.xuezhiYear = xuezhiYear;
	}

	public String getNowRank() {
		return nowRank;
	}

	public void setNowRank(String nowRank) {
		this.nowRank = nowRank;
	}

	public PersonnelPoliceRank() {
		super();
	}

	public PersonnelPoliceRank(String id){
		super(id);
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public String getChangeReason() {
		return changeReason;
	}

	public void setChangeReason(String changeReason) {
		this.changeReason = changeReason;
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
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getAwardTitleDate() {
		return awardTitleDate;
	}

	public void setAwardTitleDate(Date awardTitleDate) {
		this.awardTitleDate = awardTitleDate;
	}
	
	public String getApprovalUnitName() {
		return approvalUnitName;
	}

	public void setApprovalUnitName(String approvalUnitName) {
		this.approvalUnitName = approvalUnitName;
	}
	
	public String getApprovalNumber() {
		return approvalNumber;
	}

	public void setApprovalNumber(String approvalNumber) {
		this.approvalNumber = approvalNumber;
	}
	
	public String getApprovalUnitCode() {
		return approvalUnitCode;
	}

	public void setApprovalUnitCode(String approvalUnitCode) {
		this.approvalUnitCode = approvalUnitCode;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
	
	public String getRetainReason() {
		return retainReason;
	}

	public void setRetainReason(String retainReason) {
		this.retainReason = retainReason;
	}
	
	public String getNoretainReason() {
		return noretainReason;
	}

	public void setNoretainReason(String noretainReason) {
		this.noretainReason = noretainReason;
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

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEndBeginDate() {
		return endBeginDate;
	}

	public void setEndBeginDate(Date endBeginDate) {
		this.endBeginDate = endBeginDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEndFinishDate() {
		return endFinishDate;
	}

	public void setEndFinishDate(Date endFinishDate) {
		this.endFinishDate = endFinishDate;
	}

	public Date getSxStartDate() {
		return sxStartDate;
	}

	public void setSxStartDate(Date sxStartDate) {
		this.sxStartDate = sxStartDate;
	}

	public Date getSxEndDate() {
		return sxEndDate;
	}

	public void setSxEndDate(Date sxEndDate) {
		this.sxEndDate = sxEndDate;
	}

	public String getCalculateType() {
		return calculateType;
	}

	public void setCalculateType(String calculateType) {
		this.calculateType = calculateType;
	}

	public String getPoliceRankLevel() {
		return policeRankLevel;
	}

	public void setPoliceRankLevel(String policeRankLevel) {
		this.policeRankLevel = policeRankLevel;
	}

	public String getJobAbbreviation() {
		return jobAbbreviation;
	}

	public void setJobAbbreviation(String jobAbbreviation) {
		this.jobAbbreviation = jobAbbreviation;
	}

	public String getJobLevel() {
		return jobLevel;
	}

	public void setJobLevel(String jobLevel) {
		this.jobLevel = jobLevel;
	}

	public Date getJobStartDate() {
		return jobStartDate;
	}

	public void setJobStartDate(Date jobStartDate) {
		this.jobStartDate = jobStartDate;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getAnalysisType() {
		return analysisType;
	}

	public void setAnalysisType(String analysisType) {
		this.analysisType = analysisType;
	}

	public Integer getYears() {
		return years;
	}

	public void setYears(Integer years) {
		this.years = years;
	}

	public Date getPublicSecurityDate() {
		return publicSecurityDate;
	}

	public void setPublicSecurityDate(Date publicSecurityDate) {
		this.publicSecurityDate = publicSecurityDate;
	}

	public String getBaseStatus() {
		return baseStatus;
	}

	public void setBaseStatus(String baseStatus) {
		this.baseStatus = baseStatus;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}