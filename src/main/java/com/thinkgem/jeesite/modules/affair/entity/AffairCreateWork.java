/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 创建工作Entity
 * @author daniel.liu
 * @version 2020-07-06
 */
public class AffairCreateWork extends DataEntity<AffairCreateWork> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "测评项目", type = 0, align = 2, sort = 0)
	private String title;		// 测评项目
	@ExcelField(title = "层级", type = 0, align = 2, sort = 2, dictType = "evaluation_level")
	private String level;		// 层级
	@ExcelField(title = "年度", type = 0, align = 2, sort = 3)
	private String year;		// 年度
	@ExcelField(title = "测评标准", type = 0, align = 2, sort = 4)
	private String standard;		// 测评标准
	@ExcelField(title = "测评方法", type = 0, align = 2, sort = 5, dictType = "evaluation_method")
	private String method;		// 测评方法
	@ExcelField(title = "测评分值", type = 0, align = 2, sort = 6)
	private String score;		// 分值
	@ExcelField(title = "测评内容", type = 0, align = 2, sort = 7)
	private String content;		// 测评内容
	private String filePath;		// 附件
	/*改为台账  不使用此字段*/
	private String status;		// 签收上报状态
	private Date reportTime;		// 上报时间
	private Date signTime;		// 签收时间
	private String checkMan;		// 审核人
	private String submitMan;		// 提交人
	private String checkId;		// 审核单位id
	private String submitId;		// 提交人id
	private String shOpinion;		// 整改意见
	@ExcelField(title = "开展工作情况", type = 0, align = 2, sort = 8)
	private String workingConditions;		// 开展工作情况
	private String selfRating;		// 自评分
	private String assessmentScore;		// 考核分
	private String createOrgId;		// 创建者机构id
	private String updateOrgId;		// 更新者机构id
	private String beginYear;		// 开始 年度
	private String endYear;		// 结束 年度

	//新加字段
	@ExcelField(title = "单位", type = 0, align = 2, sort = 1)
	private String unit;		//单位
	private String unitId;		//单位Id
	//
	private boolean hasAuth;


	private String classify;		//数据分类标识


	public String getClassify() {
		return classify;
	}

	public void setClassify(String classify) {
		this.classify = classify;
	}
	
	public AffairCreateWork() {
		super();
	}

	public AffairCreateWork(String id){
		super(id);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}
	
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getReportTime() {
		return reportTime;
	}

	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSignTime() {
		return signTime;
	}

	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}
	
	public String getCheckMan() {
		return checkMan;
	}

	public void setCheckMan(String checkMan) {
		this.checkMan = checkMan;
	}
	
	public String getSubmitMan() {
		return submitMan;
	}

	public void setSubmitMan(String submitMan) {
		this.submitMan = submitMan;
	}
	
	public String getCheckId() {
		return checkId;
	}

	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}
	
	public String getSubmitId() {
		return submitId;
	}

	public void setSubmitId(String submitId) {
		this.submitId = submitId;
	}
	
	public String getShOpinion() {
		return shOpinion;
	}

	public void setShOpinion(String shOpinion) {
		this.shOpinion = shOpinion;
	}
	
	public String getWorkingConditions() {
		return workingConditions;
	}

	public void setWorkingConditions(String workingConditions) {
		this.workingConditions = workingConditions;
	}
	
	public String getSelfRating() {
		return selfRating;
	}

	public void setSelfRating(String selfRating) {
		this.selfRating = selfRating;
	}
	
	public String getAssessmentScore() {
		return assessmentScore;
	}

	public void setAssessmentScore(String assessmentScore) {
		this.assessmentScore = assessmentScore;
	}
	
	public String getCreateOrgId() {
		return createOrgId;
	}

	public void setCreateOrgId(String createOrgId) {
		this.createOrgId = createOrgId;
	}
	
	public String getUpdateOrgId() {
		return updateOrgId;
	}

	public void setUpdateOrgId(String updateOrgId) {
		this.updateOrgId = updateOrgId;
	}

	public String getBeginYear() {
		return beginYear;
	}

	public void setBeginYear(String beginYear) {
		this.beginYear = beginYear;
	}

	public String getEndYear() {
		return endYear;
	}

	public void setEndYear(String endYear) {
		this.endYear = endYear;
	}

	public boolean isHasAuth() {
		return hasAuth;
	}

	public void setHasAuth(boolean hasAuth) {
		this.hasAuth = hasAuth;
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
}