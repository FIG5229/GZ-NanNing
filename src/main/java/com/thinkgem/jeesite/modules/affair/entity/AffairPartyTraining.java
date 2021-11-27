/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 党内培训Entity
 * @author freeman
 * @version 2020-06-02
 */
public class AffairPartyTraining extends DataEntity<AffairPartyTraining> {
	
	private static final long serialVersionUID = 1L;
	private String hostUnit;		// 主办单位
	private Date beginTime;			// 开始时间
	private Date endTime;			// 结束时间
	private String trainingSites;	// 培训地点
	private String trainingName;	// 培训名称
	private String trainingForms;	// 培训形式
	private String trainingOutcome;	// 培训结果
	private String creator;			// 创建者
	private String creatorOrgId;	// 创建者机构ID
	private String creatorIdNo;		// 创建者身份证号
	private Date createTime;		// 创建时间
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String treeId;
	
	public AffairPartyTraining() {
		super();
	}

	public AffairPartyTraining(String id){
		super(id);
	}

	public String getHostUnit() {
		return hostUnit;
	}

	public void setHostUnit(String hostUnit) {
		this.hostUnit = hostUnit;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public String getTrainingSites() {
		return trainingSites;
	}

	public void setTrainingSites(String trainingSites) {
		this.trainingSites = trainingSites;
	}
	
	public String getTrainingName() {
		return trainingName;
	}

	public void setTrainingName(String trainingName) {
		this.trainingName = trainingName;
	}
	
	public String getTrainingForms() {
		return trainingForms;
	}

	public void setTrainingForms(String trainingForms) {
		this.trainingForms = trainingForms;
	}
	
	public String getTrainingOutcome() {
		return trainingOutcome;
	}

	public void setTrainingOutcome(String trainingOutcome) {
		this.trainingOutcome = trainingOutcome;
	}
	
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	public String getCreatorOrgId() {
		return creatorOrgId;
	}

	public void setCreatorOrgId(String creatorOrgId) {
		this.creatorOrgId = creatorOrgId;
	}
	
	public String getCreatorIdNo() {
		return creatorIdNo;
	}

	public void setCreatorIdNo(String creatorIdNo) {
		this.creatorIdNo = creatorIdNo;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public String getTreeId(){
		return treeId;
	}

	public void setTreeId(String treeId){
		this.treeId = treeId;
	}
	
}