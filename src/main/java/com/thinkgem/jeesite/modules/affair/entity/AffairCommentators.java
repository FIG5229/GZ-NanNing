/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.List;

/**
 * 网评员管理Entity
 * @author alan.wu
 * @version 2020-06-19
 */
public class AffairCommentators extends DataEntity<AffairCommentators> {
	
	private static final long serialVersionUID = 1L;


	@ExcelField(title = "姓名", type = 0, align = 2, sort = 0)
	private String name;		// 姓名
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 1)
	private String idNumber;		// 身份证号
	@ExcelField(title = "单位", type = 0, align = 2, sort = 2)
	private String unit;		// 单位
	private String unitId;		// 单位id
	@ExcelField(title = "职务", type = 0, align = 2, sort = 3)
	private String duty;		// 职务
	@ExcelField(title = "联系方式", type = 0, align = 2, sort = 4)
	private String phone;		// 联系方式
	@ExcelField(title = "账号情况", type = 0, align = 2, sort = 6)
	private String account;		// 网评员账号
	@ExcelField(title = "培训情况", type = 0, align = 2, sort = 5)
	private String description;		// 培训情况

	//字表数据，只做导出使用，不保存
	//@ExcelField(title = "平台名称", type = 0, align = 2, sort = 5)
	private List<String> platform;

	//字表数据，只做导出使用，不保存
	//@ExcelField(title = "平台账号", type = 0, align = 2, sort = 6)
	private List<String> platformAccount;

	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<String> getPlatform() {
		return platform;
	}

	public void setPlatform(List<String> platform) {
		this.platform = platform;
	}

	public List<String> getPlatformAccount() {
		return platformAccount;
	}

	public void setPlatformAccount(List<String> platformAccount) {
		this.platformAccount = platformAccount;
	}

	private List<AffairCommentatorsDeputy> affairCommentatorsDeputies;	//网评员管理副表

	public List<AffairCommentatorsDeputy> getAffairCommentatorsDeputies() {
		return affairCommentatorsDeputies;
	}

	public void setAffairCommentatorsDeputies(List<AffairCommentatorsDeputy> affairCommentatorsDeputies) {
		this.affairCommentatorsDeputies = affairCommentatorsDeputies;
	}

	public AffairCommentators() {
		super();
	}

	public AffairCommentators(String id){
		super(id);
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

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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