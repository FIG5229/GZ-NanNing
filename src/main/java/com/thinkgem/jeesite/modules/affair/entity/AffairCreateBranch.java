/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 党内创品牌活动Entity
 * @author eav.liu
 * @version 2019-11-07
 */
public class AffairCreateBranch extends DataEntity<AffairCreateBranch> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "活动名称", type = 0, align = 2, sort = 0)
	private String name;		// 活动名称
	@ExcelField(title = "活动开始时间", type = 0, align = 2, sort = 1)
	private Date startTime;		// 活动开始时间
	@ExcelField(title = "活动结束时间", type = 0, align = 2, sort = 2)
	private Date endTime;		// 活动结束时间
	@ExcelField(title = "党组织名称", type = 0, align = 2, sort = 3)
	private String partyBranch;		// 党组织名称
	private String partyBranchId;		// 党组织id
	@ExcelField(title = "举办单位", type = 0, align = 2, sort = 4)
	private String holdUnit;		// 举办单位
	private String holdUnitId;		// 举办单位id
	@ExcelField(title = "是否被命名", type = 0, align = 2, sort = 5, dictType = "affair_is_named")
	private String isNamed;		// 是否被命名
	@ExcelField(title = "活动地点", type = 0, align = 2, sort = 6)
	private String place;		// 活动地点
	@ExcelField(title = "参加人员", type = 0, align = 2, sort = 7)
	private String joinPerson;		// 参加人员
	@ExcelField(title = "活动方案", type = 0, align = 2, sort = 8)
	private String scheme;		// 活动方案
	@ExcelField(title = "阶段活动开展简报", type = 0, align = 2, sort = 9)
	private String briefReport;		// 阶段活动开展简报
	private String materialPath;		// 相关资料
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private String treeId;	//接受前端传来的左侧树的id

	/*新加字段，审核使用*/
	/*1未上报 2待审核 3通过 4不通过*/
	private String examineStatus;	//审核状态
	private String opinion;			//审核意见

	private String joinPersonId;	//参加人员id

	public AffairCreateBranch() {
		super();
	}

	public AffairCreateBranch(String id){
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
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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

	public String getIsNamed() {
		return isNamed;
	}

	public void setIsNamed(String isNamed) {
		this.isNamed = isNamed;
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
	
	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}
	
	public String getBriefReport() {
		return briefReport;
	}

	public void setBriefReport(String briefReport) {
		this.briefReport = briefReport;
	}
	
	public String getMaterialPath() {
		return materialPath;
	}

	public void setMaterialPath(String materialPath) {
		this.materialPath = materialPath;
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
}