/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 岗位练兵功能Entity
 * @author tom.fu
 * @version 2020-08-03
 */
public class AffairJobTraining extends DataEntity<AffairJobTraining> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "用户名", type = 0, align = 2, sort = 1)
	private String username;		// 用户名
	private String policeNumber;		// 警号
	private String policeRank;		// 警衔
	private String policeClassification;		// 警种
	@ExcelField(title = "训练开始时间",type = 0, align = 2, sort = 2)
	private Date drillDateBegin;		// 训练开始时间
	@ExcelField(title = "训练结束时间",type = 0, align = 2, sort = 3)
	private Date drillDateOver;		// 训练结束时间
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 4)
	private String name;		// 姓名
	private String idNumber;		// 证件号
	@ExcelField(title = "项目类别", type = 0, align = 2, sort = 5,dictType = "project_type")
	private String itemClassification;		// 项目类别
	private String personnelCategory;		// 人员类别
	private String managementClass;		// 管理类别
	private String administrativePost;		// 行政职务
	private String jobLevel;		// 职务类别
	@ExcelField(title = "机构", type = 0, align = 2, sort = 9)
	private String organization;		// 机构
	@ExcelField(title = "创建人", type = 0, align = 2, sort = 6)
	private String creator;		// 创建人
	@ExcelField(title = "创建日期", type = 0, align = 2, sort = 7)
	private Date creationTime;		// 创建日期
	private String region;		// 域
	private String regionId;   //域id
	@ExcelField(title = "训练概况", type = 0, align = 2, sort = 8)
	private String drillGeneralSituation;		// 训练概况
	@ExcelField(title = "备注信息", type = 0, align = 2, sort = 10)
	private String remarks;      //备注信息
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String xunlianTime;		//训练时间（小时）

	/*新加字段*/
//	@ExcelField(title = "附件", type = 0, align = 2, sort = 11)
	private String appendfile;    //附件

	private String organizationId;	//机构id

	public String getXunlianTime() {
		return xunlianTime;
	}


	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getAppendfile() {
		return appendfile;
	}

	public void setAppendfile(String appendfile) {
		this.appendfile = appendfile;
	}

	public void setXunlianTime(String xunlianTime) {
		this.xunlianTime = xunlianTime;
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

	@Override
	public String getRemarks() {
		return remarks;
	}

	@Override
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public AffairJobTraining() {
		super();
	}

	public AffairJobTraining(String id){
		super(id);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPoliceNumber() {
		return policeNumber;
	}

	public void setPoliceNumber(String policeNumber) {
		this.policeNumber = policeNumber;
	}
	
	public String getPoliceRank() {
		return policeRank;
	}

	public void setPoliceRank(String policeRank) {
		this.policeRank = policeRank;
	}
	
	public String getPoliceClassification() {
		return policeClassification;
	}

	public void setPoliceClassification(String policeClassification) {
		this.policeClassification = policeClassification;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDrillDateBegin() {
		return drillDateBegin;
	}

	public void setDrillDateBegin(Date drillDateBegin) {
		this.drillDateBegin = drillDateBegin;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDrillDateOver() {
		return drillDateOver;
	}

	public void setDrillDateOver(Date drillDateOver) {
		this.drillDateOver = drillDateOver;
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
	
	public String getItemClassification() {
		return itemClassification;
	}

	public void setItemClassification(String itemClassification) {
		this.itemClassification = itemClassification;
	}
	
	public String getPersonnelCategory() {
		return personnelCategory;
	}

	public void setPersonnelCategory(String personnelCategory) {
		this.personnelCategory = personnelCategory;
	}
	
	public String getManagementClass() {
		return managementClass;
	}

	public void setManagementClass(String managementClass) {
		this.managementClass = managementClass;
	}
	
	public String getAdministrativePost() {
		return administrativePost;
	}

	public void setAdministrativePost(String administrativePost) {
		this.administrativePost = administrativePost;
	}
	
	public String getJobLevel() {
		return jobLevel;
	}

	public void setJobLevel(String jobLevel) {
		this.jobLevel = jobLevel;
	}
	
	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}
	
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	
	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}
	
	public String getDrillGeneralSituation() {
		return drillGeneralSituation;
	}

	public void setDrillGeneralSituation(String drillGeneralSituation) {
		this.drillGeneralSituation = drillGeneralSituation;
	}
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getRegionId() {
		return regionId;
	}



}