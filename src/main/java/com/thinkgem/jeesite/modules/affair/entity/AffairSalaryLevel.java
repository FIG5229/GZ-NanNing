/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 级别工资标准Entity
 * @author cecil.li
 * @version 2020-06-08
 */
public class AffairSalaryLevel extends DataEntity<AffairSalaryLevel> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "年度", type = 0, align = 2, sort = 0)
	private String year;		// 年度
	@ExcelField(title = "级别工资级别档次", type = 0, align = 2, sort = 1)
	private String level;		// 级别工资级别档次
	@ExcelField(title = "级别工资新标准", type = 0, align = 2, sort = 2)
	private Double feeStandard;		// 级别工资新标准
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	
	public AffairSalaryLevel() {
		super();
	}

	public AffairSalaryLevel(String id){
		super(id);
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	public Double getFeeStandard() {
		return feeStandard;
	}

	public void setFeeStandard(Double feeStandard) {
		this.feeStandard = feeStandard;
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