/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 培训班管理Entity
 * @author jack.xu
 * @version 2020-07-08
 */
public class AffairClassManage extends DataEntity<AffairClassManage> {

	private static final long serialVersionUID = 1L;
	@ExcelField(title = "名称", type = 0, align = 2, sort = 1)
	private String name;		// 培训名称
	private String year;		// 培训年度
	private String unit;		// 单位
//	@ExcelField(title = "培训日期", type = 0, align = 2, sort = 3)
//	private Date trainDate;			// 培训日期
	private String title;		// 标题
	private String type;		// 培训班类型
	@ExcelField(title = "培训方式", type = 0, align = 2, sort = 8, dictType="affair_train_way")
	private String trainWay;		//培训方式
	@ExcelField(title = "培训层次", type = 0, align = 2, sort = 3, dictType="affair_train_level")
	private String level;			//培训层次
	@ExcelField(title = "培训天数", type = 0, align = 2, sort = 4)
	private String trainDay;		//培训天数
	@ExcelField(title = "培训人数", type = 0, align = 2,sort = 7)
	private String count;			//培训人数
	@ExcelField(title = "培训目的", type = 0, align = 2, sort = 15)
	private String purpose;		// 培训目的
	@ExcelField(title = "培训内容", type = 0, align = 2, sort = 14)
	private String content;		// 培训内容
	private String site;		// 培训场地
	@ExcelField(title = "审批状态", type = 0, align = 2, sort = 12)
	private String approvalStatus;
	private String trainObject;	// 培训对象
	private String budget;		// 培训预算
	@ExcelField(title = "开始日期", type = 0, align = 2,sort = 5)
	private Date beganDate;			//培训开始时间
	@ExcelField(title = "结束时间", type = 0, align = 2,sort = 6)
	private Date resultDate;		//培训结束时间
	@ExcelField(title = "实施状态",type = 0,align = 2, sort = 13, dictType = "affair_train_status")
	private String status;    		//实施状态
	@ExcelField(title = "培训费",type = 0, align = 2,sort = 9)
	private String fees;			//培训费
	@ExcelField(title = "师资费", type = 0, align = 2, sort = 10)
	private String teacherFees;		//师资费
	@ExcelField(title = "列支渠道", type = 0, align = 2, sort = 11, dictType = "affair_train_channel")
	private String channel;		// 渠道
	@ExcelField(title = "培训项目", type = 0, align = 2, sort = 2)
	private String project;		//培训项目
	private String informant;		//填报人
	private String openStatus;		// 开班状态
	private String classStatus;		// 建班状态
	private String pospStatus;		// 结项状态
	private String creator;		// 创建人
	private Date createTime;		// 创建时间
	private String creatorUnit;		// 创建人单位
	private String sponsorUnit;		// 主办部门
	private String teacher;		// 班主任
	private String assistant;		// 助教
	private String teacherPhone;		// 班主任电话
	private String phone;		// 联系电话

	private double classCount;		//课程总分
	private double score;		//评估分数
	private double participateTrain;		//应参训人数
	private double realParticipate;		//实际参训人数
	private double participateRate;		//参训率
	private double passedCount;		//已通过人数
	private double failCount;		//未通过人数
	private double passRate;		//通过率
	private double accommodationFees;		//住宿费
	private double boardWages;		//伙食费
	private double siteFees;		//场地资料交通费
	private double otherFees;		//其他费用
	private double feesCount;		//费用总额
	private double studyTime;		//学习总时长
	private double averageTime;		//平均学习时长
	private double studyCount;		//学习总次数
	private double averageCount;		//平均学习次数

	private String unitId;		// 单位id
	private String creatorUnitId;	//创建人单位id
	private String sponsorUnitId;	//主办单位id
	private String createOrgId;		// 创建者机构id
	private String updateOrgId;		// 更新者机构id
	private String createIdNo;		        // 创建者
	private String updateIdNo;		        // 更新者身份证号
//	private Date startDate;
//	private Date endDate;


	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public AffairClassManage() {
		super();
	}

