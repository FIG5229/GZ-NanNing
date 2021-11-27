/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 抚恤信息集Entity
 * @author cecil.li
 * @version 2019-11-13
 */
public class PersonnelPension extends DataEntity<PersonnelPension> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 0)
	private String idNumber;		// 身份证号
	private String personnelName;		// 姓名
	@ExcelField(title = "抚恤类别", type = 0, align = 2, sort = 1, dictType="personnel_fuxu")
	private String type;		// 抚恤类别
	@ExcelField(title = "抚恤日期", type = 0, align = 2, sort = 2)
	private Date date;		// 抚恤日期
	@ExcelField(title = "抚恤金额", type = 0, align = 2, sort = 3)
	private Double money;		// 抚恤金额
	@ExcelField(title = "抚恤标识", type = 0, align = 2, sort = 4, dictType="personnel_fxbs")
	private String identification;		// 抚恤标识
	@ExcelField(title = "抚恤单位名称", type = 0, align = 2, sort = 5)
	private String unitName;		// 抚恤单位名称
	@ExcelField(title = "抚恤单位代码", type = 0, align = 2, sort = 6)
	private String unitCode;		// 抚恤单位代码
	@ExcelField(title = "抚恤证书编号", type = 0, align = 2, sort = 7)
	private String certificateNo;		// 抚恤证书编号
	@ExcelField(title = "抚恤事由", type = 0, align = 2, sort = 8)
	private String reason;		// 抚恤事由
	@ExcelField(title = "备注", type = 0, align = 2, sort = 9)
	private String remark;		// 备注
	@ExcelField(title = "亲属姓名", type = 0, align = 2, sort = 10)
	private String relativeName;		// 亲属姓名
	@ExcelField(title = "亲属性别", type = 0, align = 2, sort = 11, dictType="sex")
	private String relativeSex;		// 亲属性别
	@ExcelField(title = "与民警关系", type = 0, align = 2, sort = 12, dictType="personnel_guanxi")
	private String relationship;		// 与民警关系
	@ExcelField(title = "工作单位及住址", type = 0, align = 2, sort = 13)
	private String address;		// 工作单位及住址
	@ExcelField(title = "亲属联系方式", type = 0, align = 2, sort = 14)
	private String relativeContact;		// 亲属联系方式
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private Date startDate;
	private Date endDate;

	public String getPersonnelName() {
		return personnelName;
	}

	public void setPersonnelName(String personnelName) {
		this.personnelName = personnelName;
	}

	public PersonnelPension() {
		super();
	}

	public PersonnelPension(String id){
		super(id);
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}
	
	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}
	
	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	
	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	
	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}
	
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getRelativeName() {
		return relativeName;
	}

	public void setRelativeName(String relativeName) {
		this.relativeName = relativeName;
	}
	
	public String getRelativeSex() {
		return relativeSex;
	}

	public void setRelativeSex(String relativeSex) {
		this.relativeSex = relativeSex;
	}
	
	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getRelativeContact() {
		return relativeContact;
	}

	public void setRelativeContact(String relativeContact) {
		this.relativeContact = relativeContact;
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

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}