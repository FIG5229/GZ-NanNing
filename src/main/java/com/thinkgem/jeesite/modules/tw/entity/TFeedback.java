/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.tw.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 微信警情-自动考评Entity
 * @author alan.wu
 * @version 2020-10-16
 */
public class TFeedback extends DataEntity<TFeedback> {
	
	private static final long serialVersionUID = 1L;
	private String policeMessageId;		// police_message_id
	private String describeMessage;		// describe_message
	private String feedbackPeople;		// feedback_people
	private Date describeTime;		// describe_time
	private String feedbackPeopleId;		// feedback_people_id
	private String toPolicePeople;		// 出警人
	private Date toPoliceTime;		// 出警时间
	private Date createTime;		// 创建时间
	private String toPoliceOrganization;		// 操作人组织机构
	
	public TFeedback() {
		super();
	}

	public TFeedback(String id){
		super(id);
	}

	public String getPoliceMessageId() {
		return policeMessageId;
	}

	public void setPoliceMessageId(String policeMessageId) {
		this.policeMessageId = policeMessageId;
	}
	
	public String getDescribeMessage() {
		return describeMessage;
	}

	public void setDescribeMessage(String describeMessage) {
		this.describeMessage = describeMessage;
	}
	
	public String getFeedbackPeople() {
		return feedbackPeople;
	}

	public void setFeedbackPeople(String feedbackPeople) {
		this.feedbackPeople = feedbackPeople;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDescribeTime() {
		return describeTime;
	}

	public void setDescribeTime(Date describeTime) {
		this.describeTime = describeTime;
	}
	
	public String getFeedbackPeopleId() {
		return feedbackPeopleId;
	}

	public void setFeedbackPeopleId(String feedbackPeopleId) {
		this.feedbackPeopleId = feedbackPeopleId;
	}
	
	public String getToPolicePeople() {
		return toPolicePeople;
	}

	public void setToPolicePeople(String toPolicePeople) {
		this.toPolicePeople = toPolicePeople;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getToPoliceTime() {
		return toPoliceTime;
	}

	public void setToPoliceTime(Date toPoliceTime) {
		this.toPoliceTime = toPoliceTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getToPoliceOrganization() {
		return toPoliceOrganization;
	}

	public void setToPoliceOrganization(String toPoliceOrganization) {
		this.toPoliceOrganization = toPoliceOrganization;
	}
	
}