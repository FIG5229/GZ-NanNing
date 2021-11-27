/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 助学子女信息表Entity
 * @author daniel.liu
 * @version 2020-09-07
 */
public class AffairZxInfoChild extends DataEntity<AffairZxInfoChild> {
	
	private static final long serialVersionUID = 1L;
	private String zxInfoId;		// 助学信息id
	@ExcelField(title = "子女姓名", type = 0, align = 2, sort = 6)
	private String name;		// 子女姓名
	@ExcelField(title = "子女性别", type = 0, align = 2, sort = 7,dictType = "sex")
	private String sex;		// 子女性别
	private Date birthday;		// 子女出生年月日

	private String type;		// 补助类型
	@ExcelField(title = "就读学校", type = 0, align = 2, sort = 8)
	private String schoolMajor;		// 就读学校
	@ExcelField(title = "所学专业", type = 0, align = 2, sort = 10)
	private String major;		// 所学专业
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private Date beginBirthday;		// 开始 子女出生年月日
	private Date endBirthday;		// 结束 子女出生年月日

	@ExcelField(title = "学校类别", type = 0, align = 2, sort = 9,dictType = "affair_zxctype")
	private String schoolType;

	@ExcelField(title = "学年制", type = 0, align = 2, sort = 11,dictType = "affair_zx_year_system")
	private String yearSystem;
	@ExcelField(title = "就读年级", type = 0, align = 2, sort = 12)
	private String grade;

	/*导出使用*/
	@ExcelField(title = "时间", type = 0, align = 2, sort = 0)
	private Date date;		// 时间
	@ExcelField(title = "单位", type = 0, align = 2, sort = 1)
	private String unit;		// 单位
	private String unitId;		// 单位机构id
	@ExcelField(title = "民警姓名", type = 0, align = 2, sort = 4)
	private String policeName;		// 民警姓名
	private String idNumber;		// 身份证号
	private String policeSex;		// 民警性别
	@ExcelField(title = "补助类型", type = 0, align = 2, sort = 2,dictType = "affair_zxtype")
	private String policeType;		// 补助类型
	@ExcelField(title = "补助金额（元）", type = 0, align = 2, sort = 13)
	private Double money;		// 补助金额（元）


	/*9.7按照模板添加字段*/
	@ExcelField(title = "顺号", type = 0, align = 2, sort = 3)
	private String shun;	//顺号（不知道干啥用的）
	@ExcelField(title = "医保号", type = 0, align = 2, sort = 5)
	private String medicaNumber;	//医保号
	@ExcelField(title = "备注", type = 0, align = 2, sort = 14)
	private String remarks;		// 备注

	public AffairZxInfoChild() {
		super();
	}

	public AffairZxInfoChild(String id){
		super(id);
	}

	public String getZxInfoId() {
		return zxInfoId;
	}

	public void setZxInfoId(String zxInfoId) {
		this.zxInfoId = zxInfoId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getSchoolMajor() {
		return schoolMajor;
	}

	public void setSchoolMajor(String schoolMajor) {
		this.schoolMajor = schoolMajor;
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
	
	public Date getBeginBirthday() {
		return beginBirthday;
	}

	public void setBeginBirthday(Date beginBirthday) {
		this.beginBirthday = beginBirthday;
	}
	
	public Date getEndBirthday() {
		return endBirthday;
	}

	public void setEndBirthday(Date endBirthday) {
		this.endBirthday = endBirthday;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getSchoolType() {
		return schoolType;
	}

	public void setSchoolType(String schoolType) {
		this.schoolType = schoolType;
	}

	public String getYearSystem() {
		return yearSystem;
	}

	public void setYearSystem(String yearSystem) {
		this.yearSystem = yearSystem;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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

	public String getPoliceName() {
		return policeName;
	}

	public void setPoliceName(String policeName) {
		this.policeName = policeName;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getPoliceSex() {
		return policeSex;
	}

	public void setPoliceSex(String policeSex) {
		this.policeSex = policeSex;
	}

	public String getPoliceType() {
		return policeType;
	}

	public void setPoliceType(String policeType) {
		this.policeType = policeType;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public String getShun() {
		return shun;
	}

	public void setShun(String shun) {
		this.shun = shun;
	}

	public String getMedicaNumber() {
		return medicaNumber;
	}

	public void setMedicaNumber(String medicaNumber) {
		this.medicaNumber = medicaNumber;
	}

	@Override
	public String getRemarks() {
		return remarks;
	}

	@Override
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}