/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;


/**
 * 训厉Entity
 * @author tom.fu
 * @version 2020-08-17
 */
public class AffairXunLi extends DataEntity<AffairXunLi> {

	private static final long serialVersionUID = 1L;

	private String username;		// 用户名
	private String policeIdNumber;		// 警号
	private String policeRank;		// 警衔
	private String policeClassification;		// 警种
	private String personnelType;		// 人员类别
	private String managementClass;		// 管理类别
	@ExcelField(title = "职务", type = 0, align = 2, sort = 4)
	private String administrativePost;		// 行政职务
	private String jobLevel;		//职务级别
	private String organization;		// 机构
	private String organizationId;		// 机构id
	private String region;		// 域
	private String regionId;        //域id
	@ExcelField(title = "出生年月", type = 0, align = 2, sort = 5)
	private Date birthday;		// 出生日期
	private String certificateType;		// 证件类型
	private String schoolName;		//毕业学校
	private String education;	//学历
	private String email;		// 邮箱
	private String bloodType;		//血型
	private Date workDate;		// 参加工作日期
	@ExcelField(title = "政治面貌", type = 0, align = 2, sort = 7,dictType = "political_status")
	private String politicsFace;//政治面貌
	private String teamMembers;//是否是班子成员
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 1)
	private String name;		// 姓名
	@ExcelField(title = "性别", type = 0, align = 2, sort = 2,dictType = "sex")
	private String sex;		//性别
	@ExcelField(title = "民族", type = 0, align = 2, sort = 3,dictType = "nation")
	private String nation;		   	 // 民族
	private String idNumber;		    // 证件号
	private String major;		//专业
	private String degree;		//学位
	@ExcelField(title = "联系电话", type = 0, align = 2, sort = 8)
	private String phoneNumber;      //电话
	private String userStatus;	//用户状态
	private String healthStatus;	//健康状态
	@ExcelField(title = "入警时间", type = 0, align = 2, sort = 6)
	private Date publicSecurityDate;		// 参加公安工作日期
	private Date organizationDate;		    // 参加组织日期
	private String status;		            // 人员状态
	private String jiGouName;		//机构名称
	private String jiGouQLJ;		//机构全路径
	private Date creationTime;		// 创建日期
	private String regionName;		// 域名称
	private String roleName;		//角色名称



	/**
	 * 培训班课程
	 */
	@ExcelField(title = "培训名称", type = 0, align = 2, sort = 9)
	private String peiXunName;
	@ExcelField(title = "培训类型", type = 0, align = 2, sort = 10,dictType = "affair_train_type")
	private String type;
	@ExcelField(title = "培训内容", type = 0, align = 2, sort = 11)
	private String content;
	@ExcelField(title = "培训场地", type = 0, align = 2, sort = 12)
	private String site;
	@ExcelField(title = "培训对象", type = 0, align = 2, sort = 13)
	private String trainObject;


	/**
	 * 委外培训
	 */
	@ExcelField(title = "培训班名称", type = 0, align = 2, sort = 14)
	private String externalName;		// 培训班名称
	@ExcelField(title = "培训班类型", type = 0, align = 2, sort = 15,dictType = "external_type")
	private String externalType;		// 培训班类别
	//@ExcelField(title = "培训班完成情况", type = 0, align = 2, sort = 16,dictType = "train_completion")
	private String completion;		//  培训完成情况
	private String institutionCode;		// 主办单位机构代码
	@ExcelField(title = "主办单位", type = 0, align = 2, sort = 16)
	private String unit;		// 主办单位
	private String unitId;		// 主办单位id
	private String unitLevel;		// 主办单位级别
	//@ExcelField(title = "开始时间", type = 0, align = 2, sort = 18,dictType = "unit_level")
	private Date beganDate;		//  开始时间
	//@ExcelField(title = "结束时间", type = 0, align = 2, sort = 19)
	private Date resultDate;		// 结束时间
	private String quitStatus;		// 培训离岗状态
	@ExcelField(title = "承训机构名称", type = 0, align = 2, sort = 17)
	private String unitName;		// 承训机构名称
	//@ExcelField(title = "培训地点", type = 0, align = 2, sort = 21)
	private String trainSite;		// 培训地点
	private String certificateCode;		// 证书编号
	@ExcelField(title = "培训时所在单位及职务", type = 0, align = 2, sort = 18)
	private String unitJob;		// 培训时所在单位及职务


	/**
	 * 岗位练兵
	 */
	@ExcelField(title = "训练开始时间", type = 0, align = 2, sort = 19)
	private Date drillDateBegin;		//  训练开始时间
	@ExcelField(title = "训练结束时间", type = 0, align = 2, sort = 20)
	private Date drillDateOver;		//  训练结束时间
	@ExcelField(title = "项目类别", type = 0, align = 2, sort = 21,dictType = "project_type")
	private String itemClassification;		// 项目类别
	@ExcelField(title = "训练概况", type = 0, align = 2, sort = 22)
	private String drillGeneralSituation;		 // 训练概况
	@ExcelField(title = "训练时长", type = 0, align = 2, sort = 23)
	private String xunlianTime;		//训练时间（小时）


	/**
	 * 考试
	 */


	private String alarm;		// 警号
	private String organ;		// 机构
	//@ExcelField(title = "考试名称", type = 0, align = 2, sort = 1)
	private String examName;		// 考试名称
	//@ExcelField(title = "考试类别", type = 0, align = 2, sort = 2)
	private String examType;		// 考试类别
	private String examSubject;		// 考试科目
	//@ExcelField(title = "得分", type = 0, align = 2, sort = 3)
	private String score;		// 得分
	private String isPass;		// 是否通过
	//@ExcelField(title = "考试时长(分钟)", type = 0, align = 2, sort = 4)
	private String examTime;		//考试时长
	private String appendfile;	//附件
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号


	public AffairXunLi() {
		super();
	}


	public String getPeiXunName() {
		return peiXunName;
	}

	public void setPeiXunName(String peiXunName) {
		this.peiXunName = peiXunName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getTrainObject() {
		return trainObject;
	}

	public void setTrainObject(String trainObject) {
		this.trainObject = trainObject;
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

	public String getItemClassification() {
		return itemClassification;
	}

	public void setItemClassification(String itemClassification) {
		this.itemClassification = itemClassification;
	}

	public String getDrillGeneralSituation() {
		return drillGeneralSituation;
	}

	public void setDrillGeneralSituation(String drillGeneralSituation) {
		this.drillGeneralSituation = drillGeneralSituation;
	}

	public String getXunlianTime() {
		return xunlianTime;
	}

	public void setXunlianTime(String xunlianTime) {
		this.xunlianTime = xunlianTime;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getBloodType() {
		return bloodType;
	}

	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}

	public Date getWorkDate() {
		return workDate;
	}

	public void setWorkDate(Date workDate) {
		this.workDate = workDate;
	}

	public String getPoliticsFace() {
		return politicsFace;
	}

	public void setPoliticsFace(String politicsFace) {
		this.politicsFace = politicsFace;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPersonnelType() {
		return personnelType;
	}

	public void setPersonnelType(String personnelType) {
		this.personnelType = personnelType;
	}

	public String getPoliceIdNumber() {
		return policeIdNumber;
	}

	public void setPoliceIdNumber(String policeIdNumber) {
		this.policeIdNumber = policeIdNumber;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public AffairXunLi(String id){
		super(id);
	}

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public String getExamType() {
		return examType;
	}

	public void setExamType(String examType) {
		this.examType = examType;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getExamTime() {
		return examTime;
	}

	public void setExamTime(String examTime) {
		this.examTime = examTime;
	}

	public String getAppendfile() {
		return appendfile;
	}

	public void setAppendfile(String appendfile) {
		this.appendfile = appendfile;
	}

	public String getAlarm() {
		return alarm;
	}

	public void setAlarm(String alarm) {
		this.alarm = alarm;
	}

	public String getOrgan() {
		return organ;
	}

	public void setOrgan(String organ) {
		this.organ = organ;
	}

	public String getExamSubject() {
		return examSubject;
	}

	public void setExamSubject(String examSubject) {
		this.examSubject = examSubject;
	}

	public String getIsPass() {
		return isPass;
	}

	public void setIsPass(String isPass) {
		this.isPass = isPass;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCertificateType() {
		return certificateType;
	}

	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTeamMembers() {
		return teamMembers;
	}

	public void setTeamMembers(String teamMembers) {
		this.teamMembers = teamMembers;
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

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getHealthStatus() {
		return healthStatus;
	}

	public void setHealthStatus(String healthStatus) {
		this.healthStatus = healthStatus;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPublicSecurityDate() {
		return publicSecurityDate;
	}

	public void setPublicSecurityDate(Date publicSecurityDate) {
		this.publicSecurityDate = publicSecurityDate;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOrganizationDate() {
		return organizationDate;
	}

	public void setOrganizationDate(Date organizationDate) {
		this.organizationDate = organizationDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getJiGouName() {
		return jiGouName;
	}

	public void setJiGouName(String jiGouName) {
		this.jiGouName = jiGouName;
	}

	public String getJiGouQLJ() {
		return jiGouQLJ;
	}

	public void setJiGouQLJ(String jiGouQLJ) {
		this.jiGouQLJ = jiGouQLJ;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
}