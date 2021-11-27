/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 党员奖惩信息Entity
 * @author eav.liu
 * @version 2019-11-12
 */
public class AffairPartyRewardPunish extends DataEntity<AffairPartyRewardPunish> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 0)
	private String name;		// 姓名
	@ExcelField(title = "性别", type = 0, align = 2, sort = 1,dictType = "sex")
	private String sex;		// 性别
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 2)
	private String idNumber;		// 身份证号
	private String policeNo;		// 警号
	@ExcelField(title = "奖惩文号", type = 0, align = 2, sort = 4)
	private String fileNo;		// 奖惩文号
	@ExcelField(title = "奖惩名称", type = 0, align = 2, sort = 3)
	private String title;		// 奖惩名称
	private String approvalOrg;		// 批准党委
	@ExcelField(title = "批准党委", type = 0, align = 2, sort = 7, dictType = "affair_paryt_committee")
	private String approvalOrgId;		// 批准党委id
	@ExcelField(title = "奖惩类别", type = 0, align = 2, sort = 8, dictType = "affair_org_reward_punish")
	private String type;		// 奖惩类别
	@ExcelField(title = "批准时间", type = 0, align = 2, sort = 6)
	private Date approvalTime;		// 批准时间
	@ExcelField(title = "奖惩说明", type = 0, align = 2, sort = 9)
	private String illustration;		// 奖惩说明
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String pushType;       //推送状态
	@ExcelField(title = "党组织", type = 0, align = 2, sort = 5)
	private String partyOrganization;       //党组织名称
	private String partyOrganizationId;       //党组织id

	private Date approvalStartTime;

	private Date approvalEndTime;

	private String treeId;	//接受前端传来的左侧树的id
	private String typeFlag;

	private String awardCategory;//奖励惩戒信息区分

	private String cjType;  //惩戒类别

	
	public AffairPartyRewardPunish() {
		super();
	}

	public AffairPartyRewardPunish(String id){
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public String getPoliceNo() {
		return policeNo;
	}

	public void setPoliceNo(String policeNo) {
		this.policeNo = policeNo;
	}
	
	public String getFileNo() {
		return fileNo;
	}

	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getApprovalTime() {
		return approvalTime;
	}

	public void setApprovalTime(Date approvalTime) {
		this.approvalTime = approvalTime;
	}
	
	public String getIllustration() {
		return illustration;
	}

	public void setIllustration(String illustration) {
		this.illustration = illustration;
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

	public Date getApprovalStartTime() {
		return approvalStartTime;
	}

	public void setApprovalStartTime(Date approvalStartTime) {
		this.approvalStartTime = approvalStartTime;
	}

	public Date getApprovalEndTime() {
		return approvalEndTime;
	}

	public void setApprovalEndTime(Date approvalEndTime) {
		this.approvalEndTime = approvalEndTime;
	}

	public String getPushType() {
		return pushType;
	}

	public void setPushType(String pushType) {
		this.pushType = pushType;
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