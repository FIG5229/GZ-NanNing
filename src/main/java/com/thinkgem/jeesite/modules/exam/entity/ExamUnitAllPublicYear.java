/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 单位年度考评结果Entity
 * @author bradley.zhao
 * @version 2020-02-19
 */
public class ExamUnitAllPublicYear extends DataEntity<ExamUnitAllPublicYear> {
	
	private static final long serialVersionUID = 1L;

	private Double weight;		// 各业务工作所占权重分
	private String unitName;		// 单位名称
	private String unitId;		// 单位id
	private String workName;		// 各业务工作
	private Double hundred;		// 业务100分制得分
	private Double zsqzhScore;		// 业务折算权重后得分
	private Double publicScore;		// 公共加、扣分合计
	private String analysis;		// 各业务加扣分情况及公共加扣分归类分析
	private String workflowId;		// 考评流程ID
	private Integer valueType;		// 加减分标识（1：加分  -1：减分）
	
	public ExamUnitAllPublicYear() {
		super();
	}

	public ExamUnitAllPublicYear(String id){
		super(id);
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getWorkName() {
		return workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}

	public Double getHundred() {
		return hundred;
	}

	public void setHundred(Double hundred) {
		this.hundred = hundred;
	}

	public Double getZsqzhScore() {
		return zsqzhScore;
	}

	public void setZsqzhScore(Double zsqzhScore) {
		this.zsqzhScore = zsqzhScore;
	}

	public Double getPublicScore() {
		return publicScore;
	}

	public void setPublicScore(Double publicScore) {
		this.publicScore = publicScore;
	}

	public String getAnalysis() {
		return analysis;
	}

	public void setAnalysis(String analysis) {
		this.analysis = analysis;
	}

	public String getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}

	public Integer getValueType() {
		return valueType;
	}

	public void setValueType(Integer valueType) {
		this.valueType = valueType;
	}
}