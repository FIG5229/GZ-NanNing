/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

import java.util.Date;

/**
 * 家庭成员及社会关系信息集Entity
 * @author cecil.li
 * @version 2019-11-09
 */
public class PersonnelFamily extends DataEntity<PersonnelFamily> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 1)
	private String name;		// 姓名
	@ExcelField(title = "与该人关系", type = 0, align = 2, sort = 2)
	private String relationship;		// 与该人关系
	@ExcelField(title = "性别", type = 0, align = 2, sort = 3, dictType="sex")
	private String sex;		// 性别
	@ExcelField(title = "出生日期", type = 0, align = 2, sort = 4)
	private Date birthday;		// 出生日期
	@ExcelField(title = "政治面貌", type = 0, align = 2, sort = 5, dictType="political_status")
	private String politicsFace;		// 政治面貌
	@ExcelField(title = "现状", type = 0, align = 2, sort = 6)
	private String status;		// 现状
	@ExcelField(title = "工作单位名称及职务", type = 0, align = 2, sort = 7)
	private String unitNameJob;		// 工作单位名称及职务
	@ExcelField(title = "工作单位所在政区", type = 0, align = 2, sort = 8)
	private String unitArea;		// 工作单位所在政区
	@ExcelField(title = "国籍", type = 0, align = 2, sort = 9)
	private String nationality;		// 国籍
	@ExcelField(title = "民族", type = 0, align = 2, sort = 10, dictType="nation")
	private String nation;		// 民族
	@ExcelField(title = "学历", type = 0, align = 2, sort = 11)
	private String education;		// 学历
	@ExcelField(title = "身份", type = 0, align = 2, sort = 12)
	private String identity;		// 身份
	@ExcelField(title = "身份或职位", type = 0, align = 2, sort = 13)
	private String identityJob;		// 身份或职位
	@ExcelField(title = "职务层次", type = 0, align = 2, sort = 14)
	private String jobLevel;		// 职务层次
	@ExcelField(title = "联系方式", type = 0, align = 2, sort = 15)
	private String contactMethod;		// 联系方式
	@ExcelField(title = "住址", type = 0, align = 2, sort = 16)
	private String address;		// 住址
	@ExcelField(title = "备注", type = 0, align = 2, sort = 17)
	private String remark;		// 备注
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 18)
	private String idNumber;		// 身份证号


	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private Date startDate;
	private Date endDate;
	
	public PersonnelFamily() {
		super();
	}

	public PersonnelFamily(String id){
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
	
	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	public String getPoliticsFace() {
		return politicsFace;
	}

	public void setPoliticsFace(String politicsFace) {
		this.politicsFace = politicsFace;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getUnitNameJob() {
		return unitNameJob;
	}

	public void setUnitNameJob(String unitNameJob) {
		this.unitNameJob = unitNameJob;
	}
	
	public String getUnitArea() {
		return unitArea;
	}

	public void setUnitArea(String unitArea) {
		this.unitArea = unitArea;
	}
	
	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	
	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}
	
	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}
	
	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}
	
	public String getIdentityJob() {
		return identityJob;
	}

	public void setIdentityJob(String identityJob) {
		this.identityJob = identityJob;
	}
	
	public String getJobLevel() {
		return jobLevel;
	}

	public void setJobLevel(String jobLevel) {
		this.jobLevel = jobLevel;
	}
	
	public String getContactMethod() {
		return contactMethod;
	}

	public void setContactMethod(String contactMethod) {
		this.contactMethod = contactMethod;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	//家庭成员信息
	public String familyMemberInfo() {
		return "姓名：'" + name + '\'' +
				", 与本人关系：'" + relationship + '\'' +
				", 性别：'" + DictUtils.getDictLabels(sex,"sex","")+ '\'' +
				", 出生日期：" + DateUtils.formatDate(birthday) +
				", 政治面貌：'" + DictUtils.getDictLabels(politicsFace,"political_status","") + '\'' +
				", 现状：'" + status + '\'' +
				", 国籍：'" + nationality + '\'' +
				", 备注：'" + remark + '\''
				;
	}
}