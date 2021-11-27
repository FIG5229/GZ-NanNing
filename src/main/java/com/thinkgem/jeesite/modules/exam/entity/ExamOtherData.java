/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 其他数据Entity
 * @author kevin.jia
 * @version 2020-11-11
 */
public class ExamOtherData extends DataEntity<ExamOtherData> {

	private static final long serialVersionUID = 1L;
	private Date checkDate;		// 检查时间
	private String checkUnit;		// 检查单位
	private String checkUnitId;		// 检查单位ID
	private String checkPerson;		// 检查人
	private String dutyUnit;		// 责任单位
	private String dutyUnitId;		// 责任单位ID
	private String useModel;		// 使用模板id
	private String chooseOptions;		// 选择项
	private String testStandart;		// 绩效考评标准
	private String scortSituation;		// 扣分情况
	private String remark;		// 备注
	private String dutyLeader;		// 责任领导
	private String dutyPerson;		// 责任人
	private String reviewType;		// 整改情况
	private String opinion;		// 整改审核意见
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String status;		// 审核状态（1：已通过 2：未通过  3：未审核）
	private String addStatus;		// 添加时状态（1：保存 2：提交）
	private String explan;		// 整改情况说明
	private String dutyUnitScore;		// 责任单位扣分
	private String dutyLeaderScore;		// 责任领导扣分
	private String dutyPersonScore;		// 责任人扣分
	private String useModelName;		// 使用模板的名称
	private String checkPersonId;		// 检查人id
	private String dutyLeaderId;		// 责任领导id
	private String dutyPersonId;		// 责任人的id

	private Date beginCheckDate;		// 开始 检查时间
	private Date endCheckDate;		// 结束 检查时间

	private List<ExamOtherDataChild> examOtherDataList = Lists.newArrayList();        // 子表列表


	public ExamOtherData() {
		super();
	}

	public ExamOtherData(String id){
		super(id);
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	
	public String getCheckUnit() {
		return checkUnit;
	}

	public void setCheckUnit(String checkUnit) {
		this.checkUnit = checkUnit;
	}
	
	public String getCheckUnitId() {
		return checkUnitId;
	}

	public void setCheckUnitId(String checkUnitId) {
		this.checkUnitId = checkUnitId;
	}
	
	public String getCheckPerson() {
		return checkPerson;
	}

	public void setCheckPerson(String checkPerson) {
		this.checkPerson = checkPerson;
	}
	
	public String getDutyUnit() {
		return dutyUnit;
	}

	public void setDutyUnit(String dutyUnit) {
		this.dutyUnit = dutyUnit;
	}
	
	public String getDutyUnitId() {
		return dutyUnitId;
	}

	public void setDutyUnitId(String dutyUnitId) {
		this.dutyUnitId = dutyUnitId;
	}
	
	public String getUseModel() {
		return useModel;
	}

	public void setUseModel(String useModel) {
		this.useModel = useModel;
	}
	
	public String getChooseOptions() {
		return chooseOptions;
	}

	public void setChooseOptions(String chooseOptions) {
		this.chooseOptions = chooseOptions;
	}
	
	public String getTestStandart() {
		return testStandart;
	}

	public void setTestStandart(String testStandart) {
		this.testStandart = testStandart;
	}
	
	public String getScortSituation() {
		return scortSituation;
	}

	public void setScortSituation(String scortSituation) {
		this.scortSituation = scortSituation;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getDutyLeader() {
		return dutyLeader;
	}

	public void setDutyLeader(String dutyLeader) {
		this.dutyLeader = dutyLeader;
	}
	
	public String getDutyPerson() {
		return dutyPerson;
	}

	public void setDutyPerson(String dutyPerson) {
		this.dutyPerson = dutyPerson;
	}
	
	public String getReviewType() {
		return reviewType;
	}

	public void setReviewType(String reviewType) {
		this.reviewType = reviewType;
	}
	
	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAddStatus() {
		return addStatus;
	}

	public void setAddStatus(String addStatus) {
		this.addStatus = addStatus;
	}

	public String getExplan() {
		return explan;
	}

	public void setExplan(String explan) {
		this.explan = explan;
	}

	public String getDutyUnitScore() {
		return dutyUnitScore;
	}

	public void setDutyUnitScore(String dutyUnitScore) {
		this.dutyUnitScore = dutyUnitScore;
	}

	public String getDutyLeaderScore() {
		return dutyLeaderScore;
	}

	public void setDutyLeaderScore(String dutyLeaderScore) {
		this.dutyLeaderScore = dutyLeaderScore;
	}

	public String getDutyPersonScore() {
		return dutyPersonScore;
	}

	public void setDutyPersonScore(String dutyPersonScore) {
		this.dutyPersonScore = dutyPersonScore;
	}
	
	public String getUseModelName() {
		return useModelName;
	}

	public void setUseModelName(String useModelName) {
		this.useModelName = useModelName;
	}
	
	public String getCheckPersonId() {
		return checkPersonId;
	}

	public void setCheckPersonId(String checkPersonId) {
		this.checkPersonId = checkPersonId;
	}
	
	public String getDutyLeaderId() {
		return dutyLeaderId;
	}

	public void setDutyLeaderId(String dutyLeaderId) {
		this.dutyLeaderId = dutyLeaderId;
	}
	
	public String getDutyPersonId() {
		return dutyPersonId;
	}

	public void setDutyPersonId(String dutyPersonId) {
		this.dutyPersonId = dutyPersonId;
	}

	public Date getBeginCheckDate() {
		return beginCheckDate;
	}

	public void setBeginCheckDate(Date beginCheckDate) {
		this.beginCheckDate = beginCheckDate;
	}

	public Date getEndCheckDate() {
		return endCheckDate;
	}

	public void setEndCheckDate(Date endCheckDate) {
		this.endCheckDate = endCheckDate;
	}

	public List<ExamOtherDataChild> getExamOtherDataList() {
		return examOtherDataList;
	}

	public void setExamOtherDataList(List<ExamOtherDataChild> examOtherDataList) {
		this.examOtherDataList = examOtherDataList;
	}
}