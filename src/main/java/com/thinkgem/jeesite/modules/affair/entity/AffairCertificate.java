/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 证书Entity
 * @author jack.xu
 * @version 2020-07-22
 */
public class AffairCertificate extends DataEntity<AffairCertificate> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "证书",type = 0, align = 2, sort = 0)
	private String certificate;		// 证书
	@ExcelField(title = "颁发途径",type = 0, align = 2, sort = 1)
	private String way;		// 颁发途径
	@ExcelField(title = "说明",type = 0, align = 2, sort = 2)
	private String explain;		// 说明
	@ExcelField(title = "颁发日期",type = 0, align = 2, sort = 3)
	private Date date;		// 颁发日期
	@ExcelField(title = "证书编号",type = 0, align = 2, sort = 4)
	private String number;		// 证书编号
	@ExcelField(title = "工号",type = 0, align = 2, sort = 5)
	private String jobNumber;		// 工号
	@ExcelField(title = "用户姓名",type = 0, align = 2, sort = 6)
	private String userName;		// 用户姓名
	@ExcelField(title = "用户编号",type = 0, align = 2, sort = 7)
	private String userNumber;		// 用户编号
	@ExcelField(title = "单位",type = 0, align = 2, sort = 8)
	private String unit;		// 单位
	private String unitId;		// 单位id
	@ExcelField(title = "证书名称",type = 0, align = 2, sort = 9)
	private String certificateName;		// 证书名称
	private String createOrgId;		// 创建者机构id
	private String updateOrgId;		// 更新者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateIdNo;		// 更新者身份证号
	
	public AffairCertificate() {
		super();
	}

	public AffairCertificate(String id){
		super(id);
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}
	
	public String getWay() {
		return way;
	}

	public void setWay(String way) {
		this.way = way;
	}
	
	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	public String getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserNumber() {
		return userNumber;
	}

	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
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
	
	public String getCertificateName() {
		return certificateName;
	}

	public void setCertificateName(String certificateName) {
		this.certificateName = certificateName;
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