/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 线上考试Entity
 * @author cecil.li
 * @version 2020-12-29
 */
public class AffairOnlineExam extends DataEntity<AffairOnlineExam> {
	
	private static final long serialVersionUID = 1L;
//	@ExcelField(title = "序号", type = 0, align = 2, sort = 0)
	private String number;   // 序号
	@ExcelField(title = "考试名称", type = 0, align = 2, sort = 0)
	private String name;		// 考试名称
	@ExcelField(title = "考试时间", type = 0, align = 2, sort = 1)
	private Date date;		// 考试时间
	@ExcelField(title = "机构名称", type = 0, align = 2, sort = 2)
	private String unit;		// 机构名称
	private String unitId;		// 机构id
	@ExcelField(title = "应考人数", type = 0, align = 2, sort = 3)
	private String numberOfCandidates;		// 应考人数
	@ExcelField(title = "实考人数", type = 0, align = 2, sort = 4)
	private String numberOfActualTest;		// 实考人数
	@ExcelField(title = "出勤率", type = 0, align = 2, sort = 5)
	private String attendance;		// 出勤率
	@ExcelField(title = "及格人数", type = 0, align = 2, sort = 6)
	private String passNumber;		// 及格人数
	@ExcelField(title = "及格率", type = 0, align = 2, sort = 7)
	private String passingRate;		// 及格率
	@ExcelField(title = "最高分", type = 0, align = 2, sort = 8)
	private String highestScore;		// 最高分
	@ExcelField(title = "最低分", type = 0, align = 2, sort = 9)
	private String lowestScore;		// 最低分
	@ExcelField(title = "平均分", type = 0, align = 2, sort = 10)
	private String averageScore;		// 平均分
	@ExcelField(title = "备注", type = 0, align = 2, sort = 11)
	private String remark;		// 备注
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	
	public AffairOnlineExam() {
		super();
	}

	public AffairOnlineExam(String id){
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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
	
	public String getNumberOfCandidates() {
		return numberOfCandidates;
	}

	public void setNumberOfCandidates(String numberOfCandidates) {
		this.numberOfCandidates = numberOfCandidates;
	}
	
	public String getNumberOfActualTest() {
		return numberOfActualTest;
	}

	public void setNumberOfActualTest(String numberOfActualTest) {
		this.numberOfActualTest = numberOfActualTest;
	}
	
	public String getAttendance() {
		return attendance;
	}

	public void setAttendance(String attendance) {
		this.attendance = attendance;
	}
	
	public String getPassNumber() {
		return passNumber;
	}

	public void setPassNumber(String passNumber) {
		this.passNumber = passNumber;
	}
	
	public String getPassingRate() {
		return passingRate;
	}

	public void setPassingRate(String passingRate) {
		this.passingRate = passingRate;
	}
	
	public String getHighestScore() {
		return highestScore;
	}

	public void setHighestScore(String highestScore) {
		this.highestScore = highestScore;
	}
	
	public String getLowestScore() {
		return lowestScore;
	}

	public void setLowestScore(String lowestScore) {
		this.lowestScore = lowestScore;
	}
	
	public String getAverageScore() {
		return averageScore;
	}

	public void setAverageScore(String averageScore) {
		this.averageScore = averageScore;
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

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
}