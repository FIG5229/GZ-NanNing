/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 典型集体走访记录Entity
 * @author daniel.liu
 * @version 2020-07-31
 */
public class AffairTypicalTeamVisit extends DataEntity<AffairTypicalTeamVisit> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "时间", type = 0, align = 2, sort = 1)
	private Date time;			//时间
	@ExcelField(title = "走访人", type = 0, align = 2, sort = 2)
	private String visitors;		// 走访人
	@ExcelField(title = "单位", type = 0, align = 2, sort =3)
	private String unit;		// 单位
	private String unitId;		// 单位Id
	@ExcelField(title = "职务", type = 0, align = 2, sort = 4)
	private String job;		// 职务
	@ExcelField(title = "检验情况", type = 0, align = 2, sort = 5)
	private String inspection;		// 检验情况
	private String createOrgId;		// 创建者机构id
	private String updateOrgId;		// 更新者机构id
	private String typicalTeamId;		// 典型集体主键
	
	public AffairTypicalTeamVisit() {
		super();
	}

	public AffairTypicalTeamVisit(String id){
		super(id);
	}

	public String getVisitors() {
		return visitors;
	}

	public void setVisitors(String visitors) {
		this.visitors = visitors;
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
	
	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}
	
	public String getInspection() {
		return inspection;
	}

	public void setInspection(String inspection) {
		this.inspection = inspection;
	}
	
	public String getCreateOrgId() {
		return createOrgId;
	}

	public void setCreateOrgId(String createOrgId) {
		this.createOrgId = createOrgId;
	}
	
	public String getUpdateOrgId() {
		return updateOrgId;
	}

	public void setUpdateOrgId(String updateOrgId) {
		this.updateOrgId = updateOrgId;
	}
	
	public String getTypicalTeamId() {
		return typicalTeamId;
	}

	public void setTypicalTeamId(String typicalTeamId) {
		this.typicalTeamId = typicalTeamId;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
}