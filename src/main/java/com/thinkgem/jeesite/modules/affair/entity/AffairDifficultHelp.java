/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 困难帮扶申报Entity
 * @author daniel.liu
 * @version 2020-06-03
 */
public class AffairDifficultHelp extends DataEntity<AffairDifficultHelp> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 姓名
	private String sex;		// 1男2女
	private Date birthday;		// 出生年月
	private Date joinPartyTime;		// 入党时间
	private String unit;		// 所在单位
	private String unitId;		// 所在单位Id
	private String job;		// 职务
	private String wwType;		// 慰问对象类别
	private String reason;		// 慰问原因
	private String money;		// 慰问金额
	private String moneySource;		// 慰问款源
	private Date time;		// 慰问时间
	private String phoneNumber;		// 联系电话
	private String createOrgId;		// 创建者机构id
	private String updateOrgId;		// 更新者机构id
	private String beginMoney;		// 开始 慰问金额
	private String endMoney;		// 结束 慰问金额

	private String checkMan;		//审核人
	private String submitMan;		//提交人
	private String checkType;		//审核状态
	private String checkId;			//审核人Id
	private String submitId;		//提交人Id
	private String shOpinion;		//审核意见
	private String userId;


	
	public AffairDifficultHelp() {
		super();
	}

	public AffairDifficultHelp(String id){
		super(id);
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

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getJoinPartyTime() {
		return joinPartyTime;
	}

	public void setJoinPartyTime(Date joinPartyTime) {
		this.joinPartyTime = joinPartyTime;
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

	public void setUnitId(String untiId) {
		this.unitId = untiId;
	}
	
	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}
	
	public String getWwType() {
		return wwType;
	}

	public void setWwType(String wwType) {
		this.wwType = wwType;
	}
	
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}
	
	public String getMoneySource() {
		return moneySource;
	}

	public void setMoneySource(String moneySource) {
		this.moneySource = moneySource;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getCreateOrgId() {
		return createOrgId;
	}

	public void setCreateOrgId(String createOrgId) {
		this.createOrgId = createOrgId;
	}
	
	public String getUpdateOrgId() {
		return updateOrgId;
	}

	public void setUpdateOrgId(String updateOrgId) {
		this.updateOrgId = updateOrgId;
	}
	
	public String getBeginMoney() {
		return beginMoney;
	}

	public void setBeginMoney(String beginMoney) {
		this.beginMoney = beginMoney;
	}
	
	public String getEndMoney() {
		return endMoney;
	}

	public void setEndMoney(String endMoney) {
		this.endMoney = endMoney;
	}

	public String getCheckMan() {
		return checkMan;
	}

	public void setCheckMan(String checkMan) {
		this.checkMan = checkMan;
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

	public String getCheckId() {
		return checkId;
	}

	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}

	public String getSubmitId() {
		return submitId;
	}

	public void setSubmitId(String submitId) {
		this.submitId = submitId;
	}

	public String getShOpinion() {
		return shOpinion;
	}

	public void setShOpinion(String shOpinion) {
		this.shOpinion = shOpinion;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}