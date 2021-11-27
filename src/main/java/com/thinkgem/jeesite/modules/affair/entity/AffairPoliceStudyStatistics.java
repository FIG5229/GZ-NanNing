/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 民警学习统计报表Entity
 * @author kevin.jia
 * @version 2020-08-11
 */
public class AffairPoliceStudyStatistics extends DataEntity<AffairPoliceStudyStatistics> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "用户名", type = 0, align = 2, sort = 1)
	private String nickName;		// 用户名
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 2)
	private String name;		// 姓名
	@ExcelField(title = "警号", type = 0, align = 2, sort = 3)
	private String alarm;		// 警号
	private String unitId;		// 部门id
	@ExcelField(title = "所属机构", type = 0, align = 2, sort = 4)
	private String unitName;		// 所属机构
	@ExcelField(title = "课程通过率", type = 0, align = 2, sort = 9)
	private String coursePassRate;		// 课程通过率
	@ExcelField(title = "学习课程总数", type = 0, align = 2, sort = 5)
	private String courseNum;		// 课程总数
	@ExcelField(title = "通过课程数", type = 0, align = 2, sort = 8)
	private String passCourseNum;		// 通过课程数
	@ExcelField(title = "学习总次数", type = 0, align = 2, sort = 7)
	private String studyTotalNumber;		// 学习总次数
	@ExcelField(title = "学习总时长(小时)", type = 0, align = 2, sort = 6)
	private String studyTotalDate;		// 学习总时长(小时)
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	
	public AffairPoliceStudyStatistics() {
		super();
	}

	public AffairPoliceStudyStatistics(String id){
		super(id);
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getAlarm() {
		return alarm;
	}

	public void setAlarm(String alarm) {
		this.alarm = alarm;
	}
	
	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	
	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	
	public String getCoursePassRate() {
		return coursePassRate;
	}

	public void setCoursePassRate(String coursePassRate) {
		this.coursePassRate = coursePassRate;
	}
	
	public String getCourseNum() {
		return courseNum;
	}

	public void setCourseNum(String courseNum) {
		this.courseNum = courseNum;
	}
	
	public String getPassCourseNum() {
		return passCourseNum;
	}

	public void setPassCourseNum(String passCourseNum) {
		this.passCourseNum = passCourseNum;
	}
	
	public String getStudyTotalNumber() {
		return studyTotalNumber;
	}

	public void setStudyTotalNumber(String studyTotalNumber) {
		this.studyTotalNumber = studyTotalNumber;
	}
	
	public String getStudyTotalDate() {
		return studyTotalDate;
	}

	public void setStudyTotalDate(String studyTotalDate) {
		this.studyTotalDate = studyTotalDate;
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