/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 特别抚恤金Entity
 * @author cecil.li
 * @version 2020-07-02
 */
public class AffairSpecial extends DataEntity<AffairSpecial> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "单位", type = 0, align = 2, sort = 0)
	private String unit;		// 单位
	private String unitId;		// 单位id
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 1)
	private String name;		// 姓名
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 2)
	private String idNumber;		// 身份证号
	@ExcelField(title = "党、团员", type = 0, align = 2, sort = 3,dictType = "affair_is_dang")
	private String personnelFlag;		// 是否是党、团员
	@ExcelField(title = "职务", type = 0, align = 2, sort = 4)
	private String job;		// 职务
	@ExcelField(title = "级别", type = 0, align = 2, sort = 5)
	private String level;		// 级别
	@ExcelField(title = "警衔", type = 0, align = 2, sort = 6)
	private String policeRank;		// 警衔
	@ExcelField(title = "参加工作时间", type = 0, align = 2, sort = 7)
	private Date date;		// 参加工作时间
	@ExcelField(title = "种类", type = 0, align = 2, sort = 8, dictType = "affair_special_type")
	private String type;		// 种类
	@ExcelField(title = "主要事迹", type = 0, align = 2, sort = 9)
	private String mainDeeds;		// 主要事迹
	@ExcelField(title = "申报金额", type = 0, align = 2, sort = 10)
	private Double money;		// 申报金额
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	@ExcelField(title = "批准时间", type = 0, align = 2, sort = 11)
	private Date aprovalDate;		// 批准时间
	@ExcelField(title = "附件", type = 0, align = 2, sort = 12)
	private String annex;		// 附件
	private Date beginDate;		// 开始 参加工作时间
	private Date endDate;		// 结束 参加工作时间
	private Date beginAprovalDate;		// 开始 批准时间
	private Date endAprovalDate;		// 结束 批准时间
	private String userId;
	
	public AffairSpecial() {
		super();
	}

	public AffairSpecial(String id){
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
	
	public String getPersonnelFlag() {
		return personnelFlag;
	}

	public void setPersonnelFlag(String personnelFlag) {
		this.personnelFlag = personnelFlag;
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
	
	public String getPoliceRank() {
		return policeRank;
	}

	public void setPoliceRank(String policeRank) {
		this.policeRank = policeRank;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getMainDeeds() {
		return mainDeeds;
	}

	public void setMainDeeds(String mainDeeds) {
		this.mainDeeds = mainDeeds;
	}
	
	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
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
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getAprovalDate() {
		return aprovalDate;
	}

	public void setAprovalDate(Date aprovalDate) {
		this.aprovalDate = aprovalDate;
	}
	
	public String getAnnex() {
		return annex;
	}

	public void setAnnex(String annex) {
		this.annex = annex;
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
		
	public Date getBeginAprovalDate() {
		return beginAprovalDate;
	}

	public void setBeginAprovalDate(Date beginAprovalDate) {
		this.beginAprovalDate = beginAprovalDate;
	}
	
	public Date getEndAprovalDate() {
		return endAprovalDate;
	}

	public void setEndAprovalDate(Date endAprovalDate) {
		this.endAprovalDate = endAprovalDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}