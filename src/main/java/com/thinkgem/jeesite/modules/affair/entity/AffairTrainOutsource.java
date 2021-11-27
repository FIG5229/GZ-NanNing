/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 委外培训Entity
 * @author jack.xu
 * @version 2020-07-17
 */
public class AffairTrainOutsource extends DataEntity<AffairTrainOutsource> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "用户名",type = 0, align = 2, sort = 0)
	private String userName;		// 用户名
	@ExcelField(title = "警号", type = 0, align = 2, sort = 1)
	private String number;		// 警号
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 2)
	private String name;		// 姓名
	private String nameId;		//用户id
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 3)
	private String idNumber;		// 身份证号
	@ExcelField(title = "外部培训班名称", type = 0, align = 2, sort = 4)
	private String externalName;		// 外部培训班名称
	@ExcelField(title = "外部培训班类别", type = 0, align = 2, sort = 5, dictType = "external_type")
	private String externalType;		// 外部培训班类别
	@ExcelField(title = "培训完成情况", type = 0, align = 2, sort = 6, dictType = "train_completion")
	private String completion;		// 培训完成情况
	@ExcelField(title = "主办单位机构代码", type = 0, align = 2, sort = 7)
	private String institutionCode;		// 主办单位机构代码
	//@ExcelField(title = "警种", type = 0, align = 2,sort = 8,dictType = "police_classification")
	private String policeClassification;		// 警种
	/*@ExcelField(title = "警衔", type = 0, align = 2,sort = 9,dictType = "police_rank")*/
	@ExcelField(title = "警衔", type = 0, align = 2,sort = 8,dictType = "police_rank_level")
	private String policeRank;		// 警衔
	@ExcelField(title = "人员类别", type = 0, align = 2,sort = 9,dictType = "person_type")
	private String personType;		// 人员类别
	@ExcelField(title = "管理类别", type = 0, align = 2,sort = 10,dictType = "management_type")
	private String managementType;		// 管理类别
	@ExcelField(title = "行政职务", type = 0, align = 2,sort = 11,dictType = "administration_post")
	private String post;		// 行政职务
	@ExcelField(title = "职务级别", type = 0, align = 2,sort = 12,dictType = "post_level")
	private String postLevel;		// 职务级别
	@ExcelField(title = "主办单位", type = 0, align = 2, sort = 13)
	private String unit;		// 主办单位
	private String unitId;		// 主办单位id
	@ExcelField(title = "域", type = 0, align = 2, sort = 14)
	private String region;		// 域
	private String regionId;		// 域id
	@ExcelField(title = "主办单位级别", type = 0, align = 2, sort = 15, dictType = "unit_level")
	private String unitLevel;		// 主办单位级别
	@ExcelField(title = "培训开始时间", type = 0, align = 2, sort = 16)
	private Date beganDate;		// 开始时间
	@ExcelField(title = "培训结束时间", type = 0, align = 2, sort = 17)
	private Date resultDate;		// 结束时间
	@ExcelField(title = "培训离岗状态", type = 0, align = 2, sort = 18, dictType = "quit_status")
	private String quitStatus;		// 培训离岗状态
	@ExcelField(title = "承训机构名称", type = 0, align = 2, sort = 19)
	private String unitName;		// 承训机构名称
	@ExcelField(title = "培训地点", type = 0, align = 2, sort = 20)
	private String trainSite;		// 培训地点
	@ExcelField(title = "证书编号", type = 0, align = 2, sort = 21)
	private String certificateCode;		// 证书编号
	@ExcelField(title = "培训时所在单位及职务", type = 0, align = 2, sort = 22)
	private String unitJob;		// 培训时所在单位及职务
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	
	public AffairTrainOutsource() {
		super();
	}

	public AffairTrainOutsource(String id){
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
	
	public String getExternalName() {
		return externalName;
	}

	public void setExternalName(String externalName) {
		this.externalName = externalName;
	}
	
	public String getExternalType() {
		return externalType;
	}

	public void setExternalType(String externalType) {
		this.externalType = externalType;
	}
	
	public String getCompletion() {
		return completion;
	}

	public void setCompletion(String completion) {
		this.completion = completion;
	}
	
	public String getInstitutionCode() {
		return institutionCode;
	}

	public void setInstitutionCode(String institutionCode) {
		this.institutionCode = institutionCode;
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
	
	public String getUnitLevel() {
		return unitLevel;
	}

	public void setUnitLevel(String unitLevel) {
		this.unitLevel = unitLevel;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBeganDate() {
		return beganDate;
	}

	public void setBeganDate(Date beganDate) {
		this.beganDate = beganDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getResultDate() {
		return resultDate;
	}

	public void setResultDate(Date resultDate) {
		this.resultDate = resultDate;
	}
	
	public String getQuitStatus() {
		return quitStatus;
	}

	public void setQuitStatus(String quitStatus) {
		this.quitStatus = quitStatus;
	}
	
	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	
	public String getTrainSite() {
		return trainSite;
	}

	public void setTrainSite(String trainSite) {
		this.trainSite = trainSite;
	}
	
	public String getCertificateCode() {
		return certificateCode;
	}

	public void setCertificateCode(String certificateCode) {
		this.certificateCode = certificateCode;
	}
	
	public String getUnitJob() {
		return unitJob;
	}

	public void setUnitJob(String unitJob) {
		this.unitJob = unitJob;
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

	public String getNameId() {
		return nameId;
	}

	public void setNameId(String nameId) {
		this.nameId = nameId;
	}
}