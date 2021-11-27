/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import javax.naming.ldap.PagedResultsControl;

/**
 * 证书模板管理Entity
 * @author jack.xu
 * @version 2020-07-23
 */
public class AffairCertificateManagement extends DataEntity<AffairCertificateManagement> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "证书名称", type = 0, align = 2, sort = 0)
	private String certificateName;		// 证书名称
	@ExcelField(title = "颁证机构名称",type = 0, align = 2, sort = 1)
	private String unit;		// 颁证机构名称
	@ExcelField(title = "证书类型",type = 0, align = 2, sort = 2, dictType = "certificate_type")
	private String type;		// 证书类型
	@ExcelField(title = "说明", type = 0,align = 2, sort = 3)
	private String status;		// 发布状态
	@ExcelField(title = "发布日期", type = 0,align = 2, sort = 4)
	private Date date;		// 发布日期
	@ExcelField(title = "有效月份", type = 0,align = 2, sort = 5)
	private String month;		// 有效月份
	@ExcelField(title = "证书编号规则", type = 0,align = 2, sort = 6)
	private String number;		// 证书编号规则
	@ExcelField(title = "证书背景图", type = 0,align = 2, sort = 7)
	private String background;		// 证书背景图
	@ExcelField(title = "说明", type = 0,align = 2, sort = 8)
	private String explain;		// 说明
	@ExcelField(title = "证书模板内容", type = 0,align = 2, sort = 9)
	private String content;		// 证书模板内容
	private String unitId;		// 单位id
	private String createOrgId;		// 创建者机构id
	private String updateOrgId;		// 更新者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateIdNo;		// 更新者身份证号

	private Date startDate;
	private Date endDate;
	public AffairCertificateManagement() {
		super();
	}

	public AffairCertificateManagement(String id){
		super(id);
	}

	public String getCertificateName() {
		return certificateName;
	}

	public void setCertificateName(String certificateName) {
		this.certificateName = certificateName;
	}
	
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}
	
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}
	
	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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