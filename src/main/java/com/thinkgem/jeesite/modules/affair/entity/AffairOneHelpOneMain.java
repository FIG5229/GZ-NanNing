/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 一帮一明细Entity
 * @author daniel.liu
 * @version 2020-06-23
 */
public class AffairOneHelpOneMain extends DataEntity<AffairOneHelpOneMain> {
	
	private static final long serialVersionUID = 1L;
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
	@ExcelField(title = "联系电话", type = 0, align = 2, sort = 10)
	private String tel;		// 联系电话
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String mainId;		// 关联表标记
	@ExcelField(title = "慰问时间", type = 0, align = 2, sort = 2)
	private Date date;		// 慰问时间


	//导出数据使用
	@ExcelField(title = "标题", type = 0, align = 2, sort = 0)
	private String title;		// 标题
	@ExcelField(title = "单位", type = 0, align = 2, sort = 1)
	private String unit;		// 单位

	public AffairOneHelpOneMain() {
		super();
	}

	public AffairOneHelpOneMain(String id){
		super(id);
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
	
	public String getMainId() {
		return mainId;
	}

	public void setMainId(String mainId) {
		this.mainId = mainId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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
}