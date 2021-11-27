/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.Date;

/**
 * 党日活动Entity
 * @author cecil.li
 * @version 2020-04-12
 */
public class AffairPartyDayActivities extends DataEntity<AffairPartyDayActivities> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 活动名称
	private Date startDate;		// 活动开始时间
	private Date endDate;		// 活动结束时间
	private String partyBranch;		// 党组织名称
	private String partyBranchId;		// 党组织id
	private String holdUnit;		// 举办单位
	private String holdUnitId;		// 举办单位id
	private String place;		// 活动地点
	private String joinPerson;		// 参加人员
	private String workflow;		// 活动流程
	private String materialPath1;		// 相关资料
	private String content;		// 主要内容
	private String materialPath2;		// 活动记录相关资料
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private Date beginStartDate;		// 开始 活动开始时间
	private Date endStartDate;		// 结束 活动开始时间

	private String treeId;	//接受前端传来的左侧树的id

	/*新加字段，审核使用*/
	/*1未上报 2待审核 3通过 4不通过*/
	private String examineStatus;	//审核状态
	private String opinion;			//审核意见

	// 自动考评需要
	private String year;
	private String month;

	private String joinPersonId;	//参加人id


	public AffairPartyDayActivities() {
		super();
	}

	public AffairPartyDayActivities(String id){
		super(id);
	}

	public String getJoinPersonId() {
		return joinPersonId;
	}

	public void setJoinPersonId(String joinPersonId) {
		this.joinPersonId = joinPersonId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public String getPartyBranch() {
		return partyBranch;
	}

	public void setPartyBranch(String partyBranch) {
		this.partyBranch = partyBranch;
	}
	
	public String getPartyBranchId() {
		return partyBranchId;
	}

	public void setPartyBranchId(String partyBranchId) {
		this.partyBranchId = partyBranchId;
	}
	
	public String getHoldUnit() {
		return holdUnit;
	}

	public void setHoldUnit(String holdUnit) {
		this.holdUnit = holdUnit;
	}
	
	public String getHoldUnitId() {
		return holdUnitId;
	}

	public void setHoldUnitId(String holdUnitId) {
		this.holdUnitId = holdUnitId;
	}
	
	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}
	
	public String getJoinPerson() {
		return joinPerson;
	}

	public void setJoinPerson(String joinPerson) {
		this.joinPerson = joinPerson;
	}
	
	public String getWorkflow() {
		return workflow;
	}

	public void setWorkflow(String workflow) {
		this.workflow = workflow;
	}
	
	public String getMaterialPath1() {
		return materialPath1;
	}

	public void setMaterialPath1(String materialPath1) {
		this.materialPath1 = materialPath1;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getMaterialPath2() {
		return materialPath2;
	}

	public void setMaterialPath2(String materialPath2) {
		this.materialPath2 = materialPath2;
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
	
	public Date getBeginStartDate() {
		return beginStartDate;
	}

	public void setBeginStartDate(Date beginStartDate) {
		this.beginStartDate = beginStartDate;
	}
	
	public Date getEndStartDate() {
		return endStartDate;
	}

	public void setEndStartDate(Date endStartDate) {
		this.endStartDate = endStartDate;
	}

	public String getTreeId() {
		return treeId;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}

	public String getExamineStatus() {
		return examineStatus;
	}

	public void setExamineStatus(String examineStatus) {
		this.examineStatus = examineStatus;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}
}