	public AffairClassManage(String id){
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

//	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//	public Date getTrainDate() {
//		return trainDate;
//	}
//
//	public void setTrainDate(Date trainDate) {
//		this.trainDate = trainDate;
//	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
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

	public String getBudget() {
		return budget;
	}

	public void setBudget(String budget) {
		this.budget = budget;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getOpenStatus() {
		return openStatus;
	}

	public void setOpenStatus(String openStatus) {
		this.openStatus = openStatus;
	}

	public String getClassStatus() {
		return classStatus;
	}

	public void setClassStatus(String classStatus) {
		this.classStatus = classStatus;
	}

	public String getPospStatus() {
		return pospStatus;
	}

	public void setPospStatus(String pospStatus) {
		this.pospStatus = pospStatus;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

//	@JsonFormat(pattern = "yyyy-MM-dd")
//	public Date getStartDate() {
//		return startDate;
//	}
//
//	public void setStartDate(Date startDate) {
//		this.startDate = startDate;
//	}
//
//	@JsonFormat(pattern = "yyyy-MM-dd")
//	public Date getEndDate() {
//		return endDate;
//	}
//
//	public void setEndDate(Date endDate) {
//		this.endDate = endDate;
//	}

	public String getCreatorUnit() {
		return creatorUnit;
	}

	public void setCreatorUnit(String creatorUnit) {
		this.creatorUnit = creatorUnit;
	}

	public String getSponsorUnit() {
		return sponsorUnit;
	}

	public void setSponsorUnit(String sponsorUnit) {
		this.sponsorUnit = sponsorUnit;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public String getAssistant() {
		return assistant;
	}

	public void setAssistant(String assistant) {
		this.assistant = assistant;
	}

	public String getTeacherPhone() {
		return teacherPhone;
	}

	public void setTeacherPhone(String teacherPhone) {
		this.teacherPhone = teacherPhone;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public String getCreateIdNo() {
		return createIdNo;
	}

	public void setCreateIdNo(String createIdNo) {
		this.createIdNo = createIdNo;
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

	public String getFees() {
		return fees;
	}

	public void setFees(String fees) {
		this.fees = fees;
	}

	public String getTeacherFees() {
		return teacherFees;
	}

	public void setTeacherFees(String teacherFees) {
		this.teacherFees = teacherFees;
	}

	public String getInformant() {
		return informant;
	}

	public void setInformant(String informant) {
		this.informant = informant;
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

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getCreatorUnitId() {
		return creatorUnitId;
	}

	public void setCreatorUnitId(String creatorUnitId) {
		this.creatorUnitId = creatorUnitId;
	}

	public String getSponsorUnitId() {
		return sponsorUnitId;
	}

	public void setSponsorUnitId(String sponsorUnitId) {
		this.sponsorUnitId = sponsorUnitId;
	}

	public String getTrainDay() {
		return trainDay;
	}

	public void setTrainDay(String trainDay) {
		this.trainDay = trainDay;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getTrainWay() {
		return trainWay;
	}

	public void setTrainWay(String trainWay) {
		this.trainWay = trainWay;
	}

	public double getClassCount() {
		return classCount;
	}

	public void setClassCount(double classCount) {
		this.classCount = classCount;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public double getParticipateTrain() {
		return participateTrain;
	}

	public void setParticipateTrain(double participateTrain) {
		this.participateTrain = participateTrain;
	}

	public double getRealParticipate() {
		return realParticipate;
	}

	public void setRealParticipate(double realParticipate) {
		this.realParticipate = realParticipate;
	}

	public double getParticipateRate() {
		return participateRate;
	}

	public void setParticipateRate(double participateRate) {
		this.participateRate = participateRate;
	}

	public double getPassedCount() {
		return passedCount;
	}

	public void setPassedCount(double passedCount) {
		this.passedCount = passedCount;
	}

	public double getFailCount() {
		return failCount;
	}

	public void setFailCount(double failCount) {
		this.failCount = failCount;
	}

	public double getPassRate() {
		return passRate;
	}

	public void setPassRate(double passRate) {
		this.passRate = passRate;
	}

	public double getAccommodationFees() {
		return accommodationFees;
	}

	public void setAccommodationFees(double accommodationFees) {
		this.accommodationFees = accommodationFees;
	}

	public double getBoardWages() {
		return boardWages;
	}

	public void setBoardWages(double boardWages) {
		this.boardWages = boardWages;
	}

	public double getSiteFees() {
		return siteFees;
	}

	public void setSiteFees(double siteFees) {
		this.siteFees = siteFees;
	}

	public double getOtherFees() {
		return otherFees;
	}

	public void setOtherFees(double otherFees) {
		this.otherFees = otherFees;
	}

	public double getFeesCount() {
		return feesCount;
	}

	public void setFeesCount(double feesCount) {
		this.feesCount = feesCount;
	}

	public double getStudyTime() {
		return studyTime;
	}

	public void setStudyTime(double studyTime) {
		this.studyTime = studyTime;
	}

	public double getAverageTime() {
		return averageTime;
	}

	public void setAverageTime(double averageTime) {
		this.averageTime = averageTime;
	}

	public double getStudyCount() {
		return studyCount;
	}

	public void setStudyCount(double studyCount) {
		this.studyCount = studyCount;
	}

	public double getAverageCount() {
		return averageCount;
	}

	public void setAverageCount(double averageCount) {
		this.averageCount = averageCount;
	}
}