/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 复查表Entity
 * @author alan.wu
 * @version 2020-08-03
 */
public class AffairSpiritualTable extends DataEntity<AffairSpiritualTable> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "单位", type = 0, align = 2, sort = 0)
	private String unit;		// 单位
	private String unitId;		// 单位id
	@ExcelField(title = "联系电话", type = 0, align = 2, sort = 0)
	private String phone;		// 联系电话
	@ExcelField(title = "单位地址", type = 0, align = 2, sort = 0)
	private String unitPlace;		// 单位地址
	@ExcelField(title = "检查成绩", type = 0, align = 2, sort = 0)
	private String number;		// 检查成绩
	@ExcelField(title = "抽查成绩", type = 0, align = 2, sort = 0)
	private String randomNumber;		// 抽查成绩
	@ExcelField(title = "创建成效简介", type = 0, align = 2, sort = 0)
	private String description;		// 创建成效简介
	@ExcelField(title = "存在问题", type = 0, align = 2, sort = 0)
	private String problem;		// 存在问题
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	@ExcelField(title = "复查建议", type = 0, align = 2, sort = 0)
	private String suggest;		// 复查建议
	@ExcelField(title = "年度", type = 0, align = 2, sort = 0)
	private String year;		// 年度
	@ExcelField(title = "批准层级", type = 0, align = 2, sort = 0)
	private String ratify;		// 批准层级
	@ExcelField(title = "批准时间", type = 0, align = 2, sort = 0)
	private Date time;		// 批准时间

	private Date beginTime;
	private Date endTime;

	private String adjunct;		// 附件

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public AffairSpiritualTable() {
		super();
	}

	public AffairSpiritualTable(String id){
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
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getUnitPlace() {
		return unitPlace;
	}

	public void setUnitPlace(String unitPlace) {
		this.unitPlace = unitPlace;
	}
	
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	public String getRandomNumber() {
		return randomNumber;
	}

	public void setRandomNumber(String randomNumber) {
		this.randomNumber = randomNumber;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getProblem() {
		return problem;
	}

	public void setProblem(String problem) {
		this.problem = problem;
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
	
	public String getSuggest() {
		return suggest;
	}

	public void setSuggest(String suggest) {
		this.suggest = suggest;
	}
	
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	public String getRatify() {
		return ratify;
	}

	public void setRatify(String ratify) {
		this.ratify = ratify;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	public String getAdjunct() {
		return adjunct;
	}

	public void setAdjunct(String adjunct) {
		this.adjunct = adjunct;
	}
	
}