/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 内部教官Entity
 * @author alan.wu
 * @version 2020-07-23
 */
public class AffairInteriorInstructorLibrary extends DataEntity<AffairInteriorInstructorLibrary> {
	
	private static final long serialVersionUID = 1L;

	@ExcelField(title = "姓名", type = 0, align = 2, sort = 1)
	private String name;		// 姓名
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 2)
	private String idNumber;		// 用户名
	@ExcelField(title = "性别", type = 0, align = 2, sort = 3, dictType="sex")
	private String sex;		// 性别（1：男 2：女）
	@ExcelField(title = "职务", type = 0, align = 2, sort = 4)
	private String job;		// 职务
	@ExcelField(title = "授课方向", type = 0, align = 2, sort = 5)
	private String direction;		// 授课方向
	@ExcelField(title = "教官简介", type = 0, align = 2, sort = 6)
	private String instructorProfile;		// 教官简介
	@ExcelField(title = "民族", type = 0, align = 2, sort = 7,dictType = "nation")
	private String nation;		// 民族
	@ExcelField(title = "健康状况", type = 0, align = 2, sort = 8)
	private String health;		// 健康状况
	@ExcelField(title = "电话", type = 0, align = 2, sort = 9)
	private String telephone;		// 电话
	@ExcelField(title = "出生日期", type = 0, align = 2, sort = 10)
	private Date birthday;		// 出生日期
	@ExcelField(title = "血型", type = 0, align = 2, sort = 11)
	private String blood;		// 血型
	@ExcelField(title = "籍贯", type = 0, align = 2, sort = 12)
	private String nativePlace;		// 籍贯
	@ExcelField(title = "警号", type = 0, align = 2, sort = 13)
	private String alarm;		// 警号
	@ExcelField(title = "单位", type = 0, align = 2, sort = 14)
	private String unit;		// 单位
	private String unitId;		// 单位id
	@ExcelField(title = "参加公安工作时间", type = 0, align = 2, sort = 15)
	private Date jobTime;		// 参加工作时间
	@ExcelField(title = "参加公安工作时间", type = 0, align = 2, sort = 16)
	private Date policeTime;		// 参加公安工作时间
	//@ExcelField(title = "政治面貌", type = 0, align = 2, sort = 17, dictType="sex")
	@ExcelField(title = "政治面貌", type = 0, align = 2, sort = 17)
	private String politicsStatus;		// 政治面貌
	@ExcelField(title = "参加组织日期", type = 0, align = 2, sort = 18)
	private Date organizationTime;		// 参加组织日期
	@ExcelField(title = "所属部门", type = 0, align = 2, sort = 19)
	private String department;		// 所属部门
	@ExcelField(title = "警衔", type = 0, align = 2, sort = 20)
	private String policeRank;		// 警衔
	@ExcelField(title = "警种", type = 0, align = 2, sort = 21)
	private String policeClassification;		// 警种
	@ExcelField(title = "管理类别", type = 0, align = 2, sort = 22)
	private String management;		// 管理类别
	@ExcelField(title = "职务级别", type = 0, align = 2, sort = 23)
	private String jobClassify;		// 职务级别ld(title = "职务级别", type = 0, align = 2, sort = 23)
	@ExcelField(title = "人员类别", type = 0, align = 2, sort = 24)
	private String peopleClassify;		// 人员类别
	@ExcelField(title = "教官级别", type = 0, align = 2, sort = 25,dictType="instructor_level")
	private String instructorLevel;		// 教官级别
	@ExcelField(title = "教官类别", type = 0, align = 2, sort = 26,dictType="Instructor_category")
	private String instructorCategory;		// 教官类别
	@ExcelField(title = "聘用单位", type = 0, align = 2, sort = 27)
	private String workUnits;		// 聘用单位
	private String workUnitsId;		// 聘用单位id
	@ExcelField(title = "聘用日期", type = 0, align = 2, sort = 28)
	private Date hireDate;		// 聘用日期
	@ExcelField(title = "门户是否显示", type = 0, align = 2, sort = 29)
	private String isShow;		// 门户是否显示
	@ExcelField(title = "解聘日期", type = 0, align = 2, sort = 30)
	private Date terminationDate;		// 解聘日期
	@ExcelField(title = "评审日期", type = 0, align = 2, sort = 31)
	private Date inspectionDate;		// 评审日期
	@ExcelField(title = "评审人员", type = 0, align = 2, sort = 32)
	private String judge;		// 评审人员
	@ExcelField(title = "教官特长", type = 0, align = 2, sort = 33)
	private String speciality;		// 教官特长
	@ExcelField(title = "评审理由", type = 0, align = 2, sort = 34)
	private String reviewTheReason;		// 评审理由
	@ExcelField(title = "工作经历", type = 0, align = 2, sort = 35)
	private String workExperience;		// 工作经历
	@ExcelField(title = "任教经历", type = 0, align = 2, sort = 36)
	private String teachExperience;		// 任教经历
	@ExcelField(title = "进修经历", type = 0, align = 2, sort = 37)
	private String educationExperience;		// 进修经历
	@ExcelField(title = "参与培训教材编导情况", type = 0, align = 2, sort = 38)
	private String scenarist;		// 参与培训教材编导情况
	@ExcelField(title = "参与典型事件工作情况", type = 0, align = 2, sort = 39)
	private String typicalEvent;		// 参与典型事件工作情况
	@ExcelField(title = "参与典型事件工作情况", type = 0, align = 2, sort = 40)
	private String perpleState;		//人员状态
	private String adjunct;		// 附件
	private String createOrgId;		// 创建者机构id
	private String updateOrgId;		// 更新者机构id
	private String departmentId;		// 所属部门id

	//@ExcelField(title = "文化程度", type = 0, align = 2, sort = 4)
	private String education;			//文化程度

	//@ExcelField(title = "教官范围", type = 0, align = 2, sort = 13)
	private String scope;				//教官范围

	//@ExcelField(title = "其他", type = 0, align = 2, sort = 14)
	private String other;				//其他

	private String userId;
	private String officeId;
	/*统计分析使用*/
	private String dateType;
	private Integer year;
	private String dateStart;
	private String dateEnd;
	private String month;
	private String label;


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public AffairInteriorInstructorLibrary() {
		super();
	}

	public AffairInteriorInstructorLibrary(String id){
		super(id);
	}

	public String getName() {
		return name;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getJobTime() {
		return jobTime;
	}

	public void setJobTime(Date jobTime) {
		this.jobTime = jobTime;
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
	
	public String getHealth() {
		return health;
	}

	public void setHealth(String health) {
		this.health = health;
	}
	
	public String getPerpleState() {
		return perpleState;
	}

	public void setPerpleState(String perpleState) {
		this.perpleState = perpleState;
	}
	
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	public String getBlood() {
		return blood;
	}

	public void setBlood(String blood) {
		this.blood = blood;
	}
	
	public String getNativePlace() {
		return nativePlace;
	}

	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}
	
	public String getAlarm() {
		return alarm;
	}

	public void setAlarm(String alarm) {
		this.alarm = alarm;
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


	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPoliceTime() {
		return policeTime;
	}

	public void setPoliceTime(Date policeTime) {
		this.policeTime = policeTime;
	}

	public String getPoliticsStatus() {
		return politicsStatus;
	}

	public void setPoliticsStatus(String politicsStatus) {
		this.politicsStatus = politicsStatus;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOrganizationTime() {
		return organizationTime;
	}

	public void setOrganizationTime(Date organizationTime) {
		this.organizationTime = organizationTime;
	}
	
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
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
	
	public String getManagement() {
		return management;
	}

	public void setManagement(String management) {
		this.management = management;
	}
	
	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}
	
	public String getJobClassify() {
		return jobClassify;
	}

	public void setJobClassify(String jobClassify) {
		this.jobClassify = jobClassify;
	}
	
	public String getPeopleClassify() {
		return peopleClassify;
	}

	public void setPeopleClassify(String peopleClassify) {
		this.peopleClassify = peopleClassify;
	}
	
	public String getInstructorLevel() {
		return instructorLevel;
	}

	public void setInstructorLevel(String instructorLevel) {
		this.instructorLevel = instructorLevel;
	}
	
	public String getWorkUnits() {
		return workUnits;
	}

	public void setWorkUnits(String workUnits) {
		this.workUnits = workUnits;
	}
	
	public String getWorkUnitsId() {
		return workUnitsId;
	}

	public void setWorkUnitsId(String workUnitsId) {
		this.workUnitsId = workUnitsId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}
	
	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTerminationDate() {
		return terminationDate;
	}

	public void setTerminationDate(Date terminationDate) {
		this.terminationDate = terminationDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getInspectionDate() {
		return inspectionDate;
	}

	public void setInspectionDate(Date inspectionDate) {
		this.inspectionDate = inspectionDate;
	}
	
	public String getJudge() {
		return judge;
	}

	public void setJudge(String judge) {
		this.judge = judge;
	}
	
	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}
	
	public String getReviewTheReason() {
		return reviewTheReason;
	}

	public void setReviewTheReason(String reviewTheReason) {
		this.reviewTheReason = reviewTheReason;
	}
	
	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	public String getInstructorProfile() {
		return instructorProfile;
	}

	public void setInstructorProfile(String instructorProfile) {
		this.instructorProfile = instructorProfile;
	}
	
	public String getWorkExperience() {
		return workExperience;
	}

	public void setWorkExperience(String workExperience) {
		this.workExperience = workExperience;
	}
	
	public String getTeachExperience() {
		return teachExperience;
	}

	public void setTeachExperience(String teachExperience) {
		this.teachExperience = teachExperience;
	}
	
	public String getEducationExperience() {
		return educationExperience;
	}

	public void setEducationExperience(String educationExperience) {
		this.educationExperience = educationExperience;
	}
	
	public String getScenarist() {
		return scenarist;
	}

	public void setScenarist(String scenarist) {
		this.scenarist = scenarist;
	}
	
	public String getTypicalEvent() {
		return typicalEvent;
	}

	public void setTypicalEvent(String typicalEvent) {
		this.typicalEvent = typicalEvent;
	}
	
	public String getAdjunct() {
		return adjunct;
	}

	public void setAdjunct(String adjunct) {
		this.adjunct = adjunct;
	}
	
	public String getCreateOrgId() {
		return createOrgId;
	}

	public void setCreateOrgId(String createOrgId) {
		this.createOrgId = createOrgId;
	}
	
	public String getUpdateOrgId() {
		return updateOrgId;
	}

	public void setUpdateOrgId(String updateOrgId) {
		this.updateOrgId = updateOrgId;
	}
	
	public String getInstructorCategory() {
		return instructorCategory;
	}

	public void setInstructorCategory(String instructorCategory) {
		this.instructorCategory = instructorCategory;
	}
	
	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getDateStart() {
		return dateStart;
	}

	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}

	public String getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}