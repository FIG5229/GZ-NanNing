/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 因公负伤Entity
 * @author cecil.li
 * @version 2020-07-03
 */
public class AffairInjuries extends DataEntity<AffairInjuries> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "单位", type = 0, align = 2, sort = 0)
	private String unit;		// 单位
	private String unitId;		// 单位id
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 1)
	private String name;		// 姓名
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 2)
	private String idNumber;		// 身份证号`
	@ExcelField(title = "职务", type = 0, align = 2, sort = 3)
	private String job;		// 职务
	@ExcelField(title = "职级", type = 0, align = 2, sort = 4)
	private String level;		// 职级
	@ExcelField(title = "发生时间", type = 0, align = 2, sort = 5)
	private Date happenDate;		// 发生时间
	@ExcelField(title = "公伤认定时间", type = 0, align = 2, sort = 6)
	private Date date;		// 公伤认定时间
	@ExcelField(title = "认定单位", type = 0, align = 2, sort = 7)
	private String rdUnit;		// 认定单位
	private String rdUnitId;		// 认定单位id
	@ExcelField(title = "认定情况", type = 0, align = 2, sort = 8)
	private String confirmation;		// 认定情况
	@ExcelField(title = "认定情况说明", type = 0, align = 2, sort = 9)
	private String explanation;		// 认定情况说明
	@ExcelField(title = "认定材料", type = 0, align = 2, sort = 10)
	private String material;		// 认定材料
	@ExcelField(title = "批复情况", type = 0, align = 2, sort = 11)
	private String approval;		// 批复情况
	@ExcelField(title = "抚恤待遇", type = 0, align = 2, sort = 12)
	private String pension;		// 抚恤待遇
	@ExcelField(title = "备注", type = 0, align = 2, sort = 13)
	private String remark;		// 备注
	@ExcelField(title = "附件", type = 0, align = 2, sort = 14)
	private String annex;		// 附件
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private Date beginHappenDate;		// 开始 发生时间
	private Date endHappenDate;		// 结束 发生时间
	private String userId;
	
	public AffairInjuries() {
		super();
	}

	public AffairInjuries(String id){
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
	
	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}
	
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getHappenDate() {
		return happenDate;
	}

	public void setHappenDate(Date happenDate) {
		this.happenDate = happenDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getRdUnit() {
		return rdUnit;
	}

	public void setRdUnit(String rdUnit) {
		this.rdUnit = rdUnit;
	}
	
	public String getRdUnitId() {
		return rdUnitId;
	}

	public void setRdUnitId(String rdUnitId) {
		this.rdUnitId = rdUnitId;
	}
	
	public String getConfirmation() {
		return confirmation;
	}

	public void setConfirmation(String confirmation) {
		this.confirmation = confirmation;
	}
	
	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}
	
	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}
	
	public String getApproval() {
		return approval;
	}

	public void setApproval(String approval) {
		this.approval = approval;
	}
	
	public String getPension() {
		return pension;
	}

	public void setPension(String pension) {
		this.pension = pension;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getAnnex() {
		return annex;
	}

	public void setAnnex(String annex) {
		this.annex = annex;
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
	
	public Date getBeginHappenDate() {
		return beginHappenDate;
	}

	public void setBeginHappenDate(Date beginHappenDate) {
		this.beginHappenDate = beginHappenDate;
	}
	
	public Date getEndHappenDate() {
		return endHappenDate;
	}

	public void setEndHappenDate(Date endHappenDate) {
		this.endHappenDate = endHappenDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}