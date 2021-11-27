/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 证书管理员Entity
 * @author jack.xu
 * @version 2020-07-27
 */
public class AffairCertificateAdministrator extends DataEntity<AffairCertificateAdministrator> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "用户编号", type = 0, align = 2, sort = 0)
	private String number;		// 用户编号
	@ExcelField(title = "用户姓名",type = 0,align = 2,sort = 1)
	private String name;		// 用户姓名
	@ExcelField(title = "机构全路径",type = 0, align = 2,sort = 2)
	private String unit;		// 机构全路径
	private String unitId;		// 机构id
	private String createOrgId;		// 创建者机构id
	private String updateOrgId;		// 更新者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateIdNo;		// 更新者身份证号
	
	public AffairCertificateAdministrator() {
		super();
	}

	public AffairCertificateAdministrator(String id){
		super(id);
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
	public String getCreateIdNo() {
		return createIdNo;
	}

	public void setCreateIdNo(String createIdNo) {
		this.createIdNo = createIdNo;
	}
	
	public String getUpdateIdNo() {
		return updateIdNo;
	}

	public void setUpdateIdNo(String updateIdNo) {
		this.updateIdNo = updateIdNo;
	}
	
}