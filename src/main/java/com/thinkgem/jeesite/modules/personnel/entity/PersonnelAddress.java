/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 住址通信信息集Entity
 * @author cecil.li
 * @version 2019-11-11
 */
public class PersonnelAddress extends DataEntity<PersonnelAddress> {
	
	private static final long serialVersionUID = 1L;
	private String personnelName;		// 姓名
	@ExcelField(title = "工作单位通信地址", type = 0, align = 2, sort = 0)
	private String unitAdress;		// 工作单位通信地址
	@ExcelField(title = "工作单位邮政编码", type = 0, align = 2, sort = 1)
	private String unitPostCode;		// 工作单位邮政编码
	@ExcelField(title = "工作电话", type = 0, align = 2, sort = 2)
	private String workTel;		// 工作电话
	@ExcelField(title = "家庭住址", type = 0, align = 2, sort = 3)
	private String familyAddress;		// 家庭住址
	@ExcelField(title = "个人电子信箱", type = 0, align = 2, sort = 4)
	private String email;		// 个人电子信箱
	@ExcelField(title = "内部电话（小号或专线）", type = 0, align = 2, sort = 5)
	private String innerTel;		// 内部电话（小号或专线）
	@ExcelField(title = "移动电话号码", type = 0, align = 2, sort = 6)
	private String mobilePhone;		// 移动电话号码
	@ExcelField(title = "家庭电话", type = 0, align = 2, sort = 7)
	private String homeTel;		// 家庭电话
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 8)
	private String idNumber;		// 身份证号


	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	
	public PersonnelAddress() {
		super();
	}

	public String getPersonnelName() {
		return personnelName;
	}

	public void setPersonnelName(String personnelName) {
		this.personnelName = personnelName;
	}

	public PersonnelAddress(String id){
		super(id);
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public String getUnitAdress() {
		return unitAdress;
	}

	public void setUnitAdress(String unitAdress) {
		this.unitAdress = unitAdress;
	}
	
	public String getUnitPostCode() {
		return unitPostCode;
	}

	public void setUnitPostCode(String unitPostCode) {
		this.unitPostCode = unitPostCode;
	}
	
	public String getWorkTel() {
		return workTel;
	}

	public void setWorkTel(String workTel) {
		this.workTel = workTel;
	}
	
	public String getFamilyAddress() {
		return familyAddress;
	}

	public void setFamilyAddress(String familyAddress) {
		this.familyAddress = familyAddress;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getInnerTel() {
		return innerTel;
	}

	public void setInnerTel(String innerTel) {
		this.innerTel = innerTel;
	}
	
	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	
	public String getHomeTel() {
		return homeTel;
	}

	public void setHomeTel(String homeTel) {
		this.homeTel = homeTel;
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