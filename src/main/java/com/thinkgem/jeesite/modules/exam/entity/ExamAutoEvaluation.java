/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.Date;

/**
 * 自动考评Entity
 * @author alan.wu
 * @version 2020-08-24
 */
public class ExamAutoEvaluation extends DataEntity<ExamAutoEvaluation> {
	
	private static final long serialVersionUID = 1L;
	private String type;		// 考评对象类别（1单位，2民警）
	private String evalType;		// 考评类别//考评类别 1 局考处  2 局考局机关  3 处考队所  4处考处机关 5 处领导考评 6中基层领导  7 民警
	private String period;		// 考评周期（1月，2年）
	private String year;		// 年份
	private String month;		// 月份
	private String details;		// 详情
	private String name;		// 姓名
	private String idNumber;		// 身份证号
	private String unit;		// 单位
	private String unitId;		// 单位id
	private String assess;		// 考核部门
	private String assessId;		// 考核部门id
	private String remark;		// 备注
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String model;		// 使用模板
	private String modelId;		// 模板id
	private String option;		// 选择项
	private String optionId;		// 选择项id
	private String score;		// 分值
	private Date time;		// 时间
	private Date happenTime;		// 问题发生时间
	private Date checkTime;		// 检查时间
	private String evaluationId;   // 被考评对象id
	private String evaluation;   // 被考评对象

	/*新加字段 行号*/
	private String rowNum;
	private String fromSys;		//来源说明
	private String flagType;

	//推送奖惩库
	private String fileNo;		// 文号
	private String JcTypeName;  //奖惩名称
	private String pushFrom;    //推送来源
	private String awardParty;  //获奖组织名
	private String awardPartyId;  //获奖组织Id

	public String getFlagType() {
		return flagType;
	}

	public void setFlagType(String flagType) {
		this.flagType = flagType;
	}

	public ExamAutoEvaluation() {
		super();
	}

	public ExamAutoEvaluation(String id){
		super(id);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFromSys() {
		return fromSys;
	}

	public void setFromSys(String fromSys) {
		this.fromSys = fromSys;
	}

	public String getEvalType() {
		return evalType;
	}

	public void setEvalType(String evalType) {
		this.evalType = evalType;
	}
	
	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}
	
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}
	
	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
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
	
	public String getAssess() {
		return assess;
	}

	public void setAssess(String assess) {
		this.assess = assess;
	}
	
	public String getAssessId() {
		return assessId;
	}

	public void setAssessId(String assessId) {
		this.assessId = assessId;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
	
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
	
	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	
	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}
	
	public String getOptionId() {
		return optionId;
	}

	public void setOptionId(String optionId) {
		this.optionId = optionId;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getHappenTime() {
		return happenTime;
	}

	public void setHappenTime(Date happenTime) {
		this.happenTime = happenTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public String getEvaluationId() {
		return evaluationId;
	}

	public void setEvaluationId(String evaluationId) {
		this.evaluationId = evaluationId;
	}

	public String getRowNum() {
		return rowNum;
	}

	public void setRowNum(String rowNum) {
		this.rowNum = rowNum;
	}

	public String getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(String evaluation) {
		this.evaluation = evaluation;
	}

	public String getFileNo() {
		return fileNo;
	}

	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}

	public String getJcTypeName() {
		return JcTypeName;
	}

	public void setJcTypeName(String jcTypeName) {
		JcTypeName = jcTypeName;
	}

	public String getPushFrom() {
		return pushFrom;
	}

	public void setPushFrom(String pushFrom) {
		this.pushFrom = pushFrom;
	}

	public String getAwardParty() {
		return awardParty;
	}

	public void setAwardParty(String awardParty) {
		this.awardParty = awardParty;
	}

	public String getAwardPartyId() {
		return awardPartyId;
	}

	public void setAwardPartyId(String awardPartyId) {
		this.awardPartyId = awardPartyId;
	}
}