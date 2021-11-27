/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 颁发证书Entity
 * @author jack.xu
 * @version 2020-07-22
 */
public class AffairCertificateIssue extends DataEntity<AffairCertificateIssue> {
	
	private static final long serialVersionUID = 1L;
	private String certificate;		// 证书
	private String way;		// 颁发途径
	private String explain;		// 说明
	private Date date;		// 颁发日期
	private String number;		// 编号
	private String createOrgId;		// 创建者机构id
	private String updateOrgId;		// 更新者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateIdNo;		// 更新者身份证号
	
	public AffairCertificateIssue() {
		super();
	}

	public AffairCertificateIssue(String id){
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