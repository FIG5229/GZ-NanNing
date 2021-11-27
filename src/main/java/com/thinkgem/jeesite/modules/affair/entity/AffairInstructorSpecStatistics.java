/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 教官特长统计报表Entity
 * @author kevin.jia
 * @version 2020-08-10
 */
public class AffairInstructorSpecStatistics extends DataEntity<AffairInstructorSpecStatistics> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "身份证号", type = 0, align = 1, sort = 2)
	private String idNumber;		// 身份证号
	@ExcelField(title = "教官姓名", type = 0, align = 1, sort = 1)
	private String name;		// 教官姓名
	@ExcelField(title = "特长分类", type = 0, align = 1, sort = 4)
	private String specialityClass;		// 特长分类
	@ExcelField(title = "特长", type = 0, align = 1, sort = 5)
	private String speciality;		// 特长
	private String unitId;		// 机构id
	@ExcelField(title = "所属机构", type = 0, align = 1, sort = 3)
	private String unitName;		// 机构名称

	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	
	public AffairInstructorSpecStatistics() {
		super();
	}

	public AffairInstructorSpecStatistics(String id){
		super(id);
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getSpecialityClass() {
		return specialityClass;
	}

	public void setSpecialityClass(String specialityClass) {
		this.specialityClass = specialityClass;
	}
	
	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}
	
	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	
	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
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