/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 系统公示Entity
 * @author mason.xv
 * @version 2019-12-13
 */
public class ExamSystemDisplay extends DataEntity<ExamSystemDisplay> {
	
	private static final long serialVersionUID = 1L;
	private String unit;		// 单位名称
	private String unitId;		// 单位id
	private Double sumCode;		// 总得分
	private Double initCode;		// 基础分
	private Double lessCode;		// 扣分情况
	private Double realCode;		// 实际得分
	private Double conversonCode;		// 换算后得分
	private String remark;		// 备注
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	
	public ExamSystemDisplay() {
		super();
	}

	public ExamSystemDisplay(String id){
		super(id);
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

	public Double getSumCode() {
		return sumCode;
	}

	public void setSumCode(Double sumCode) {
		this.sumCode = sumCode;
	}

	public Double getInitCode() {
		return initCode;
	}

	public void setInitCode(Double initCode) {
		this.initCode = initCode;
	}

	public Double getLessCode() {
		return lessCode;
	}

	public void setLessCode(Double lessCode) {
		this.lessCode = lessCode;
	}

	public Double getRealCode() {
		return realCode;
	}

	public void setRealCode(Double realCode) {
		this.realCode = realCode;
	}

	public Double getConversonCode() {
		return conversonCode;
	}

	public void setConversonCode(Double conversonCode) {
		this.conversonCode = conversonCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
	
}