/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.warn.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.Date;

/**
 * 预警接收部门Entity
 * @author eav.liu
 * @version 2019-11-28
 */
public class WarnReceive extends DataEntity<WarnReceive> {
	
	private static final long serialVersionUID = 1L;
	private String warnId;		// 预警id
    private String personId;        // 接受人员id
    private String personName;        // 接收人员名称
    private String isHandle;    //是否处理过（1：已处理 0：未处理）
    private Date nextRepeatTime;    //下次重复提醒时间，第一次创建数据时为空
    private String createOrgId;        // 创建者机构id
    private String updateOrgId;        // 更新者机构id

    private String repeatCycle;        // 重复周期
    private String month;    //月
    private String day;        // 日
    private String hour;        // 时
    private String minute;        // 分钟
    private String week;        // 周 周一至周日
    private Date date;        // 重复周期永不对应的时间
    private String remind;        // 重复提醒
    private String continueDay;    //持续时间
    private Date noRemindTime;     //不再提醒点击时间
    private Date handleTime;      //处理时间


	public WarnReceive() {
		super();
	}

	public WarnReceive(String id){
		super(id);
	}

	public String getWarnId() {
		return warnId;
	}

	public void setWarnId(String warnId) {
		this.warnId = warnId;
	}

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getIsHandle() {
        return isHandle;
    }

    public void setIsHandle(String isHandle) {
        this.isHandle = isHandle;
    }

    public Date getNextRepeatTime() {
        return nextRepeatTime;
    }

    public void setNextRepeatTime(Date nextRepeatTime) {
        this.nextRepeatTime = nextRepeatTime;
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

    public String getRepeatCycle() {
        return repeatCycle;
    }

    public void setRepeatCycle(String repeatCycle) {
        this.repeatCycle = repeatCycle;
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

    public String getRemind() {
        return remind;
    }

    public void setRemind(String remind) {
        this.remind = remind;
    }

    public String getContinueDay() {
        return continueDay;
    }

    public void setContinueDay(String continueDay) {
        this.continueDay = continueDay;
    }

    public Date getNoRemindTime() {
        return noRemindTime;
    }

    public void setNoRemindTime(Date noRemindTime) {
        this.noRemindTime = noRemindTime;
    }

    public Date getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(Date handleTime) {
        this.handleTime = handleTime;
    }
}