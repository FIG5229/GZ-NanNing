/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 教育训练培训计划Entity
 * @author jack.xu
 * @version 2020-07-01
 */
public class AffairEducationTrain extends DataEntity<AffairEducationTrain> {
	
	private static final long serialVersionUID = 1L;
	private String trainYear;		// 培训年度
	private String informant;		// 填报人
	@ExcelField(title = "单位", type = 0, align = 2, sort = 1)
	private String unit;		// 填报机构
	@ExcelField(title = "培训班名称", type = 0, align = 2, sort = 2)
	private String title;		// 标题
	@ExcelField(title = "培训时间", type = 0, align = 2, sort =  6)
	private String trainDate;		// 培训日期
	private Date beganDate;		// 开始时间
	private Date resultDate;		// 结束时间
	private Date lastDate;		// 最后一次填报日期
	private String filledClassCount;		// 已填报的计划培训班总数
	private String approvedClassCount;		// 已审批通过的计划培训班数量
	private String approvedCount;		// 已审批通过的计划培训总人数
	private String incompleteApprovalCount;		// 未完成审批的计划培训班数量
 	private String trainName;		// 培训名称
//	@ExcelField(title = "培训班类型", type = 0, align = 2, sort = 12)
//	private String trainType;		// 培训班类型
//	@ExcelField(title = "培训层次", type = 0, align = 2, sort = 13)
//	private String trainLevel;		// 培训层次
 	@ExcelField(title = "天数", type = 0, align = 2, sort = 7)
 	private String trainDay;		// 培训天数
 	@ExcelField(title = "参训人数", type = 0, align = 2, sort =  9)
 	private String trainCount;		// 培训人数
 	@ExcelField(title = "培训对象", type = 0, align = 2, sort =  4)
 	private String trainObject;		// 培训对象
//	@ExcelField(title = "培训方式", type = 0, align = 2, sort = 17)
//	private String trainWay;		// 培训方式
 	@ExcelField(title = "培训地点", type = 0, align = 2, sort = 8)
 	private String trainSite;		// 培训场地
 	@ExcelField(title = "培训目的", type = 0, align = 2, sort = 3)
 	private String trainPurpose;		// 培训目的
//	@ExcelField(title = "培训地点", type = 0, align = 2, sort = 20)
//	private String site;		// 培训地点
 	@ExcelField(title = "培训内容", type = 0, align = 2, sort = 5)
 	private String content;		// 培训内容
 	@ExcelField(title = "列支渠道", type = 0, align = 2, sort =  12)
 	private String listOfChannel;		// 列支渠道
 	@ExcelField(title = "培训费", type = 0, align = 2, sort =  10)
 	private String trainFees;		// 培训费
 	@ExcelField(title = "师资费", type = 0, align = 2, sort =  11)
 	private String teacherFees;		// 师资费
//	@ExcelField(title = "预算给用", type = 0, align = 2, sort = 25)
//	private String budget;		// 预算给用
 	@ExcelField(title = "实施状态", type = 0, align = 2, sort = 13)
 	private String implementStatus;		// 实施状态

	private String reviewer;		// 审批人
	private Date approveDate;		// 审批时间
	private String approveLevel;		// 审批层级
	private String approveResult;		// 审批结果
	private String approveStatus;		// 审批状态
	private String remarks;				// 审批备注

	private String unitId;		// 单位id
	private String filePath;		// 附件
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		        // 创建者
	private String updateIdNo;		        // 更新者身份证号
	private String updateOrgId;		// 更新者机构id
	private String treeId;
	private Date startDate;
	private Date endDate;


	public String getTrainDate() {
		return trainDate;
	}

	public void setTrainDate(String trainDate) {
		this.trainDate = trainDate;
	}

	public String getTrainDay() {
		return trainDay;
	}

	public void setTrainDay(String trainDay) {
		this.trainDay = trainDay;
	}

	public String getImplementStatus() {
		return implementStatus;
	}

	public void setImplementStatus(String implementStatus) {
		this.implementStatus = implementStatus;
	}

