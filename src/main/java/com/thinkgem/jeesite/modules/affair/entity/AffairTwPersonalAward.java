/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 团委个人表彰Entity
 * @author cecil.li
 * @version 2019-11-06
 */
public class AffairTwPersonalAward extends DataEntity<AffairTwPersonalAward> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "获奖时间", type = 0, align = 2, sort = 0)
	private Date date;		// 获奖时间
	@ExcelField(title = "获奖时单位", type = 0, align = 2, sort = 1)
	private String unit;		// 获奖单位
	private String unitId;		// 获奖单位id
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 2)
	private String name;		// 姓名
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 3)
	private String idNumber;		// 身份证号
	@ExcelField(title = "性别", type = 0, align = 2, sort = 4, dictType="sex")
	private String sex;		// 性别
	@ExcelField(title = "奖项名称", type = 0, align = 2, sort = 5)
	private String rewardName;		// 奖项名称
	@ExcelField(title = "奖项文号", type = 0, align = 2, sort = 6)
	private String fileNo;		// 奖项文号
	@ExcelField(title = "奖励级别", type = 0, align = 2, sort = 7, dictType = "affair_tw_reward_punish")
	private String type;       //奖励级别
	private String userId;         //用户单位id
	private String cardNum;
	@ExcelField(title = "审核单位", type = 0, align = 2, sort = 8)
	private String approvalUnit;		// 审核单位
	private String approvalUnitId;		// 审核单位id
	@ExcelField(title = "备注", type = 0, align = 2, sort = 9)
	private String remark;		// 备注
	private String filePath;		// 相关文件
	private String opinion;		// 审核意见
	private String status;		// 审核状态

	private String pushType;    //推送状态
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String shPerson;    //审核人

	private Date startDate;
	private Date endDate;
	private String typeFlag;
	
	public AffairTwPersonalAward() {
		super();
	}

	public AffairTwPersonalAward(String id){
		super(id);
	}
	public String getShPerson() {
		return shPerson;
	}

	public void setShPerson(String shPerson) {
		this.shPerson = shPerson;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
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
	}

	public void setApprovalUnit(String approvalUnit) {
		this.approvalUnit = approvalUnit;
	}
	
	public String getApprovalUnitId() {
		return approvalUnitId;
	}

	public void setApprovalUnitId(String approvalUnitId) {
		this.approvalUnitId = approvalUnitId;
	}
	
	public String getRewardName() {
		return rewardName;
	}

	public void setRewardName(String rewardName) {
		this.rewardName = rewardName;
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
	
	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	
	public String getStatus() {
		return status;
	}

	public String getPushType() {
		return pushType;
	}

	public void setPushType(String pushType) {
		this.pushType = pushType;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public String getTypeFlag() {
		return typeFlag;
	}

	public void setTypeFlag(String typeFlag) {
		this.typeFlag = typeFlag;
	}
}