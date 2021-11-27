/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 台账录入次数Entity
 * @author cecil.li
 * @version 2020-12-29
 */
public class AffairLedgerEntryTimes extends DataEntity<AffairLedgerEntryTimes> {
	
	private static final long serialVersionUID = 1L;
//	@ExcelField(title = "序号", type = 0, align = 2, sort = 0)
	private String number;  // 序号
	@ExcelField(title = "时间", type = 0, align = 2, sort = 0)
	private String yearMonth;  // 时间
	@ExcelField(title = "单位", type = 0, align = 2, sort = 1)
	private String unit;		// 单位
	private String unitId;		// 单位id
	@ExcelField(title = "一月", type = 0, align = 2, sort = 2)
	private String january;		// 一月
	@ExcelField(title = "二月", type = 0, align = 2, sort = 3)
	private String february;		// 二月
	@ExcelField(title = "三月", type = 0, align = 2, sort = 4)
	private String march;		// 三月
	@ExcelField(title = "四月", type = 0, align = 2, sort = 5)
	private String april;		// 四月
	@ExcelField(title = "五月", type = 0, align = 2, sort = 6)
	private String may;		// 五月
	@ExcelField(title = "六月", type = 0, align = 2, sort = 7)
	private String june;		// 六月
	@ExcelField(title = "七月", type = 0, align = 2, sort = 8)
	private String july;		// 七月
	@ExcelField(title = "八月", type = 0, align = 2, sort = 9)
	private String august;		// 八月
	@ExcelField(title = "九月", type = 0, align = 2, sort = 10)
	private String september;		// 九月
	@ExcelField(title = "十月", type = 0, align = 2, sort = 11)
	private String october;		// 十月
	@ExcelField(title = "十一月", type = 0, align = 2, sort = 12)
	private String november;		// 十一月
	@ExcelField(title = "十二月", type = 0, align = 2, sort = 13)
	private String december;		// 十二月
	@ExcelField(title = "备注", type = 0, align = 2, sort = 14)
	private String remark;		// 备注
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	
	public AffairLedgerEntryTimes() {
		super();
	}

	public AffairLedgerEntryTimes(String id){
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
	
	public String getJanuary() {
		return january;
	}

	public void setJanuary(String january) {
		this.january = january;
	}
	
	public String getFebruary() {
		return february;
	}

	public void setFebruary(String february) {
		this.february = february;
	}
	
	public String getMarch() {
		return march;
	}

	public void setMarch(String march) {
		this.march = march;
	}
	
	public String getApril() {
		return april;
	}

	public void setApril(String april) {
		this.april = april;
	}
	
	public String getMay() {
		return may;
	}

	public void setMay(String may) {
		this.may = may;
	}
	
	public String getJune() {
		return june;
	}

	public void setJune(String june) {
		this.june = june;
	}
	
	public String getJuly() {
		return july;
	}

	public void setJuly(String july) {
		this.july = july;
	}
	
	public String getAugust() {
		return august;
	}

	public void setAugust(String august) {
		this.august = august;
	}
	
	public String getSeptember() {
		return september;
	}

	public void setSeptember(String september) {
		this.september = september;
	}
	
	public String getOctober() {
		return october;
	}

	public void setOctober(String october) {
		this.october = october;
	}
	
	public String getNovember() {
		return november;
	}

	public void setNovember(String november) {
		this.november = november;
	}
	
	public String getDecember() {
		return december;
	}

	public void setDecember(String december) {
		this.december = december;
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

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}
}