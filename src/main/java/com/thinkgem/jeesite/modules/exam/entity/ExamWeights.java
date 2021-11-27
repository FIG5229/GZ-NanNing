/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.entity;

import java.util.List;
import com.google.common.collect.Lists;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 权重Entity
 * @author cecil.li
 * @version 2020-01-17
 */
public class ExamWeights extends DataEntity<ExamWeights> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String department;		// 单位
	private String departmentId;		// 单位id
	private String kpType;		    // 考评类别
	private String kpChu;		    // 考评处
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private List<ExamWeightsMain> examWeightsMainList = Lists.newArrayList();		// 子表列表
	
	public ExamWeights() {
		super();
	}

	public ExamWeights(String id){
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
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
	
	public List<ExamWeightsMain> getExamWeightsMainList() {
		return examWeightsMainList;
	}

	public void setExamWeightsMainList(List<ExamWeightsMain> examWeightsMainList) {
		this.examWeightsMainList = examWeightsMainList;
	}

	public String getKpType() {
		return kpType;
	}

	public void setKpType(String kpType) {
		this.kpType = kpType;
	}

	public String getKpChu() {
		return kpChu;
	}

	public void setKpChu(String kpChu) {
		this.kpChu = kpChu;
	}
}