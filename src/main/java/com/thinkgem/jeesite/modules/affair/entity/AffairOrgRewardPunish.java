/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 组织奖惩信息Entity
 * @author cecil.li
 * @version 2019-11-01
 */
public class AffairOrgRewardPunish extends DataEntity<AffairOrgRewardPunish> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "奖惩标题", type = 0, align = 2, sort = 3)
	private String title;		// 奖惩标题
	@ExcelField(title = "奖惩类别", type = 0, align = 2, sort = 2, dictType = "affair_org_reward_punish")
	private String type;		// 奖惩类别
	@ExcelField(title = "奖惩时间", type = 0, align = 2, sort = 0)
	private Date date;		// 奖惩时间
	@ExcelField(title = "党组织名称", type = 0, align = 2, sort = 4)
	private String partyOrganization;		// 党组织名称
	private String partyOrganizationId;		// 党组织id
	@ExcelField(title = "奖惩文号", type = 0, align = 2, sort = 5)
	private String fileNo;		// 奖惩文号
	@ExcelField(title = "奖惩内容", type = 0, align = 2, sort = 6)
	private String content;		// 奖惩内容
	@ExcelField(title = "批准党委", type = 0, align = 2, sort = 1, dictType = "affair_paryt_committee")
	private String approvalOrg;		// 批准党委
	private String approvalOrgId;		// 批准机关id
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	//供查询时间区间使用
	private Date startDate;
	//供查询时间区间使用
	private Date endDate;
	private String pushType;          //推送状态
	private String treeId;	//接受前端传来的左侧树的id
	private String typeFlag;

	private String awardCategory; //奖励类别
	private String cjType;  //惩戒类别


	public AffairOrgRewardPunish() {
		super();
	}

	public AffairOrgRewardPunish(String id){
		super(id);
	}

	public String getAwardCategory() {
		return awardCategory;
	}

	public void setAwardCategory(String awardCategory) {
		this.awardCategory = awardCategory;
	}

	public String getCjType() {
		return cjType;
	}

	public void setCjType(String cjType) {
		this.cjType = cjType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getPartyOrganization() {
		return partyOrganization;
	}

	public void setPartyOrganization(String partyOrganization) {
		this.partyOrganization = partyOrganization;
	}
	
	public String getPartyOrganizationId() {
		return partyOrganizationId;
	}

	public void setPartyOrganizationId(String partyOrganizationId) {
		this.partyOrganizationId = partyOrganizationId;
	}
	

	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getApprovalOrg() {
		return approvalOrg;
	}

	public void setApprovalOrg(String approvalOrg) {
		this.approvalOrg = approvalOrg;
	}
	
	public String getApprovalOrgId() {
		return approvalOrgId;
	}

	public void setApprovalOrgId(String approvalOrgId) {
		this.approvalOrgId = approvalOrgId;
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

	public String getFileNo() {
		return fileNo;
	}

	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}

	public String getPushType() {
		return pushType;
	}

	public void setPushType(String pushType) {
		this.pushType = pushType;
	}

	public String getTreeId() {
		return treeId;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}

	public String getTypeFlag() {
		return typeFlag;
	}

	public void setTypeFlag(String typeFlag) {
		this.typeFlag = typeFlag;
	}
}