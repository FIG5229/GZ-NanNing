/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 基本技能成绩Entity
 * @author cecil.li
 * @version 2020-12-28
 */
public class AffairBaseSkill extends DataEntity<AffairBaseSkill> {
	
	private static final long serialVersionUID = 1L;
//	@ExcelField(title = "序号", type = 0, align = 2, sort = 0)
	private String number;  // 序号
	@ExcelField(title = "时间", type = 0, align = 2, sort = 0)
	private String yearMonth;   // 时间
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 1)
	private String name;		// 姓名
	@ExcelField(title = "证件号", type = 0, align = 2, sort = 2)
	private String idNumber;		// 证件号
	@ExcelField(title = "年龄", type = 0, align = 2, sort = 3)
	private String age;		// 年龄
	@ExcelField(title = "性别", type = 0, align = 2, sort = 4, dictType = "sex")
	private String sex;		// 性别
	@ExcelField(title = "单位", type = 0, align = 2, sort = 5)
	private String unit;		// 单位
	private String unitId;		// 单位id
	@ExcelField(title = "职务", type = 0, align = 2, sort = 6)
	private String job;		// 职务
	@ExcelField(title = "项目一", type = 0, align = 2, sort = 7)
	private String itemOne;		// 项目一
	@ExcelField(title = "项目二", type = 0, align = 2, sort = 8)
	private String itemTwo;		// 项目二
	@ExcelField(title = "项目三", type = 0, align = 2, sort = 9)
	private String itemThree;		// 项目三
	@ExcelField(title = "项目四", type = 0, align = 2, sort = 10)
	private String itemFour;		// 项目四
	@ExcelField(title = "项目五", type = 0, align = 2, sort = 11)
	private String itemFive;		// 项目五
	@ExcelField(title = "项目六", type = 0, align = 2, sort = 12)
	private String itemSix;		// 项目六
	@ExcelField(title = "项目七", type = 0, align = 2, sort = 13)
	private String itemSeven;		// 项目七
	@ExcelField(title = "项目八", type = 0, align = 2, sort = 14)
	private String itemEight;		// 项目八
	@ExcelField(title = "项目九", type = 0, align = 2, sort = 15)
	private String itemNine;		// 项目九
	@ExcelField(title = "项目十", type = 0, align = 2, sort = 16)
	private String itemTen;		// 项目十
	@ExcelField(title = "综合评定", type = 0, align = 2, sort = 17, dictType = "pass_status")
	private String assessment;		// 综合评定
	@ExcelField(title = "备注", type = 0, align = 2, sort = 18)
	private String remark;		// 备注
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	
	public AffairBaseSkill() {
		super();
	}

	public AffairBaseSkill(String id){
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
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
	
	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}
	
	public String getItemOne() {
		return itemOne;
	}

	public void setItemOne(String itemOne) {
		this.itemOne = itemOne;
	}
	
	public String getItemTwo() {
		return itemTwo;
	}

	public void setItemTwo(String itemTwo) {
		this.itemTwo = itemTwo;
	}
	
	public String getItemThree() {
		return itemThree;
	}

	public void setItemThree(String itemThree) {
		this.itemThree = itemThree;
	}
	
	public String getItemFour() {
		return itemFour;
	}

	public void setItemFour(String itemFour) {
		this.itemFour = itemFour;
	}
	
	public String getItemFive() {
		return itemFive;
	}

	public void setItemFive(String itemFive) {
		this.itemFive = itemFive;
	}
	
	public String getItemSix() {
		return itemSix;
	}

	public void setItemSix(String itemSix) {
		this.itemSix = itemSix;
	}
	
	public String getItemSeven() {
		return itemSeven;
	}

	public void setItemSeven(String itemSeven) {
		this.itemSeven = itemSeven;
	}
	
	public String getItemEight() {
		return itemEight;
	}

	public void setItemEight(String itemEight) {
		this.itemEight = itemEight;
	}
	
	public String getItemNine() {
		return itemNine;
	}

	public void setItemNine(String itemNine) {
		this.itemNine = itemNine;
	}
	
	public String getItemTen() {
		return itemTen;
	}

	public void setItemTen(String itemTen) {
		this.itemTen = itemTen;
	}
	
	public String getAssessment() {
		return assessment;
	}

	public void setAssessment(String assessment) {
		this.assessment = assessment;
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

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}
}