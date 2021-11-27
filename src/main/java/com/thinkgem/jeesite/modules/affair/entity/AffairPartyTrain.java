/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.Date;

/**
 * 党内培训Entity
 * @author daniel.liu
 * @version 2020-06-09
 */
public class AffairPartyTrain extends DataEntity<AffairPartyTrain> {
	
	private static final long serialVersionUID = 1L;
	private Date startTime;		// 开始时间
	private Date endTime;		// 结束时间
	private String unit;		// 主办单位
	private String unitId;		// 主办单位id
	private String trainPlace;		// 培训地点
	private String trainForm;		// 培训形式
	private String trainName;		// 培训名称
	private String trainResult;		// 培训结果
	private String createOrgId;		// 创建者机构id
	private String updateOrgId;		// 更新者机构id
	private Date beginStartTime;		// 开始 开始时间
	private Date endStartTime;		// 结束 开始时间
	private Date beginEndTime;		// 开始 结束时间
	private Date endEndTime;		// 结束 结束时间

	//前端
	private String treeId;
	private String idNumber;

	private String idNum;
	
	public AffairPartyTrain() {
		super();
	}

	public AffairPartyTrain(String id){
		super(id);
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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
	
	public String getTrainPlace() {
		return trainPlace;
	}

	public void setTrainPlace(String trainPlace) {
		this.trainPlace = trainPlace;
	}
	
	public String getTrainForm() {
		return trainForm;
	}

	public void setTrainForm(String trainForm) {
		this.trainForm = trainForm;
	}
	
	public String getTrainName() {
		return trainName;
	}

	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}
	
	public String getTrainResult() {
		return trainResult;
	}

	public void setTrainResult(String trainResult) {
		this.trainResult = trainResult;
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
	
	public Date getBeginStartTime() {
		return beginStartTime;
	}

	public void setBeginStartTime(Date beginStartTime) {
		this.beginStartTime = beginStartTime;
	}
	
	public Date getEndStartTime() {
		return endStartTime;
	}

	public void setEndStartTime(Date endStartTime) {
		this.endStartTime = endStartTime;
	}
		
	public Date getBeginEndTime() {
		return beginEndTime;
	}

	public void setBeginEndTime(Date beginEndTime) {
		this.beginEndTime = beginEndTime;
	}
	
	public Date getEndEndTime() {
		return endEndTime;
	}

	public void setEndEndTime(Date endEndTime) {
		this.endEndTime = endEndTime;
	}

	public String getTreeId() {
		return treeId;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getIdNum() {
		return idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}
}