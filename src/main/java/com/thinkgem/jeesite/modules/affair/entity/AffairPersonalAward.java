/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 工会个人表彰Entity
 * @author cecil.li
 * @version 2019-11-06
 */
public class AffairPersonalAward extends DataEntity<AffairPersonalAward> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "时间", type = 0, align = 2, sort = 0)
	private Date date;		// 时间
	@ExcelField(title = "单位", type = 0, align = 2, sort = 1)
	private String unit;		// 单位
	private String unitId;		// 单位id
	@ExcelField(title = "民警姓名", type = 0, align = 2, sort = 2)
	private String policeName;		// 民警姓名
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 3)
	private String idNumber;		// 身份证号
	@ExcelField(title = "职务", type = 0, align = 2, sort = 4, dictType="affair_njzhiwu")
	private String job;		// 职务
	@ExcelField(title = "性别", type = 0, align = 2, sort = 5, dictType="sex")
	private String sex;		// 性别
	@ExcelField(title = "奖项名称", type = 0, align = 2, sort = 6)
	private String awardName;		// 奖项名称
	@ExcelField(title = "奖项级别", type = 0, align = 2, sort = 8, dictType="affair_org_reward_punish")
	private String awardLevel;		// 奖项级别
	@ExcelField(title = "表彰单位", type = 0, align = 2, sort = 9)
	private String bzUnit;		// 表彰单位
	private String bzUnitId;		// 表彰单位id
	@ExcelField(title = "备注", type = 0, align = 2, sort = 10)
	private String remark;		// 备注
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String  opinion;       //审核意见
	@ExcelField(title = "文号", type = 0, align = 2, sort = 7)
	private String fileNo;     //文号
	private String userId;         //获取当前账号单位
	private String pushType;      //推送状态
	private Date startDate;
	private Date endDate;
	private String typeFlag;
	
	public AffairPersonalAward() {
		super();
	}

	public AffairPersonalAward(String id){
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
	
	public String getPoliceName() {
		return policeName;
	}

	public void setPoliceName(String policeName) {
		this.policeName = policeName;
	}
	
	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getAwardName() {
		return awardName;
	}

	public void setAwardName(String awardName) {
		this.awardName = awardName;
	}
	
	public String getAwardLevel() {
		return awardLevel;
	}

	public void setAwardLevel(String awardLevel) {
		this.awardLevel = awardLevel;
	}
	
	public String getBzUnit() {
		return bzUnit;
	}

	public void setBzUnit(String bzUnit) {
		this.bzUnit = bzUnit;
	}
	
	public String getBzUnitId() {
		return bzUnitId;
	}

	public void setBzUnitId(String bzUnitId) {
		this.bzUnitId = bzUnitId;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTypeFlag() {
		return typeFlag;
	}

	public void setTypeFlag(String typeFlag) {
		this.typeFlag = typeFlag;
	}
}