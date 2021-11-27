/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 自动考评考评标准关联Entity
 * @author kevin.jia
 * @version 2021-01-20
 */
public class ExamAutoStandardRelevance extends DataEntity<ExamAutoStandardRelevance> {
	
	private static final long serialVersionUID = 1L;
	private String item;		// 考评事项
	private String evealType;		// 考评类别
	private String period;		// 考评周期(1月2年)
	private String assess;		// 考核部门
	private String assessId;		// 考核部门id
	private String chuId;		// 所属公安处id
	private String chu;		// 所属公安处
	private String model;		// 使用模板
	private String modelId;		// 模板id
	private String option;		// 选择项
	private String optionId;		// 选择项id
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	/**
	 * 新加字段
	 */
	private String newModel;	//新的使用模板
	private String newModelId;	//新的使用模板id
	private String newOption;	//新的选择项
	private String newOptionId;	//新的选择项id

	private String userId; //数据过滤使用

	public ExamAutoStandardRelevance() {
		super();
	}

	public ExamAutoStandardRelevance(String id){
		super(id);
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}
	
	public String getEvealType() {
		return evealType;
	}

	public void setEvealType(String evealType) {
		this.evealType = evealType;
	}
	
	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}
	
	public String getAssess() {
		return assess;
	}

	public void setAssess(String assess) {
		this.assess = assess;
	}
	
	public String getAssessId() {
		return assessId;
	}

	public void setAssessId(String assessId) {
		this.assessId = assessId;
	}
	
	public String getChuId() {
		return chuId;
	}

	public void setChuId(String chuId) {
		this.chuId = chuId;
	}
	
	public String getChu() {
		return chu;
	}

	public void setChu(String chu) {
		this.chu = chu;
	}
	
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
	
	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	
	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}
	
	public String getOptionId() {
		return optionId;
	}

	public void setOptionId(String optionId) {
		this.optionId = optionId;
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

	public String getNewModel() {
		return newModel;
	}

	public void setNewModel(String newModel) {
		this.newModel = newModel;
	}

	public String getNewModelId() {
		return newModelId;
	}

	public void setNewModelId(String newModelId) {
		this.newModelId = newModelId;
	}

	public String getNewOption() {
		return newOption;
	}

	public void setNewOption(String newOption) {
		this.newOption = newOption;
	}

	public String getNewOptionId() {
		return newOptionId;
	}

	public void setNewOptionId(String newOptionId) {
		this.newOptionId = newOptionId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}