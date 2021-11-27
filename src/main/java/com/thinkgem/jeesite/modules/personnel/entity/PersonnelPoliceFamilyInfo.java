/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

import java.util.Date;

/**
 * 民警家庭Entity
 * @author cecil.li
 * @version 2020-11-04
 */
public class PersonnelPoliceFamilyInfo extends DataEntity<PersonnelPoliceFamilyInfo> {
	
	private static final long serialVersionUID = 1L;
	private String pfId;		// 主表id
	private String name;		// 姓名
	private String idNumber;		// 身份证号
	private String relationship;		// 与本人关系
	private String sex;		// 性别
	private Date birthday;		// 出生年月
	private String politicalStatus;		// 政治面貌
	private String statusQuo;		// 现状
	private String job;		// 工作单位名称及职务
	private String steer;		// 工作单位所在政区
	private String nationality;		// 国籍
	private String nation;		// 民族
	private String education;		// 学历
	private String identity;		// 身份
	private String position;		// 身份或职位
	private String jobLevel;		// 职务层次
	private String contact;		// 联系方式
	private String address;		// 住址
	private String remark;		// 备注
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String status;
	
	public PersonnelPoliceFamilyInfo() {
		super();
	}

	public PersonnelPoliceFamilyInfo(String id){
		super(id);
	}

	public String getPfId() {
		return pfId;
	}

	public void setPfId(String pfId) {
		this.pfId = pfId;
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
	
	public String getPoliticalStatus() {
		return politicalStatus;
	}

	public void setPoliticalStatus(String politicalStatus) {
		this.politicalStatus = politicalStatus;
	}
	
	public String getStatusQuo() {
		return statusQuo;
	}

	public void setStatusQuo(String statusQuo) {
		this.statusQuo = statusQuo;
	}
	
	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}
	
	public String getSteer() {
		return steer;
	}

	public void setSteer(String steer) {
		this.steer = steer;
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
	
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
	public String getJobLevel() {
		return jobLevel;
	}

	public void setJobLevel(String jobLevel) {
		this.jobLevel = jobLevel;
	}
	
	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

    public String getFamilyMemberInfo() {
		return "姓名：'" + name + '\'' +
				", 与本人关系：'" + relationship + '\'' +
				", 性别：'" + DictUtils.getDictLabels(sex,"sex","")+ '\'' +
				", 出生日期：" + DateUtils.formatDate(birthday) +
				", 政治面貌：'" + DictUtils.getDictLabels(politicalStatus,"political_status","") + '\'' +
				", 现状：'" + status + '\'' +
				", 国籍：'" + nationality + '\'' +
				", 备注：'" + remark + '\''
				;
    }
}