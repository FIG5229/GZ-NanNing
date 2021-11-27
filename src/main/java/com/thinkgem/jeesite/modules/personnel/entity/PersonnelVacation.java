/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 休假信息集Entity
 * @author cecil.li
 * @version 2019-11-11
 */
public class PersonnelVacation extends DataEntity<PersonnelVacation> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "休假种类", type = 0, align = 2, sort = 2, dictType="personnel_xjtype")
	private String vacationType;		// 休假种类
	@ExcelField(title = "休假起始日期", type = 0, align = 2, sort = 3)
	private Date startDate;		// 休假起始日期
	@ExcelField(title = "休假结束日期", type = 0, align = 2, sort = 4)
	private Date endDate;		// 休假结束日期
	@ExcelField(title = "实际休假天数", type = 0, align = 2, sort = 5)
	private Double day;		// 实际休假天数
	@ExcelField(title = "病假类别", type = 0, align = 2, sort = 6, dictType="personnel_xjtype")
	private String illnessType;		// 病假类别
	private	String unit;     //单位
	private String unitId;
	private String department;   //部门
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 0)
	private String name;  //姓名
	private String position;   //职务
	private String remark;   //备注
	private String rsopinion;   //人事意见
	private String bmopinion;    //部门意见
	private Date fakeDate;    //销假日期
	private Date reworkDate;    //恢复工作时间
	private String fakeDescription;   //销假说明
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 1)
	private String idNumber;		// 身份证号


	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private Date beginDate;
	private Date finishDate;
	
	public PersonnelVacation() {
		super();
	}

	public PersonnelVacation(String id){
		super(id);
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public String getVacationType() {
		return vacationType;
	}

	public void setVacationType(String vacationType) {
		this.vacationType = vacationType;
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
	
	public Double getDay() {
		return day;
	}

	public void setDay(Double day) {
		this.day = day;
	}
	
	public String getIllnessType() {
		return illnessType;
	}

	public void setIllnessType(String illnessType) {
		this.illnessType = illnessType;
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
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
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

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRsopinion() {
		return rsopinion;
	}

	public void setRsopinion(String rsopinion) {
		this.rsopinion = rsopinion;
	}

	public String getBmopinion() {
		return bmopinion;
	}

	public void setBmopinion(String bmopinion) {
		this.bmopinion = bmopinion;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getFakeDate() {
		return fakeDate;
	}

	public void setFakeDate(Date fakeDate) {
		this.fakeDate = fakeDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getReworkDate() {
		return reworkDate;
	}

	public void setReworkDate(Date reworkDate) {
		this.reworkDate = reworkDate;
	}

	public String getFakeDescription() {
		return fakeDescription;
	}

	public void setFakeDescription(String fakeDescription) {
		this.fakeDescription = fakeDescription;
	}
}