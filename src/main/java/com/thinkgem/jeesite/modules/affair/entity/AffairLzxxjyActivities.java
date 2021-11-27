/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.Date;

/**
 * 廉政学习教育活动Entity
 * @author cecil.li
 * @version 2019-11-08
 */
public class AffairLzxxjyActivities extends DataEntity<AffairLzxxjyActivities> {
	
	private static final long serialVersionUID = 1L;
	private String eventName;		// 标题名称
	private String unit;			// 发布单位
	private String unitId;			// 发布单位 id
	private String type;    		// 教育类型
	private Date eventDate;			// 发布日期
	private String participants;	// 参加人员
	private String evevtLocation;	// 活动地点
	private String activityTheme;	// 活动主题
	private String activites;		// 活动内容
	private String annex;			// 附件
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private Date eventEndDate;
	private String summary;  		// 摘要
	private Date startDate;
	private Date endDate;
	private String qxUnit;
	private String qxUnitId;
	private Date startYear;   		//本年度
	private String otherYear;  		//其他年份
	private String flag;		// 标识（
	private String isPush;		//是否推送到主页  1：是
	//是否要发布的权限
	private boolean hasAuth;
	//接收部门签收的日期
	private Date signDate;
	//接收部门签收的状态
	private String signStatus; //1：签收

	private Integer signNum;

	private Integer sumNum;

	private boolean isFront; //是否来自前端
	private String status;
	private String publishDep;		// 发布部门
	private String publisher;		// 发布人
	private String publishOrgId;		// 发布部门机构id
	private String receiveDep;		// 接收部门
	private String receiveDepId;	// 接收部门id

	public AffairLzxxjyActivities() {
		super();
	}

	public AffairLzxxjyActivities(String id){
		super(id);
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
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
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}
	
	public String getParticipants() {
		return participants;
	}

	public void setParticipants(String participants) {
		this.participants = participants;
	}
	
	public String getEvevtLocation() {
		return evevtLocation;
	}

	public void setEvevtLocation(String evevtLocation) {
		this.evevtLocation = evevtLocation;
	}
	
	public String getActivityTheme() {
		return activityTheme;
	}

	public void setActivityTheme(String activityTheme) {
		this.activityTheme = activityTheme;
	}
	
	public String getActivites() {
		return activites;
	}

	public void setActivites(String activites) {
		this.activites = activites;
	}
	
	public String getAnnex() {
		return annex;
	}

	public void setAnnex(String annex) {
		this.annex = annex;
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
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEventEndDate() {
		return eventEndDate;
	}

	public void setEventEndDate(Date eventEndDate) {
		this.eventEndDate = eventEndDate;
	}

	public String getQxUnit() {
		return qxUnit;
	}

	public void setQxUnit(String qxUnit) {
		this.qxUnit = qxUnit;
	}

	public String getQxUnitId() {
		return qxUnitId;
	}

	public void setQxUnitId(String qxUnitId) {
		this.qxUnitId = qxUnitId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	@JsonFormat(pattern = "yyyy")
	public Date getStartYear() {
		return startYear;
	}

	public void setStartYear(Date startYear) {
		this.startYear = startYear;
	}

	public String getOtherYear() {
		return otherYear;
	}

	public void setOtherYear(String otherYear) {
		this.otherYear = otherYear;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getIsPush() {
		return isPush;
	}

	public void setIsPush(String isPush) {
		this.isPush = isPush;
	}

	public boolean isHasAuth() {
		return hasAuth;
	}

	public void setHasAuth(boolean hasAuth) {
		this.hasAuth = hasAuth;
	}

	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	public String getSignStatus() {
		return signStatus;
	}

	public void setSignStatus(String signStatus) {
		this.signStatus = signStatus;
	}

	public Integer getSignNum() {
		return signNum;
	}

	public void setSignNum(Integer signNum) {
		this.signNum = signNum;
	}

	public Integer getSumNum() {
		return sumNum;
	}

	public void setSumNum(Integer sumNum) {
		this.sumNum = sumNum;
	}

	public boolean isFront() {
		return isFront;
	}

	public void setFront(boolean front) {
		isFront = front;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPublishDep() {
		return publishDep;
	}

	public void setPublishDep(String publishDep) {
		this.publishDep = publishDep;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getPublishOrgId() {
		return publishOrgId;
	}

	public void setPublishOrgId(String publishOrgId) {
		this.publishOrgId = publishOrgId;
	}

	public String getReceiveDep() {
		return receiveDep;
	}

	public void setReceiveDep(String receiveDep) {
		this.receiveDep = receiveDep;
	}

	public String getReceiveDepId() {
		return receiveDepId;
	}

	public void setReceiveDepId(String receiveDepId) {
		this.receiveDepId = receiveDepId;
	}
}