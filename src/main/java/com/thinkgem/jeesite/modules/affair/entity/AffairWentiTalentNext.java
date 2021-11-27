/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 文艺人才库Entity
 * @author mason.xv
 * @version 2020-04-02
 */
public class AffairWentiTalentNext extends DataEntity<AffairWentiTalentNext> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "单位", type = 0, align = 2, sort = 0)
	private String unit;		// 单位
	@ExcelField(title = "警号", type = 0, align = 2, sort = 1)
	private String policeNo;		// 警号
	private String unitId;		// 单位id
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 2)
	private String name;		// 姓名
	@ExcelField(title = "性别", type = 0, align = 2, sort = 3, dictType="sex")
	private String sex;      //性别
	@ExcelField(title = "出生年月", type = 0, align = 2, sort = 4)
	private Date birthday;		// 出生年月
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 5)
	private String idNumber;		// 身份证号
	@ExcelField(title = "毕业院校", type = 0, align = 2, sort = 6)
	private String school;		// 毕业院校
	@ExcelField(title = "何种特长", type = 0, align = 2, sort = 7)
	private String skill;		// 何种特长
	@ExcelField(title = "文艺等级", type = 0, align = 2, sort = 8)
	private String yisuLevel;		// 文艺等级
	@ExcelField(title = "取得成绩", type = 0, align = 2, sort = 9)
	private String achievement;		// 取得成绩
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private Date beginBirthday;		// 开始 出生年月
	private Date endBirthday;		// 结束 出生年月
	
	public AffairWentiTalentNext() {
		super();
	}

	public AffairWentiTalentNext(String id){
		super(id);
	}

	public String getPoliceNo() {
		return policeNo;
	}

	public void setPoliceNo(String policeNo) {
		this.policeNo = policeNo;
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
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}
	
	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}
	
	public String getAchievement() {
		return achievement;
	}

	public void setAchievement(String achievement) {
		this.achievement = achievement;
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
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getYisuLevel() {
		return yisuLevel;
	}

	public void setYisuLevel(String yisuLevel) {
		this.yisuLevel = yisuLevel;
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
		
}