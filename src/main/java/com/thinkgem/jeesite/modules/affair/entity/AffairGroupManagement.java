/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 团费收缴Entity
 * @author cecil.li
 * @version 2019-11-06
 */
public class AffairGroupManagement extends DataEntity<AffairGroupManagement> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "缴费人", type = 0, align = 2, sort = 0)
	private String payer;		// 缴费人
	//@ExcelField(title = "缴费人身份证", type = 0, align = 2, sort = 1)
	private String payerNum;		// 缴费人身份证
	@ExcelField(title = "金额", type = 0, align = 2, sort = 1)
	private Integer amount;		// 金额
	@ExcelField(title = "缴费内容", type = 0, align = 2, sort = 2)
	private String paymentContent;		// 缴费内容
	@ExcelField(title = "缴费日期", type = 0, align = 2, sort = 3)
	private Date paymentTime;		// 缴费日期
	@ExcelField(title = "团组织", type = 0, align = 2, sort = 4)
	private String group1;		// 团组织
	private String groupId;		// 团组织id
	@ExcelField(title = "收款人", type = 0, align = 2, sort = 5)
	private String payee;		// 收款人
	//@ExcelField(title = "收款人身份证", type = 0, align = 2, sort = 7)
	private String payeeNum;		// 收款人身份证
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private Date startDate;
	private Date endDate;
	private Integer minMoney;
	private Integer maxMoney;

	/*统计分析使用*/
	private String dateType;
	private Integer year;
	private String dateStart;
	private String dateEnd;
	private String month;
	private String label;
	private String unit;	//单位名称
	private String unitId;	//单位id

	private String userOffice;      //所在机构
	private String userGroup;  // 团组织


	public AffairGroupManagement() {
		super();
	}

	public AffairGroupManagement(String id){
		super(id);
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

	public String getUserOffice() {
		return userOffice;
	}

	public void setUserOffice(String userOffice) {
		this.userOffice = userOffice;
	}

	public String getPayer() {
		return payer;
	}

	public void setPayer(String payer) {
		this.payer = payer;
	}
	
	public String getPayerNum() {
		return payerNum;
	}

	public void setPayerNum(String payerNum) {
		this.payerNum = payerNum;
	}
	
	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	public String getPaymentContent() {
		return paymentContent;
	}

	public void setPaymentContent(String paymentContent) {
		this.paymentContent = paymentContent;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
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

	public String getGroup1() {
		return group1;
	}

	public void setGroup1(String group1) {
		this.group1 = group1;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getDateStart() {
		return dateStart;
	}

	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}

	public String getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
	}
}