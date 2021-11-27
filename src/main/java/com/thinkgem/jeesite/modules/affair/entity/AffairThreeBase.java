/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 三基综合Entity
 * @author cecil.li
 * @version 2020-12-29
 */
public class AffairThreeBase extends DataEntity<AffairThreeBase> {
	
	private static final long serialVersionUID = 1L;
//	@ExcelField(title = "序号", type = 0, align = 2, sort = 0)
	private String number;  // 序号
	@ExcelField(title = "时间", type = 0, align = 2, sort = 0)
	private String yearMonth;  // 时间
	@ExcelField(title = "单位", type = 0, align = 2, sort = 1)
	private String unit;		// 单位
	private String unitId;		// 单位id
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 2)
	private String name;		// 姓名
	@ExcelField(title = "证件号", type = 0, align = 2, sort = 3)
	private String idNumber;		// 证件号
	@ExcelField(title = "性别", type = 0, align = 2, sort = 4, dictType = "sex")
	private String sex;		// 性别
	@ExcelField(title = "年龄", type = 0, align = 2, sort = 5)
	private String age;		// 年龄
	@ExcelField(title = "职务", type = 0, align = 2, sort = 6)
	private String job;		// 职务
	@ExcelField(title = "基本知识", type = 0, align = 2, sort = 7)
	private String basicKnowledge;		// 基本知识
	@ExcelField(title = "基本体能", type = 0, align = 2, sort = 8)
	private String basicFitness;		// 基本体能
	@ExcelField(title = "基本体能", type = 0, align = 2, sort = 9)
	private String baseSkill;  // 基本技能
	@ExcelField(title = "成绩总评", type = 0, align = 2, sort = 10, dictType = "pass_status")
	private String overallScore;		// 成绩总评
	@ExcelField(title = "备注", type = 0, align = 2, sort = 11)
	private String remark;		// 备注
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	
	public AffairThreeBase() {
		super();
	}

	public AffairThreeBase(String id){
		super(id);
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
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}
	
	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}
	
	public String getBasicKnowledge() {
		return basicKnowledge;
	}

	public void setBasicKnowledge(String basicKnowledge) {
		this.basicKnowledge = basicKnowledge;
	}
	
	public String getBasicFitness() {
		return basicFitness;
	}

	public void setBasicFitness(String basicFitness) {
		this.basicFitness = basicFitness;
	}
	
	public String getOverallScore() {
		return overallScore;
	}

	public void setOverallScore(String overallScore) {
		this.overallScore = overallScore;
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

	public String getBaseSkill() {
		return baseSkill;
	}

	public void setBaseSkill(String baseSkill) {
		this.baseSkill = baseSkill;
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