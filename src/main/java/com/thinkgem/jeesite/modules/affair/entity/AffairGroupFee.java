/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 团费收支Entity
 * @author cecil.li
 * @version 2019-11-06
 */
public class AffairGroupFee extends DataEntity<AffairGroupFee> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "支出类别", type = 0, align = 2, sort = 0, dictType="affair_tftype")
	private String expenditureCategory;		// 支出类别
	@ExcelField(title = "支出金额", type = 0, align = 2, sort = 1)
	private Integer payoutAmount;		// 支出金额
	@ExcelField(title = "支出内容", type = 0, align = 2, sort = 2)
	private String expenditureContent;		// 支出内容
	@ExcelField(title = "支出时间", type = 0, align = 2, sort = 3)
	private Date expenditureDate;		// 支出时间
	@ExcelField(title = "团组织", type = 0, align = 2, sort = 4)
	private String groupOrg;		// 团组织
	private String groupOrgId;		// 团组织id
	@ExcelField(title = "经办人", type = 0, align = 2, sort = 5)
	private String manager;		// 经办人
	@ExcelField(title = "经办人身份证", type = 0, align = 2, sort = 6)
	private String managerNum;		// 经办人身份证
	@ExcelField(title = "收款人", type = 0, align = 2, sort = 7)
	private String payee;		// 收款人
	@ExcelField(title = "收款人身份证", type = 0, align = 2, sort = 8)
	private String payeeNum;		// 收款人身份证
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private Date startDate;
	private Date endDate;
	private Integer minMoney;
	private Integer maxMoney;
	
	public AffairGroupFee() {
		super();
	}

	public AffairGroupFee(String id){
		super(id);
	}

	public String getExpenditureCategory() {
		return expenditureCategory;
	}

	public void setExpenditureCategory(String expenditureCategory) {
		this.expenditureCategory = expenditureCategory;
	}
	
	public Integer getPayoutAmount() {
		return payoutAmount;
	}

	public void setPayoutAmount(Integer payoutAmount) {
		this.payoutAmount = payoutAmount;
	}
	
	public String getExpenditureContent() {
		return expenditureContent;
	}

	public void setExpenditureContent(String expenditureContent) {
		this.expenditureContent = expenditureContent;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getExpenditureDate() {
		return expenditureDate;
	}

	public void setExpenditureDate(Date expenditureDate) {
		this.expenditureDate = expenditureDate;
	}
	
	public String getGroupOrg() {
		return groupOrg;
	}

	public void setGroupOrg(String groupOrg) {
		this.groupOrg = groupOrg;
	}
	
	public String getGroupOrgId() {
		return groupOrgId;
	}

	public void setGroupOrgId(String groupOrgId) {
		this.groupOrgId = groupOrgId;
	}
	
	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}
	
	public String getManagerNum() {
		return managerNum;
	}

	public void setManagerNum(String managerNum) {
		this.managerNum = managerNum;
	}
	
	public String getPayee() {
		return payee;
	}

	public void setPayee(String payee) {
		this.payee = payee;
	}
	
	public String getPayeeNum() {
		return payeeNum;
	}

	public void setPayeeNum(String payeeNum) {
		this.payeeNum = payeeNum;
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

	public Integer getMinMoney() {
		return minMoney;
	}

	public void setMinMoney(Integer minMoney) {
		this.minMoney = minMoney;
	}

	public Integer getMaxMoney() {
		return maxMoney;
	}

	public void setMaxMoney(Integer maxMoney) {
		this.maxMoney = maxMoney;
	}
}