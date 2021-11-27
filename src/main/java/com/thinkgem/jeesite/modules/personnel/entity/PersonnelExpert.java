/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 评审专家经历信息集Entity
 * @author cecil.li
 * @version 2019-11-11
 */
public class PersonnelExpert extends DataEntity<PersonnelExpert> {
	
	private static final long serialVersionUID = 1L;
	private String personnelName;		// 姓名
	@ExcelField(title = "评审层次", type = 0, align = 2, sort = 0)
	private String level;		// 评审层次
	@ExcelField(title = "评审系列", type = 0, align = 2, sort = 1)
	private String series;		// 评审系列
	@ExcelField(title = "评委会职务", type = 0, align = 2, sort = 2)
	private String job;		// 评委会职务
	@ExcelField(title = "评审年度", type = 0, align = 2, sort = 3)
	private String year;		// 评审年度
	@ExcelField(title = "组建单位", type = 0, align = 2, sort = 4)
	private String unit;		// 组建单位
	@ExcelField(title = "评审会名称", type = 0, align = 2, sort = 5)
	private String name;		// 评审会名称
	@ExcelField(title = "评审会名称", type = 0, align = 2, sort = 6)
	private String idNumber;		// 身份证号

	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String unitId;		// 组件单位id

	public String getPersonnelName() {
		return personnelName;
	}

	public void setPersonnelName(String personnelName) {
		this.personnelName = personnelName;
	}

	public PersonnelExpert() {
		super();
	}

	public PersonnelExpert(String id){
		super(id);
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}
	
	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}
	
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	
}