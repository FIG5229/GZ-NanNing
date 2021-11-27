/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 履历信息管理Entity
 * @author mason.xv
 * @version 2019-11-06
 */
public class AffairResume extends DataEntity<AffairResume> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 姓名
	private String policeNo;		// 警号
	private String idNumber;		// 身份证号
	private Date startDate;		// 起始日期
	private Date endDate;		// 截止日期
	private String unit;		// 所在单位
	private String identityJobExplain;		// 身份或职务说明
	private String type;		// 履历类别
	private String sign;		// 基层工作的标志（是否）
	private String explain;		// 履历说明
	private String unitId;		// 所在单位id
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	private Date beginStartDate;		// 开始 起始日期
	private Date endStartDate;		// 结束 起始日期
	private Date beginEndDate;		// 开始 截止日期
	private Date endEndDate;		// 结束 截止日期
	
	public AffairResume() {
		super();
	}

	public AffairResume(String id){
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getPoliceNo() {
		return policeNo;
	}

	public void setPoliceNo(String policeNo) {
		this.policeNo = policeNo;
	}
	
	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public String getIdentityJobExplain() {
		return identityJobExplain;
	}

	public void setIdentityJobExplain(String identityJobExplain) {
		this.identityJobExplain = identityJobExplain;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	
	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}
	
	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
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
	
	public Date getBeginStartDate() {
		return beginStartDate;
	}

	public void setBeginStartDate(Date beginStartDate) {
		this.beginStartDate = beginStartDate;
	}
	
	public Date getEndStartDate() {
		return endStartDate;
	}

	public void setEndStartDate(Date endStartDate) {
		this.endStartDate = endStartDate;
	}
		
	public Date getBeginEndDate() {
		return beginEndDate;
	}

	public void setBeginEndDate(Date beginEndDate) {
		this.beginEndDate = beginEndDate;
	}
	
	public Date getEndEndDate() {
		return endEndDate;
	}

	public void setEndEndDate(Date endEndDate) {
		this.endEndDate = endEndDate;
	}
		
}