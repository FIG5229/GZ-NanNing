/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 其他党代表会Entity
 * @author daniel.liu
 * @version 2020-06-30
 */
public class AffairOtherPartyRepresentative extends DataEntity<AffairOtherPartyRepresentative> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 0)
	private String name;		// 姓名
	@ExcelField(title = "党组织", type = 0, align = 2, sort = 1)
	private String unit;		// 党组织
	private String unitId;		// 党组织id
	@ExcelField(title = "性别", type = 0, align = 2, sort = 2, dictType = "sex")
	private String sex;		// 性别
	@ExcelField(title = "民族", type = 0, align = 2, sort = 3, dictType = "nation")
	private String nation;		// 民族
	@ExcelField(title = "年龄", type = 0, align = 2, sort = 4)
	private String age;		// 年龄
	@ExcelField(title = "学历", type = 0, align = 2, sort = 5)
	private String education;		// 学历
	@ExcelField(title = "是否在职", type = 0, align = 2, sort = 6, dictType = "yes_no")
	private String isWork;		// 是否在职
	@ExcelField(title = "届次", type = 0, align = 2, sort = 7)
	private String session;		// 届次
	private String createOrgId;		// 创建者机构id
	private String updateOrgId;		// 更新者机构id

	//前端树
	private String treeId;


	//统计分析使用
	private String dateType;
	private Integer year;
	private Date dateStart;
	private Date dateEnd;
	private String month;


	public AffairOtherPartyRepresentative() {
		super();
	}

	public AffairOtherPartyRepresentative(String id){
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}
	
	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}
	
	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}
	
	public String getIsWork() {
		return isWork;
	}

	public void setIsWork(String isWork) {
		this.isWork = isWork;
	}
	
	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
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

	public String getTreeId() {
		return treeId;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}
}