	public String getTrainName() {
		return trainName;
	}

	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}

	public String getTrainCount() {
		return trainCount;
	}

	public void setTrainCount(String trainCount) {
		this.trainCount = trainCount;
	}

	public String getTrainObject() {
		return trainObject;
	}

	public void setTrainObject(String trainObject) {
		this.trainObject = trainObject;
	}

	public String getTrainSite() {
		return trainSite;
	}

	public void setTrainSite(String trainSite) {
		this.trainSite = trainSite;
	}

	public String getTrainPurpose() {
		return trainPurpose;
	}

	public void setTrainPurpose(String trainPurpose) {
		this.trainPurpose = trainPurpose;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getListOfChannel() {
		return listOfChannel;
	}

	public void setListOfChannel(String listOfChannel) {
		this.listOfChannel = listOfChannel;
	}

	public String getTrainFees() {
		return trainFees;
	}

	public void setTrainFees(String trainFees) {
		this.trainFees = trainFees;
	}

	public String getTeacherFees() {
		return teacherFees;
	}

	public void setTeacherFees(String teacherFees) {
		this.teacherFees = teacherFees;
	}

	public String getTreeId() {
		return treeId;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}

	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public AffairEducationTrain() {
		super();
	}

	public AffairEducationTrain(String id){
		super(id);
	}

	public String getTrainYear() {
		return trainYear;
	}

	public void setTrainYear(String trainYear) {
		this.trainYear = trainYear;
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
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

//	public String getTrainName() {
//		return trainName;
//	}
//
//	public void setTrainName(String trainName) {
//		this.trainName = trainName;
//	}
////
//	public String getInformant() {
//		return informant;
//	}
//
//	public void setInformant(String informant) {
//		this.informant = informant;
//	}
//
//	public String getTrainType() {
//		return trainType;
//	}
//
//	public void setTrainType(String trainType) {
//		this.trainType = trainType;
//	}
//
//	public String getTrainLevel() {
//		return trainLevel;
//	}
//
//	public void setTrainLevel(String trainLevel) {
//		this.trainLevel = trainLevel;
//	}
//

	public String getInformant() {
		return informant;
	}

	public void setInformant(String informant) {
		this.informant = informant;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getResultDate() {
		return resultDate;
	}

	public void setResultDate(Date resultDate) {
		this.resultDate = resultDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getLastDate() {
		return lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}

//	public String getTrainDay() {
//		return trainDay;
//	}
//
//	public void setTrainDay(String trainDay) {
//		this.trainDay = trainDay;
//	}
//
//	public String getTrainCount() {
//		return trainCount;
//	}
//
//	public void setTrainCount(String trainCount) {
//		this.trainCount = trainCount;
//	}
//
//	public String getTrainObject() {
//		return trainObject;
//	}
//
//	public void setTrainObject(String trainObject) {
//		this.trainObject = trainObject;
//	}
//
//	public String getTrainWay() {
//		return trainWay;
//	}
//
//	public void setTrainWay(String trainWay) {
//		this.trainWay = trainWay;
//	}
//
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
//
//	public String getTrainSite() {
//		return trainSite;
//	}
//
//	public void setTrainSite(String trainSite) {
//		this.trainSite = trainSite;
//	}
//
//	public String getImplementStatus() {
//		return implementStatus;
//	}
//
//	public void setImplementStatus(String implementStatus) {
//		this.implementStatus = implementStatus;
//	}
//
//	public String getTrainFees() {
//		return trainFees;
//	}
//
//	public void setTrainFees(String trainFees) {
//		this.trainFees = trainFees;
//	}
//
//	public String getTeacherFees() {
//		return teacherFees;
//	}
//
//	public void setTeacherFees(String teacherFees) {
//		this.teacherFees = teacherFees;
//	}
//
//	public String getBudget() {
//		return budget;
//	}
//
//	public void setBudget(String budget) {
//		this.budget = budget;
//	}
//
//	public String getTrainPurpose() {
//		return trainPurpose;
//	}
//
//	public void setTrainPurpose(String trainPurpose) {
//		this.trainPurpose = trainPurpose;
//	}
//
//	public String getListOfChannel() {
//		return listOfChannel;
//	}
//
//	public void setListOfChannel(String listOfChannel) {
//		this.listOfChannel = listOfChannel;
//	}
//
//	public String getSite() {
//		return site;
//	}
//
//	public void setSite(String site) {
//		this.site = site;
//	}
//
//	public String getContent() {
//		return content;
//	}
//
//	public void setContent(String content) {
//		this.content = content;
//	}

	public String getReviewer() {
		return reviewer;
	}

	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}
	
	public String getApproveLevel() {
		return approveLevel;
	}

	public void setApproveLevel(String approveLevel) {
		this.approveLevel = approveLevel;
	}
	
	public String getApproveResult() {
		return approveResult;
	}

	public void setApproveResult(String approveResult) {
		this.approveResult = approveResult;
	}
	
	public String getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}
	
	public String getFilledClassCount() {
		return filledClassCount;
	}

	public void setFilledClassCount(String filledClassCount) {
		this.filledClassCount = filledClassCount;
	}
	
	public String getApprovedClassCount() {
		return approvedClassCount;
	}

	public void setApprovedClassCount(String approvedClassCount) {
		this.approvedClassCount = approvedClassCount;
	}
	
	public String getApprovedCount() {
		return approvedCount;
	}

	public void setApprovedCount(String approvedCount) {
		this.approvedCount = approvedCount;
	}
	
	public String getIncompleteApprovalCount() {
		return incompleteApprovalCount;
	}

	public void setIncompleteApprovalCount(String incompleteApprovalCount) {
		this.incompleteApprovalCount = incompleteApprovalCount;
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

	public Date getBeganDate() {
		return beganDate;
	}

	public void setBeganDate(Date beganDate) {
		this.beganDate = beganDate;
	}
}