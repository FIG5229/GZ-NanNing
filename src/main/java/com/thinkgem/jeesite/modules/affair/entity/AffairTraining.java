/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import java.sql.Timestamp;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 练兵比武Entity
 * @author alan.wu
 * @version 2020-07-15
 */
public class AffairTraining extends DataEntity<AffairTraining> {
	
	private static final long serialVersionUID = 1L;

	@ExcelField(title = "考核比武名称", type = 0, align = 2, sort = 0)
	private String title;		// 标题
	@ExcelField(title = "类型", type = 0, align = 2, sort = 1)
	private String type;		// 类型
	@ExcelField(title = "层次", type = 0, align = 2, sort = 2)
	private String level;		// 层次
	@ExcelField(title = "主办部门", type = 0, align = 2, sort = 3)
	private String unit;		// 主办部门
	@ExcelField(title = "开始日期", type = 0, align = 2, sort = 4)
	private Date beginTime;		// 开始日期
	@ExcelField(title = "结束日期", type = 0, align = 2, sort = 5)
	private Date endTime;		// 结束日期
	@ExcelField(title = "描述", type = 0, align = 2, sort = 6)
	private String description;		// 描述
	@ExcelField(title = "状态", type = 0, align = 2, sort = 7)
	private String state;		// 状态
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String unitId;		// 主办部门id

	public AffairTraining() {
		super();
	}

	public AffairTraining(String id){
		super(id);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
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
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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
	
	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	
}