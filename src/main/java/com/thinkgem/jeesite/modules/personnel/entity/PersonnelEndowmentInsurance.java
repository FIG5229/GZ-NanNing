/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 养老保险Entity
 * @author alan.wu
 * @version 2020-06-29
 */
public class PersonnelEndowmentInsurance extends DataEntity<PersonnelEndowmentInsurance> {

	private static final long serialVersionUID = 1L;

	@ExcelField(title = "保险年份", type = 0, align = 2, sort = 0)
	private String timeYear;		// 保险年份

	@ExcelField(title = "姓名", type = 0, align = 2, sort = 1)
	private String name;		// 姓名

	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 2)
	private String idNumber;		// 身份证号

	@ExcelField(title = "养老保险缴费基数", type = 0, align = 2, sort = 3)
	private Double cardinalNumber;		// 养老保险缴费基数

	@ExcelField(title = "养老金个人缴费比例", type = 0, align = 2, sort = 4)
	private Double individualProportion;		// 养老金个人缴费比例

//	@ExcelField(title = "养老金个人月缴费", type = 0, align = 2, sort = 4)
	private Double individualPayment;		// 养老金个人月缴费

	@ExcelField(title = "养老金单位缴费比例", type = 0, align = 2, sort = 5)
	private Double unitProportion;		// 养老金单位缴费比例

//	@ExcelField(title = "养老金单位月缴费", type = 0, align = 2, sort = 5)
	private Double unitPayment;		// 养老金单位月缴费

	@ExcelField(title = "职业年金个人缴费比例", type = 0, align = 2, sort = 6)
	private Double oaIndividualProportion;		// 职业年金个人缴费比例

//	@ExcelField(title = "职业年金个人月缴费", type = 0, align = 2, sort = 6)
	private Double oaIndividualPayment;		// 职业年金个人月缴费

	@ExcelField(title = "职业年金单位缴费比例", type = 0, align = 2, sort = 7)
	private Double oaUnitProportion;		// 职业年金单位缴费比例

//	@ExcelField(title = "职业年金单位月缴费", type = 0, align = 2, sort = 7)
	private Double oaUnitPayment;

	private Double maxNumber;

	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private String userId;
	private String unit;
	private String unitId;
	
	public PersonnelEndowmentInsurance() {
		super();
	}

	public PersonnelEndowmentInsurance(String id){
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

	public Double getOaIndividualProportion() {
		return oaIndividualProportion;
	}

	public void setOaIndividualProportion(Double oaIndividualProportion) {
		this.oaIndividualProportion = oaIndividualProportion;
	}

	public Double getOaIndividualPayment() {
		return oaIndividualPayment;
	}

	public void setOaIndividualPayment(Double oaIndividualPayment) {
		this.oaIndividualPayment = oaIndividualPayment;
	}

	public Double getOaUnitProportion() {
		return oaUnitProportion;
	}

	public void setOaUnitProportion(Double oaUnitProportion) {
		this.oaUnitProportion = oaUnitProportion;
	}

	public Double getOaUnitPayment() {
		return oaUnitPayment;
	}

	public void setOaUnitPayment(Double oaUnitPayment) {
		this.oaUnitPayment = oaUnitPayment;
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

	public Double getMaxNumber() {
		return maxNumber;
	}

	public void setMaxNumber(Double maxNumber) {
		this.maxNumber = maxNumber;
	}
}