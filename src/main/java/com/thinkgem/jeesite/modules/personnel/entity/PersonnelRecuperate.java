/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 疗（休）养信息集Entity
 * @author cecil.li
 * @version 2019-11-09
 */
public class PersonnelRecuperate extends DataEntity<PersonnelRecuperate> {
	
	private static final long serialVersionUID = 1L;
	private String personnelName;		// 姓名
	@ExcelField(title = "疗(休)养起始日期", type = 0, align = 2, sort = 0)
	private Date startDate;		// 疗(休)养起始日期
	@ExcelField(title = "疗(休)养结束日期", type = 0, align = 2, sort = 1)
	private Date endDate;		// 疗(休)养结束日期
	@ExcelField(title = "是否带家属", type = 0, align = 2, sort = 2, dictType="yes_no")
	private String isBringMember;		// 是否带家属
	@ExcelField(title = "疗(休)养名称", type = 0, align = 2, sort = 3)
	private String name;		// 疗(休)养名称
	@ExcelField(title = "疗(休)养去处", type = 0, align = 2, sort = 4)
	private String place;		// 疗(休)养去处
	@ExcelField(title = "疗(休)养去处(补充)", type = 0, align = 2, sort = 5)
	private String placeSupplement;		// 疗(休)养去处(补充)
	@ExcelField(title = "疗(休)养备注", type = 0, align = 2, sort = 6)
	private String remark;		// 疗(休)养备注
	@ExcelField(title = "疗(休)养组织单位名称", type = 0, align = 2, sort = 7)
	private String unitName;		// 疗(休)养组织单位名称
	@ExcelField(title = "疗(休)养组织单位代码", type = 0, align = 2, sort = 8)
	private String unitCode;		// 疗(休)养组织单位代码
	@ExcelField(title = "疗(休)养组织单位级别", type = 0, align = 2, sort = 9, dictType="personnel_lyzzl")
	private String unitLevel;		// 疗(休)养组织单位级别
	@ExcelField(title = "疗(休)养组织单位性质类别", type = 0, align = 2, sort = 10, dictType="personnel_lyzzdw")
	private String unitType;		// 疗(休)养组织单位性质类别
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 11)
	private String idNumber;		// 身份证号


	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号

	private Date beginDate;
	private Date finishDate;
	
	public PersonnelRecuperate() {
		super();
	}

	public String getPersonnelName() {
		return personnelName;
	}

	public void setPersonnelName(String personnelName) {
		this.personnelName = personnelName;
	}

	public PersonnelRecuperate(String id){
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
	
	public String getIsBringMember() {
		return isBringMember;
	}

	public void setIsBringMember(String isBringMember) {
		this.isBringMember = isBringMember;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}
	
	public String getPlaceSupplement() {
		return placeSupplement;
	}

	public void setPlaceSupplement(String placeSupplement) {
		this.placeSupplement = placeSupplement;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	
	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	
	public String getUnitLevel() {
		return unitLevel;
	}

	public void setUnitLevel(String unitLevel) {
		this.unitLevel = unitLevel;
	}
	
	public String getUnitType() {
		return unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
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
	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
}