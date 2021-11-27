/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 学习强国--年度统计Entity
 * @author Alan
 * @version 2020-06-08
 */
public class AffairLearnPowerYear extends DataEntity<AffairLearnPowerYear> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "单位", type = 0, align = 2, sort = 0)
	private String unit;		// 单位
	private String unitId;		// 单位id
	@ExcelField(title = "时间", type = 0, align = 2, sort = 1)
	private String time;		// 时间
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 2)
	private String name;		// 姓名
	@ExcelField(title = "身份证号码", type = 0, align = 2, sort = 3)
	private String idNumber;
	@ExcelField(title = "去年学习积分", type = 0, align = 2, sort = 4)
	private Double lastYearIntegral;		// 去年 学习积分
	@ExcelField(title = "本年及去年学习积分", type = 0, align = 2, sort = 5)
	private Double thisYearStatistics;		// 本年及去年 学习累计积分
	@ExcelField(title = "本年学习积分", type = 0, align = 2, sort = 6)
	private Double thisYearIntegral;		// 本年 学习积分

	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private Integer year;//查询用
	private Integer lastYear;//查询用

	public AffairLearnPowerYear() {
		super();
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public AffairLearnPowerYear(String id){
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
	
	@JsonFormat(pattern = "yyyy")
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getLastYearIntegral() {
		return lastYearIntegral;
	}

	public void setLastYearIntegral(Double lastYearIntegral) {
		this.lastYearIntegral = lastYearIntegral;
	}

	public Double getThisYearStatistics() {
		return thisYearStatistics;
	}

	public void setThisYearStatistics(Double thisYearStatistics) {
		this.thisYearStatistics = thisYearStatistics;
	}

	public Double getThisYearIntegral() {
		return thisYearIntegral;
	}

	public void setThisYearIntegral(Double thisYearIntegral) {
		this.thisYearIntegral = thisYearIntegral;
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

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getLastYear() {
		return lastYear;
	}

	public void setLastYear(Integer lastYear) {
		this.lastYear = lastYear;
	}
}