/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;
import java.util.List;

/**
 * 幼儿补助Entity
 * @author cecil.li
 * @version 2019-12-03
 */
public class AffairChildSubsidyChild extends DataEntity<AffairChildSubsidyChild> {

	private static final long serialVersionUID = 1L;
    /*@ExcelField(title = "时间", type = 0, align = 2, sort = 0)
	private Date date;		// 时间
    @ExcelField(title = "单位", type = 0, align = 2, sort = 1)
	private String unit;		// 单位
	private String unitId;		// 单位机构id
    @ExcelField(title = "民警姓名", type = 0, align = 2, sort = 2)
	private String name;		// 民警姓名
    @ExcelField(title = "身份证号", type = 0, align = 2, sort = 4)
	private String idNumber;		// 身份证号
    @ExcelField(title = "民警性别", type = 0, align = 2, sort = 5, dictType="sex")
	private String sex;		// 民警性别
    @ExcelField(title = "补助金额", type = 0, align = 2, sort = 6)
    private Double money;		// 补助金额
//    @ExcelField(title = "审核意见", type = 0, align = 2, sort = 6)
	private String opinion;		// 审核意见*/
    @ExcelField(title = "子女姓名", type = 0, align = 2, sort = 7)
	private String childName;     //子女姓名
    @ExcelField(title = "子女性别", type = 0, align = 2, sort = 8, dictType="sex")
    private String childSex;        //子女性别
	@ExcelField(title = "子女出生日期", type = 0, align = 2, sort = 9)
	private Date childBirthDay;       //子女出生日期
	@ExcelField(title = "子女现状", type = 0, align = 2, sort = 10)
	private String childNow;		// 子女现状
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
/*	private Date beginDate;		// 开始 时间
	private Date endDate;		// 结束 时间
	private Double beginMoney;		// 开始 补助金额
	private Double endMoney;		// 结束 补助金额
	@ExcelField(title = "职务", type = 0, align = 2, sort = 3)
    private String job;*/
	private String userId;         //获取当前账号单位

	private List<AffairChildSubsidyChild> affairChildSubsidyChildList = Lists.newArrayList();		// 子表列表

	public List<AffairChildSubsidyChild> getAffairChildSubsidyChildList() {
		return affairChildSubsidyChildList;
	}

	public void setAffairChildSubsidyChildList(List<AffairChildSubsidyChild> affairChildSubsidyChildList) {
		this.affairChildSubsidyChildList = affairChildSubsidyChildList;
	}

	/*前端标识 操作里添加子女*/
	private boolean handleAdd;

	public boolean isHandleAdd() {
		return handleAdd;
	}

	public void setHandleAdd(boolean handleAdd) {
		this.handleAdd = handleAdd;
	}

	public AffairChildSubsidyChild() {
		super();
	}

	public AffairChildSubsidyChild(String id){
		super(id);
	}
/*
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	
	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	*/
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
/*

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
		
	public Double getBeginMoney() {
		return beginMoney;
	}

	public void setBeginMoney(Double beginMoney) {
		this.beginMoney = beginMoney;
	}
	
	public Double getEndMoney() {
		return endMoney;
	}

	public void setEndMoney(Double endMoney) {
		this.endMoney = endMoney;
	}

*/

	public String getChildName() {
		return childName;
	}

	public void setChildName(String childName) {
		this.childName = childName;
	}

	public String getChildSex() {
		return childSex;
	}

	public void setChildSex(String childSex) {
		this.childSex = childSex;
	}
    @JsonFormat(pattern = "yyyy-MM-dd")
	public Date getChildBirthDay() {
		return childBirthDay;
	}

	public void setChildBirthDay(Date childBirthDay) {
		this.childBirthDay = childBirthDay;
	}

	public String getChildNow() {
		return childNow;
	}

	public void setChildNow(String childNow) {
		this.childNow = childNow;
	}
/*
	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}*/

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}