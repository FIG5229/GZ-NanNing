/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.entity;

import java.util.List;
import com.google.common.collect.Lists;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 考评人员分配规则管理Entity
 * @author bradley.zhao
 * @version 2020-03-21
 */
public class ExamPersonsAssignRule extends DataEntity<ExamPersonsAssignRule> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String abbreviation;		// 简称
	private String objType;		// 被考评对象类别（1：单位，2：民警）
	private String kpType;		// 考评类别
	private String cycle;		// 考评周期
	private String isEnable;		// 是否启用
	private String unitId;		// 单位
	private String unitName;		// 单位名称
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private List<ExamPersonsAssignRuleChild> examPersonsAssignRuleChildList = Lists.newArrayList();		// 子表列表
	
	public ExamPersonsAssignRule() {
		super();
	}

	public ExamPersonsAssignRule(String id){
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
	
	public String getObjType() {
		return objType;
	}

	public void setObjType(String objType) {
		this.objType = objType;
	}
	
	public String getKpType() {
		return kpType;
	}

	public void setKpType(String kpType) {
		this.kpType = kpType;
	}
	
	public String getCycle() {
		return cycle;
	}

	public void setCycle(String cycle) {
		this.cycle = cycle;
	}
	
	public String getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}
	
	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	
	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
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
	
	public List<ExamPersonsAssignRuleChild> getExamPersonsAssignRuleChildList() {
		return examPersonsAssignRuleChildList;
	}

	public void setExamPersonsAssignRuleChildList(List<ExamPersonsAssignRuleChild> examPersonsAssignRuleChildList) {
		this.examPersonsAssignRuleChildList = examPersonsAssignRuleChildList;
	}
}