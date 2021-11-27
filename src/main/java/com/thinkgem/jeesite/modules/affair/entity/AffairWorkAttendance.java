/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 考勤信息Entity
 * @author mason.xv
 * @version 2019-11-15
 */
public class AffairWorkAttendance extends DataEntity<AffairWorkAttendance> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 0)
	private String name;		// 姓名
	@ExcelField(title = "警种", type = 0, align = 2, sort = 2, dictType="affair_kaoqin_jingzhong")
	private String policeType;		// 警种
	@ExcelField(title = "当月工时", type = 0, align = 2, sort = 3)
	private Double hour;		// 当月工时
	@ExcelField(title = "缺勤", type = 0, align = 2, sort =4)
	private Double absence;		// 缺勤
	@ExcelField(title = "工伤", type = 0, align = 2, sort = 5)
	private Double jobInjury;		// 工伤
	@ExcelField(title = "年休", type = 0, align = 2, sort = 6)
	private Double annualRest;		// 年休
	@ExcelField(title = "出差", type = 0, align = 2, sort = 7)
	private Double evection;		// 出差
	@ExcelField(title = "执勤", type = 0, align = 2, sort = 8)
	private Double onDuty;		// 执勤
	@ExcelField(title = "加班", type = 0, align = 2, sort = 9)
	private Double overtime;		// 加班
	@ExcelField(title = "零星加班", type = 0, align = 2, sort = 10)
	private Double lxOvertime;		// 零星加班
	@ExcelField(title = "单位", type = 0, align = 2, sort = 11)
	private String unit;		// 单位

	private String unitId;		// 单位id

	@ExcelField(title = "日期", type = 0, align = 2, sort = 12)
	private Date date;		// 日期

	private String year;		// 年
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 12)
	private String idNumber;		// 身份证号


	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private Date beginDate;		// 开始 日期
	private Date endDate;		// 结束 日期

	public Double getAbsence() {
		return absence;
	}

	public Double getJobInjury() {
		return jobInjury;
	}

	public Double getAnnualRest() {
		return annualRest;
	}

	public Double getEvection() {
		return evection;
	}

	public Double getOnDuty() {
		return onDuty;
	}

	public Double getOvertime() {
		return overtime;
	}

	public Double getLxOvertime() {
		return lxOvertime;
	}

	public Double getHour() {
		return hour;
	}

	public void setHour(Double hour) {
		this.hour = hour;
	}

	public void setAbsence(Double absence) {
		this.absence = absence;
	}

	public void setJobInjury(Double jobInjury) {
		this.jobInjury = jobInjury;
	}

	public void setAnnualRest(Double annualRest) {
		this.annualRest = annualRest;
	}

	public void setEvection(Double evection) {
		this.evection = evection;
	}

	public void setOnDuty(Double onDuty) {
		this.onDuty = onDuty;
	}

	public void setOvertime(Double overtime) {
		this.overtime = overtime;
	}

	public void setLxOvertime(Double lxOvertime) {
		this.lxOvertime = lxOvertime;
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
	
	public String getPoliceType() {
		return policeType;
	}

	public void setPoliceType(String policeType) {
		this.policeType = policeType;
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
	
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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
	
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
		
}