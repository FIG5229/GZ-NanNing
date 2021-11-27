/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 医保金Entity
 * @author mason.xv
 * @version 2019-11-28
 */
public class AffairGuarantee extends DataEntity<AffairGuarantee> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 姓名
	private String idNumber;		// 身份证号
	private String sex;		// 性别
	private String type;		// 保险种类
	private String bxName;		// 保险名称
	private String bxOrg;		// 保险机构名称
	private Double cost;		// 保险费
	private Double money;		// 保险金额
	private Date startDate;		// 保险起始日期
	private Date endDate;		// 保险终止日期
	private Date payDate;		// 保险赔付日期
	private String payReason;		// 保险赔付事由
	private String endReason;		// 保险终止事由
	private String remark;		// 保险备注
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	
	public AffairGuarantee() {
		super();
	}

	public AffairGuarantee(String id){
		super(id);
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
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getBxName() {
		return bxName;
	}

	public void setBxName(String bxName) {
		this.bxName = bxName;
	}
	
	public String getBxOrg() {
		return bxOrg;
	}

	public void setBxOrg(String bxOrg) {
		this.bxOrg = bxOrg;
	}
	
	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}
	
	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	
	public String getPayReason() {
		return payReason;
	}

	public void setPayReason(String payReason) {
		this.payReason = payReason;
	}
	
	public String getEndReason() {
		return endReason;
	}

	public void setEndReason(String endReason) {
		this.endReason = endReason;
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
	
}