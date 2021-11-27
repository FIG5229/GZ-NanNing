/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 团委集体表彰Entity
 * @author cecil.li
 * @version 2019-11-06
 */
public class AffairTwUnitAward extends DataEntity<AffairTwUnitAward> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "获奖时间", type = 0, align = 2, sort = 0)
	private Date date;		// 获奖时间
	@ExcelField(title = "获奖组织", type = 0, align = 2, sort = 2)
	private String getAwardParty;
	@ExcelField(title = "获奖单位", type = 0, align = 2, sort = 1)
	private String unit;		// 获奖单位
	private String unitId;		// 获奖单位id
	@ExcelField(title = "审批单位", type = 0, align = 2, sort = 4)
	private String approvalUnit;		// 审批单位
	private String approvaleUnitId;		// 审批单位id
	@ExcelField(title = "奖项名称", type = 0, align = 2, sort = 3)
	private String name;		// 奖项名称
	@ExcelField(title = "奖励级别", type = 0, align = 2, sort = 6, dictType = "affair_tw_reward_punish")
	private String type;   //奖励级别
	@ExcelField(title = "文号", type = 0, align = 2, sort = 5)
	private String fileNo;		// 文号
	@ExcelField(title = "备注", type = 0, align = 2, sort = 7)
	private String remark;		// 备注

	private String filePath;		// 相关文件
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String shPerson;       //审核人
	private String status;         //审核状态
	private String opinion;         //审核意见
	private String pushType;      //推送状态
	private Date startDate;
	private Date endDate;
	private String userId;         //用户Id
	private String userOffice;      //所在机构
	private String typeFlag;


	public AffairTwUnitAward() {
		super();
	}

	public AffairTwUnitAward(String id){
		super(id);
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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
	
	public String getApprovalUnit() {
		return approvalUnit;
	}public String getShPerson() {
		return shPerson;
	}

	public void setShPerson(String shPerson) {
		this.shPerson = shPerson;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public void setApprovalUnit(String approvalUnit) {
		this.approvalUnit = approvalUnit;
	}
	
	public String getApprovaleUnitId() {
		return approvaleUnitId;
	}

	public void setApprovaleUnitId(String approvaleUnitId) {
		this.approvaleUnitId = approvaleUnitId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getFileNo() {
		return fileNo;
	}

	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
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

	public String getPushType() {
		return pushType;
	}

	public void setPushType(String pushType) {
		this.pushType = pushType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getGetAwardParty() {
		return getAwardParty;
	}

	public void setGetAwardParty(String getAwardParty) {
		this.getAwardParty = getAwardParty;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserOffice() {
		return userOffice;
	}

	public void setUserOffice(String userOffice) {
		this.userOffice = userOffice;
	}

	public String getTypeFlag() {
		return typeFlag;
	}

	public void setTypeFlag(String typeFlag) {
		this.typeFlag = typeFlag;
	}
}