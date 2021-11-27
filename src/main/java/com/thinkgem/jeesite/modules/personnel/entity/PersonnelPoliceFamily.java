/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 民警家庭Entity
 * @author daniel.liu
 * @version 2020-07-09
 */
public class PersonnelPoliceFamily extends DataEntity<PersonnelPoliceFamily> {
	
	private static final long serialVersionUID = 1L;

	private String policeName;		///民警姓名
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 18)
	private String idNumber;		///民警身份证号
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 1)
	private String name;		// 民警家庭成员姓名
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

	//前端
	private boolean hasMarried;		//是否已婚
	private boolean hasBrother;		//是否有兄弟姐妹
	private boolean hasChild;		//是否有孩子
	private boolean hasChildInLow;		//孩子是否有配偶
	private String add;			//是否为添加
	/*0：父亲信息，1：母亲信息，2：配偶信息，3：配偶父亲信息，4：配偶母亲信息，5：兄弟姐妹信息，6：子女信息，7：子女配偶信息*/
	private String step;

	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private Date startDate;
	private Date endDate;
	
	public PersonnelPoliceFamily() {
		super();
	}

	public PersonnelPoliceFamily(String id){
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
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getPoliceName() {
		return policeName;
	}

	public void setPoliceName(String policeName) {
		this.policeName = policeName;
	}

	public boolean isHasMarried() {
		return hasMarried;
	}

	public void setHasMarried(boolean hasMarried) {
		this.hasMarried = hasMarried;
	}

	public boolean isHasBrother() {
		return hasBrother;
	}

	public void setHasBrother(boolean hasBrother) {
		this.hasBrother = hasBrother;
	}

	public boolean isHasChild() {
		return hasChild;
	}

	public void setHasChild(boolean hasChild) {
		this.hasChild = hasChild;
	}

	public boolean isHasChildInLow() {
		return hasChildInLow;
	}

	public void setHasChildInLow(boolean hasChildInLow) {
		this.hasChildInLow = hasChildInLow;
	}

	public String getAdd() {
		return add;
	}

	public void setAdd(String add) {
		this.add = add;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}
}