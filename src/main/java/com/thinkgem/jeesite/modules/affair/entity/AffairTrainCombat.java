/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 实弹训练Entity
 * @author jack.xu
 * @version 2020-07-15
 */
public class AffairTrainCombat extends DataEntity<AffairTrainCombat> {
	
	private static final long serialVersionUID = 1L;
	//@ExcelField(title = "用户名",type = 0, align = 2, sort = 0)
	private String userName;		// 用户名
	//@ExcelField(title = "警号", type = 0, align = 2, sort = 1)
	private String number;		// 警号
	//@ExcelField(title = "训练日期", type = 0, align = 2, sort = 3)
	private Date date;		// 训练日期
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 1)
	private String name;		// 姓名
	@ExcelField(title = "身份证号", type = 0, align = 2,sort = 2)
	private String idNumber;		// 身份证号
	//@ExcelField(title = "项目类别", type = 0, align = 2,sort = 5,dictType = "project_type")
	private String projectType;		// 项目类别
	//@ExcelField(title = "警种", type = 0, align = 2,sort = 6,dictType = "police_classification")
	private String policeClassification;		// 警种
	/*@ExcelField(title = "警衔", type = 0, align = 2,sort = 7,dictType = "police_rank")*/
	//@ExcelField(title = "警衔", type = 0, align = 2,sort = 7,dictType = "police_rank_level")
	private String policeRank;		// 警衔
	//@ExcelField(title = "人员类别", type = 0, align = 2,sort = 8,dictType = "person_type")
	private String personType;		// 人员类别
	//@ExcelField(title = "管理类别", type = 0, align = 2,sort = 9,dictType = "management_type")
	private String managementType;		// 管理类别
	//@ExcelField(title = "行政职务", type = 0, align = 2,sort = 10,dictType = "administration_post")
	private String post;		// 行政职务
	//@ExcelField(title = "职务级别", type = 0, align = 2,sort = 11,dictType = "post_level")
	private String postLevel;		// 职务级别
	//@ExcelField(title = "单位", type = 0, align = 2,sort = 12)
	private String unit;		// 单位
	private String unitId;		// 单位id
	//@ExcelField(title = "域", type = 0, align = 2,sort = 13)
	private String region;		// 域
	private String regionId;	//域id
	@ExcelField(title = "子弹数量", type = 0, align = 2,sort = 3)
	private String count;		// 子弹数量
	@ExcelField(title = "平均单发环数", type = 0, align = 2,sort = 4)
	private String average;		// 平均单发环数
	@ExcelField(title = "备注", type = 0, align = 2,sort = 5)
	private String remarks;			//备注
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号


	private Date startDate;
	private Date endDate;

	private String orderBy;

	private Float maxBulletNum;
	private Float minBulletNum;

	private String organization;	//机构
	private String organizationId;	//机构id

	public AffairTrainCombat() {
		super();
	}

	public AffairTrainCombat(String id){
		super(id);
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public Float getMaxBulletNum() {
		return maxBulletNum;
	}

	public void setMaxBulletNum(Float maxBulletNum) {
		this.maxBulletNum = maxBulletNum;
	}

	public Float getMinBulletNum() {
		return minBulletNum;
	}

	public void setMinBulletNum(Float minBulletNum) {
		this.minBulletNum = minBulletNum;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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
	
	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	
	public String getPoliceClassification() {
		return policeClassification;
	}

	public void setPoliceClassification(String policeClassification) {
		this.policeClassification = policeClassification;
	}
	
	public String getPoliceRank() {
		return policeRank;
	}

	public void setPoliceRank(String policeRank) {
		this.policeRank = policeRank;
	}
	
	public String getPersonType() {
		return personType;
	}

	public void setPersonType(String personType) {
		this.personType = personType;
	}
	
	public String getManagementType() {
		return managementType;
	}

	public void setManagementType(String managementType) {
		this.managementType = managementType;
	}
	
	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}
	
	public String getPostLevel() {
		return postLevel;
	}

	public void setPostLevel(String postLevel) {
		this.postLevel = postLevel;
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
	
	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}
	
	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}
	
	public String getAverage() {
		return average;
	}

	public void setAverage(String average) {
		this.average = average;
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

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
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