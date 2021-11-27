/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 机构部门学习统计Entity
 * @author alan.wu
 * @version 2020-07-28
 */
public class AffairOrganzationStatistics extends DataEntity<AffairOrganzationStatistics> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "单位", type = 0, align = 2, sort = 0)
	private String unit;		// 单位
	private String unitId;		// 单位id

	private String className;		//课程名称

	private String classify;		//课程类别

	private Date beginTime;			//学习开始时间

	private Date endTime;			//学习结束时间
	@ExcelField(title = "用户总数", type = 0, align = 2, sort = 1)
	private String peopleSum;		// 用户总数
	@ExcelField(title = "学习总人数", type = 0, align = 2, sort = 2)
	private String studySum;		// 学习总人数
	@ExcelField(title = "学习率", type = 0, align = 2, sort = 3)
	private String studyRatio;		// 学习率
	@ExcelField(title = "学习时间总数", type = 0, align = 2, sort = 4)
	private String studyTimeSum;		// 学习时间总数
	@ExcelField(title = "参加学习用户平均学习时长", type = 0, align = 2, sort = 5)
	private String studyTimeAvg;		// 参加学习用户平均学习时长
	@ExcelField(title = "全体用户平均学习时长", type = 0, align = 2, sort = 6)
	private String studyTimeAvgAll;		// 全体用户平均学习时长
	@ExcelField(title = "学习总次数", type = 0, align = 2, sort = 7)
	private String studyNumber;		// 学习总次数
	@ExcelField(title = "参加用户平均次数", type = 0, align = 2, sort = 8)
	private String peopleAvg;		// 参加用户平均次数
	@ExcelField(title = "全体用户平均学习次数", type = 0, align = 2, sort = 9)
	private String peopleAvgAll;		// 全体用户平均学习次数
	@ExcelField(title = "课程通过总数", type = 0, align = 2, sort = 10)
	private String coursePassNumber;		// 课程通过总数
	@ExcelField(title = "人均通过课程", type = 0, align = 2, sort = 11)
	private String coursePassPeople;		// 人均通过课程
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	
	public AffairOrganzationStatistics() {
		super();
	}

	public AffairOrganzationStatistics(String id){
		super(id);
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

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getClassify() {
		return classify;
	}

	public void setClassify(String classify) {
		this.classify = classify;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	
	public String getPeopleSum() {
		return peopleSum;
	}

	public void setPeopleSum(String peopleSum) {
		this.peopleSum = peopleSum;
	}
	
	public String getStudySum() {
		return studySum;
	}

	public void setStudySum(String studySum) {
		this.studySum = studySum;
	}
	
	public String getStudyRatio() {
		return studyRatio;
	}

	public void setStudyRatio(String studyRatio) {
		this.studyRatio = studyRatio;
	}
	
	public String getStudyTimeSum() {
		return studyTimeSum;
	}

	public void setStudyTimeSum(String studyTimeSum) {
		this.studyTimeSum = studyTimeSum;
	}
	
	public String getStudyTimeAvg() {
		return studyTimeAvg;
	}

	public void setStudyTimeAvg(String studyTimeAvg) {
		this.studyTimeAvg = studyTimeAvg;
	}
	
	public String getStudyTimeAvgAll() {
		return studyTimeAvgAll;
	}

	public void setStudyTimeAvgAll(String studyTimeAvgAll) {
		this.studyTimeAvgAll = studyTimeAvgAll;
	}
	
	public String getStudyNumber() {
		return studyNumber;
	}

	public void setStudyNumber(String studyNumber) {
		this.studyNumber = studyNumber;
	}
	
	public String getPeopleAvg() {
		return peopleAvg;
	}

	public void setPeopleAvg(String peopleAvg) {
		this.peopleAvg = peopleAvg;
	}
	
	public String getPeopleAvgAll() {
		return peopleAvgAll;
	}

	public void setPeopleAvgAll(String peopleAvgAll) {
		this.peopleAvgAll = peopleAvgAll;
	}
	
	public String getCoursePassNumber() {
		return coursePassNumber;
	}

	public void setCoursePassNumber(String coursePassNumber) {
		this.coursePassNumber = coursePassNumber;
	}
	
	public String getCoursePassPeople() {
		return coursePassPeople;
	}

	public void setCoursePassPeople(String coursePassPeople) {
		this.coursePassPeople = coursePassPeople;
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
	
}