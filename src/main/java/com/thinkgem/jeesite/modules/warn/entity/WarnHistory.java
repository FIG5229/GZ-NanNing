/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.warn.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 预警历史记录Entity
 * @author kevin.jia
 * @version 2020-12-28
 */
public class WarnHistory extends DataEntity<WarnHistory> {
	
	private static final long serialVersionUID = 1L;
	private String warnName;		// 预警名称
	private String warnId;		// 预警id
	private String receivePerName;		// 接受人员姓名
	private String receivePerId;		// 接受部门id
	private String repeatCycle;		// 重复周期
	private Date lastRemindTime;		// 上次提醒时间
	private String remind;		// 重复提醒
	private String alertContent;		// 弹窗内容
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String continueDay;		// 持续时间
	private String isNoRemind;		//是否不再提醒
	private String isFinish; 		//是否已完成
	private String userId;          //数据过滤使用
	private String alertDegree;     //紧急程度
	private String month;	//月
	private String day;		// 日
	private String hour;		// 时
	private String minute;		// 分钟
	private String week;		// 周 周一至周日
	private Date date;		// 重复周期永不对应的时间
	private String warnCreateBy;  //预警创建者id
	public WarnHistory() {
		super();
	}

	public WarnHistory(String id){
		super(id);
	}

	public String getWarnName() {
		return warnName;
	}

	public void setWarnName(String warnName) {
		this.warnName = warnName;
	}

	public String getWarnId() {
		return warnId;
	}

	public void setWarnId(String warnId) {
		this.warnId = warnId;
	}

	public String getReceivePerName() {
		return receivePerName;
	}

	public void setReceivePerName(String receivePerName) {
		this.receivePerName = receivePerName;
	}
	
	public String getReceivePerId() {
		return receivePerId;
	}

	public void setReceivePerId(String receivePerId) {
		this.receivePerId = receivePerId;
	}
	
	public String getRepeatCycle() {
		return repeatCycle;
	}

	public void setRepeatCycle(String repeatCycle) {
		this.repeatCycle = repeatCycle;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLastRemindTime() {
		return lastRemindTime;
	}

	public void setLastRemindTime(Date lastRemindTime) {
		this.lastRemindTime = lastRemindTime;
	}
	
	public String getRemind() {
		return remind;
	}

	public void setRemind(String remind) {
		this.remind = remind;
	}
	
	public String getAlertContent() {
		return alertContent;
	}

	public void setAlertContent(String alertContent) {
		this.alertContent = alertContent;
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
	
	public String getContinueDay() {
		return continueDay;
	}

	public void setContinueDay(String continueDay) {
		this.continueDay = continueDay;
	}

	public String getIsNoRemind() {
		return isNoRemind;
	}

	public void setIsNoRemind(String isNoRemind) {
		this.isNoRemind = isNoRemind;
	}

	public String getIsFinish() {
		return isFinish;
	}

	public void setIsFinish(String isFinish) {
		this.isFinish = isFinish;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAlertDegree() {
		return alertDegree;
	}

	public void setAlertDegree(String alertDegree) {
		this.alertDegree = alertDegree;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getMinute() {
		return minute;
	}

	public void setMinute(String minute) {
		this.minute = minute;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getWarnCreateBy() {
		return warnCreateBy;
	}

	public void setWarnCreateBy(String warnCreateBy) {
		this.warnCreateBy = warnCreateBy;
	}
}