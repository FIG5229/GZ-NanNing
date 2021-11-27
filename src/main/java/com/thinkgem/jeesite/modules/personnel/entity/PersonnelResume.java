/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 履历信息集Entity
 * @author cecil.li
 * @version 2019-11-09
 */
public class PersonnelResume extends DataEntity<PersonnelResume> {
	
	private static final long serialVersionUID = 1L;
	private String personnelName;		// 姓名
	@ExcelField(title = "起始日期", type = 0, align = 2, sort = 0)
	private Date startDate;		// 起始日期
	@ExcelField(title = "截止日期", type = 0, align = 2, sort = 1)
	private Date endDate;		// 截止日期
	@ExcelField(title = "所在单位", type = 0, align = 2, sort = 2)
	private String unit;		// 所在单位
	@ExcelField(title = "身份或职务说明", type = 0, align = 2, sort = 3)
	private String identityJobExplain;		// 身份或职务说明
	@ExcelField(title = "履历类别", type = 0, align = 2, sort = 4, dictType="personnel_lltype")
	private String type;		// 履历类别
	@ExcelField(title = "基层工作的标志", type = 0, align = 2, sort = 5, dictType="yes_no")
	private String sign;		// 基层工作的标志
	@ExcelField(title = "履历说明", type = 0, align = 2, sort = 6)
	private String explain;		// 履历说明
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 7)
	private String idNumber;		// 身份证号
	private String unitId;


	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private Date beginDate;
	private Date finishDate;
	
	public PersonnelResume() {
		super();
	}

	public String getPersonnelName() {
		return personnelName;
	}

	public void setPersonnelName(String personnelName) {
		this.personnelName = personnelName;
	}

	public PersonnelResume(String id){
		super(id);
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

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}


	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
}