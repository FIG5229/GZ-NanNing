/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 民警课程学习统计Entity
 * @author alan.wu
 * @version 2020-07-17
 */
public class AffairPoliceStatistics extends DataEntity<AffairPoliceStatistics> {
	
	private static final long serialVersionUID = 1L;

	@ExcelField(title = "用户名", type = 0, align = 2, sort = 0)
	private String nickName;		// 用户名
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 1)
	private String name;		// 姓名
	private String age;			//年龄
	private String sex;			//性别
	@ExcelField(title = "警号", type = 0, align = 2, sort = 2)
	private String alarm;		// 警号
	private String policeClassify;		//警种
	@ExcelField(title = "主办部门", type = 0, align = 2, sort = 3)
	private String unit;		// 主办部门
	private String unitId;		// 部门id
	@ExcelField(title = "学习途径", type = 0, align = 2, sort = 4)
	private String way;		// 学习途径
	@ExcelField(title = "项目名称", type = 0, align = 2, sort = 5)
	private String projectName;		// 项目名称
	@ExcelField(title = "课程编号", type = 0, align = 2, sort = 6)
	private String classNumber;		// 课程编号
	@ExcelField(title = "课程名称", type = 0, align = 2, sort = 7)
	private String className;		// 课程名称
	@ExcelField(title = "课程类别", type = 0, align = 2, sort = 8)
	private String classify;		// 课程类别
	@ExcelField(title = "学时", type = 0, align = 2, sort = 9)
	private String learnTime;		// 学时
	@ExcelField(title = "播放时长", type = 0, align = 2, sort = 10)
	private String longTime;		// 播放时长
	@ExcelField(title = "第一次学习时间", type = 0, align = 2, sort = 11)
	private Date firstTime;		// 第一次学习时间
	@ExcelField(title = "最后一次学习时间", type = 0, align = 2, sort = 12)
	private Date lastTime;		// 最后一次学习时间
	@ExcelField(title = "学习进度", type = 0, align = 2, sort = 13)
	private String schedule;		// 学习进度
	@ExcelField(title = "学习时长", type = 0, align = 2, sort = 14)
	private String learnTimeLong;		// 学习时长
	@ExcelField(title = "是否通过", type = 0, align = 2, sort = 15)
	private String isPass;		// 是否通过
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
	public AffairPoliceStatistics() {
		super();
	}

	public String getPoliceClassify() {
		return policeClassify;
	}

	public void setPoliceClassify(String policeClassify) {
		this.policeClassify = policeClassify;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public AffairPoliceStatistics(String id){
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
	
	public String getWay() {
		return way;
	}

	public void setWay(String way) {
		this.way = way;
	}
	
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
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
	
	public String getLearnTime() {
		return learnTime;
	}

	public void setLearnTime(String learnTime) {
		this.learnTime = learnTime;
	}
	
	public String getLongTime() {
		return longTime;
	}

	public void setLongTime(String longTime) {
		this.longTime = longTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getFirstTime() {
		return firstTime;
	}

	public void setFirstTime(Date firstTime) {
		this.firstTime = firstTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	
	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}
	
	public String getLearnTimeLong() {
		return learnTimeLong;
	}

	public void setLearnTimeLong(String learnTimeLong) {
		this.learnTimeLong = learnTimeLong;
	}
	
	public String getIsPass() {
		return isPass;
	}

	public void setIsPass(String isPass) {
		this.isPass = isPass;
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
	
	public String getClassNumber() {
		return classNumber;
	}

	public void setClassNumber(String classNumber) {
		this.classNumber = classNumber;
	}
	
}