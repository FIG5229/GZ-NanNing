/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 民警个人训历档案报表Entity
 * @author kevin.jia
 * @version 2020-09-28
 */
public class AffairPolicePersonalTrainingFile extends DataEntity<AffairPolicePersonalTrainingFile> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "姓名", type = 0, align = 2, sort = 0)
	private String name;		// 姓名
	@ExcelField(title = "性别", type = 0, align = 2, sort = 1)
	private String sex;		// 性别
	@ExcelField(title = "单位", type = 0, align = 2, sort = 2)
	private String unit;		// 单位
	private String unitId;		// 单位id
	@ExcelField(title = "职务", type = 0, align = 2, sort = 3)
	private String job;		// 职务
	@ExcelField(title = "出生日期", type = 0, align = 2, sort = 4)
	private Date birthday;		// 出生日期
	@ExcelField(title = "入警时间", type = 0, align = 2, sort = 5)
	private Date hiredate;		// 入警时间
	@ExcelField(title = "学历", type = 0, align = 2, sort = 6)
	private String education;		// 学历
	@ExcelField(title = "政治面貌", type = 0, align = 2, sort = 7)
	private String politicsFace;		// 政治面貌
	@ExcelField(title = "专业技术职称", type = 0, align = 2, sort = 8)
	private String technicalTitle;		// 专业技术职称
	@ExcelField(title = "联系电话", type = 0, align = 2, sort = 9)
	private String phone;		// 联系电话
	@ExcelField(title = "在线课程-参加次数", type = 0, align = 2, sort = 10)
	private String onlineCourseNum;		// 在线课程-参加次数
	@ExcelField(title = "在线课程-通过率", type = 0, align = 2, sort = 11)
	private String onlineCoursePassing;		// 在线课程-通过率
	@ExcelField(title = "在线课程-平均分", type = 0, align = 2, sort = 12)
	private String onlineCourseAverage;		// 在线课程-平均分
	@ExcelField(title = "在线课程-总课时(分钟)", type = 0, align = 2, sort = 13)
	private String onlineCourseTotalTime;		// 在线课程-总课时(分钟)
	@ExcelField(title = "在线课程-学分", type = 0, align = 2, sort = 14)
	private String onlineCourseCredit;		// 在线课程-学分
	@ExcelField(title = "培训班课程-参加次数", type = 0, align = 2, sort = 15)
	private String trainCourseNum;		// 培训班课程-参加次数
	@ExcelField(title = "培训班课程-通过率", type = 0, align = 2, sort = 16)
	private String trainCoursePassing;		// 培训班课程-通过率
	@ExcelField(title = "培训班课程-平均分", type = 0, align = 2, sort = 17)
	private String trainCourseAverage;		// 培训班课程-平均分
	@ExcelField(title = "培训班课程-总课时(分钟)", type = 0, align = 2, sort = 17)
	private String trainCourseTotalTime;		// 培训班课程-总课时(分钟)
	@ExcelField(title = "培训班课程-学分", type = 0, align = 2, sort = 18)
	private String trainCourseCredit;		// 培训班课程-学分
	@ExcelField(title = "岗位练兵-参加次数", type = 0, align = 2, sort = 19)
	private String jobTrainingNum;		// 岗位练兵-参加次数
	@ExcelField(title = "总时长(小时)", type = 0, align = 2, sort = 20)
	private String jobTrainingTotalTime;		// 岗位练兵-总时长(小时)
	@ExcelField(title = "委外培训-参加次数", type = 0, align = 2, sort = 21)
	private String outTrainingNum;		// 委外培训-参加次数
	@ExcelField(title = "委外培训-完成率", type = 0, align = 2, sort = 22)
	private String outTrainingFinish;		// 委外培训-完成率
	@ExcelField(title = "主要类别", type = 0, align = 2, sort = 23)
	private String outTrainingMainType;		// 委外培训-主要类别
	@ExcelField(title = "最高级别", type = 0, align = 2, sort = 24)
	private String outTrainingHighestLevel;		// 委外培训-最高级别
	@ExcelField(title = "取得证书", type = 0, align = 2, sort = 25)
	private String outTrainingGetCcie;		// 委外培训取得证书
	@ExcelField(title = "交流学习-参加次数", type = 0, align = 2, sort = 26)
	private String exchangeStudyNum;		// 交流学习-参加次数
	@ExcelField(title = "交流学习-完成率", type = 0, align = 2, sort = 27)
	private String exchangeStudyFinish;		// 交流学习-完成率
	@ExcelField(title = "主要岗位", type = 0, align = 2, sort = 28)
	private String exchangeStudyMainJob;		// 交流学习-主要岗位
	@ExcelField(title = "最高规格", type = 0, align = 2, sort = 29)
	private String exchangeStudyHighestSpec;		// 交流学习-最高规格
	@ExcelField(title = "交流学习-总时长", type = 0, align = 2, sort = 30)
	private String exchangeStudyTotalTime;		// 交流学习-总时长
	@ExcelField(title = "考试-参加次数", type = 0, align = 2, sort = 31)
	private String examNum;		// 考试-参加次数
	@ExcelField(title = "考试-合格率", type = 0, align = 2, sort = 32)
	private String examPass;		// 考试-合格率
	@ExcelField(title = "考试-平均分", type = 0, align = 2, sort = 33)
	private String examAverage;		// 考试-平均分
	@ExcelField(title = "考试-作弊次数", type = 0, align = 2, sort = 34)
	private String examCheat;		// 考试-作弊次数
	@ExcelField(title = "教育训练积分", type = 0, align = 2, sort = 35)
	private String trainingIntegral;		// 教育训练积分
	@ExcelField(title = "教育训练主管部门鉴定意见", type = 0, align = 2, sort = 36)
	private String expertOpinion;		// 教育训练主管部门鉴定意见
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String idNumber;		// 身份证号

	public AffairPolicePersonalTrainingFile() {
		super();
	}

	public AffairPolicePersonalTrainingFile(String id){
		super(id);
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
	
	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getHiredate() {
		return hiredate;
	}

	public void setHiredate(Date hiredate) {
		this.hiredate = hiredate;
	}
	
	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}
	
	public String getPoliticsFace() {
		return politicsFace;
	}

	public void setPoliticsFace(String politicsFace) {
		this.politicsFace = politicsFace;
	}
	
	public String getTechnicalTitle() {
		return technicalTitle;
	}

	public void setTechnicalTitle(String technicalTitle) {
		this.technicalTitle = technicalTitle;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getOnlineCourseNum() {
		return onlineCourseNum;
	}

	public void setOnlineCourseNum(String onlineCourseNum) {
		this.onlineCourseNum = onlineCourseNum;
	}
	
	public String getOnlineCoursePassing() {
		return onlineCoursePassing;
	}

	public void setOnlineCoursePassing(String onlineCoursePassing) {
		this.onlineCoursePassing = onlineCoursePassing;
	}
	
	public String getOnlineCourseAverage() {
		return onlineCourseAverage;
	}

	public void setOnlineCourseAverage(String onlineCourseAverage) {
		this.onlineCourseAverage = onlineCourseAverage;
	}
	
	public String getOnlineCourseTotalTime() {
		return onlineCourseTotalTime;
	}

	public void setOnlineCourseTotalTime(String onlineCourseTotalTime) {
		this.onlineCourseTotalTime = onlineCourseTotalTime;
	}
	
	public String getOnlineCourseCredit() {
		return onlineCourseCredit;
	}

	public void setOnlineCourseCredit(String onlineCourseCredit) {
		this.onlineCourseCredit = onlineCourseCredit;
	}
	
	public String getTrainCourseNum() {
		return trainCourseNum;
	}

	public void setTrainCourseNum(String trainCourseNum) {
		this.trainCourseNum = trainCourseNum;
	}
	
	public String getTrainCoursePassing() {
		return trainCoursePassing;
	}

	public void setTrainCoursePassing(String trainCoursePassing) {
		this.trainCoursePassing = trainCoursePassing;
	}
	
	public String getTrainCourseAverage() {
		return trainCourseAverage;
	}

	public void setTrainCourseAverage(String trainCourseAverage) {
		this.trainCourseAverage = trainCourseAverage;
	}
	
	public String getTrainCourseTotalTime() {
		return trainCourseTotalTime;
	}

	public void setTrainCourseTotalTime(String trainCourseTotalTime) {
		this.trainCourseTotalTime = trainCourseTotalTime;
	}
	
	public String getTrainCourseCredit() {
		return trainCourseCredit;
	}

	public void setTrainCourseCredit(String trainCourseCredit) {
		this.trainCourseCredit = trainCourseCredit;
	}
	
	public String getJobTrainingNum() {
		return jobTrainingNum;
	}

	public void setJobTrainingNum(String jobTrainingNum) {
		this.jobTrainingNum = jobTrainingNum;
	}
	
	public String getJobTrainingTotalTime() {
		return jobTrainingTotalTime;
	}

	public void setJobTrainingTotalTime(String jobTrainingTotalTime) {
		this.jobTrainingTotalTime = jobTrainingTotalTime;
	}
	
	public String getOutTrainingNum() {
		return outTrainingNum;
	}

	public void setOutTrainingNum(String outTrainingNum) {
		this.outTrainingNum = outTrainingNum;
	}
	
	public String getOutTrainingFinish() {
		return outTrainingFinish;
	}

	public void setOutTrainingFinish(String outTrainingFinish) {
		this.outTrainingFinish = outTrainingFinish;
	}
	
	public String getOutTrainingMainType() {
		return outTrainingMainType;
	}

	public void setOutTrainingMainType(String outTrainingMainType) {
		this.outTrainingMainType = outTrainingMainType;
	}
	
	public String getOutTrainingHighestLevel() {
		return outTrainingHighestLevel;
	}

	public void setOutTrainingHighestLevel(String outTrainingHighestLevel) {
		this.outTrainingHighestLevel = outTrainingHighestLevel;
	}
	
	public String getOutTrainingGetCcie() {
		return outTrainingGetCcie;
	}

	public void setOutTrainingGetCcie(String outTrainingGetCcie) {
		this.outTrainingGetCcie = outTrainingGetCcie;
	}
	
	public String getExchangeStudyNum() {
		return exchangeStudyNum;
	}

	public void setExchangeStudyNum(String exchangeStudyNum) {
		this.exchangeStudyNum = exchangeStudyNum;
	}
	
	public String getExchangeStudyFinish() {
		return exchangeStudyFinish;
	}

	public void setExchangeStudyFinish(String exchangeStudyFinish) {
		this.exchangeStudyFinish = exchangeStudyFinish;
	}
	
	public String getExchangeStudyMainJob() {
		return exchangeStudyMainJob;
	}

	public void setExchangeStudyMainJob(String exchangeStudyMainJob) {
		this.exchangeStudyMainJob = exchangeStudyMainJob;
	}
	
	public String getExchangeStudyHighestSpec() {
		return exchangeStudyHighestSpec;
	}

	public void setExchangeStudyHighestSpec(String exchangeStudyHighestSpec) {
		this.exchangeStudyHighestSpec = exchangeStudyHighestSpec;
	}
	
	public String getExchangeStudyTotalTime() {
		return exchangeStudyTotalTime;
	}

	public void setExchangeStudyTotalTime(String exchangeStudyTotalTime) {
		this.exchangeStudyTotalTime = exchangeStudyTotalTime;
	}
	
	public String getExamNum() {
		return examNum;
	}

	public void setExamNum(String examNum) {
		this.examNum = examNum;
	}
	
	public String getExamPass() {
		return examPass;
	}

	public void setExamPass(String examPass) {
		this.examPass = examPass;
	}
	
	public String getExamAverage() {
		return examAverage;
	}

	public void setExamAverage(String examAverage) {
		this.examAverage = examAverage;
	}
	
	public String getExamCheat() {
		return examCheat;
	}

	public void setExamCheat(String examCheat) {
		this.examCheat = examCheat;
	}
	
	public String getTrainingIntegral() {
		return trainingIntegral;
	}

	public void setTrainingIntegral(String trainingIntegral) {
		this.trainingIntegral = trainingIntegral;
	}
	
	public String getExpertOpinion() {
		return expertOpinion;
	}

	public void setExpertOpinion(String expertOpinion) {
		this.expertOpinion = expertOpinion;
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

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
}