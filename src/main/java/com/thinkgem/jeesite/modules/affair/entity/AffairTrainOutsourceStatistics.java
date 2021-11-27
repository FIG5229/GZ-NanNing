/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 委外培训统计Entity
 * @author alan.wu
 * @version 2020-07-30
 */
public class AffairTrainOutsourceStatistics extends DataEntity<AffairTrainOutsourceStatistics> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "主办单位",type = 0, align = 2, sort = 0)
	private String unit;		// 主办单位
	private String unitId;		// 主办单位id
	@ExcelField(title = "人数",type = 0, align = 2, sort = 1)
	private String peopleSum;		// 人数
	@ExcelField(title = "全脱产",type = 0, align = 2, sort = 2)
	private String offJob;		// 全脱产
	@ExcelField(title = "半脱产",type = 0, align = 2, sort = 3)
	private String halfJob;		// 半脱产
	@ExcelField(title = "不脱产",type = 0, align = 2, sort = 4)
	private String notJob;		// 不脱产
	@ExcelField(title = "公安部",type = 0, align = 2, sort = 5)
	private String hq;		// 公安部
	@ExcelField(title = "公安省部",type = 0, align = 2, sort = 6)
	private String provincial;		// 公安省部
	@ExcelField(title = "公安市部",type = 0, align = 2, sort = 7)
	private String city;		// 公安市部
	@ExcelField(title = "公安县部",type = 0, align = 2, sort = 8)
	private String county;		// 公安县部
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String userId;
	private String officeId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public AffairTrainOutsourceStatistics() {
		super();
	}

	public AffairTrainOutsourceStatistics(String id){
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
	
	public String getPeopleSum() {
		return peopleSum;
	}

	public void setPeopleSum(String peopleSum) {
		this.peopleSum = peopleSum;
	}
	
	public String getOffJob() {
		return offJob;
	}

	public void setOffJob(String offJob) {
		this.offJob = offJob;
	}
	
	public String getHalfJob() {
		return halfJob;
	}

	public void setHalfJob(String halfJob) {
		this.halfJob = halfJob;
	}
	
	public String getNotJob() {
		return notJob;
	}

	public void setNotJob(String notJob) {
		this.notJob = notJob;
	}
	
	public String getHq() {
		return hq;
	}

	public void setHq(String hq) {
		this.hq = hq;
	}
	
	public String getProvincial() {
		return provincial;
	}

	public void setProvincial(String provincial) {
		this.provincial = provincial;
	}
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
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