/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 医疗保险Entity
 * @author jack.xu
 * @version 2020-07-03
 */
public class AffairMedicalInsurance extends DataEntity<AffairMedicalInsurance> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "时间", type = 0, align = 2, sort = 0)
	private String timeYear;		// 时间

	@ExcelField(title = "姓名", type = 0, align = 2, sort = 1)
	private String name;		// 姓名

	@ExcelField(title = "出生年月", type = 0, align = 2, sort = 3)
	private Date birthday;		// 出生年月

	@ExcelField(title = "年龄", type = 0, align = 2, sort = 4)
	private Integer age;		// 年龄

	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 5)
	private String idNumber;		// 身份证号

	@ExcelField(title = "缴费基数", type = 0, align = 2, sort = 6)
	private Double cardinalNumber;		// 缴费基数

	@ExcelField(title = "个人缴费比例", type = 0, align = 2, sort = 7)
	private Double individualProportion;		// 个人缴费比例

	@ExcelField(title = "月个人缴费", type = 0, align = 2, sort = 8)
	private Double individualPayment;		// 月个人缴费

	@ExcelField(title = "单位缴费比例", type = 0, align = 2, sort = 9)
	private Double unitProportion;		// 单位缴费比例

	@ExcelField(title = "月单位缴费", type = 0, align = 2, sort = 10)
	private Double unitPayment;		// 月单位缴费

	@ExcelField(title = "月个账划入比例", type = 0, align = 2, sort = 11)
	private Double monthAccount;//月个账划入比例

	@ExcelField(title = "月个账划入", type = 0, align = 2, sort = 12)
	private Double monthAccountDelimit;		// 月个账划入

	@ExcelField(title = "补充资金月个账划入比例", type = 0, align = 2, sort = 13)
	private Double addition;  //  补充资金月个账划入比例

	@ExcelField(title = "补充资金月个账划入", type = 0, align = 2, sort = 14)
	private Double additionFunds;		// 补充资金月个账划入

	@ExcelField(title = "全区公务员平均月工资", type = 0, align = 2, sort = 15)
	private Double averageSalary;		// 全区公务员平均月工资

	@ExcelField(title = "年度补助比例", type = 0, align = 2, sort = 16)
	private Double annualProportion;		// 年度补助比例

	private Double annualProportionOver;  // 年度补助比例（大于45岁）

	@ExcelField(title = "年度个账划入", type = 0, align = 2, sort = 17)
	private Double annualAccountDelimit;		// 年度个账划入

	private Double maxNumber;

	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String treeId;
	@ExcelField(title = "性别", type = 0, align = 2, sort = 2, dictType = "sex")
	private String sex;

	private String userId;
	private String unit;
	private String unitId;


	public AffairMedicalInsurance() {
		super();
	}

	public AffairMedicalInsurance(String id){
		super(id);
	}

	public String getTimeYear() {
		return timeYear;
	}

	public void setTimeYear(String timeYear) {
		this.timeYear = timeYear;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
	
	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public Double getCardinalNumber() {
		return cardinalNumber;
	}

	public void setCardinalNumber(Double cardinalNumber) {
		this.cardinalNumber = cardinalNumber;
	}
	
	public Double getIndividualProportion() {
		return individualProportion;
	}

	public void setIndividualProportion(Double individualProportion) {
		this.individualProportion = individualProportion;
	}
	
	public Double getIndividualPayment() {
		return individualPayment;
	}

	public void setIndividualPayment(Double individualPayment) {
		this.individualPayment = individualPayment;
	}
	
	public Double getUnitProportion() {
		return unitProportion;
	}

	public void setUnitProportion(Double unitProportion) {
		this.unitProportion = unitProportion;
	}
	
	public Double getUnitPayment() {
		return unitPayment;
	}

	public void setUnitPayment(Double unitPayment) {
		this.unitPayment = unitPayment;
	}
	
	public Double getMonthAccountDelimit() {
		return monthAccountDelimit;
	}

	public void setMonthAccountDelimit(Double monthAccountDelimit) {
		this.monthAccountDelimit = monthAccountDelimit;
	}
	
	public Double getAdditionFunds() {
		return additionFunds;
	}

	public void setAdditionFunds(Double additionFunds) {
		this.additionFunds = additionFunds;
	}
	
	public Double getAverageSalary() {
		return averageSalary;
	}

	public void setAverageSalary(Double averageSalary) {
		this.averageSalary = averageSalary;
	}
	
	public Double getAnnualProportion() {
		return annualProportion;
	}

	public void setAnnualProportion(Double annualProportion) {
		this.annualProportion = annualProportion;
	}
	
	public Double getAnnualAccountDelimit() {
		return annualAccountDelimit;
	}

	public void setAnnualAccountDelimit(Double annualAccountDelimit) {
		this.annualAccountDelimit = annualAccountDelimit;
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

	public String getTreeId() {
		return treeId;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getCreateOrgId() {
		return createOrgId;
	}

	public void setCreateOrgId(String createOrgId) {
		this.createOrgId = createOrgId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public Double getMonthAccount() {
		return monthAccount;
	}

	public void setMonthAccount(Double monthAccount) {
		this.monthAccount = monthAccount;
	}

	public Double getAddition() {
		return addition;
	}

	public void setAddition(Double addition) {
		this.addition = addition;
	}


	public Double getAnnualProportionOver() {
		return annualProportionOver;
	}

	public void setAnnualProportionOver(Double annualProportionOver) {
		this.annualProportionOver = annualProportionOver;
	}

	public Double getMaxNumber() {
		return maxNumber;
	}

	public void setMaxNumber(Double maxNumber) {
		this.maxNumber = maxNumber;
	}
}