/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 公安局党代会代表Entity
 * @author cecil.li
 * @version 2020-06-09
 */
public class AffairJuPartyCongressRepresentative extends DataEntity<AffairJuPartyCongressRepresentative> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 0)
	private String name;		// 姓名
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
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String treeId;
	@ExcelField(title = "党组织", type = 0, align = 2, sort = 1)
	private String unit;
	private String unitId;
	
	public AffairJuPartyCongressRepresentative() {
		super();
	}

	public AffairJuPartyCongressRepresentative(String id){
		super(id);
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

	public String getTreeId() {
		return treeId;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
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
}