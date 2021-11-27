/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.Date;

/**
 * 工会活动管理Entity
 * @author mason.xv
 * @version 2020-03-26
 */
public class AffairGhActivityManage extends DataEntity<AffairGhActivityManage> {
	
	private static final long serialVersionUID = 1L;
	private Date activityDate;		// 活动时间
	private String unit;		// 单位
	private String unitId;         //单位ID
	private String name;		// 姓名
	private String moneyNum;		// 报销金额
	private String kzKemu;		// 开支科目
	private String useWay;		// 用途
	private String jbMan;		// 经办人
	private String checkZmMan;		// 审核证明人
	private String opinion;		// 审核意见
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String threeCheckMan;		// 局审核人
	private String twoCheckMan;		// 处审核人
	private String oneCheckMan;		// 所审核人
	private String submitMan;		// 提交人
	private String checkType;		// 审核状态（0-4/提交）
	private String threeCheckId;		// 审核局id
	private String twoCheckId;		// 审核处id
	private String oneCheckId;		// 审核单位id
	private String submitId;		// 提交人id
	private Date beginActivityDate;		// 开始 活动时间
	private Date endActivityDate;		// 结束 活动时间

	private String userId;
	private String filePath;
	
	public AffairGhActivityManage() {
		super();
	}

	public AffairGhActivityManage(String id){
		super(id);
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getActivityDate() {
		return activityDate;
	}

	public void setActivityDate(Date activityDate) {
		this.activityDate = activityDate;
	}
	
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getMoneyNum() {
		return moneyNum;
	}

	public void setMoneyNum(String moneyNum) {
		this.moneyNum = moneyNum;
	}
	
	public String getKzKemu() {
		return kzKemu;
	}

	public void setKzKemu(String kzKemu) {
		this.kzKemu = kzKemu;
	}
	
	public String getUseWay() {
		return useWay;
	}

	public void setUseWay(String useWay) {
		this.useWay = useWay;
	}
	
	public String getJbMan() {
		return jbMan;
	}

	public void setJbMan(String jbMan) {
		this.jbMan = jbMan;
	}
	
	public String getCheckZmMan() {
		return checkZmMan;
	}

	public void setCheckZmMan(String checkZmMan) {
		this.checkZmMan = checkZmMan;
	}
	
	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
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
	
	public String getThreeCheckMan() {
		return threeCheckMan;
	}

	public void setThreeCheckMan(String threeCheckMan) {
		this.threeCheckMan = threeCheckMan;
	}
	
	public String getTwoCheckMan() {
		return twoCheckMan;
	}

	public void setTwoCheckMan(String twoCheckMan) {
		this.twoCheckMan = twoCheckMan;
	}
	
	public String getOneCheckMan() {
		return oneCheckMan;
	}

	public void setOneCheckMan(String oneCheckMan) {
		this.oneCheckMan = oneCheckMan;
	}
	
	public String getSubmitMan() {
		return submitMan;
	}

	public void setSubmitMan(String submitMan) {
		this.submitMan = submitMan;
	}
	
	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}
	
	public String getThreeCheckId() {
		return threeCheckId;
	}

	public void setThreeCheckId(String threeCheckId) {
		this.threeCheckId = threeCheckId;
	}
	
	public String getTwoCheckId() {
		return twoCheckId;
	}

	public void setTwoCheckId(String twoCheckId) {
		this.twoCheckId = twoCheckId;
	}
	
	public String getOneCheckId() {
		return oneCheckId;
	}

	public void setOneCheckId(String oneCheckId) {
		this.oneCheckId = oneCheckId;
	}
	
	public String getSubmitId() {
		return submitId;
	}

	public void setSubmitId(String submitId) {
		this.submitId = submitId;
	}
	
	public Date getBeginActivityDate() {
		return beginActivityDate;
	}

	public void setBeginActivityDate(Date beginActivityDate) {
		this.beginActivityDate = beginActivityDate;
	}
	
	public Date getEndActivityDate() {
		return endActivityDate;
	}

	public void setEndActivityDate(Date endActivityDate) {
		this.endActivityDate = endActivityDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}