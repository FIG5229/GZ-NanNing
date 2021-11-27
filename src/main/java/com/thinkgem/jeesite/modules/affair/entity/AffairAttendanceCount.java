/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 考勤数据统计Entity
 * @author cecil.li
 * @version 2020-06-01
 */
public class AffairAttendanceCount extends DataEntity<AffairAttendanceCount> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "单位", type = 0, align = 2, sort = 0)
	private String unit;		// 单位
	private String unitId;		// 单位id
	@ExcelField(title = "月份", type = 0, align = 2, sort = 2)
	private Integer date;		// 月份
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 3)
	private String name;		// 姓名
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 4)
	private String idNumber;		// 身份证号
	@ExcelField(title = "考勤请假天数", type = 0, align = 2, sort = 5)
	private String leaveDays;		// 考勤请假天数
	@ExcelField(title = "上报请假天数", type = 0, align = 2, sort = 6)
	private String acLeaveDays;		// 上报请假天数
	@ExcelField(title = "休假天数差", type = 0, align = 2, sort = 7)
	private String poorDaysOff;		// 休假天数差
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	@ExcelField(title = "年份", type = 0, align = 2, sort = 1)
	private Integer yearDate;		//年份
	private String userId;


	public AffairAttendanceCount() {
		super();
	}

	public AffairAttendanceCount(String id){
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
	
	public String getLeaveDays() {
		return leaveDays;
	}

	public void setLeaveDays(String leaveDays) {
		this.leaveDays = leaveDays;
	}
	
	public String getAcLeaveDays() {
		return acLeaveDays;
	}

	public void setAcLeaveDays(String acLeaveDays) {
		this.acLeaveDays = acLeaveDays;
	}
	
	public String getPoorDaysOff() {
		return poorDaysOff;
	}

	public void setPoorDaysOff(String poorDaysOff) {
		this.poorDaysOff = poorDaysOff;
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


	public Integer getDate() {
		return date;
	}

	public void setDate(Integer date) {
		this.date = date;
	}

	public Integer getYearDate() {
		return yearDate;
	}

	public void setYearDate(Integer yearDate) {
		this.yearDate = yearDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}