/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 个人事项报告表Entity
 * @author mason.xv
 * @version 2019-11-06
 */
public class AffairItemReport extends DataEntity<AffairItemReport> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 0)
	private String name;		// 姓名
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 1)
	private String idNumber;		// 身份证号
	@ExcelField(title = "单位名称", type = 0, align = 2, sort = 2)
	private String unit;		// 单位名称

	private String unitId;		// 单位机构id
	@ExcelField(title = "警号", type = 0, align = 2, sort = 3)
	private String policeNo;		// 警号
	@ExcelField(title = "婚姻状况", type = 0, align = 2, sort = 4, dictType="affair_marital_status")
	private String marriageStatus;		// 婚姻状况
	@ExcelField(title = "户口性质", type = 0, align = 2, sort = 5)
	private String hk;		// 户口性质
	@ExcelField(title = "户籍所在地详址", type = 0, align = 2, sort = 6)
	private String address;		// 户籍所在地详址
	@ExcelField(title = "审核状态", type = 0, align = 2, sort = 7, dictType="affair_query_shenhe")
	private String status;		// 审核状态



	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String shPerson;        //审核人
	private String hasAuth;       //审核权限
	private String opinion;         //审核意见

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public String getShPerson() {
		return shPerson;
	}

	public void setShPerson(String shPerson) {
		this.shPerson = shPerson;
	}

	public String getHasAuth() {
		return hasAuth;
	}

	public void setHasAuth(String hasAuth) {
		this.hasAuth = hasAuth;
	}

	public AffairItemReport() {
		super();
	}

	public AffairItemReport(String id){
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
	
	public String getPoliceNo() {
		return policeNo;
	}

	public void setPoliceNo(String policeNo) {
		this.policeNo = policeNo;
	}
	
	public String getMarriageStatus() {
		return marriageStatus;
	}

	public void setMarriageStatus(String marriageStatus) {
		this.marriageStatus = marriageStatus;
	}
	
	public String getHk() {
		return hk;
	}

	public void setHk(String hk) {
		this.hk = hk;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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