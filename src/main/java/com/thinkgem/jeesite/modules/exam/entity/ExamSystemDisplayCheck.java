/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 系统最终审核公示Entity
 * @author mason.xv
 * @version 2019-12-13
 */
public class ExamSystemDisplayCheck extends DataEntity<ExamSystemDisplayCheck> {
	
	private static final long serialVersionUID = 1L;
	private String unit;		// 单位名称
	private String unitId;		// 单位id
	private String sumCode;		// 总得分
	private String initCode;		// 基础分
	private String lessCode;		// 扣分情况
	private String realCode;		// 实际得分
	private String conversonCode;		// 换算后得分
	private String remark;		// 备注
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	
	public ExamSystemDisplayCheck() {
		super();
	}

	public ExamSystemDisplayCheck(String id){
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
	
	public String getSumCode() {
		return sumCode;
	}

	public void setSumCode(String sumCode) {
		this.sumCode = sumCode;
	}
	
	public String getInitCode() {
		return initCode;
	}

	public void setInitCode(String initCode) {
		this.initCode = initCode;
	}
	
	public String getLessCode() {
		return lessCode;
	}

	public void setLessCode(String lessCode) {
		this.lessCode = lessCode;
	}
	
	public String getRealCode() {
		return realCode;
	}

	public void setRealCode(String realCode) {
		this.realCode = realCode;
	}
	
	public String getConversonCode() {
		return conversonCode;
	}

	public void setConversonCode(String conversonCode) {
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