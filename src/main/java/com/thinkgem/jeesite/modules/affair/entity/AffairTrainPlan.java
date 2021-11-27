/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 培训计划报表Entity
 * @author alan.wu
 * @version 2020-07-28
 */
public class AffairTrainPlan extends DataEntity<AffairTrainPlan> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "单位",type = 0, align = 2, sort = 0)
	private String unit;		// 单位
	private String unitId;		// 单位id
	@ExcelField(title = "培训名称",type = 0, align = 2, sort = 1)
	private String name;		// 培训名称
	@ExcelField(title = "培训目的",type = 0, align = 2, sort = 2)
	private String goal;		// 培训目的
	@ExcelField(title = "培训对象",type = 0, align = 2, sort = 3)
	private String target;		// 培训对象
	@ExcelField(title = "培训内容",type = 0, align = 2, sort = 4)
	private String content;		// 培训内容
	private String year;		// 年度
	@ExcelField(title = "培训时间",type = 0, align = 2, sort = 5)
	private Date time;		// 培训时间
	@ExcelField(title = "天数",type = 0, align = 2, sort = 6)
	private String day;		// 天数
	@ExcelField(title = "培训地点",type = 0, align = 2, sort = 7)
	private String place;		// 培训地点
	@ExcelField(title = "参训人数",type = 0, align = 2, sort = 8)
	private String number;		// 参训人数
	@ExcelField(title = "培训费（万元）",type = 0, align = 2, sort = 9)
	private Double trainExpense;		// 培训费（万元）
	@ExcelField(title = "师资费（万元）",type = 0, align = 2, sort = 10)
	private Double teacherExpense;		// 师资费（万元）
	@ExcelField(title = "列支渠道",type = 0, align = 2, sort = 11)
	private String trench;		// 列支渠道
	private String organ;		// 填报机构
	private String organId;		// 填报机构id
	@ExcelField(title = "实施状态",type = 0, align = 2, sort = 12)
	private String state;		// 实施状态
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String userId;
	private String officeId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public AffairTrainPlan() {
		super();
	}

	public AffairTrainPlan(String id){
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

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}
	
	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}
	
	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}
	
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	public Double getTrainExpense() {
		return trainExpense;
	}

	public void setTrainExpense(Double trainExpense) {
		this.trainExpense = trainExpense;
	}
	
	public Double getTeacherExpense() {
		return teacherExpense;
	}

	public void setTeacherExpense(Double teacherExpense) {
		this.teacherExpense = teacherExpense;
	}
	
	public String getTrench() {
		return trench;
	}

	public void setTrench(String trench) {
		this.trench = trench;
	}
	
	public String getOrgan() {
		return organ;
	}

	public void setOrgan(String organ) {
		this.organ = organ;
	}
	
	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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