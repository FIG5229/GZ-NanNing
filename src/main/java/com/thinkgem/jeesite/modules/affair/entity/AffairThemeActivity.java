/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 党内主题实践活动Entity
 * @author eav.liu
 * @version 2019-11-06
 */
public class AffairThemeActivity extends DataEntity<AffairThemeActivity> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "活动名称", type = 0, align = 2, sort = 0)
	private String name;		// 活动名称
	@ExcelField(title = "活动开始时间", type = 0, align = 2, sort = 3)
	private Date startTime;		// 活动开始时间
	@ExcelField(title = "活动结束时间", type = 0, align = 2, sort = 4)
	private Date endTime;		// 活动结束时间
	@ExcelField(title = "活动类型", type = 0, align = 2, sort = 5, dictType = "affair_theme_activity")
	private String type;		// 活动类型
	@ExcelField(title = "活动地点", type = 0, align = 2, sort = 6)
	private String place;		// 活动地点
	@ExcelField(title = "参加人员", type = 0, align = 2, sort = 7)
	private String joinPerson;		// 参加人员
	@ExcelField(title = "工作流程", type = 0, align = 2, sort = 8)
	private String workflow;		// 工作流程
	private String materialPath;		// 相关材料
	@ExcelField(title = "党组织名称", type = 0, align = 2, sort = 1)
	private String partyOrganizationName;		// 党组织名称
	private String partyOrganizationId;		// 党组织id
	@ExcelField(title = "举办单位", type = 0, align = 2, sort = 2)
	private String holdUnitName;		// 举办单位
	private String holdUnitId;		// 举办单位id
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private String treeId;	//接受前端传来的左侧树的id
	
	public AffairThemeActivity() {
		super();
	}

	public AffairThemeActivity(String id){
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
	
	public String getMaterialPath() {
		return materialPath;
	}

	public void setMaterialPath(String materialPath) {
		this.materialPath = materialPath;
	}
	
	public String getPartyOrganizationName() {
		return partyOrganizationName;
	}

	public void setPartyOrganizationName(String partyOrganizationName) {
		this.partyOrganizationName = partyOrganizationName;
	}
	
	public String getPartyOrganizationId() {
		return partyOrganizationId;
	}

	public void setPartyOrganizationId(String partyOrganizationId) {
		this.partyOrganizationId = partyOrganizationId;
	}
	
	public String getHoldUnitName() {
		return holdUnitName;
	}

	public void setHoldUnitName(String holdUnitName) {
		this.holdUnitName = holdUnitName;
	}
	
	public String getHoldUnitId() {
		return holdUnitId;
	}

	public void setHoldUnitId(String holdUnitId) {
		this.holdUnitId = holdUnitId;
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
}