/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 一帮一Entity
 * @author mason.xv
 * @version 2020-04-15
 * 存储标题单位字段，其他字段之前就有 未删除，保存时，其他字段为空
 * 	其他字段导入导出时使用
 */
public class AffairOneHelpOne extends DataEntity<AffairOneHelpOne> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "序号", type = 0, align = 2, sort = 0)
	private String number;			//序号

	@ExcelField(title = "标题", type = 0, align = 2, sort = 1)
	private String title;		// 标题

	@ExcelField(title = "单位", type = 0, align = 2, sort = 2)
	private String unit;		// 单位
	private String unitId;		// 单位id
	private Date date;		// 慰问时间
	@ExcelField(title = "帮扶人姓名", type = 0, align = 2, sort = 3)
	private String name;		// 帮扶人姓名
	@ExcelField(title = "职务", type = 0, align = 2, sort = 4)
	private String job;		// 职务
	@ExcelField(title = "被帮扶姓名", type = 0, align = 2, sort = 5)
	private String beName;		// 被帮扶姓名
	@ExcelField(title = "单位职务", type = 0, align = 2, sort = 6)
	private String unitJob;		// 单位职务
	@ExcelField(title = "被帮扶人情况", type = 0, align = 2, sort = 7)
	private String situation;		// 被帮扶人情况
	@ExcelField(title = "被帮扶人住址", type = 0, align = 2, sort = 8)
	private String address;		// 被帮扶人住址
	@ExcelField(title = "帮扶金额", type = 0, align = 2, sort = 9)
	private String money;		// 帮扶金额
	@ExcelField(title = "联系电话", type = 0, align = 2, sort =  10)
	private String tel;		// 联系电话

	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private String userId;     //用户ID

	/**
	 * 一帮一明细子表
	 */
	private List<AffairOneHelpOneMain> oneHelpOneMainList;

	/**
	 * 导入数据保存时使用
	 */
	private String isImport;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	//未用 不知道干啥的，之前为单表，现在增加子表
	private List<AffairOneHelpOne> childrens ;

	public List<AffairOneHelpOne> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<AffairOneHelpOne> children) {
		this.childrens = children;
	}

	public AffairOneHelpOne() {
		super();
	}

	public AffairOneHelpOne(String id){
		super(id);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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
	
	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}
	
	public String getBeName() {
		return beName;
	}

	public void setBeName(String beName) {
		this.beName = beName;
	}
	
	public String getUnitJob() {
		return unitJob;
	}

	public void setUnitJob(String unitJob) {
		this.unitJob = unitJob;
	}
	
	public String getSituation() {
		return situation;
	}

	public void setSituation(String situation) {
		this.situation = situation;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}
	
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<AffairOneHelpOneMain> getOneHelpOneMainList() {
		return oneHelpOneMainList;
	}

	public void setOneHelpOneMainList(List<AffairOneHelpOneMain> oneHelpOneMainList) {
		this.oneHelpOneMainList = oneHelpOneMainList;
	}

	public String getIsImport() {
		return isImport;
	}

	public void setIsImport(String isImport) {
		this.isImport = isImport;
	}
}