/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 职务工资标准Entity
 * @author cecil.li
 * @version 2020-06-08
 */
public class AffairBasicWage extends DataEntity<AffairBasicWage> {
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "职级", type = 0, align = 2, sort = 1, dictType = "affair_base_wage")
	private String level;		// 职级
	@ExcelField(title = "职务工资", type = 0, align = 2, sort = 2)
	private Double jobSalary;		// 职务工资
	@ExcelField(title = "工作补贴", type = 0, align = 2, sort = 3)
	private Double workAllowance;		// 工作补贴
	@ExcelField(title = "生活补贴", type = 0, align = 2, sort = 4)
	private Double livingAllowance;		// 生活补贴
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
	@ExcelField(title = "年度", type = 0, align = 2, sort = 0)
	private String year;		// 年度
	
	public AffairBasicWage() {
		super();
	}

	public AffairBasicWage(String id){
		super(id);
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	public Double getJobSalary() {
		return jobSalary;
	}

	public void setJobSalary(Double jobSalary) {
		this.jobSalary = jobSalary;
	}
	
	public Double getWorkAllowance() {
		return workAllowance;
	}

	public void setWorkAllowance(Double workAllowance) {
		this.workAllowance = workAllowance;
	}
	
	public Double getLivingAllowance() {
		return livingAllowance;
	}

	public void setLivingAllowance(Double livingAllowance) {
		this.livingAllowance = livingAllowance;
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
	
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
}