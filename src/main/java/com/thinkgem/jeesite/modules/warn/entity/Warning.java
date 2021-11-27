/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.warn.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.Date;
import java.util.List;

/**
 * 预警信息Entity
 * @author eav.liu
 * @version 2019-11-28
 */
public class Warning extends DataEntity<Warning> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 预警名称
	private String receivePerName;        // 接收人员姓名
	private String receivePerId;        // 接收人员id
	private String repeatCycle;		// 重复周期 0 每天,1 每周, 2 每月, 3 每年, 4 永不
	private String month;	//月
	private String day;		// 日
	private String hour;		// 时
	private String minute;		// 分钟
	private String week;		// 周 周一至周日
	private Date date;		// 重复周期永不对应的时间
	private String remind;		// 重复提醒
	private String isVoice;		// 是否有声音
	private String voice;		// 铃声
	private String isBubble;		// 是否有气泡
	private String bubbleContent;		// 气泡内容
	private String bubbleDegree;		// 气泡紧急程度
	private String isAlert;		// 是否有弹窗
	private String alertContent;		// 弹窗内容
	private String alertDegree;		// 弹窗紧急程度 1 红 2 黄 3 蓝
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String continueDay;     //持续时间  0：收到后停止

	private List<String> idStrList;	//要做预警处理的id

	private String userId;//数据过滤使用
	
	public Warning() {
		super();
	}

	public Warning(String id){
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
	public String getRemind() {
		return remind;
	}

	public void setRemind(String remind) {
		this.remind = remind;
	}
	
	public String getIsVoice() {
		return isVoice;
	}

	public void setIsVoice(String isVoice) {
		this.isVoice = isVoice;
	}
	
	public String getVoice() {
		return voice;
	}

	public void setVoice(String voice) {
		this.voice = voice;
	}
	
	public String getIsBubble() {
		return isBubble;
	}

	public void setIsBubble(String isBubble) {
		this.isBubble = isBubble;
	}
	
	public String getBubbleContent() {
		return bubbleContent;
	}

	public void setBubbleContent(String bubbleContent) {
		this.bubbleContent = bubbleContent;
	}
	
	public String getBubbleDegree() {
		return bubbleDegree;
	}

	public void setBubbleDegree(String bubbleDegree) {
		this.bubbleDegree = bubbleDegree;
	}
	
	public String getIsAlert() {
		return isAlert;
	}

	public void setIsAlert(String isAlert) {
		this.isAlert = isAlert;
	}
	
	public String getAlertContent() {
		return alertContent;
	}

	public void setAlertContent(String alertContent) {
		this.alertContent = alertContent;
	}
	
	public String getAlertDegree() {
		return alertDegree;
	}

	public void setAlertDegree(String alertDegree) {
		this.alertDegree = alertDegree;
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

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}
	public List<String> getIdStrList() {
		return idStrList;
	}

	public void setIdStrList(List<String> idStrList) {
		this.idStrList = idStrList;
	}

	private Date nextRepeatTime;

	public Date getNextRepeatTime() {
		return nextRepeatTime;
	}

	public void setNextRepeatTime(Date nextRepeatTime) {
		this.nextRepeatTime = nextRepeatTime;
	}

	public String getContinueDay() {
		return continueDay;
	}

	public void setContinueDay(String continueDay) {
		this.continueDay = continueDay;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}