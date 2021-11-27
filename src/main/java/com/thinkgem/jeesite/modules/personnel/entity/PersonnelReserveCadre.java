/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 后备干部信息集Entity
 * @author cecil.li
 * @version 2019-11-13
 */
public class PersonnelReserveCadre extends DataEntity<PersonnelReserveCadre> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 0)
	private String idNumber;		// 身份证号
	@ExcelField(title = "呈报单位名称", type = 0, align = 2, sort = 1)
	private String unitName;		// 呈报单位名称
	@ExcelField(title = "呈报单位代码", type = 0, align = 2, sort = 2)
	private String unitCode;		// 呈报单位代码
	@ExcelField(title = "呈报日期", type = 0, align = 2, sort = 3)
	private Date date;		// 呈报日期
	@ExcelField(title = "培养目标（方向）", type = 0, align = 2, sort = 4)
	private String target;		// 培养目标（方向）
	@ExcelField(title = "培养目标任职机构类别", type = 0, align = 2, sort = 5, dictType="personnel_pymbrzjglb")
	private String targetOrgType;		// 培养目标任职机构类别
	@ExcelField(title = "培养目标职务级别", type = 0, align = 2, sort = 6, dictType="personnel_pymbzwjb")
	private String targetJobLevel;		// 培养目标职务级别
	@ExcelField(title = "培养目标职务类别", type = 0, align = 2, sort = 7, dictType="personnel_pymbzwlb")
	private String targetJobTypr;		// 培养目标职务类别
	@ExcelField(title = "后备类别", type = 0, align = 2, sort = 8, dictType="personnel_hblb")
	private String reserveType;		// 后备类别
	@ExcelField(title = "当前状态", type = 0, align = 2, sort = 9, dictType="personnel_dqzt")
	private String status;		// 当前状态
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String personName;  // 姓名

	private Date startDate;
	private Date endDate;
	
	public PersonnelReserveCadre() {
		super();
	}

	public PersonnelReserveCadre(String id){
		super(id);
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
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
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
	
	public String getTargetOrgType() {
		return targetOrgType;
	}

	public void setTargetOrgType(String targetOrgType) {
		this.targetOrgType = targetOrgType;
	}
	
	public String getTargetJobLevel() {
		return targetJobLevel;
	}

	public void setTargetJobLevel(String targetJobLevel) {
		this.targetJobLevel = targetJobLevel;
	}
	
	public String getTargetJobTypr() {
		return targetJobTypr;
	}

	public void setTargetJobTypr(String targetJobTypr) {
		this.targetJobTypr = targetJobTypr;
	}
	
	public String getReserveType() {
		return reserveType;
	}

	public void setReserveType(String reserveType) {
		this.reserveType = reserveType;
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

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}
}