/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 各类特长人才Entity
 * @author cecil.li
 * @version 2019-11-05
 */
public class AffairPolicewomanTalent extends DataEntity<AffairPolicewomanTalent> {
	
	private static final long serialVersionUID = 1L;
	private Date date;		// 时间
	@ExcelField(title = "单位", type = 0, align = 2, sort = 0)
	private String unit;		// 单位
	private String unitId;		// 单位机构id
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 1)
	private String name;		// 姓名
	@ExcelField(title = "职务", type = 0, align = 2, sort = 3)
	private String job;		// 删除后字段改为"标题"
	@ExcelField(title = "出生年月日", type = 0, align = 2, sort = 4)
	private Date birthday;		// 出生年月日
	@ExcelField(title = "毕业院校", type = 0, align = 2, sort = 5)
	private String school;		// 毕业院校
	@ExcelField(title = "何种特长", type = 0, align = 2, sort = 6)
	private String speciality;		// 何种特长
	@ExcelField(title = "获奖情况", type = 0, align = 2, sort = 7)
	private String situation;		// 获奖情况
	@ExcelField(title = "年龄", type = 0, align = 2, sort = 2)
	private Integer age;        // 年龄
	@ExcelField(title = "内容", type = 0, align = 2, sort = 8)
	private String content;        //内容
	private String filePath;    //上传图片

	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String userId;         //获取当前账号单位

	private Date startDate;
	private Date endDate;
	private Date startBirthdayDate;
	private Date endBirthdayDate;
	
	public AffairPolicewomanTalent() {
		super();
	}

	public AffairPolicewomanTalent(String id){
		super(id);
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
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
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
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
	
	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}
	
	public String getSituation() {
		return situation;
	}

	public void setSituation(String situation) {
		this.situation = situation;
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

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getStartBirthdayDate() {
		return startBirthdayDate;
	}

	public void setStartBirthdayDate(Date startBirthdayDate) {
		this.startBirthdayDate = startBirthdayDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEndBirthdayDate() {
		return endBirthdayDate;
	}

	public void setEndBirthdayDate(Date endBirthdayDate) {
		this.endBirthdayDate = endBirthdayDate;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}