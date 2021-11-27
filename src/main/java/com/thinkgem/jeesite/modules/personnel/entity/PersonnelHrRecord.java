/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 人事档案管理信息集Entity
 * @author cecil.li
 * @version 2019-11-12
 */
public class PersonnelHrRecord extends DataEntity<PersonnelHrRecord> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 0)
	private String name;			//姓名
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 1)
	private String idNumber;		// 身份证号
	@ExcelField(title = "职务", type = 0, align = 2, sort = 2)
	private String duty;			//职务
	@ExcelField(title = "级别", type = 0, align = 2, sort = 3)
	private String level;			//级别
	@ExcelField(title = "部门", type = 0, align = 2, sort = 4)
	private String department;		//部门
	@ExcelField(title = "档案转入日期", type = 0, align = 2, sort = 5)
	private Date intoDate;		// 档案转入日期
	@ExcelField(title = "档案来处", type = 0, align = 2, sort = 6)
	private String source;		// 档案来处
	@ExcelField(title = "档案管理单位", type = 0, align = 2, sort = 7, dictType="personnel_daunit")
	private String unit;        //档案管理单位
	@ExcelField(title = "档案编号", type = 0, align = 2, sort = 8)
	private String recordNo;		// 档案编号
	@ExcelField(title = "档案版本类别", type = 0, align = 2, sort = 9, dictType="personnel_banben")
	private String type;		// 档案版本类别
	@ExcelField(title = "档案转出日期", type = 0, align = 2, sort = 10)
	private Date outDate;		// 档案转出日期
	@ExcelField(title = "档案去处", type = 0, align = 2, sort =11)
	private String toPlace;		// 档案去处
	@ExcelField(title = "正本卷数", type = 0, align = 2, sort = 12)
	private Integer zNum;		// 正本卷数
	@ExcelField(title = "副本卷数", type = 0, align = 2, sort = 13)
	private Integer fNum2;		// 副本卷数
	@ExcelField(title = "档案位置", type = 0, align = 2, sort = 14, dictType="personnel_daunit")
	private String location;		// 档案位置
	@ExcelField(title = "档案条形码号", type = 0, align = 2, sort = 15)
	private String barCodeNo;		// 档案条形码号
	@ExcelField(title = "档案备注", type = 0, align = 2, sort = 16)
	private String remark;		// 档案备注
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号




	private String userOffice;
	private Date startDate;
	private Date endDate;

	public String getDepartment() {
		return department;
	}


	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PersonnelHrRecord() {
		super();
	}

	public PersonnelHrRecord(String id){
		super(id);
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getIntoDate() {
		return intoDate;
	}

	public void setIntoDate(Date intoDate) {
		this.intoDate = intoDate;
	}
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	public String getRecordNo() {
		return recordNo;
	}

	public void setRecordNo(String recordNo) {
		this.recordNo = recordNo;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getOutDate() {
		return outDate;
	}

	public void setOutDate(Date outDate) {
		this.outDate = outDate;
	}
	
	public String getToPlace() {
		return toPlace;
	}

	public void setToPlace(String toPlace) {
		this.toPlace = toPlace;
	}

	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getBarCodeNo() {
		return barCodeNo;
	}

	public void setBarCodeNo(String barCodeNo) {
		this.barCodeNo = barCodeNo;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

		public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getZNum() {
		return zNum;
	}

	public void setZNum(Integer zNum) {
		this.zNum = zNum;
	}

	public Integer getFNum2() {
		return fNum2;
	}

	public void setFNum2(Integer fNum2) {
		this.fNum2 = fNum2;
	}

	public String getUserOffice() {
		return userOffice;
	}

	public void setUserOffice(String userOffice) {
		this.userOffice = userOffice;
	}
}