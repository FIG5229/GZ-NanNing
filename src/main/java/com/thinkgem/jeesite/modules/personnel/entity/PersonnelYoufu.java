/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 优抚信息集Entity
 * @author cecil.li
 * @version 2019-11-13
 */
public class PersonnelYoufu extends DataEntity<PersonnelYoufu> {
	
	private static final long serialVersionUID = 1L;
	private String idNumber;		// 身份证号
	private String personnelName;		// 证照姓名
	private String pensionType;		// 抚恤类别
	private Date pensionDate;		// 抚恤日期
	private Double money;		// 抚恤金额
	private String pensionMark;		// 抚恤标识
	private String pensionUnit;		// 抚恤单位名称
	private String pensionCode;		// 抚恤单位代码
	private String pensionNum;		// 抚恤证书编号
	private String pensionCause;		// 抚恤事由
	private String remark;		// 备注
	private String relativesName;		// 亲属姓名
	private String relativesSex;		// 亲属性别
	private String relationship;		// 与民警关系
	private String address;		// 工作单位及住址
	private String contact;		// 联系方式
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

	public PersonnelYoufu() {
		super();
	}

	public PersonnelYoufu(String id){
		super(id);
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public String getPensionType() {
		return pensionType;
	}

	public void setPensionType(String pensionType) {
		this.pensionType = pensionType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getPensionDate() {
		return pensionDate;
	}

	public void setPensionDate(Date pensionDate) {
		this.pensionDate = pensionDate;
	}
	
	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}
	
	public String getPensionMark() {
		return pensionMark;
	}

	public void setPensionMark(String pensionMark) {
		this.pensionMark = pensionMark;
	}
	
	public String getPensionUnit() {
		return pensionUnit;
	}

	public void setPensionUnit(String pensionUnit) {
		this.pensionUnit = pensionUnit;
	}
	
	public String getPensionCode() {
		return pensionCode;
	}

	public void setPensionCode(String pensionCode) {
		this.pensionCode = pensionCode;
	}
	
	public String getPensionNum() {
		return pensionNum;
	}

	public void setPensionNum(String pensionNum) {
		this.pensionNum = pensionNum;
	}
	
	public String getPensionCause() {
		return pensionCause;
	}

	public void setPensionCause(String pensionCause) {
		this.pensionCause = pensionCause;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getRelativesName() {
		return relativesName;
	}

	public void setRelativesName(String relativesName) {
		this.relativesName = relativesName;
	}
	
	public String getRelativesSex() {
		return relativesSex;
	}

	public void setRelativesSex(String relativesSex) {
		this.relativesSex = relativesSex;
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
	
	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
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