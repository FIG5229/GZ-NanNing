/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.Date;
import java.util.List;

/**
 * 考评流程定义Entity
 * @author eav.liu
 * @version 2019-12-09
 */
public class ExamWorkflowDefine extends DataEntity<ExamWorkflowDefine> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 流程名称
	private String examType;		// 考评类别
	private String examCycle;		// 考评周期
	private String examObjectType;		// 被考评对象类别
	private String isUse;		// 是否启用
	private String templatesName;		// 对应考评标准模板
	private String templatesIds;		// 考评模板id
	private String flowType;		// 考评流程类型
	private String createOrgId;		// 创建者机构
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构
	private String updateIdNo;		// 更新者身份证号

	private Date createStartDate;
	private Date createEndDate;
	/*考评流程环节名称*/
	private List<String> segments;
	private List<Integer> sorts;

	/*对应考评标准模板*/
    String[] templatesIdsArr;
	
	public ExamWorkflowDefine() {
		super();
	}

	public ExamWorkflowDefine(String id){
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getExamType() {
		return examType;
	}

	public void setExamType(String examType) {
		this.examType = examType;
	}
	
	public String getExamCycle() {
		return examCycle;
	}

	public void setExamCycle(String examCycle) {
		this.examCycle = examCycle;
	}
	
	public String getExamObjectType() {
		return examObjectType;
	}

	public void setExamObjectType(String examObjectType) {
		this.examObjectType = examObjectType;
	}
	
	public String getIsUse() {
		return isUse;
	}

	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}
	
	public String getTemplatesName() {
		return templatesName;
	}

	public void setTemplatesName(String templatesName) {
		this.templatesName = templatesName;
	}
	
	public String getTemplatesIds() {
		return templatesIds;
	}

	public void setTemplatesIds(String templatesIds) {
		this.templatesIds = templatesIds;
	}
	
	public String getFlowType() {
		return flowType;
	}

	public void setFlowType(String flowType) {
		this.flowType = flowType;
	}

	public Date getCreateStartDate() {
		return createStartDate;
	}

	public void setCreateStartDate(Date createStartDate) {
		this.createStartDate = createStartDate;
	}

	public Date getCreateEndDate() {
		return createEndDate;
	}

	public void setCreateEndDate(Date createEndDate) {
		this.createEndDate = createEndDate;
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

	public List<String> getSegments() {
		return segments;
	}

	public void setSegments(List<String> segments) {
		this.segments = segments;
	}

	public List<Integer> getSorts() {
		return sorts;
	}

	public void setSorts(List<Integer> sorts) {
		this.sorts = sorts;
	}
    public String[] getTemplatesIdsArr() {
        return templatesIdsArr;
    }

    public void setTemplatesIdsArr(String[] templatesIdsArr) {
        this.templatesIdsArr = templatesIdsArr;
    }
}