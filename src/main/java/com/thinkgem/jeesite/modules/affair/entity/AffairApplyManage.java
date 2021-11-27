/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 报名管理Entity
 * @author alan.wu
 * @version 2020-07-07
 */
public class AffairApplyManage extends DataEntity<AffairApplyManage> {
	
	private static final long serialVersionUID = 1L;

	@ExcelField(title = "标题", type = 0, align = 2, sort = 0)
	private String title;		// 标题

	private String classId;			//班级id

	@ExcelField(title = "报名类型", type = 0, align = 2, sort = 1)
	private String type;		// 报名类型

	@ExcelField(title = "报名开始时间", type = 0, align = 2, sort = 2)
	private Date beginTime;		// 报名开始时间

	@ExcelField(title = "报名结束时间", type = 0, align = 2, sort = 3)
	private Date endTime;		// 报名结束时间

	@ExcelField(title = "人数限制", type = 0, align = 2, sort = 4)
	private String number;		// 人数限制（数值为0则不设限）

	@ExcelField(title = "报名方式", type = 0, align = 2, sort = 5)
	private String way;		// 报名方式

	@ExcelField(title = "报名说明", type = 0, align = 2, sort = 6)
	private String description;		// 报名说明

	private String adjunct;		// 附件
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String unit;		//单位
	private String unitId;			//单位id
	private String entrant;			//已报名人数


	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getEntrant() {
		return entrant;
	}

	public void setEntrant(String entrant) {
		this.entrant = entrant;
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

	public AffairApplyManage() {
		super();
	}

	public AffairApplyManage(String id){
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
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	public String getWay() {
		return way;
	}

	public void setWay(String way) {
		this.way = way;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getAdjunct() {
		return adjunct;
	}

	public void setAdjunct(String adjunct) {
		this.adjunct = adjunct;
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