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
 * @version 2020-08-18
 */
public class AffairXunLiDetails extends DataEntity<AffairXunLiDetails> {

	private static final long serialVersionUID = 1L;

	/**
	 * 在线课程
	 */
	//private String name;		//   课程名称
	private String plan;			// 学习进度
	private String learnTime;		// 学习时长
	private String studyTime;		//  学习总次数
	private String beforeClassGrade;  //课前成绩
	private String afterClassGrade;  //课前成绩
	private String passStatus;		//通过状态
	private String passTime;		//通过时间
	private String hour;		//课时
	/**
	 * 培训班课程
	 */
	@ExcelField(title = "培训名称", type = 0, align = 2, sort = 1)
	private String name;
	@ExcelField(title = "培训类型", type = 0, align = 2, sort = 2,dictType = "affair_train_type")
	private String type;
	@ExcelField(title = "培训内容", type = 0, align = 2, sort = 3)
	private String content;
	@ExcelField(title = "培训场地", type = 0, align = 2, sort = 4)
	private String site;
	@ExcelField(title = "培训对象", type = 0, align = 2, sort = 5)
	private String trainObject;


	/**
	 * 委外培训
	 */
	private String userName;		//  用户名
	private String idNumber;		// 身份证号
	@ExcelField(title = "培训班名称", type = 0, align = 2, sort = 6)
	private String externalName;		// 培训班名称
	@ExcelField(title = "培训班类型", type = 0, align = 2, sort = 7,dictType = "external_type")
	private String externalType;		// 培训班类别
	@ExcelField(title = "培训班完成情况", type = 0, align = 2, sort = 8,dictType = "train_completion")
	private String completion;		//  培训完成情况
	private String institutionCode;		// 主办单位机构代码
	@ExcelField(title = "主办单位", type = 0, align = 2, sort = 9)
	private String unit;		// 主办单位
	private String unitId;		// 主办单位id
	private String unitLevel;		// 主办单位级别
	@ExcelField(title = "开始时间", type = 0, align = 2, sort = 10,dictType = "unit_level")
	private Date beganDate;		//  开始时间
	@ExcelField(title = "结束时间", type = 0, align = 2, sort = 11)
	private Date resultDate;		// 结束时间
	private String quitStatus;		// 培训离岗状态
	@ExcelField(title = "承训机构名称", type = 0, align = 2, sort = 12)
	private String unitName;		// 承训机构名称
	@ExcelField(title = "培训地点", type = 0, align = 2, sort = 13)
	private String trainSite;		// 培训地点
	private String certificateCode;		// 证书编号
	@ExcelField(title = "培训时所在单位及职务", type = 0, align = 2, sort = 14)
	private String unitJob;		// 培训时所在单位及职务

	/**
	 * 交流学习
	 */
	//@ExcelField(title = "交流名称", type = 0, align = 2, sort = 171)
	private String swapName;		// 交流名称
	//@ExcelField(title = "交流规格类型", type = 0, align = 2, sort = 181)
	private String sizeType;		// 交流规格类型
	//@ExcelField(title = "交流岗位类型", type = 0, align = 2, sort = 191)
	private String jobType;		//  交流岗位类型
	//@ExcelField(title = "交流学习类别", type = 0, align = 2, sort = 201)
	private String studyType;		//   交流学习类别
	//@ExcelField(title = "交流时间", type = 0, align = 2, sort = 211)
	private Date date;		// 交流时间
	//@ExcelField(title = "交流单位任职情况", type = 0, align = 2, sort = 221)
	private String serviceCondition;		// 交流单位任职情况
	//@ExcelField(title = "交流学习鉴定", type = 0, align = 2, sort = 231)
	private String studyIdentity;		// 交流学习鉴定
	/**
	 * 岗位练兵
	 */
	@ExcelField(title = "训练开始时间", type = 0, align = 2, sort = 15)
	private Date drillDateBegin;		//  训练开始时间
	@ExcelField(title = "训练结束时间", type = 0, align = 2, sort = 16)
	private Date drillDateOver;		//  训练结束时间
	@ExcelField(title = "项目类别", type = 0, align = 2, sort = 17,dictType = "project_type")
	private String itemClassification;		// 项目类别
	@ExcelField(title = "训练概况", type = 0, align = 2, sort = 18)
	private String drillGeneralSituation;		 // 训练概况
	@ExcelField(title = "训练时长", type = 0, align = 2, sort = 19)
	private String xunlianTime;		//训练时间（小时）




	public AffairXunLiDetails() {
		super();
	}

	public AffairXunLiDetails(String id){
		super(id);
	}
	//------------------------------------------


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

	public String getXunlianTime() {
		return xunlianTime;
	}

	public void setXunlianTime(String xunlianTime) {
		this.xunlianTime = xunlianTime;
	}


	public String getAfterClassGrade() {
		return afterClassGrade;
	}

	public void setAfterClassGrade(String afterClassGrade) {
		this.afterClassGrade = afterClassGrade;
	}

	public String getBeforeClassGrade() {
		return beforeClassGrade;
	}

	public void setBeforeClassGrade(String beforeClassGrade) {
		this.beforeClassGrade = beforeClassGrade;
	}

	public String getPassStatus() {
		return passStatus;
	}

	public void setPassStatus(String passStatus) {
		this.passStatus = passStatus;
	}

	public String getPassTime() {
		return passTime;
	}

	public void setPassTime(String passTime) {
		this.passTime = passTime;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	/*public String getDrillTime() {
		return drillTime;
	}

	public void setDrillTime(String drillTime) {
		this.drillTime = drillTime;
	}*/

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public String getLearnTime() {
		return learnTime;
	}

	public void setLearnTime(String learnTime) {
		this.learnTime = learnTime;
	}

	public String getStudyTime() {
		return studyTime;
	}

	public void setStudyTime(String studyTime) {
		this.studyTime = studyTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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



}