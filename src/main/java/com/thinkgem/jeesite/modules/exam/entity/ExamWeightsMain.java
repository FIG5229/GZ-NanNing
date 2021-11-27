/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 权重Entity
 * @author cecil.li
 * @version 2020-01-17
 */
public class ExamWeightsMain extends DataEntity<ExamWeightsMain> {
	
	private static final long serialVersionUID = 1L;
	private String ewId;		// 关联id
	private String workName;		// 工作名称
	private Double weights;		// 权重分数
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private String workNameType;    //工作项类别  0 常规  1 重点  3 公共加扣分
	
	public ExamWeightsMain() {
		super();
	}

	public ExamWeightsMain(String id){
		super(id);
	}

	public String getEwId() {
		return ewId;
	}

	public void setEwId(String ewId) {
		this.ewId = ewId;
	}
	
	public String getWorkName() {
		return workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}
	
	public Double getWeights() {
		return weights;
	}

	public void setWeights(Double weights) {
		this.weights = weights;
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

	public String getWorkNameType() {
		return workNameType;
	}

	public void setWorkNameType(String workNameType) {
		this.workNameType = workNameType;
	}
}