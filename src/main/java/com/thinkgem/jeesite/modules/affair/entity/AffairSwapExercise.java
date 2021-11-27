/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 交流锻炼Entity
 * @author jack.xu
 * @version 2020-07-16
 */
public class AffairSwapExercise extends DataEntity<AffairSwapExercise> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "用户名",type = 0, align = 2, sort = 0)
	private String userName;		// 用户名
	@ExcelField(title = "警号",type = 0, align = 2, sort = 1)
	private String number;		// 警号
	@ExcelField(title = "姓名",type = 0, align = 2, sort = 2)
	private String name;		// 姓名
	@ExcelField(title = "身份证号",type = 0, align = 2, sort = 3)
	private String idNumber;		// 身份证号
	@ExcelField(title = "交流名称",type = 0, align = 2, sort = 4)
	private String swapName;		// 交流名称
	@ExcelField(title = "交流规格类型",type = 0, align = 2, sort = 5, dictType = "size_type")
	private String sizeType;		// 交流规格类型
	@ExcelField(title = "交流岗位类型",type = 0, align = 2, sort = 6, dictType = "job_type")
	private String jobType;		// 交流岗位类型
	@ExcelField(title = "交流学习类别",type = 0, align = 2, sort = 7, dictType = "study_type")
	private String studyType;		// 交流学习类别
	@ExcelField(title = "交流时间",type = 0, align = 2, sort = 8)
	private Date date;		// 交流时间
	//@ExcelField(title = "警种",type = 0, align = 2, sort = 9, dictType = "police_classification")
	private String policeClassification;		// 警种
	/*@ExcelField(title = "警衔",type = 0, align = 2, sort = 10,dictType = "police_rank")*/
	@ExcelField(title = "警衔",type = 0, align = 2, sort = 9,dictType = "police_rank_level")
	private String policeRank;		// 警衔
	@ExcelField(title = "人员类别",type = 0, align = 2, sort = 10, dictType = "person_type")
	private String personType;		// 人员类别
	@ExcelField(title = "管理类别",type = 0, align = 2, sort = 11, dictType = "management_type")
	private String managementType;		// 管理类别
	@ExcelField(title = "行政职务",type = 0, align = 2, sort = 12,dictType = "administration_post")
	private String post;		// 行政职务
	@ExcelField(title = "职务级别",type = 0, align = 2, sort = 13,dictType = "post_level")
	private String postLevel;		// 职务级别
	@ExcelField(title = "单位",type = 0, align = 2, sort = 14)
	private String unit;		// 单位
	private String unitId;		// 单位id
	@ExcelField(title = "域",type = 0, align = 2, sort = 15)
	private String region;		// 域
	private String regionId;		// 域id
	@ExcelField(title = "任职情况",type = 0, align = 2, sort = 16,dictType = "service_condition")
	private String serviceCondition;		// 任职情况
	@ExcelField(title = "交流学习鉴别",type = 0, align = 2, sort = 17)
	private String studyIdentity;		// 交流学习鉴定
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private Date startDate;
	private Date endDate;

	private Integer total;


	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public AffairSwapExercise() {
		super();
	}

	public AffairSwapExercise(String id){
		super(id);
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
	
	public String getSwapName() {
		return swapName;
	}

	public void setSwapName(String swapName) {
		this.swapName = swapName;
	}
	
	public String getSizeType() {
		return sizeType;
	}

	public void setSizeType(String sizeType) {
		this.sizeType = sizeType;
	}
	
	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	
	public String getStudyType() {
		return studyType;
	}

	public void setStudyType(String studyType) {
		this.studyType = studyType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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
	
	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
	
	public String getServiceCondition() {
		return serviceCondition;
	}

	public void setServiceCondition(String serviceCondition) {
		this.serviceCondition = serviceCondition;
	}
	
	public String getStudyIdentity() {
		return studyIdentity;
	}

	public void setStudyIdentity(String studyIdentity) {
		this.studyIdentity = studyIdentity;
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
	
}