/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.Date;

/**
 * 队伍作风纪律整顿Entity
 * @author cecil.li
 * @version 2019-11-08
 */
public class AffairTeamDiscipline extends DataEntity<AffairTeamDiscipline> {
	
	private static final long serialVersionUID = 1L;
	private String eventName;		// 活动名称
	private String unit;		// 单位
	private String unitId;		// 单位id
	private String eventLocation;		// 活动地点
	private Date eventDate;		// 活动时间
	private String participants;		// 参加人员
	private String form;		// 整顿形式
	private String content;		// 整顿内容
	private String annex;		// 附件
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private Date finishDate;    //活动结束时火箭

	private Date startDate;
	private Date endDate;
	private Date startFinishDate;
	private Date endFinishDate;
	private String qxUnitId;
	private Date startYear;   //本年度
	private String otherYear;  //其他年份
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
	
	public AffairTeamDiscipline() {
		super();
	}

	public AffairTeamDiscipline(String id){
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
	
	public String getEventLocation() {
		return eventLocation;
	}

	public void setEventLocation(String eventLocation) {
		this.eventLocation = eventLocation;
	}

	
	public String getParticipants() {
		return participants;
	}

	public void setParticipants(String participants) {
		this.participants = participants;
	}
	
	public String getForm() {
		return form;
	}

	public void setForm(String form) {
		this.form = form;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public String getQxUnitId() {
		return qxUnitId;
	}

	public void setQxUnitId(String qxUnitId) {
		this.qxUnitId = qxUnitId;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getStartFinishDate() {
		return startFinishDate;
	}

	public void setStartFinishDate(Date startFinishDate) {
		this.startFinishDate = startFinishDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEndFinishDate() {
		return endFinishDate;
	}

	public void setEndFinishDate(Date endFinishDate) {
		this.endFinishDate = endFinishDate;
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