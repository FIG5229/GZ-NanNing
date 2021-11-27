/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 谈话函询管理Entity
 * @author cecil.li
 * @version 2019-11-08
 */
public class AffairTalkManagement extends DataEntity<AffairTalkManagement> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 0)
	private String name;		// 姓名
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 1)
	private String personalNum;		// 身份证号码
	private String sex;		// 性别
	@ExcelField(title = "警号", type = 0, align = 2, sort = 2)
	private String siren;		// 警号
	@ExcelField(title = "单位", type = 0, align = 2, sort = 3)
	private String unit;		// 单位
	@ExcelField(title = "职务", type = 0, align = 2, sort = 4)
	private String job;      //职务
	@ExcelField(title = "职级", type = 0, align = 2, sort = 5)
	private String jobLevel;            //职级
	@ExcelField(title = "政治面貌", type = 0, align = 2, sort = 6, dictType="political_status")
	private String mianmao;         //政治面貌
	@ExcelField(title = "主要问题", type = 0, align = 2, sort = 7)
	private String problem;		// 主要问题
	private String phoneNum;		// 电话号码
	@ExcelField(title = "主办部门", type = 0, align = 2, sort = 8, dictType="affair_zb_unit")
	private String zbUnit;      //主办部门
	@ExcelField(title = "类型", type = 0, align = 2, sort = 9, dictType="affair_tanhua")
	private String letterCategory;		// 类型
	@ExcelField(title = "谈话人", type = 0, align = 2, sort = 10)
	private String talker;       //谈话人
	@ExcelField(title = "谈话时间", type = 0, align = 2, sort = 11)
	private Date time;		// 谈话时间
	@ExcelField(title = "谈话地点", type = 0, align = 2, sort = 12)
	private String talkPlace;     //谈话地点
	private String unitId;		// 单位id
	private String mainProblem;		// 主要问题
	private String annex;		// 附件
	private String reviewer;		// 审核人
	private String auditOpinion;		// 审核意见
	private String approvalStatus;		// 审核状态
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private Date startDate;
	private Date endDate;

	private String qxUnit;
	private String qxUnitId;
	private Date startYear;   //本年度
	private String otherYear;  //其他年份

	private String dateType;

	private Integer year;

	private String dateStart;

	private String dateEnd;

	private String month;

	private String zbUnitType;

	private String thType;
	
	public AffairTalkManagement() {
		super();
	}

	public AffairTalkManagement(String id){
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getSiren() {
		return siren;
	}

	public void setSiren(String siren) {
		this.siren = siren;
	}

	
	public String getProblem() {
		return problem;
	}

	public void setProblem(String problem) {
		this.problem = problem;
	}
	
	public String getLetterCategory() {
		return letterCategory;
	}

	public void setLetterCategory(String letterCategory) {
		this.letterCategory = letterCategory;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
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
	
	public String getMainProblem() {
		return mainProblem;
	}

	public void setMainProblem(String mainProblem) {
		this.mainProblem = mainProblem;
	}
	
	public String getAnnex() {
		return annex;
	}

	public void setAnnex(String annex) {
		this.annex = annex;
	}
	
	public String getReviewer() {
		return reviewer;
	}

	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}
	
	public String getAuditOpinion() {
		return auditOpinion;
	}

	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}
	
	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
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
	
	public String getPersonalNum() {
		return personalNum;
	}

	public void setPersonalNum(String personalNum) {
		this.personalNum = personalNum;
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

	public String getQxUnit() {
		return qxUnit;
	}

	public void setQxUnit(String qxUnit) {
		this.qxUnit = qxUnit;
	}

	public String getQxUnitId() {
		return qxUnitId;
	}

	public void setQxUnitId(String qxUnitId) {
		this.qxUnitId = qxUnitId;
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

	public String getZbUnit() {
		return zbUnit;
	}

	public void setZbUnit(String zbUnit) {
		this.zbUnit = zbUnit;
	}

	public String getTalker() {
		return talker;
	}

	public void setTalker(String talker) {
		this.talker = talker;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getMianmao() {
		return mianmao;
	}

	public void setMianmao(String mianmao) {
		this.mianmao = mianmao;
	}

	public String getTalkPlace() {
		return talkPlace;
	}

	public void setTalkPlace(String talkPlace) {
		this.talkPlace = talkPlace;
	}

	@JsonFormat(pattern = "yyyy")
	public Date getStartYear() {
		return startYear;
	}

	public void setStartYear(Date startYear) {
		this.startYear = startYear;
	}

	public String getOtherYear() {
		return otherYear;
	}

	public void setOtherYear(String otherYear) {
		this.otherYear = otherYear;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getDateStart() {
		return dateStart;
	}

	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}

	public String getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getZbUnitType() {
		return zbUnitType;
	}

	public void setZbUnitType(String zbUnitType) {
		this.zbUnitType = zbUnitType;
	}

	public String getThType() {
		return thType;
	}

	public void setThType(String thType) {
		this.thType = thType;
	}
}