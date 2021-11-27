/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 专业技术工作获奖信息集Entity
 * @author cecil.li
 * @version 2019-11-11
 */
public class PersonnelAward extends DataEntity<PersonnelAward> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "获奖日期", type = 0, align = 2, sort = 0)
	private Date date;		// 获奖日期
	private String personnelName;		// 姓名
	@ExcelField(title = "获奖项目名称", type = 0, align = 2, sort = 1)
	private String projectName;		// 获奖项目名称
	@ExcelField(title = "获奖名称", type = 0, align = 2, sort = 2)
	private String awardName;		// 获奖名称
	@ExcelField(title = "授奖单位", type = 0, align = 2, sort = 3)
	private String grantUnit;		// 授奖单位
	@ExcelField(title = "授奖单位级别", type = 0, align = 2, sort = 4)
	private String grantUnitLevel;		// 授奖单位级别
	@ExcelField(title = "获奖顺序", type = 0, align = 2, sort = 5)
	private String awardSort;		// 获奖顺序
	@ExcelField(title = "获奖金额", type = 0, align = 2, sort = 6)
	private Double money;		// 获奖金额
	@ExcelField(title = "所获奖励等材料的图片资料", type = 0, align = 2, sort = 7)
	private String material;		// 所获奖励等材料的图片资料
	@ExcelField(title = "获奖备注", type = 0, align = 2, sort = 8)
	private String remark;		// 获奖备注
	@ExcelField(title = "身份证号", type = 0, align = 2, sort = 9)
	private String idNumber;		// 身份证号

	private String grantUnitId;		// 获奖单位id
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号


	private Date startDate;
	private Date endDate;
	private Double minMoney;
	private Double maxMoney;
	
	public PersonnelAward() {
		super();
	}

	public String getPersonnelName() {
		return personnelName;
	}

	public void setPersonnelName(String personnelName) {
		this.personnelName = personnelName;
	}

	public PersonnelAward(String id){
		super(id);
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getAwardName() {
		return awardName;
	}

	public void setAwardName(String awardName) {
		this.awardName = awardName;
	}
	
	public String getGrantUnit() {
		return grantUnit;
	}

	public void setGrantUnit(String grantUnit) {
		this.grantUnit = grantUnit;
	}
	
	public String getGrantUnitLevel() {
		return grantUnitLevel;
	}

	public void setGrantUnitLevel(String grantUnitLevel) {
		this.grantUnitLevel = grantUnitLevel;
	}
	
	public String getAwardSort() {
		return awardSort;
	}

	public void setAwardSort(String awardSort) {
		this.awardSort = awardSort;
	}
	
	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}
	
	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
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
	
	public String getGrantUnitId() {
		return grantUnitId;
	}

	public void setGrantUnitId(String grantUnitId) {
		this.grantUnitId = grantUnitId;
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

	public Double getMinMoney() {
		return minMoney;
	}

	public void setMinMoney(Double minMoney) {
		this.minMoney = minMoney;
	}

	public Double getMaxMoney() {
		return maxMoney;
	}

	public void setMaxMoney(Double maxMoney) {
		this.maxMoney = maxMoney;
	}
}