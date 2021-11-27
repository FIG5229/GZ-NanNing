/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 商业保险Entity
 * @author alan.wu
 * @version 2020-07-03
 */
public class AffairCommercialInsurance extends DataEntity<AffairCommercialInsurance> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "年度", type = 0, align = 2, sort = 0)
	private String year;		// 年度
	@ExcelField(title = "险种", type = 0, align = 2, sort = 1)
	private String kind;		// 险种
	@ExcelField(title = "被保险人", type = 0, align = 2, sort = 2)
	private String name;		// 被保险人
	@ExcelField(title = "保单账号", type = 0, align = 2, sort = 3)
	private String accountNumber;		// 保单账号
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 4)
	private String idNumber;		// 身份证号
	@ExcelField(title = "出险经过", type = 0, align = 2, sort = 5)
	private String pass;		// 出险经过
	@ExcelField(title = "申报材料", type = 0, align = 2, sort = 6)
	private String materials;		// 申报材料
	@ExcelField(title = "认定情况", type = 0, align = 2, sort = 7)
	private String result;		// 认定情况
	@ExcelField(title = "赔付情况", type = 0, align = 2, sort = 8)
	private String compensate;		// 赔付情况
//	@ExcelField(title = "附件", type = 0, align = 2, sort = 9)
	private String adjunct;		// 附件
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String userId;
	
	public AffairCommercialInsurance() {
		super();
	}

	public AffairCommercialInsurance(String id){
		super(id);
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
	
	public String getMaterials() {
		return materials;
	}

	public void setMaterials(String materials) {
		this.materials = materials;
	}
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	public String getCompensate() {
		return compensate;
	}

	public void setCompensate(String compensate) {
		this.compensate = compensate;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}